<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>SYLMS</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>
<style type="text/css">
ul{
list-style: none;
}
</style>

<script type="text/javascript">
function goBack(){
	location.href="${pageContext.request.contextPath}/lecture/main.do";
}

function content_write(){
	location.href="${pageContext.request.contextPath}/lecture/content_write.do?subjectNo=${subjectNo}";
}
</script>
</head>

<body>

<main>
	<section>
		<div class="container-fluid">
			<div class="row">
				<div class="col-1"></div>
				<div class="col-10">
					<div class="row">
					<div class="col-auto bg-dark bg-gradient rounded" style="min-height: 100vh">
						<!-- 왼쪽 사이드바 자리 -->
						<jsp:include page="/WEB-INF/views/layout/brief_sidebar.jsp" />
					</div>
					<div class="col">
						<jsp:include page="/WEB-INF/views/layout/header2.jsp" />
						<jsp:include page="/WEB-INF/views/layout/classroom_header.jsp" />
						<jsp:include page="/WEB-INF/views/layout/lecture_index.jsp" />
					
					<div class="card mb-3">
					  <div class="card-header fw-bold fs-6 bg-navy bg-gradient text-white ps-4 p-2" id="thisweek_lec">
					   <h5 class="d-inline"> <i class="fas fa-pen fa-lg bg-navy"></i>&nbsp;이번주 강의</h5>
					  </div>
					  <div class="card-body">
					  <c:forEach var="dto" items="${thisweekList}" varStatus="status">
					    	<div class="ps-3 pe-3">
					  		<div class="card mb-1">
						  		<div class="card-body">
						  		<div class="row">
									<div class="col-1 text-center">
										<c:if test="${dto.part == '동영상강의'}">
										<i class="fas fa-video fa-3x"></i>
										</c:if>
										<c:if test="${dto.part == '강의자료'}">
										<i class="fas fa-file fa-3x"></i>
										</c:if>
									</div>
						    		<div class="col-11 ms-auto">
						    			<h5 class="card-title fw-bold"><a href="${pageContext.request.contextPath}/lecture/content.do?subjectNo=${subjectNo}&bbsNum=${dto.bbsNum}">${dto.week}주차 ${dto.part} - ${dto.title}</a></h5>
						    			<span class="text-muted">시작일: ${dto.start_date} | 종료일: ${dto.end_date}</span>
						    		</div>
					    		</div>
					    		</div>
						    </div>
						    </div>
					    </c:forEach>
					    <c:if test="${empty thisweekList}"> 
					    <br>
					    <h4 class="text-center"> 강의가 없습니다.</h4>
					    <br>
					    </c:if>
					    </div>
					</div>

					
					
					<div class="card mb-3">
					  <div class="card-header fw-bold fs-6 bg-navy bg-gradient text-white ps-4 p-2" id="all_lecture">
					    <h5 class="d-inline"><i class="fas fa-calendar fa-lg"></i>&nbsp;주차 별 학습 활동</h5>
					</div>
					  <div class="card-body">
					    <div class="resultLayout">
					    <c:if test="${empty lectureList}">
					   	<p class="fs-5 fw-bold text-center"> 강의가 없습니다.</p>
					    </c:if>
					    
					    <c:forEach var="dto" items="${lectureList}" varStatus="status">
					    	<div class="ps-3 pe-3">
					  		<div class="card mb-1">
						  		<div class="card-body">
						  		<div class="row">
									<div class="col-1 text-center">
										<c:if test="${dto.part == '동영상강의'}">
										<i class="fas fa-video fa-3x"></i>
										</c:if>
										<c:if test="${dto.part == '강의자료'}">
										<i class="fas fa-file fa-3x"></i>
										</c:if>
									</div>
						    		<div class="col-10 d-flex align-items-center">
						    			<div class="row">
						    			<div class="row">
						    			<h5 class="card-title fw-bold"><a href="${pageContext.request.contextPath}/lecture/content.do?subjectNo=${subjectNo}&bbsNum=${dto.bbsNum}">${dto.week}주차 ${dto.part} - ${dto.title}</a></h5>
						    			</div>
						    			<div class="row">
						    			<span class="text-muted">시작일: ${dto.start_date} | 종료일: ${dto.end_date}</span>
						    			</div>
						    			</div>
						    		</div>
						    		<div class="col-1 ms-auto dropdown d-inline text-end pe-4">
						    		<c:if test="${fn:length(sessionScope.member.userId) != 8}">
						    			<a id="update_menu" data-bs-toggle="dropdown" aria-expanded="false">
						    			<i class="fas fa-ellipsis-vertical text-muted fa-lg"></i></a>
						    			<ul class="dropdown-menu" aria-labelledby="update_menu">
					    					<li><a href="${pageContext.request.contextPath}/lecture/content_update.do?subjectNo=${subjectNo}&bbsNum=${dto.bbsNum}" class="dropdown-item">수정하기</a></li>
						    				<li><a onclick="if(confirm(' ${dto.week}주차 ${dto.part} - ${dto.title} 을(를) 삭제하시겠습니까?')){location.href='${pageContext.request.contextPath}/lecture/content_delete.do?subjectNo=${subjectNo}&bbsNum=${dto.bbsNum}'}" class="dropdown-item">삭제하기</a></li>
						    			</ul>
						    		</c:if>
						    		</div>
					    		</div>
					    		</div>
						    </div>
						    </div>
					    </c:forEach>
					    </div>
					    <br>
					    <div class="d-block text-end">
					    <c:if test="${fn:length(sessionScope.member.userId) != 8}">
							  <button type="button" class="btn btn-primary" onclick="content_write();">글쓰기</button>
						</c:if>
							  <button type="button" class="btn btn-outline-dark" onclick="goBack();">뒤로가기</button>
						  </div>
					  </div>
					</div>
					</div>
				</div>
			</div>

				<div class="col-1"></div>
			</div>
		</div>
				
				<!-- 본문 끝 -->
	</section>
</main>
<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>
</html>

