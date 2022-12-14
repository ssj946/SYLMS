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
* {
	padding: 0;
	margin: 0;
}

*, *::after, *::before {
	box-sizing: border-box;
}

body {
	font-family: "Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
	font-size: 14px;
	color: #222;
}

a {
	color: #222;
	text-decoration: none;
	cursor: pointer;
}

a:active, a:hover {
	color: #f28011;
	text-decoration: underline;
}

/* form-control */
.btn {
	color: #333;
	border: 1px solid #999;
	background-color: #fff;
	padding: 5px 10px;
	border-radius: 4px;
	font-weight: 500;
	cursor: pointer;
	font-size: 14px;
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	vertical-align: baseline;
}

.btn:active, .btn:focus, .btn:hover {
	background-color: #f8f9fa;
	color: #333;
}

.btn[disabled], fieldset[disabled] .btn {
	pointer-events: none;
	cursor: default;
	opacity: .65;
}

.form-control {
	border: 1px solid #999;
	border-radius: 4px;
	background-color: #fff;
	padding: 5px 5px;
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	vertical-align: baseline;
}

.form-control[readonly] {
	background-color: #f8f9fa;
}

textarea.form-control {
	height: 170px;
	resize: none;
}

.form-select {
	border: 1px solid #999;
	border-radius: 4px;
	background-color: #fff;
	padding: 4px 5px;
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	vertical-align: baseline;
}

.form-select[readonly] {
	background-color: #f8f9fa;
}

textarea:focus, input:focus {
	outline: none;
}

input[type=checkbox], input[type=radio] {
	vertical-align: middle;
}

/* table */
.table {
	width: 100%;
	border-spacing: 0;
	border-collapse: collapse;
}

.table th, .table td {
	padding-top: 10px;
	padding-bottom: 10px;
}

.table-border thead>tr {
	border-top: 2px solid #212529;
	border-bottom: 1px solid #ced4da;
}

.table-border tfoot>tr {
	border-bottom: 1px solid #ced4da;
}

.td-border td {
	border: 1px solid #ced4da;
}

/* container */
.container {
	width: 400px;
	margin: 30px auto;
}

.title {
	width: 100%;
	font-size: 16px;
	font-weight: bold;
	padding: 13px 0;
}

.form-table th, .form-table td {
	padding: 5px 0;
}

.form-table select {
	width: 130px;
	height: 120px;
}

.form-table button {
	display: block;
	width: 80px;
}

.form-table textarea {
	height: 60px;
	width: 98%;
}

.left {
	text-align: left;
	padding-left: 5px;
}

.center {
	text-align: center;
}

.right {
	text-align: right;
	padding-right: 5px;
}
</style>

<script type="text/javascript">
/*
	function sendOk() {
		const f = document.noteForm;
		
		if ($("#itemRight option").length == 0) {
			alert("받는사람을 먼저 추가하세요...");
			return;
		}
		
		$("#itemRight option:selected").prop("selected", true);
		$("#itemRight option:selected").val();

		f.submit;
		f.action = "${pageContext.request.contextPath}/messege/send_ok.do";
		
	}
*/
	function check() {
		window.location.href = "${pageContext.request.contextPath}/messege/receive.do";
	}
	
	
	$(function() {
		$("#btnRight").click(function() {
			$("#itemLeft option:selected").each(function() {
				$(this).appendTo("#itemRight");
			});
		});

		$("#btnAllRight").click(function() {
			$("#itemLeft option").each(function() {
				$(this).appendTo("#itemRight");
			});
		});

		$("#btnLeft").click(function() {
			$("#itemRight option:selected").each(function() {
				$(this).appendTo("#itemLeft");
			});
		});

		$("#btnAllLeft").click(function() {
			$("#itemRight option").each(function() {
				$(this).appendTo("#itemLeft");
			});
		});
	});
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
					<div class="col-2 bg-dark bg-gradient rounded" >
						<!-- 왼쪽 사이드바 자리 -->
						<jsp:include page="/WEB-INF/views/layout/l_sidebar.jsp" />
					</div>
					<div class="col-10">
						<jsp:include page="/WEB-INF/views/layout/header2.jsp" />
						<div class="row">
							<div class="col-9">
					<!-- 본문 -->
							<div class="card mt-3">

								<div class="card-header bg-navy bg-gradient text-white">
									<h5 class="d-inline">
										<span>|</span> 쪽지 보내기
									</h5>
								</div>

								<form class="card-body" name="noteForm" method="post" action="${pageContext.request.contextPath}/messege/send_ok.do">
									<table class="table form-table">
										<tr>
											<td width="150"><span>보내는 목록</span></td>
											<td width="100">&nbsp;</td>
											<td width="150"><span>받는사람</span></td>
										</tr>

										<tr>
											<td class="left">
		        								<select name="itemLeft" id="itemLeft" multiple="multiple" class="form-select">
		        									<c:forEach var="vo" items="${listMember}">
														<option value="${vo.id}">${vo.name}(${vo.id})</option>
													</c:forEach>
		        								</select>													
											</td>
											<td class="center">
												<button type="button" class="btn" id="btnRight">&gt;</button>
												<button type="button" class="btn" id="btnAllRight">&gt;&gt;</button>
												<button type="button" class="btn" id="btnLeft">&lt;</button>
												<button type="button" class="btn" id="btnAllLeft">&lt;&lt;</button>
											</td>
											<td class="left">
		        								<select name="itemRight" id="itemRight" multiple="multiple" class="form-select"></select>
										    </td>
										</tr>
										<tr>
											<td colspan="3"><span>메시지</span></td>
										</tr>
										<tr>
											<td colspan="3" class="left">
											<textarea name="msg" class="form-control"></textarea>
											</td>
										</tr>
									</table>
									<table class="table">
										<tr>
											<td class="right">
												<button type="submit" class="btn">쪽지보내기</button>
												<button type="button" class="btn" onclick="check()">쪽지함 확인하기</button>
											</td>
										</tr>
									</table>
								</form>

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