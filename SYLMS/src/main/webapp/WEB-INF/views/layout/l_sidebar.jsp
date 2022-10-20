<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces ="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style type="text/css">
ul{
list-style: none;
}
</style>
<!-- 왼쪽 사이드바 -->
	<div class="card mt-3 pt-4 ps-2 pe-2">
		<img src="${pageContext.request.contextPath}/resources/images/syuniv_logo.png" class="card-img-top">
		<div class="card-body text-center">	
		</div>
	</div>
		
	<ul class="nav navbar-nav flex-column mt-3">
		<li class="nav-item">
			<a href="${pageContext.request.contextPath}/" class="nav-link text-white p-3 mb-2 current"> <i class="fas fa-home text-white fa-lg mr-3"></i>&nbsp;대쉬 보드</a>
		</li>
		<li class="nav-item dropdown">
			<button class="nav-link text-white p-3 mb-2 current navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#side_mypage" aria-controls="side_mypage" aria-expanded="false" aria-label="Toggle navigation">
			 <i class="fas fa-address-card text-white fa-lg mr-3"></i>&nbsp;마이 페이지
			</button>
			<div class="collapse navbar-collapse" id="side_mypage" >
			<ul>
				<li><a href="#" class="nav-link text-white p-3 mb-2 current">개인정보수정</a></li>
				<li><a href="#" class="nav-link text-white p-3 mb-2 current">올린파일함</a></li>
				<li><a href="#" class="nav-link text-white p-3 mb-2 current">수강과목</a></li>
				<li><a href="#" class="nav-link text-white p-3 mb-2 current">조교신청</a></li>
			</ul>
			</div>
		</li>
		<li class="nav-item">
			<a class="nav-link text-white p-3 mb-2 current" href="${pageContext.request.contextPath}/lecture/main.do"><i class="fas fa-chalkboard text-white fa-lg mr-3"></i>&nbsp;강의실</a>
		</li>
		<li class="nav-item">
			<a class="nav-link text-white p-3 mb-2 current" href="#">
			 <i class="fas fa-poo text-white fa-lg mr-3"></i>&nbsp;커뮤니티
			 </a>
		</li>
	</ul>
<!-- 왼쪽 사이드바 끝 -->