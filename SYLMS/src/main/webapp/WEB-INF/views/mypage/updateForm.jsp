<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	function updateOk() {
		const f = document.updateForm;

		if(f.mode.value==="profile") {
			$(".pwdTr").show();
			$(".pwdTr2").show();
			
			$("form input[name=email").prop("readonly", false);
			$("form input[name=tel").prop("readonly", false);
			
			$(".btnOk").text("수정완료");
			f.mode.value = "update"
			return;
		}
	 
		
		str = f.pwd.value;
		if(! f.pwd.value.trim() ) { 
			alert("패스워드를 다시 입력 하세요. ");
			f.pwd.focus();
			return;
		}

		if( str !== f.pwd2.value ) {
	        alert("패스워드가 일치하지 않습니다. ");
	        f.pwd.focus();
	        return;
		}
		
		
		
	   if(! f.email.value.trim()){
	       alert("이메일 입력해주세요.");
	        f.email.focus()
	        return;
	        
	  }
	   
	   if(! f.tel.value.trim()){
	       alert("전화번호 입력해주세요.");
	        f.tel.focus()
	        return;
	        
	  }
		   
	  f.action = "${pageContext.request.contextPath}/mypage/update_ok.do";
	  f.submit();

		
	}
	
</script>
</head>

<body>

	<header>
		<jsp:include page="/WEB-INF/views/layout/header.jsp" />
	</header>

	<main>
		<section>
			<div class="container-fluid">

				<div class="row">&nbsp;</div>
				<div class="row">
					<div class="col-xl-2 col-lg-3 col-md-4 bg-dark bg-gradient pt-1">
						<!-- 왼쪽 사이드바 자리 -->
						<jsp:include page="/WEB-INF/views/layout/l_sidebar.jsp" />
					</div>

					<!-- 본문 -->
					<div class="col-xl-10 col-lg-9 col-md-8">
						<div class="body-container">
							<div class="body-title">
								<h3>
									<i class="bi bi-person-square"></i> ${title}
								</h3>
							</div>
							<form name="photoForm">
								<div class="form">
									<div class="img-grid" style="text-align: center;">
										<img class="item img-add"
											style="width: 200px; height: 200px; margin: 10px;"
											src="${pageContext.request.contextPath}/resources/images/profile.png">
									</div>
									<input type="file" name="selectFile" accept="image/*"
										style="display: none;">
								</div>
							</form>
							<form name="updateForm" method="post">

								<table class="table table-border table-form">
									<tr>
										<td>학&nbsp;번</td>
										<td><input type="text" name="userId"
											value="${dto.userId}" id="userId" class="form-control"
											style="width: 50%;" readonly="readonly">
									</tr>

									<tr>
										<td>학&nbsp;과</td>
										<td><input type="text" name="department"
											readonly="readonly" value="${dto.departmentName }"
											class="form-control" style="width: 50%;" readonly="readonly"></td>
									</tr>

									<tr style="display: none;" class="pwdTr">
										<td>패스워드</td>
										<td>
											<p>
												<input type="password" name="pwd" class="form-control"
													style="width: 50%;">
											</p>
											<p class="help-block">패스워드는 5~10자 이내이며, 하나 이상의 숫자나 특수문자가
												포함되어야 합니다.</p>
										</td>

									</tr>
									<tr style="display: none;" class="pwdTr2">
										<td>패스워드 확인</td>
										<td>
											<p>
												<input type="password" name="pwd2" class="form-control"
													style="width: 50%;">
											</p>
											<p class="help-block">패스워드를 한번 더 입력해주세요.</p>
										</td>
									</tr>


									<tr>
										<td>이&nbsp;&nbsp;&nbsp;&nbsp;름 *</td>
										<td><input type="text" name="userName" maxlength="20"
											class="form-control" style="width: 50%;" value="${dto.name }"
											readonly="readonly"></td>
									</tr>

									<tr>
										<td>생년월일 *</td>
										<td><input type="text" name="birth" maxlength="20"
											class="form-control" style="width: 50%;" value="${dto.birth}"
											readonly="readonly"></td>
									</tr>

									<tr>
										<td>이 메 일 *</td>
										<td><input type="text" name="email" maxlength="30"
											class="form-control" style="width: 50%;"
											value="${dto.email }" readonly="readonly"></td>
									</tr>

									<tr>
										<td>전화번호 *</td>
										<td><input type="text" name="tel" class="form-control"
											style="width: 50%;" value="${dto.tel }" readonly="readonly"}></td>
									</tr>

								</table>
								<div style="padding: 10px 0;"></div>
								<table class="table">

									<tr>
										<td align="center"><input type="hidden" name="mode"
											value="${mode}">

											<button type="button" class="btn btnOk" id="btnOk"
												onclick="updateOk();">수정</button>

											<button type="button" class="btn"
												onclick="javascript:location.href='${pageContext.request.contextPath}/';">돌아가기</button>

										</td>
									</tr>

								</table>
							</form>
						</div>

					</div>


					<!-- 본문 끝 -->
				</div>
			</div>
		</section>
	</main>

</body>
</html>