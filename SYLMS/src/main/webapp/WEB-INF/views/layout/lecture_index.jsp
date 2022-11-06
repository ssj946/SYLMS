<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces ="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- 강의실 사이드바 -->		
<div class="card mb-3">
  <div class="card-header rounded fw-bold fs-6 bg-navy bg-gradient text-light ps-4 p-2">
    <h5 class="d-inline"><i class="fas fa-rectangle-list fa-lg bg-navy"></i>&nbsp;강좌 개요</h5>
  </div>
  <div class="card-body m-auto">
	<ul class="nav justify-content-center align-items-center">
	<li class="nav-item">
		<a class="nav-link text-body fs-6  p-3 " href="${pageContext.request.contextPath}/lecture/classroom.do?subjectNo=${subjectNo}" ><i class="fas fa-chalkboard fa-lg mr-3"></i>&nbsp;강의실 홈</a>
		</li>
	  <li class="nav-item">
		<c:if test="${fn:length(sessionScope.member.userId) == 8 }">
		<a class="nav-link text-body fs-6 p-3" href="${pageContext.request.contextPath}/syllabus/list.do?subjectNo=${subjectNo}"> 
		 <i class="fas fa-map fa-lg mr-3"></i>&nbsp;수업계획서
		 </a>
		</c:if>
		<c:if test="${fn:length(sessionScope.member.userId) != 8 }">
		<a class="nav-link text-body fs-6  p-3" href="${pageContext.request.contextPath}/syllabus/listProfessor.do"> 
		 <i class="fas fa-map fa-lg mr-3"></i>&nbsp;수업계획서
		 </a>
		</c:if>						
	  </li>	
		<li class="nav-item">
		<a class="nav-link text-body fs-6  p-3 " href="${pageContext.request.contextPath}/notice/notice.do?subjectNo=${subjectNo}" ><i class="fas fa-microphone fa-lg mr-3"></i>&nbsp;공지사항</a>
		</li>

	  <li class="nav-item dropdown">
	    <a class="nav-link text-body fs-6 dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-expanded="false"><i class="fas fa-list  fa-lg mr-3"></i>&nbsp;성적/출석관리</a>
	    <ul class="dropdown-menu">
	      <li class="nav-item">
			<a class="nav-link text-body  p-3 mb-2 " href="${pageContext.request.contextPath}/lecture/attend.do?subjectNo=${subjectNo}">
		 		<i class="fas fa-hand  fa-lg mr-3"></i>&nbsp;출석조회
		 	</a>
		</li>
		<c:if test="${fn:length(sessionScope.member.userId) !=8}">
		<li class="nav-item">
			<a class="nav-link text-body fs-6  p-3 mb-2 " href="${pageContext.request.contextPath}/lecture/attend_manage.do?subjectNo=${subjectNo}">
			 <i class="fas fa-list-check  fa-lg mr-3"></i>&nbsp;출석관리
			 </a>
		</li>
		</c:if>	
		<li class="nav-item">
		<c:if test="${fn:length(sessionScope.member.userId) == 5 }">
			<a class="nav-link text-body  p-3 mb-2 " href="${pageContext.request.contextPath}/exam/exam.do">
			 <i class="fas fa-fire  fa-lg mr-3"></i>&nbsp;시험성적입력
			 </a>
			 </c:if>
			 <c:if test="${fn:length(sessionScope.member.userId) == 8 }">
			 <a class="nav-link text-body  p-3 mb-2 " href="${pageContext.request.contextPath}/exam/exam_check.do">
			 <i class="fas fa-fire  fa-lg mr-3"></i>&nbsp;시험성적확인
			 </a>
			 </c:if>
		</li>	
		<li class="nav-item">
		<c:if test="${fn:length(sessionScope.member.userId) == 5 }">
			<a class="nav-link text-body  p-3 mb-2 " href="${pageContext.request.contextPath}/exam/update.do">
			 <i class="fas fa-fire  fa-lg mr-3"></i>&nbsp;시험성적수정
			 </a>
			 </c:if>
			 </li>
	    </ul>
	  </li>
	  
	  <li class="nav-item dropdown">
	    <a class="nav-link text-body fs-6 dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-expanded="false"> <i class="fas fa-list fa-lg mr-3"></i>&nbsp;학습활동</a>
	    <ul class="dropdown-menu">
	      <li class="nav-item">
		<a class="nav-link text-body p-3 mb-2 " href="${pageContext.request.contextPath}/lecture/assignment.do?subjectNo=${subjectNo}">
	 	<i class="fas fa-pen-ruler  fa-lg mr-3"></i>&nbsp;과제게시판
	 	</a>
	  </li>
		
		</ul>
	  </li>
	  
	  <li class="nav-item dropdown">
	    <a class="nav-link text-body fs-6 dropdown-toggle" data-bs-toggle="dropdown" href="#" role="button" aria-expanded="false"> <i class="fas fa-list  fa-lg mr-3"></i>&nbsp;커뮤니티</a>
	    <ul class="dropdown-menu">
	   <li class="nav-item">
	<a class="nav-link text-body  p-3 mb-2 " href="${pageContext.request.contextPath}/qna/qna.do?subjectNo=${subjectNo}"> 
	 <i class="fas fa-clipboard-question  fa-lg mr-3"></i>&nbsp;질의응답
	 </a>
</li>

<li class="nav-item">
	<a class="nav-link text-body  p-3 mb-2 " href="${pageContext.request.contextPath}/freebbs/freebbs.do?subjectNo=${subjectNo}">
	 <i class="fas fa-poo  fa-lg mr-3"></i>&nbsp;자유게시판
	 </a>
</li>

<li class="nav-item">
	<a class="nav-link text-body  p-3 mb-2 " href="${pageContext.request.contextPath}/debate/list.do?subjectNo=${subjectNo}">
	 <i class="fa-solid fa-people-line fa-lg mr-3"></i>&nbsp;토론게시판
	 </a>
</li>		
		</ul>
	  </li>
	  
	  
	  
	</ul>
	</div>
  </div>
<!-- 왼쪽 사이드바 끝 -->