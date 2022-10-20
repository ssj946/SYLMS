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
</style>
</head>

<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"/>
</header>
	
<main>
<section>
	<div class="container-fluid">
		<div class="row">&nbsp;</div>
		<div class="row">
			<div class="col-lg-1 sidebar">
			<!-- brief 사이드바 자리 -->
			<jsp:include page="/WEB-INF/views/layout/brief_sidebar.jsp"/>
			</div>
			<div class="col-lg-11 ms-auto">
			<div class="row">
			<!-- classroom header 자리 -->
			<jsp:include page="/WEB-INF/views/layout/classroom_header.jsp"/>
			</div>
				<div class="row">
				<!-- 강의 사이드바 자리 -->
				<div class="col-lg-3 sidebar">
				<jsp:include page="/WEB-INF/views/layout/lecture_sidebar.jsp"/>
				</div>
				<!-- 본문 -->
				<div class="col-lg-9 ms-auto gap-3">
					<div class="card">
						<div class="card-header">
						  진도현황
						</div>
						<div class="card-body m-auto">
						<ul class="list-group list-group-horizontal">
							<li><a class="dropdown-item" href="#">1주차</a></li>
							<li><a class="dropdown-item" href="#">2주차</a></li>
							<li><a class="dropdown-item" href="#">3주차</a></li>
							<li><a class="dropdown-item" href="#">4주차</a></li>
							<li><a class="dropdown-item" href="#">5주차</a></li>
							<li><a class="dropdown-item" href="#">6주차</a></li>
							<li><a class="dropdown-item" href="#">7주차</a></li>
							<li><a class="dropdown-item" href="#">8주차</a></li>
							<li><a class="dropdown-item" href="#">9주차</a></li>
							<li><a class="dropdown-item" href="#">10주차</a></li>
							<li><a class="dropdown-item" href="#">11주차</a></li>
							<li><a class="dropdown-item" href="#">12주차</a></li>
							<li><a class="dropdown-item" href="#">13주차</a></li>
							<li><a class="dropdown-item" href="#">14주차</a></li>
							<li><a class="dropdown-item" href="#">15주차</a></li>
							<li><a class="dropdown-item" href="#">16주차</a></li>
						</ul>
						</div>
					</div>
					<div class="card">
						<div class="row">
						<div class="card-header">
						  
						<div class="dropdown col ms-auto">
						주차 별 학습활동
						<button class="btn btn-secondary dropdown-toggle" type="button" id="weekly_btn" data-bs-toggle="dropdown" aria-expanded="false">
						  전체
						</button>
							<ul class="dropdown-menu" aria-labelledby="weekly_btn">
							<li><a class="dropdown-item" href="#">1주차</a></li>
							<li><a class="dropdown-item" href="#">2주차</a></li>
							<li><a class="dropdown-item" href="#">3주차</a></li>
							<li><a class="dropdown-item" href="#">4주차</a></li>
							<li><a class="dropdown-item" href="#">5주차</a></li>
							<li><a class="dropdown-item" href="#">6주차</a></li>
							<li><a class="dropdown-item" href="#">7주차</a></li>
							<li><a class="dropdown-item" href="#">8주차</a></li>
							<li><a class="dropdown-item" href="#">9주차</a></li>
							<li><a class="dropdown-item" href="#">10주차</a></li>
							<li><a class="dropdown-item" href="#">11주차</a></li>
							<li><a class="dropdown-item" href="#">12주차</a></li>
							<li><a class="dropdown-item" href="#">13주차</a></li>
							<li><a class="dropdown-item" href="#">14주차</a></li>
							<li><a class="dropdown-item" href="#">15주차</a></li>
							<li><a class="dropdown-item" href="#">16주차</a></li>
							</ul>
							</div>
						</div>
						</div>
						 <div class="card-body">
						    
						</div>
					</div>
				</div>
				</div>
				<!-- 본문 끝 -->
				</div>
			</div>
			
				
		</div>
		
	
	</section>
</main>
<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>