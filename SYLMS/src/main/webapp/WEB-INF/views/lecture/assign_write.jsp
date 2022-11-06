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
	$(".save_btn").click(function(){
		const f = document.assignment;
		f.action = "${pageContext.request.contextPath}/lecture/assignment_write.do";
		f.method = "POST";
		f.submit();
	});
});

</script>
</head>

<body>

<main>
	<section>
		<div class="container-fluid">
			<div class="row">
				<div class="col-1"></div>
				<div class="col-10">
					<div class="card p-2">
					<div class="row ps-3 pe-1">
					<div class="col-auto bg-dark bg-gradient rounded" style="min-height: 100vh">
						<!-- 왼쪽 사이드바 자리 -->
						<jsp:include page="/WEB-INF/views/layout/brief_sidebar.jsp" />
					</div>
					<div class="col">
						<jsp:include page="/WEB-INF/views/layout/header2.jsp" />
						<jsp:include page="/WEB-INF/views/layout/classroom_header.jsp" />
						<jsp:include page="/WEB-INF/views/layout/lecture_index.jsp" />
						
						<div class="card mb-3">
						<form name="assignment" >
						<input type="hidden" name="subjectNo" value="${subjectNo}">
							<div class="card-header rounded fw-bold fs-6 bg-navy bg-gradient text-center text-white p-2">
							   <h4 class="d-inline"> <i class="fas fa-pen-ruler fa-lg bg-navy"></i>&nbsp;</h4>
							</div>
							<div class="card-body p-4">
							<div class= "mb-3 row d-flex">
								<label for="week" class="col-2 col-form-label text-center fw-bold fs-4">등록 일자</label>
								<div class="col-4 ">
									<input type="date" class="form-control h-100 mb-2" name="s_date" >
								</div>
								<label for="week" class="col-2 col-form-label text-center fw-bold fs-4">종료 일자</label>
								<div class="col-4 ">
									<input type="date" class="form-control h-100 mb-2" name="e_date">
								</div>
							</div>
							<hr>
							<div class="mb-3 row">
								<label for="title" class="col-2 col-form-label text-center fw-bold fs-4">과제 제목</label>
								<div class="col-10 d-flex align-content-center">
								<input class="h-100 form-control mb-2" name="title" placeholder="제목을 입력해주세요.">
								</div>
							</div>
						  	<hr>
							<textarea name="content" class="form-control" placeholder="내용을 입력해주세요." rows="20"></textarea>
							<div class="d-flex justify-content-end p-2">
						  	<div>
							  <button type="button" class="btn btn-outline-primary save_btn me-2">저장</button>
							  </div>
							  <div>
							  <button type="button" class="btn btn-outline-dark" onclick="goBack();">뒤로가기</button>
							  </div>
						  </div>
							</div>
							</form>
						</div>
						
					<!-- 본문 -->
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

