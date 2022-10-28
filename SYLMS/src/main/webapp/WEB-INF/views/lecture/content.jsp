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
	let query="subjectNo=${subjectNo}";
	location.href="${pageContext.request.contextPath}/lecture/classroom.do?"+query;	
}

$(function(){
	if(${mode == 'update'||mode == 'write'}){
		$("input").attr("readonly",false);
		$("textarea").attr("readonly",false);
	}
	
});

function content_view(){
	let query="subjectNo=${subjectNo}&bbsNum=${contentDTO.bbsNum}";
	location.href="${pageContext.request.contextPath}/lecture/content_update.do?"+query;
}

function content_write(){
	let query="subjectNo=${subjectNo}&bbsNum=${contentDTO.bbsNum}";
	location.href="${pageContext.request.contextPath}/lecture/content_write.do?"+query;
}

function content_update(){
	let query="subjectNo=${subjectNo}&bbsNum=${contentDTO.bbsNum}";
	location.href="${pageContext.request.contextPath}/lecture/content_update.do?"+query;
}

function content_delete(){
	let query="subjectNo=${subjectNo}&bbsNum=${contentDTO.bbsNum}";
	location.href="${pageContext.request.contextPath}/lecture/content_delete.do?"+query;
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
							<div class="card-header fw-bold fs-6 bg-navy bg-gradient text-center text-white p-2">
							   <h5> <i class="fas fa-tv fa-lg bg-navy"></i>&nbsp;</h5>
							</div>
							<div class="card-body p-4">
							<div class="mb-3 row">
								<label for="title" class="col-2 col-form-label text-center fw-bold fs-4">강의 제목</label>
								<div class="col-10 d-flex align-content-center">
								<input class="h-100 form-control mb-2" id="title" readonly="readonly" value="${contentDTO.title}" placeholder="제목을 입력해주세요.">
								</div>
							</div>
						  	<hr>
							<video src="${pageContext.request.contextPath}/resources/videos/cat.mp4" class="w-100 rounded" controls></video>
							</div>
						</div>
						<div class="card mb-3">
						  <div class="card-header fw-bold fs-6 bg-navy bg-gradient text-center text-white p-2">
						   <h5> <i class="fas fa-file-pen fa-lg bg-navy"></i>&nbsp;</h5>
						  </div>
						  <div class="card-body p-4">
						  <h4 class="mb-3 fw-bold">강의 내용</h4> <textarea class="form-control" readonly="readonly" placeholder="내용을 입력해주세요." rows="10">${contentDTO.content}</textarea>
						  <br>
						 <h4 class="mb-3 fw-bold">강의 자료</h4> <input type="file" class="form-control">
						  <br>
						  <div class="text-end">
						  	<c:if test="${fn:length(sessionScope.member.userId) != 8}">
							  <button type="button" class="btn btn-outline-primary" onclick="content_${mode}();">
								  <c:if test="${mode == 'write'}">저장</c:if>
								  <c:if test="${mode == 'update'|| mode == 'view'}">수정</c:if>
							  </button>
							  <button type="button" class="btn btn-outline-danger" onclick="content_delete();">삭제</button>
							</c:if>
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

