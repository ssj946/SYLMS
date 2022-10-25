<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces ="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- 강의실 사이드바 -->		
	<ul class="nav navbar-nav flex-column mt-3 text-white">
		<li class="nav-item">
			<a href="${pageContext.request.contextPath}/lecture/main.do" class="nav-link  p-3 mb-2 current"> <i class="fas fa-home  fa-lg mr-3"></i>&nbsp;강의실 홈</a>
		</li>
		<li class="nav-item dropdown">
			<button class="nav-link  p-3 mb-2 current navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#lec_info" aria-controls="lec_info" aria-expanded="false" aria-label="Toggle navigation">
			 <i class="fas fa-address-card  fa-lg mr-3"></i>&nbsp;강의정보
			</button>
				<div class="collapse navbar-collapse" id="lec_info" >
				<ul>
					<li class="nav-item">
						<a class="nav-link  p-3 mb-2 current" href="${pageContext.request.contextPath}/lecture/syllabus.do">
						 <i class="fas fa-map  fa-lg mr-3"></i>&nbsp;수업계획서
						 </a>
					</li>	
				</ul>
				</div>
		</li>
		
		<li class="nav-item">
		<a class="nav-link  p-3 mb-2 current" href="${pageContext.request.contextPath}/notice/notice.do" ><i class="fas fa-microphone  fa-lg mr-3"></i>&nbsp;공지사항</a>
		</li>
		
		<li class="nav-item dropdown">
			<button class="nav-link  p-3 mb-2 current navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#lec_attend" aria-controls="lec_attend" aria-expanded="false" aria-label="Toggle navigation">
			 <i class="fas fa-list  fa-lg mr-3"></i>&nbsp;성적/출석관리
			</button>
				<div class="collapse navbar-collapse" id="lec_attend" >
				<ul>
					<li class="nav-item">
						<a class="nav-link  p-3 mb-2 current" href="${pageContext.request.contextPath}/lecture/attend.do">
						 <i class="fas fa-hand  fa-lg mr-3"></i>&nbsp;출석조회
						 </a>
					</li>
					
					<li class="nav-item">
						<a class="nav-link  p-3 mb-2 current" href="#">
						 <i class="fas fa-list-check  fa-lg mr-3"></i>&nbsp;출석입력
						 </a>
					</li>		
				</ul>
				</div>
		</li>
		
		<li class="nav-item dropdown">
			<button class="nav-link  p-3 mb-2 current navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#lec_assign" aria-controls="lec_assign" aria-expanded="false" aria-label="Toggle navigation">
			 <i class="fas fa-list fa-lg mr-3"></i>&nbsp;학습활동
			</button>
				<div class="collapse navbar-collapse" id="lec_assign" >
				<ul>
					<li class="nav-item">
						<a class="nav-link  p-3 mb-2 current" href="${pageContext.request.contextPath}/lecture/assignment.do">
						 <i class="fas fa-pen-ruler  fa-lg mr-3"></i>&nbsp;과제게시판
						 </a>
					</li>
					
					<li class="nav-item">
						<a class="nav-link  p-3 mb-2 current" href="${pageContext.request.contextPath}/lecture/exam.do">
						 <i class="fas fa-fire  fa-lg mr-3"></i>&nbsp;시험성적
						 </a>
					</li>		
				</ul>
				</div>
		</li>
		
		<li class="nav-item dropdown">
			<button class="nav-link  p-3 mb-2 current navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#lec_community" aria-controls="lec_community" aria-expanded="false" aria-label="Toggle navigation">
			 <i class="fas fa-list  fa-lg mr-3"></i>&nbsp;커뮤니티
			</button>
				<div class="collapse navbar-collapse" id="lec_community" >
				<ul>
					<li class="nav-item">
						<a class="nav-link  p-3 mb-2 current" href="${pageContext.request.contextPath}/lecture/qna.do">
						 <i class="fas fa-clipboard-question  fa-lg mr-3"></i>&nbsp;질의응답
						 </a>
					</li>
					
					<li class="nav-item">
						<a class="nav-link  p-3 mb-2 current" href="${pageContext.request.contextPath}/lecture/general.do">
						 <i class="fas fa-poo  fa-lg mr-3"></i>&nbsp;자유게시판
						 </a>
					</li>
					
					<li class="nav-item">
						<a class="nav-link  p-3 mb-2 current" href="${pageContext.request.contextPath}/lecture/debate.do">
						 <i class="fas fa-comments  fa-lg mr-3"></i>&nbsp;토론게시판
						 </a>
					</li>		
				</ul>
				</div>
		</li>
	</ul>

<!-- 왼쪽 사이드바 끝 -->