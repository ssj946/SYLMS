<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces ="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<!-- 사이드바 -->
			<div class="col-xl-2 col-lg-3 col-md-4 sidebar" >
				<a href="${pageContext.request.contextPath}/" title="로고" class="navbar-brand text-white text-center d-block mx-auto py-3 mb-4 bottom-border"><img src="${pageContext.request.contextPath}/resources/images/syuniv_logo.png" style="height: 75px"></a>
				<div class="bottom-border pb-3 text-center">
				<img src="${pageContext.request.contextPath}/resources/images/loading.gif" alt="" width="50" class="rounded-circle mr-3">&nbsp;
				<a href="#" class="text-white">기계공학과 김철수</a></div>
				<ul class="nav navbar-nav flex-column mt-4">
					<li class="nav-item">
						<a href="${pageContext.request.contextPath}/" class="nav-link text-white p-3 mb-2 current"> <i class="fas fa-home text-white fa-lg mr-3"></i>&nbsp;대쉬 보드</a>
					</li>
					<li class="nav-item">
						<a href="#" class="nav-link text-white p-3 mb-2 current dropdown-toggle" data-toggle="dropdown"> <i class="fas fa-address-card text-white fa-lg mr-3"></i>&nbsp;마이 페이지</a>
						<ul class="dropdown-menu">
							<li><a href="#">submenu1</a></li>
							<li><a href="#">submenu2</a></li>
						</ul>
					</li>
					<li class="nav-item">
						<a href="#" class="nav-link text-white p-3 mb-2 current"> <i class="fas fa-chalkboard text-white fa-lg mr-3"></i>&nbsp;강의실</a>
					</li>
					<li class="nav-item">
						<a href="#" class="nav-link text-white p-3 mb-2 current"> <i class="fas fa-poo text-white fa-lg mr-3"></i>&nbsp;커뮤니티</a>
					</li>
				</ul>
			</div>