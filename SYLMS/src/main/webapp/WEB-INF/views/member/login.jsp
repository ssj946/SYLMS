﻿<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>		</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp" />

<style type="text/css">

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
height:100%;
background-image: url('${pageContext.request.contextPath}/resources/images/university_login.jpg');
background-repeat: no-repeat;
background-size: cover;
background-position: center;
}
.logo{
width: 100%;
height: 100px;
background-image: url('${pageContext.request.contextPath}/resources/images/s_logo.png');
background-repeat: no-repeat;
background-size: contain;
background-position: center;
margin: 1rem;
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
			<div class="row">
					<div class="p-0 col-9">
					<div class=" login_image">
					</div>
					</div>

					<div class="col-3" style="min-height: 100vh">
						<div class="loginform" style="margin-top: 6rem;">
						<div class="logo">
						</div>
						<!-- 여기는 대학교 로고 넣는자리  -->
						<div>
						<h3 class="text-center">
							<i class="fas fa-lock"></i> 통합관리 시스템 로그인
						</h3>
						</div>
						<div class="m-4">
						<h6 style="font-size: small; text-align: center;">초기 아이디와
							비밀번호는 학번과 생년월일입니다.</h6>
						</div>
						<div class="m-auto w-75">
						<form name="loginForm" action="" method="post">
								<input type="text" name="userId" class="form-control m-2" placeholder="아이디를 입력해주세요">
								<input type="password" name="userPwd" class="form-control m-2" placeholder="패스워드를 입력해주세요">
								<div class="d-flex align-items-center m-2">
									<input class="form-check-input" type="checkbox" id="rememberMe">
									<label class="form-check-label" for="rememberMe">&nbsp;아이디 저장</label>
									</div>
								<button type="button" class="btn btn-primary opacity-75 d-inline w-100 m-2" onclick="sendLogin();">
								&nbsp;Login&nbsp;<i class="bi bi-check2"></i>
								</button>
						</form>
						</div>
						<div >
							<p class="text-center mb-0 mt-2">
								<a href="#" class="text-decoration-none me-2">아이디 찾기</a><span>|</span> <a
									href="#" class="text-decoration-none me-2">&nbsp;	패스워드 찾기</a>
							</p>
						</div>

						<div >
							<p class="form-control-plaintext text-center text-primary">${message}</p>
						</div>
					</div>
					
					</div>
					</div>
				</div>

</main>
</body>
	<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</html>