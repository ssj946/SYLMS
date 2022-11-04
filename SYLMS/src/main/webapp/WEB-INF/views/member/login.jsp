﻿<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>spring</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp" />

<style type="text/css">
.body-container {
	max-width: 800px;
}

#style {
	background-color: #FFFFFF;
}
.notice{
min-height: 100px;
position: relative;
padding-left: 10%;
padding-right: 10%;
}


.notice::before{
content:"";
opacity:0.07;
position: absolute;

background-image: url('${pageContext.request.contextPath}/resources/images/s_logo.png');
background-size: contain;
background-repeat: no-repeat;
background-position: center;	
  top: 0px;
        left: 0px;
        right: 0px;
        bottom: 0px;
  
}
.notice td {
	position:relative;
	
}

.login_image{
width: 100%;
min-height: 100%;
background-image: url('${pageContext.request.contextPath}/resources/images/university_login.jpg');
background-repeat: no-repeat;
background-size: contain;

}

</style>

<script type="text/javascript">
function sendLogin() {
    const f = document.loginForm;
	let str;
	
	str = f.userId.value;
    if(!str) {
        f.userId.focus();
        return;
    }

    str = f.userPwd.value;
    if(!str) {
        f.userPwd.focus();
        return;
    }

    f.action = "${pageContext.request.contextPath}/member/login_ok.do";
    f.submit();
}
</script>
</head>
<body>
	<main>
		<div class="container-fluid fixed-top">
			<div class="d-flex">
				<div class="row">
					<div class="col-9 ">
					<div class="login_image"></div>
					</div>

					<div class="col-3">
						<div class="d-flex justify-content-center" id="style">
							<form name="loginForm" action="" method="post" class="row g-3">
							<div>
							<img alt="" src="">
							</div>
							<!-- 여기는 대학교 로고 넣는자리  -->
							<div>
							<h3 class="text-center">
								<i class="bi bi-lock"></i> 통합관리 시스템 로그인
							</h3>
							</div>
							<div>
							<h6 style="font-size: small; text-align: center;">초기 아이디와
								비밀번호는 학번과 생년월일입니다.</h6>
							</div>
							<div>
								<input type="text" name="userId" class="form-control"
									placeholder="아이디를 입력해주세요"
									style="width: 90%; margin: 0 auto; background-image:">
							</div>
							<div >
								<input type="password" name="userPwd" class="form-control"
									placeholder="패스워드를 입력해주세요" style="width: 90%; margin: 0 auto;">
							</div>
							<div style="padding-left: 10%">
							<input class="form-check-input" type="checkbox"
								id="rememberMe"> <label class="form-check-label"
								for="rememberMe"> 아이디 저장</label>
							</div>
							<div class="text-center">
								<button type="button" class="btn btn-primary opacity-75 w-75 d-inline"
									onclick="sendLogin();" >
									&nbsp;Login&nbsp;<i class="bi bi-check2"></i>
								</button>
							</div>
							<div >
								<p class="text-center mb-0 mt-2">
									<a href="#" class="text-decoration-none me-2">아이디 찾기</a> <a
										href="#" class="text-decoration-none me-2">패스워드 찾기</a>
								</p>
							</div>

							<div >
								<p class="form-control-plaintext text-center text-primary">${message}</p>
							</div>
							</form>
						</div>
						<br>
						<br>
						<div class="text-start ">
							<h5 class="d-inline ps-4">
								<i class="fa-solid fa-graduation-cap"></i>공지사항
							</h5>
						<hr class="m-3">
						<div class="notice">
							<table class="table-borderless  table" >
								<tr><td class="w-75">대학 디폴트</td><td>2001-10-01</td></tr>
								
								<tr><td>대학 디폴트</td><td>2001-10-01</td></tr>
								<tr><td>대학 디폴트</td><td>2001-10-01</td></tr>
								<tr><td>대학 디폴트</td><td>2001-10-01</td></tr>
							</table>
						</div>
						</div>
						</div>
					</div>
				</div>
				</div>
	</main>

	<footer>
		<jsp:include page="/WEB-INF/views/layout/footer.jsp" />
	</footer>

	<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />

</body>
</html>