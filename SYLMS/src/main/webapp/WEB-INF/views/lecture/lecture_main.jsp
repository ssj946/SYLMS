<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"/>
</header>
	
<main>
<section>
	<div class="container-fluid">
		<div class="row" style="line-height: 1.5rem">&nbsp;</div>
		<div class="row">
			<div class="col-lg-1 bg-dark bg-gradient" >
			<!-- brief 사이드바 자리 -->
			<jsp:include page="/WEB-INF/views/layout/brief_sidebar.jsp"/>
			</div>
			<div class="col-lg-11 ms-auto">
			
			<!-- classroom header 자리 -->
			<div class="row">
			<jsp:include page="/WEB-INF/views/layout/classroom_header.jsp"/>
			</div>
			<div class="row">
			<!-- 강의 사이드바 자리 -->
			<div class="col-xl-2 col-md-2 col-lg-2 bg-black bg-gradient" style="box-shadow: none;">
			<jsp:include page="/WEB-INF/views/layout/lecture_sidebar.jsp"/>
			</div>
			
			<!-- 본문 -->
			<div class="col-xl-10 col-md-10 col-lg-10 gap-3 ms-auto">
				<div class="ms-1 me-1 pt-3 mt-3 mb-5">
					<div class="card mb-3">
					  <div class="card-header fw-bold fs-6 bg-navy bg-gradient text-white p-2">
					    <h5><i class="fas fa-rectangle-list fa-lg bg-navy"></i>&nbsp;강좌 개요</h5>
					  </div>
					  <div class="card-body m-auto">
					    <ul class="list-group list-group-horizontal text-center fw-bold">
					    <li><ul>
					    <li class="list-group-item"><a href="#"><i class="fas fa-microphone fa-3x"></i></a></li>
					    <li class="list-group-item">강의공지</li>
					    </ul></li>
					    <li><ul>
					    <li class="list-group-item"><a href="#"><i class="fas fa-clipboard-question fa-3x"></i></a></li>
					    <li class="list-group-item">질의응답</li>
					    </ul></li>
					    
					    <li><ul>
					    <li class="list-group-item"><a href="#"><i class="fas fa-pen fa-3x"></i></a></li>
					    <li class="list-group-item">이번주 강의</li>
					    </ul></li>
					    
					    <li><ul>
					    <li class="list-group-item"><a href="#"><i class="fas fa-calendar fa-3x"></i></a></li>
					    <li class="list-group-item">주차별 학습활동</li>
					    
					    </ul></li>
					    
					    <li><ul>
					    <li class="list-group-item"><a href="#"><i class="fas fa-map fa-3x"></i></a></li>
					    <li class="list-group-item">강의계획서</li>
					    </ul></li>
						</ul>
					  </div>
					</div>
					
					<div class="card mb-3">
					  <div class="card-header fw-bold fs-6 bg-navy bg-gradient text-white p-2">
					   <h5> <i class="fas fa-pen fa-lg bg-navy"></i>&nbsp;이번주 강의</h5>
					  </div>
					  <div class="card-body">
					  <c:forEach var="dto" items="${thisweekList}" varStatus="status">
					    	<div class="ps-3 pe-3">
					  		<div class="card mb-1">
						  		<div class="card-body">
						  		<div class="row">
									<div class="col-1 text-center">
									<i class="bi bi-book fa-3x"></i>
									</div>
						    		<div class="col-11 ms-auto">
						    			<h5 class="card-title fw-bold"><a href="#">${dto.week}&nbsp;${dto.part} - ${dto.title}</a></h5>
						    			<span class="text-muted">시작일: ${dto.reg_date} | 종료일: ${dto.end_date}</span>
						    		</div>
					    		</div>
					    		</div>
						    </div>
						    </div>
					    </c:forEach>
					    <c:if test="${empty thisweekList}"> 
					    <p class="fs-5 fw-bold text-center"> 강의가 없습니다.</p>
					    </c:if>
					    </div>
					</div>

					
					
					<div class="card mb-3">
					  <div class="card-header fw-bold fs-6 bg-navy bg-gradient text-white p-2">
					    <h5><i class="fas fa-calendar fa-lg"></i>&nbsp;주차 별 학습 활동</h5>
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
										<i class="bi bi-book fa-3x"></i>
									</div>
						    		<div class="col-10">
						    			<h5 class="card-title fw-bold"><a href="${pageContext.request.contextPath}/lecture/content.do?subjectNo=${subjectNo}&bbsNum=${dto.bbsNum}">${dto.week}&nbsp;${dto.part} - ${dto.title}</a></h5>
						    			<span class="text-muted">시작일: ${dto.reg_date} | 종료일: ${dto.end_date}</span>
						    		</div>
						    		<div class="col-1 ms-auto dropdown d-inline text-end pe-4">
						    			<a id="update_menu" data-bs-toggle="dropdown" aria-expanded="false">
						    			<i class="fas fa-ellipsis-vertical text-muted fa-lg"></i></a>
						    			<ul class="dropdown-menu" aria-labelledby="update_menu">
					    					<li><a href="${pageContext.request.contextPath}/lecture/content_update.do?subjectNo=${subjectNo}&bbsNum=${dto.bbsNum}" class="dropdown-item">수정하기</a></li>
						    				<li><a onclick="if(confirm(' ${dto.week}&nbsp;${dto.part} - ${dto.title} 을(를) 삭제하시겠습니까?')){location.href='${pageContext.request.contextPath}/lecture/content_delete.do?subjectNo=${subjectNo}&bbsNum=${dto.bbsNum}'}" class="dropdown-item">삭제하기</a></li>
						    			</ul>
						    		</div>
					    		</div>
					    		</div>
						    </div>
						    </div>
					    </c:forEach>
					    </div>
					    <br>
					    <div class="d-block text-end">
							  <button type="button" class="btn btn-primary" onclick="content_write();">글쓰기</button>
							  <button type="button" class="btn btn-outline-dark" onclick="goBack();">뒤로가기</button>
						  </div>
					  </div>
					</div>
					</div>
				</div>
			<!-- 본문 끝 -->
			</div>
			</div>

				
				</div>
			</div>
	
	</section>
</main>


<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>

