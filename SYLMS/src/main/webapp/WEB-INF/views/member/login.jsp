﻿<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>spring</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>

<style type="text/css">
.body-container {
	max-width: 800px;
}

#style{
background-color: #FFFFFF;
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
					<div class="col-9 p-0" >
					<img style="width: 100%" alt="" src="${pageContext.request.contextPath}/resources/images/university_login.jpg">
					
					</div>

					<div class="col-3 p-0">
						<div class="border  p-4" id="style">
							<form name="loginForm" action="" method="post" class="row g-3">
							<img alt="" src=""> <!-- 여기는 대학교 로고 넣는자리  -->
								<h3 class="text-center">
									<i class="bi bi-lock"></i> 통합관리 시스템 로그인
								</h3>
								<hr class="mt-3" style="width: 90%; margin:0 auto;">
							
								<h6 style="font-size: small; text-align: center;">초기 아이디와 비밀번호는 학번과 생년월일입니다.</h6>
								
								<div class="col-12">
				          
									 <input type="text"
										name="userId" class="form-control" placeholder="아이디를 입력해주세요" style="width: 90%; margin:0 auto; background-image: ">
								</div>
								<div class="col-12">
						  <input type="password"
										name="userPwd" class="form-control" placeholder="패스워드를 입력해주세요" style="width: 90%; margin:0 auto;">
								</div>
								<div class="col-12">
									<div class="form-check">
										<input class="form-check-input" type="checkbox"
											id="rememberMe"> <label class="form-check-label"
											for="rememberMe"> 아이디 저장</label>
									</div>
								</div>
								<div class="col-12" style="text-align: center;">
									<button type="button" class="btn btn-primary opacity-75"
										onclick="sendLogin();" style="width: 90%; margin: 0 auto;">
										&nbsp;Login&nbsp;<i class="bi bi-check2"></i>
									</button>
								</div>
							</form>
							<div class="col-12">
								<p class="text-center mb-0 mt-2">
									<a href="#" class="text-decoration-none me-2">아이디 찾기</a> <a
										href="#" class="text-decoration-none me-2">패스워드 찾기</a>
								</p>
							</div>
						</div>
						<div class="d-grid">
							<p class="form-control-plaintext text-center text-primary">${message}</p>
						</div>
						<div>
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