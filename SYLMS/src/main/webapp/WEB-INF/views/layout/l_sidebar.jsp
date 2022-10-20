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
<div class="col-xl-2 col-lg-3 col-md-4 sidebar pt-5">
<div class="card pt-1 pb-1">
		<a href="${pageContext.request.contextPath}/" title="로고" class="navbar-brand text-white text-center d-block mx-auto py-3 mb-4 bottom-border"><img src="${pageContext.request.contextPath}/resources/images/syuniv_logo.png" style="height: 75px"></a>
</div>
	
	<ul class="nav navbar-nav flex-column mt-4">
		<li class="nav-item">
			<a href="${pageContext.request.contextPath}/" class="nav-link text-white p-3 mb-2 current"> <i class="fas fa-home text-white fa-lg mr-3"></i>&nbsp;대쉬 보드</a>
		</li>
		<li class="nav-item dropdown">
			<button class="nav-link text-white p-3 mb-2 current navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#side_mypage" aria-controls="side_mypage" aria-expanded="false" aria-label="Toggle navigation">
			 <i class="fas fa-address-card text-white fa-lg mr-3"></i>&nbsp;마이 페이지
			</button>
			<div class="collapse navbar-collapse" id="side_mypage" >
			<ul class="text-none">
				<li><a href="#" class="nav-link text-white p-3 mb-2 current">개인정보수정</a></li>
				<li><a href="#" class="nav-link text-white p-3 mb-2 current">올린파일함</a></li>
				<li><a href="#" class="nav-link text-white p-3 mb-2 current">수강과목</a></li>
				<li><a href="#" class="nav-link text-white p-3 mb-2 current">조교신청</a></li>
			</ul>
			</div>
		</li>
		<li class="nav-item">
			<button class="nav-link text-white p-3 mb-2 current navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#side_lecture" aria-controls="side_lecture" aria-expanded="false" aria-label="Toggle navigation"> 
			<i class="fas fa-chalkboard text-white fa-lg mr-3"></i>&nbsp;강의실
			</button>
			<div class="collapse navbar-collapse" id="side_lecture" >
			<ul class="text-none">
				<li><a href="#" class="nav-link text-white p-3 mb-2 current">과목1</a></li>
				<li><a href="#" class="nav-link text-white p-3 mb-2 current">과목2</a></li>
				<li><a href="#" class="nav-link text-white p-3 mb-2 current">과목3</a></li>
				<li><a href="#" class="nav-link text-white p-3 mb-2 current">과목4</a></li>
				<li><a href="#" class="nav-link text-white p-3 mb-2 current">과목5</a></li>
				<li><a href="#" class="nav-link text-white p-3 mb-2 current">과목6</a></li>
			</ul>
			</div>
		</li>
		<li class="nav-item">
			<a class="nav-link text-white p-3 mb-2 current" href="#">
			 <i class="fas fa-poo text-white fa-lg mr-3"></i>&nbsp;커뮤니티
			 </a>
		</li>
	</ul>
</div>
<!-- 왼쪽 사이드바 끝 -->