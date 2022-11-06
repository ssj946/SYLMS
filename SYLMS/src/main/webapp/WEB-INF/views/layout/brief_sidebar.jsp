<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces ="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- brief 사이드바 -->	
	<ul class="nav navbar-nav flex-column mt-3 text-center">
		<li class="nav-item">
			<a href="${pageContext.request.contextPath}/" class="nav-link text-white p-3 mb-2 current"><i class="fas fa-home text-white fa-lg mr-3"></i></a>
		</li>
		<li class="nav-item">
			<a class="nav-link text-white p-3 mb-2 current"><i class="fas fa-address-card text-white fa-lg mr-3"></i></a>
		</li>
		<li class="nav-item">
			<a class="nav-link text-white p-3 mb-2 current" href="${pageContext.request.contextPath}/lecture/main.do"><i class="fas fa-chalkboard text-white fa-lg mr-3"></i></a>
		</li>
		<li class="nav-item">
			<a class="nav-link text-white p-3 mb-2 current" href="${pageContext.request.contextPath}/haksa/list.do">
			 <i class="fa-solid fa-landmark text-white text-white fa-lg mr-3"></i>
			 </a>
		</li>
	</ul>

<!-- brief 사이드바 끝 -->