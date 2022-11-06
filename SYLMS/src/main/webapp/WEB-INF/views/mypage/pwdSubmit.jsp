﻿<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>SYLMS</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp" />
<style type="text/css">
</style>
<script type="text/javascript">
function sendOk() {
	const f = document.pwdForm;

	let str = f.userPwd.value;
	if(!str) {
		f.userPwd.focus();
		return;
	}

	f.action = "${pageContext.request.contextPath}/mypage/pwd_ok.do";
	f.submit();
}

</script>
</head>

<body>

<main>
	<section>
		<div class="container-fluid">
			<div class="row">
				<div class="col-1"></div>
				<div class="col-10">
					<div class="card p-2">
					<div class="row ps-3 pe-1">
					<div class="col-2 bg-dark bg-gradient rounded" style="min-height: 100vh">
						<!-- 왼쪽 사이드바 자리 -->
						<jsp:include page="/WEB-INF/views/layout/l_sidebar.jsp" />
					</div>
					<div class="col-10">
						<jsp:include page="/WEB-INF/views/layout/header2.jsp" />
						<div class="row">
							<div class="col-9">
					<!-- 본문 -->
							<div  class="row justify-content-center h-50" >
							<div class="col-6 align-self-center">
								<div class="border mt-5 p-4">
									<form name="pwdForm" method="post">
										<h3 class="text-center fw-bold">패스워드 재확인</h3>
										<div class="d-grid pb-2">
											<p class="form-control-plaintext text-center">정보보호를 위해
												패스워드를 다시 한 번 입력해주세요.</p>
										</div>

										<div class="d-grid pb-2">
											<input type="text" name="userId"
												class="form-control form-control-lg" placeholder="아이디"
												value="${sessionScope.member.userId}" readonly="readonly">
										</div>
										<div class="d-grid pb-2">
											<input type="password" name="userPwd"
												class="form-control form-control-lg" placeholder="패스워드">
										</div>
										<div class="d-grid">
											<button type="button" class="btn btn-lg btn-primary" onclick="sendOk();">
												확인 <i class="bi bi-check2"></i>
											</button>
										</div>
									</form>
								</div>

								<div class="d-grid">
									<p class="form-control-plaintext text-center">${message}</p>
								</div>
							</div>
						</div>
						</div>
							<!-- 오른쪽 사이드바 자리 -->
						<div class="col-3 mt-3"><jsp:include page="/WEB-INF/views/layout/r_sidebar.jsp" /></div>
					</div>
					</div>
					</div>
				</div>
				<div class="col-1"></div>
			</div>
			</div>
		</div>
				
				<!-- 본문 끝 -->
	</section>
</main>
<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>
</html>