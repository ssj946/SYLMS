<%@ page contentType="text/html; charset=UTF-8" %>
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
* { padding: 0; margin: 0; }
*, *::after, *::before { box-sizing: border-box; }

body {
	font-family:"Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
	font-size: 14px;
	color: #222;
}

a { color: #222; text-decoration: none; cursor: pointer; }
a:active, a:hover { color: #f28011; text-decoration: underline; }

/* form-control */
.btn {
	color: #333;
	border: 1px solid #999;
	background-color: #fff;
	padding: 5px 10px;
	border-radius: 4px;
	font-weight: 500;
	cursor:pointer;
	font-size: 14px;
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	vertical-align: baseline;
}
.btn:active, .btn:focus, .btn:hover {
	background-color: #f8f9fa;
	color:#333;
}
.btn[disabled], fieldset[disabled] .btn {
	pointer-events: none;
	cursor: default;
	opacity: .65;
}

.form-control {
	border: 1px solid #999; border-radius: 4px; background-color: #fff;
	padding: 5px 5px; 
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	vertical-align: baseline;
}
.form-control[readonly] { background-color:#f8f9fa; }

textarea.form-control { height: 170px; resize : none; }

.form-select {
	border: 1px solid #999; border-radius: 4px; background-color: #fff;
	padding: 4px 5px; 
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	vertical-align: baseline;
}
.form-select[readonly] { background-color:#f8f9fa; }

textarea:focus, input:focus { outline: none; }
input[type=checkbox], input[type=radio] { vertical-align: middle; }

/* table */
.table { width: 100%; border-spacing: 0; border-collapse: collapse; }
.table th, .table td { padding-top: 10px; padding-bottom: 10px; }

.table-border thead > tr { border-top: 2px solid #212529; border-bottom: 1px solid #ced4da; }
.table-border tfoot > tr { border-bottom: 1px solid #ced4da; }
.td-border td { border: 1px solid #ced4da; }

/* container */
.container { width: 400px; margin: 30px auto; }
.title { width:100%; font-size: 16px; font-weight: bold; padding: 13px 0; }

.form-table th, .form-table td { padding: 5px 0; }
.form-table select { width:130px; height:120px; }
.form-table button { display:block; width:80px; }
.form-table textarea { height:60px; width: 98%; }

.left { text-align: left; padding-left: 5px; }
.center { text-align: center; }
.right { text-align: right; padding-right: 5px; }
</style>

<script type="text/javascript">

function check() {
    const f = document.noteForm;
	let str;
	
    str = f.content.value.trim();
    if(!str || str === "<p><br></p>") {
        alert("내용을 입력하세요. ");
        f.content.focus();
        return false;
    }

}

// 선택된 option을 좌 또는 우로 이동
function itemMove(pos) {
	const f = document.noteForm;
	let source, target;
	
	if(pos === "left") { // right -> left
		source = f.itemRight;
		target = f.itemLeft;
	} else { // left -> right
		source = f.itemLeft;
		target = f.itemRight;
	}
	
	let len = source.length;
	for(let i=0; i<len; i++) {
		if( source.options[i].selected ) { // 선택된 항목만
			target[target.length] = 
				new Option(source.options[i].text, source.options[i].value);
			source[i] = null; // 삭제
			i--;
			len--;
		}
	}
}

// 모든 option을 좌 또는 우로 이동
function itemAllMove(pos) {
	const f = document.noteForm;
	let source, target;
	
	if(pos === "left") { // right -> left
		source = f.itemRight;
		target = f.itemLeft;
	} else { // left -> right
		source = f.itemLeft;
		target = f.itemRight;
	}
	
	let len = source.length;
	for(let i=0; i<len; i++) {
		target[target.length] = 
			new Option(source.options[0].text, source.options[0].value);
		source[0] = null; // 삭제
	}
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
					<div class="col-xl-8 col-lg-7 col-md-7">
						<div class="row ms-3 me-3 pt-3 mt-3 mb-5 gap-3">
							
								<div class="container">

									<div class="title">
										<h3>
											<span>|</span> 쪽지 보내기
										</h3>
									</div>

									<form name="noteForm" method="post">
										<table class="table form-table">
											<tr>
												<td width="150"><span>보내는 목록</span></td>
												<td width="100">&nbsp;</td>
												<td width="150"><span>받는사람</span></td>
											</tr>

											<tr>
												<td class="left">
													<select name="itemLeft"
													multiple="multiple" class="form-select"
													style="width: 130px; height: 150px;">	
													
													<c:forEach var="vo" items="${listMember}">
														<option value="${vo.id}">${vo.name}(${vo.id})</option>
													</c:forEach>
																	
													</select>
												</td>
												<td class="center">
													<button type="button" class="btn"
														onclick="itemMove('right');"
														style="display: block; width: 80px;">&gt;</button>
													<button type="button" class="btn"
														onclick="itemAllMove('right');"
														style="display: block; width: 80px;">&gt;&gt;</button>
													<button type="button" class="btn"
														onclick="itemMove('left');"
														style="display: block; width: 80px;">&lt;</button>
													<button type="button" class="btn"
														onclick="itemAllMove('left');"
														style="display: block; width: 80px;">&lt;&lt;</button>
												</td>
												<td class="left"><select name="itemRight"
													multiple="multiple" class="form-select"
													style="width: 130px; height: 150px;">
												</select></td>
											</tr>
											<tr>
												<td colspan="3"><span>메시지</span></td>
											</tr>
											<tr>
												<td colspan="3" class="left">
												<textarea name="msg" class="form-control" style="height: 80px; width: 98%;">${vo.content}</textarea>
												</td>
											</tr>
										</table>
										<table class="table">
											<tr>
												<td class="right">
													<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/message/send_ok.do';">
														쪽지보내기</button>
														<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/message/receive.do';">
														쪽지함 확인하기</button>
												</td>
											</tr>
										</table>
									</form>

								</div>
							</div>
						</div>
					</div>
				</div>
			
		</section>

	</main>

	<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>
</html>