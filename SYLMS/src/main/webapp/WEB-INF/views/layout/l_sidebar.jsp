<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces ="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- 왼쪽 사이드바 -->
	<div class="card mt-3 p-4">
		<img src="${pageContext.request.contextPath}/resources/images/syuniv_logo.png" class="card-img-top">
	</div>
		
	<ul class="nav navbar-nav flex-column mt-3" >
		<li class="nav-item">
			<a href="${pageContext.request.contextPath}/" class="nav-link text-white p-3 mb-2 current"> <i class="fas fa-home text-white fa-lg mr-3"></i>&nbsp;대쉬 보드</a>
		</li>
		<li class="nav-item dropdown">
			<button class="nav-link text-white p-3 mb-2 current navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#side_mypage" aria-controls="side_mypage" aria-expanded="false" aria-label="Toggle navigation">
			 <i class="fas fa-address-card text-white fa-lg mr-3"></i>&nbsp;MY PAGE
			</button>
			<div class="collapse navbar-collapse" id="side_mypage" >
			<ul style="list-style: none;">
				<li><a href="${pageContext.request.contextPath}/mypage/pwd.do" class="nav-link text-white p-3 mb-2 current">개인정보 수정</a></li>
				<c:if test="${fn:length(sessionScope.member.userId) == 8}">
				<li><a href="${pageContext.request.contextPath}/file/file.do" class="nav-link text-white p-3 mb-2 current">파일관리</a></li>
				</c:if>
				
				<li><a href="${pageContext.request.contextPath}/schedule/schedule.do" class="nav-link text-white p-3 mb-2 current">강의목록</a></li>
				
				<c:if test="${fn:length(sessionScope.member.userId) == 8}">
				<li><a href="${pageContext.request.contextPath}/mypage/assistant.do" class="nav-link text-white p-3 mb-2 current">조교신청</a></li>
				</c:if>
				<c:if test="${fn:length(sessionScope.member.userId) != 8}">
				<li><a href="${pageContext.request.contextPath}/mypage/assistant.do" class="nav-link text-white p-3 mb-2 current">조교관리</a></li>
				</c:if>
			</ul>
			</div>
		</li>
		<li class="nav-item">
			<a class="nav-link text-white p-3 mb-2 current" href="${pageContext.request.contextPath}/lecture/main.do"><i class="fas fa-chalkboard text-white fa-lg mr-3"></i>&nbsp;강의실</a>
		</li>
		<li class="nav-item">
			<a class="nav-link text-white p-3 mb-2 current" href="${pageContext.request.contextPath}/messege/receive.do"><i class="fa-regular fa-envelope fa-lg"></i>&nbsp;메시지</a>
		</li>
		<li class="nav-item">
			<a class="nav-link text-white p-3 mb-2 current"href="${pageContext.request.contextPath}/haksa/list.do">
			 <i class="fas fa-poo text-white fa-lg mr-3"></i>&nbsp;커뮤니티
			 </a>
		</li>
	</ul>
<!-- 왼쪽 사이드바 끝 -->