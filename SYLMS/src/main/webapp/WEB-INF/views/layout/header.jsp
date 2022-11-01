<%@page import="com.member.SessionInfo"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
	$(document).ready(function() {
		$('[data-toggle=popover]').popover({
			html : true,
			content: function() {
		        return $('#popover_content_wrapper').html();
		      }
		});
	})
	
	$(function() {
		url = "${pageContext.request.contextPath}/messege/count.do";
		$.post(url, null, function(data) {
			let count = data.count;
			$(".message-count").html(data.count);
		}, "json");
	});

	$(function() {
		url = "${pageContext.request.contextPath}/header/count.do";
		$.post(url, null, function(data) {
			let count = data.count;
			$(".alert-count").html(data.count);
			if(count>=1){
			$(".alert-list").html("알람이 있습니다");
			}
		}, "json");
	});
</script>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
	<div class="container-fluid">
		<button class="navbar-toggler ms-auto" type="button"
			data-bs-toggle="collapse" data-bs-target="#topbar"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse" id="topbar"></div>
		<div class="collapse navbar-collapse">
			<div class="col-xl-2 col-lg-3 col-md-4"></div>
			<div class="col-xl-7 col-lg-6 col-md-4"></div>
			<div class="col-xl-3 col-lg-3 col-md-4">
				<ul class="navbar-nav ms-0 mb-2 mb-lg-0">

					<c:if test="${empty sessionScope.member}">
						<li class="nav-item ms-auto"><a class="nav-link"
							href="javascript:dialogLogin();" title="로그인"><i
								class="fas fa-lock text-muted fa-lg text-warning"></i></a></li>
					</c:if>


					<c:if test="${sessionScope.member.userId == 'admin'}">
						<li class="nav-item dropdown"><a
							class="nav-link dropdown-toggle" href="#" title="관리"
							id="administrator" role="button" data-bs-toggle="dropdown"
							aria-expanded="false"><i class="fas fa-gear text-muted fa-lg"></i>&nbsp;</a>
							<ul class="dropdown-menu" aria-labelledby="administrator">
								<li><a class="dropdown-item" href="#">관리자님, 환영합니다.</a></li>
								<li><hr class="dropdown-divider"></li>
								<li><a class="dropdown-item" href="#">강의실 관리</a></li>
								<li><a class="dropdown-item" href="#">커뮤니티 관리</a></li>
							</ul>&nbsp;&nbsp;</li>
					</c:if>
					<c:if test="${not empty sessionScope.member}">
						<li><span class="badge rounded-pill text-bg-dark"> ${ sessionScope.member.userName}
						</span></li>
						<li class="nav-item"><a class="nav-link" href="#" id="mypage"
							title="마이페이지"> &nbsp;<i class="fas fa-user text-muted fa-lg">&nbsp;</i></a>
						</li>





						<li class="nav-item">
						<a role="button" class="position-relative nav-link" data-toggle="popover"
							data-bs-placement="bottom" data-bs-title="알림">
								&nbsp;&nbsp;<i class="fas fa-bell text-muted fa-lg"></i> 
							<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger alert-count">
							</span>
							
							<div id="popover_content_wrapper" style="display: none">
    						<div class= "alert-list"></div>
							</div>
						</a>
						</li>






						<li class="nav-item"><a class="nav-link position-relative"
							href="${pageContext.request.contextPath}/messege/send.do"
							title="메시지">&nbsp;&nbsp;&nbsp;&nbsp;<i
								class="fas fa-comments text-muted fa-lg"></i> <span
								class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger message-count"></span>
						</a>&nbsp;&nbsp;</li>

						<li class="nav-item ms-auto"><a class="nav-link"
							href="${pageContext.request.contextPath}/member/logout.do"
							title="로그아웃">&nbsp;<i
								class="fas fa-sign-out-alt text-danger fa-lg"></i>&nbsp;
						</a></li>
					</c:if>
				</ul>
			</div>
		</div>
	</div>
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
				</div>

			</div>
		</div>
	</div>
</div>
