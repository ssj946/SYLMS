<%@page import="com.member.SessionInfo"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script type="text/javascript">
$(function () {	
	function ajaxFun(url, method, query, dataType, fn) {
		$.ajax({
			type : method,
			url : url,
			data : query,
			dataType : dataType,
			success : function(data) {
				fn(data);
			},
			beforeSend : function(jqXHR) {
				jqXHR.setRequestHeader("AJAX", true);
			},
			error : function(jqXHR) {
				console.log(jqXHR.responseText);
			}
		});
	}
	
	$(".btnBell").click(function(){
		let url = "${pageContext.request.contextPath}/header/notice.do";
		let query = null;
		
		const fn = function(data){
			let out="";
			for(let item of data.notiList){
				let noticeCode = item.noticeCode;
				let content = item.content;
				let reg_date = item.reg_date;
				let url = item.url;
				
				out += noticeCode+"<br>";
				out += "<a href='"+url+"'>"+content+"</a>"+"<br>";
				out += reg_date+"<hr>";
			}
			$("#alertModal .modal-body").html(out);
			$("#alertModal").modal("show");
		};
		
		ajaxFun(url, "get", query, "json", fn);
	});
	
});

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
			if (count >= 1) {
				$(".alert-list").html("알람이 있습니다");
			}
		}, "json");
	});
</script>
<div class="card p-2 bg-primary bg-gradient">
<div class="d-flex justify-content-end align-items-center">
	<c:if test="${empty sessionScope.member}">
		<div  class="p-2"><a href="javascript:dialogLogin();" title="로그인"><i class="fas fa-lock text-light fa-lg text-warning"></i></a></div>
	</c:if>


	<c:if test="${sessionScope.member.userId == 'admin'}">
		<div class=" p-2 dropdown">
		<a class=" dropdown-toggle" href="#" title="관리" id="administrator" role="button" data-bs-toggle="dropdown" aria-expanded="false">
		<i class="fas fa-gear text-light fa-lg"></i>&nbsp;
		</a>
			<ul class="dropdown-menu" aria-labelledby="administrator">
				<li><a class="dropdown-item" href="#">관리자님, 환영합니다.</a></li>
				<li><hr class="dropdown-divider"></li>
				<li><a class="dropdown-item" href="#">강의실 관리</a></li>
				<li><a class="dropdown-item" href="#">커뮤니티 관리</a></li>
			</ul>&nbsp;&nbsp;</div>
	</c:if>
	<c:if test="${not empty sessionScope.member}">
		<div>
		<span class="badge rounded-pill text-bg-dark p-2">${sessionScope.member.userName}</span>
		</div>
		<div>
		<a class="p-2" href="#" id="mypage" title="마이페이지">&nbsp;&nbsp;<i class="fas fa-user text-light fa-lg"></i></a>
		</div>





		<div class="p-2" >
			<a id="openModalBtn" href="#" class="position-relative  btn-modal"
			data-target="#staticBackdrop"> &nbsp;&nbsp; 
			<i class="fas fa-bell text-light fa-lg btnBell"></i> 
			<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger alert-count">
			</span>
		</a> 
		</div>

		<div class="p-2"><a class=" position-relative"
			href="${pageContext.request.contextPath}/messege/send.do"
			title="메시지">&nbsp;&nbsp;&nbsp;&nbsp;<i
				class="fas fa-comments text-light fa-lg"></i> <span
				class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger message-count"></span>
		</a>&nbsp;&nbsp;</div>

		<div class="p-2"><a 
			href="${pageContext.request.contextPath}/member/logout.do"
			title="로그아웃">&nbsp;<i
				class="fas fa-sign-out-alt text-danger fa-lg"></i>&nbsp;
		</a></div>
	</c:if>

</div>
</div>
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


<div class="modal fade" id="alertModal" tabindex="-1"
	data-bs-backdrop="static" data-bs-keyboard="false"
	aria-labelledby="alertModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="loginViewerModalLabel">알림</h5>
				<button type="button" class="btn-close" data-bs-dismiss="modal"
					aria-label="Close"></button>
			</div>
			<div class="modal-body">
			</div>
		</div>
	</div>
</div>
