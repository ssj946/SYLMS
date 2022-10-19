<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<nav class="navbar navbar-expand-md navbar-light ">
	<button class="navbar-toggler nav-button mb-2 bg-light ms-auto" type="button" data-bs-toggle="collapse" data-bs-target="#sidebar" aria-controls="sidebar" aria-expanded="false" aria-label="Toggle navigation">
		<i class="fas fa-list"></i>
	</button>
	<div class="collapse  navbar-collapse" id="sidebar">
	<div class="container-fluid">
			<div class="row">
			<!-- 사이드바 -->
			<div class="col-xl-2 col-lg-3 col-md-4 sidebar fixed-top" >
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
			</div>
			</div>
			</div>
			<!-- 사이드바 끝 -->
			<!-- 상단 바 -->
			<div class="collapse navbar-collapse" id="topbar">
			<div class="col-xl-10 col-lg-9 col-md-8 ms-auto bg-dark py-1 fixed-top">
				<div class="row align-items-center">
					<div class="col-xl-3 col-lg-3 col-md-3">
						<h4 class="text-light text-uppercase mb-0">&nbsp;DashBoard</h4>
					</div>
					<div class="col-xl-6 col-lg-5 col-md-4">
						&nbsp;
					</div>
					
					<div class="col-xl-3 col-lg-4 col-md-5">
					<ul class="navbar-nav ms-auto">
						<c:if test="${empty sessionScope.member}">
							<li class="nav-item icon-parent"><a href="${pageContext.request.contextPath}/member/member.do" title="회원가입" class="nav-link">&nbsp;<i class="fas fa-user-plus text-muted fa-lg"></i>&nbsp;</a></li>
							<li class="nav-item ms-auto"><a href="javascript:dialogLogin();" title="로그인" class="nav-link">&nbsp;<i class="fas fa-sign-in-alt text-muted fa-lg"></i>&nbsp;</a></li>
						</c:if>
						
						<c:if test="${not empty sessionScope.member}">
							<c:if test="${sessionScope.member.userId == 'admin'}">
							<li class="nav-item icon-parent"> <a href="#" title="관리자" class="nav-link"><i class="fas fa-gear text-muted fa-lg"></i></a></li>
							</c:if>
							<li class="nav-item icon-parent"><a href="#" title="마이페이지" class="nav-link"><i class="fas fa-user text-muted fa-lg">&nbsp;</i></a></li>
							<li class="nav-item icon-parent"><a href="#" title="메세지" class="nav-link"><i class="fas fa-comments text-muted fa-lg">&nbsp;</i></a>&nbsp;</li>
							<li class="nav-item icon-parent"><a href="#" title="알림" class="nav-link">&nbsp;<i class="fas fa-bell text-muted fa-lg"></i>&nbsp;</a></li>
							<li class="nav-item ms-auto"><a href="${pageContext.request.contextPath}/member/logout.do" title="로그아웃" class="nav-link">&nbsp;<i class="fas fa-sign-out-alt text-danger fa-lg"></i>&nbsp;</a></li>
						</c:if>
					</ul>
					</div>
				</div>
			</div>
			</div>
			<!-- 상단바 끝 -->
</nav>			





<!-- Login Modal -->
<script type="text/javascript">
	function dialogLogin() {
		$("form[name=modelLoginForm] input[name=userId]").val("");
		$("form[name=modelLoginForm] input[name=userPwd]").val("");

		$("#loginModal").modal("show");

		$("form[name=modelLoginForm] input[name=userId]").focus();
	}

	function sendModelLogin() {
		var f = document.modelLoginForm;
		var str;

		str = f.userId.value;
		if (!str) {
			f.userId.focus();
			return;
		}

		str = f.userPwd.value;
		if (!str) {
			f.userPwd.focus();
			return;
		}

		f.action = "${pageContext.request.contextPath}/member/login_ok.do";
		f.submit();
	}
</script>

<div class="modal fade" id="loginModal" tabindex="-1"
	data-bs-backdrop="static" data-bs-keyboard="false"
	aria-labelledby="loginModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="loginViewerModalLabel">Login</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
				<div class="p-3">
					<form name="modelLoginForm" action="" method="post" class="row g-3">
						<div class="mt-0">
							<p class="form-control-plaintext">계정으로 로그인 하세요</p>
						</div>
						<div class="mt-0">
							<input type="text" name="userId" class="form-control"
								placeholder="아이디">
						</div>
						<div>
							<input type="password" name="userPwd" class="form-control"
								placeholder="패스워드">
						</div>
						<div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox"
									id="rememberMeModel"> <label class="form-check-label"
									for="rememberMeModel"> 아이디 저장</label>
							</div>
						</div>
						<div>
							<button type="button" class="btn btn-primary w-100"
								onclick="sendModelLogin();">Login</button>
						</div>
						<div>
							<p class="form-control-plaintext text-center">
								<a href="#" class="text-decoration-none me-2">패스워드를 잊으셨나요 ?</a>
							</p>
						</div>
					</form>
					<hr class="mt-3">
					<div>
						<p class="form-control-plaintext mb-0">
							아직 회원이 아니세요 ? <a
								href="${pageContext.request.contextPath}/member/member.do"
								class="text-decoration-none">회원가입</a>
						</p>
					</div>
				</div>

			</div>
		</div>
	</div>
</div>

