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
form .img-viewer {
	cursor: pointer;
	border: 1px solid #ccc;
	width: 45px;
	height: 45px;
	border-radius: 45px;
	background-image:
		url("${pageContext.request.contextPath}/resources/images/add_photo.png");
	position: relative;
	background-repeat: no-repeat;
	background-size: cover;
}
</style>
<script type="text/javascript">
	function updateOk() {
		const f = document.updateForm;

		if (f.mode.value === "profile") {
			$(".pwdTr").show();
			$(".pwdTr2").show();
			$(".image").show();

			$("form input[name=email").prop("readonly", false);
			$("form input[name=tel").prop("readonly", false);

			$(".btnOk").text("수정완료");
			$(".title").text("학사정보수정")
			f.mode.value = "update"
			return;
		}

		str = f.pwd.value;
		if (!f.pwd.value.trim()) {
			alert("패스워드를 다시 입력 하세요. ");
			f.pwd.focus();
			return;
		}

		if (str !== f.pwd2.value) {
			alert("패스워드가 일치하지 않습니다. ");
			f.pwd.focus();
			return;
		}

		if (!f.email.value.trim()) {
			alert("이메일 입력해주세요.");
			f.email.focus()
			return;

		}

		if (!f.tel.value.trim()) {
			alert("전화번호 입력해주세요.");
			f.tel.focus()
			return;

		}

		f.action = "${pageContext.request.contextPath}/mypage/update_ok.do";
		f.submit();

	}

	$(function() {
		let img = "${dto.fileName}";
		if (img) { // 수정인 경우
			img = "${pageContext.request.contextPath}/uploads/account/" + img;
			$("form .img-viewer").empty();
			$("form .img-viewer").css("background-image", "url(" + img + ")");
		}

		$("form .img-viewer").click(
				function() {
					const f = document.updateForm;
					if (f.mode.value === "update") {
						$("form[name=updateForm] input[name=selectFile]")
								.trigger("click");
					}
				});

		$("form[name=updateForm] input[name=selectFile]")
				.change(
						function() {
							let file = this.files[0];
							if (!file) {
								$("form .img-viewer").empty();
								if (img) {
									img = "${pageContext.request.contextPath}/uploads/photo/"
											+ img;
									$("form .img-viewer").css(
											"background-image",
											"url(" + img + ")");
								} else {
									img = "${pageContext.request.contextPath}/resources/images/add_photo.png";
									$("form .img-viewer").css(
											"background-image",
											"url(" + img + ")");
								}
								return false;
							}

							if (!file.type.match("image.*")) {
								this.focus();
								return false;
							}

							let reader = new FileReader();
							reader.onload = function(e) {
								$("form .img-viewer").empty();
								$("form .img-viewer").css("background-image",
										"url(" + e.target.result + ")");
							}
							reader.readAsDataURL(file);
						});
	});
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
					<div class="col-xl-2 col-lg-3 col-md-4 bg-dark bg-gradient pt-1 style="min-height:100vh""
						style="height: 100vh">

						<!-- 왼쪽 사이드바 자리 -->
						<jsp:include page="/WEB-INF/views/layout/l_sidebar.jsp" />
					</div>

					<!-- 본문 -->
					<div class="col-xl-10 col-lg-9 col-md-8" style="min-height:100vh">
						<div class="card pt-2 pb-2 ps-2 pe-2">
							<h5 class="card-header">
								<i class="bi bi-person-square"></i>${title}</h5>
							<div class="card-body">
								<form name="updateForm" method="post"
									enctype="multipart/form-data">
									<div>
										<div style="margin: auto; width: 150px; height: 150px;"
											class="img-viewer"></div>
										<input type="file" name="selectFile" accept="image/*"
											style="display: none;"> <input name="fileName"
											type="hidden" value="${dto.fileName }">
									</div>
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
												class="form-control" style="width: 50%;"
												value="${dto.name }" readonly="readonly"></td>
										</tr>
										<tr>
											<td>생년월일 *</td>
											<td><input type="text" name="birth" maxlength="20"
												class="form-control" style="width: 50%;"
												value="${dto.birth}" readonly="readonly"></td>
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
												style="width: 50%;" value="${dto.tel }" readonly="readonly"></td>
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
					</div>

					<!-- 본문 끝 -->
					<!-- 오른쪽 사이드바 자리 -->
				</div>
			</div>
		</section>
	</main>
	<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>
</html>

