<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>SYLMS</title>
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
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
.table-border tbody > tr { border-bottom: 1px solid #ced4da; }
.table-border tfoot > tr { border-bottom: 1px solid #ced4da; }
.td-border td { border: 1px solid #ced4da; }

/* board */
.board { margin: 30px auto; width: 700px; }

.title { width:100%; font-size: 16px; font-weight: bold; padding: 13px 0; }

.table-article { margin-top: 20px; }
.table-article tr > td { padding-left: 5px; padding-right: 5px; }
</style>

<script type="text/javascript">
function deleteBoard() {
	if(confirm('게시글을 삭제 하시겠습니까?')){
		location.href="${pageContext.request.contextPath}/bbs/delete.do?num=${dto.num}&${query}";
	}
}				
</script>

</head>
<body>

	<div class="board">
   <div class="title">
      <h3><i class="fas fa-map  fa-lg mr-3"></i> 수업계획서 </h3>
					
					
					</div>
					<div class="panel panel-info">
						<div class="panel-heading">
							<h3 class="panel-title text-center">
								<strong>수업전 수업계획서는 수정 반영은 포텔에서 해야합니다. </strong>
							</h3>
						</div>
						<div class="panel-body">
							<div class="d-grid">
                            <button type="button" class="btn btn-lg btn-primary" onclick="location.href='${pageContext.request.contextPath}/';">확인 <i class="bi bi-check2"></i> </button>
                        </div>
						</div>
					</div>
					<hr>
					<form class="form-inline" action="">
						<div class="form-group">
							<label>학년도 학기</label>
							<select name="semester" class="form-control">
								<option value="0" selected> -- 전체 -- </option>
								<option value="1">2022년 2학기</option>
				
							</select>
						</div>
						<div class="form-group">
							<label>수업선택</label>
							<select class="form-control">
								<option> -- 전체 -- </option>
								<option> 동의학</option>
								<option> 정의학</option>
							</select>
						</div>
					</form>
					<hr>
			
		<script type="text/javascript">
function itemAdd() {
	const f = document.noteForm;
	const item = f.itemLeft;
	
	item[item.length] = new Option("김자바", "kim"); // text, value
	item[item.length] = new Option("스프링", "spring"); 
	item[item.length] = new Option("서블릿", "servlet"); 
	item[item.length] = new Option("오라클", "oracle"); 
	item[item.length] = new Option("이자바", "lee"); 
	item[item.length] = new Option("홍자바", "hong"); 
	item[item.length] = new Option("나대한", "na"); 
}

// window.onload = () => itemAdd();
window.addEventListener('load', () => itemAdd());

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

function sendOk() {
	const f = document.noteForm;
	
	if( f.itemRight.length === 0 ) {
		alert("받는 사람을 먼저 추가 하세요...");
		f.itemRight.focus();
		return;
	}
	
	if(! f.msg.value.trim() ) {
		alert("메시지를 입력하세요...");
		f.msg.focus();
		return;
	}
	
	// select 요소는 서버로 전송하기 위해서 반드시 항목들이 선택되어 있어야 한다.
	for(let i=0; i < f.itemRight.length; i++) {
		f.itemRight[i].selected = true; // select 항목 선택
	}
	
	f.action = "note_ok.jsp";
	f.submit();
}
</script>

					
			<!-- 본문 -->	
				<div class="board">
	           <div class="title">
	    <h3><i class=></i> 평가항목 </h3>
	</div>

	<table class="table">
		<tr>
			<td width="50%">${dataCount}개(${page}/${total_page}페이지)</td>
			<td align="right">&nbsp;</td>
		</tr>
	</table>
	
	<table class="table table-border table-list">
	<thead class="table-light">
		<thead>
			<tr>
							<th class="subjectName">과목명</th>
							<th class="name">담당교수</th>
							<th class="credit">학점</th>
							<th class="assignmentRate">과제비율</th>
							<th class="middleRate">중간고사비율</th>
							<th class="finalRate">기말고사비율 </th>	
			</tr>
		</thead>
		
		<tbody>
		    <c:forEach var="dto" items="${list }" varStatus="status">
				<tr>
					<td>${dataCount - (page-1) * size - status.index}</td>
								<td class="left">
									${dto.subjectName}
								</td>
								<td>${dto.name}</td>
								<td>${dto.openDate}</td>
								<td>${dto.credit}</td>
								<td>${dto.assignmentRate}</td>
							    <td>${dto.middleRate}</td>
								<td>${dto.finalRate}</td>
				</tr>
				</c:forEach>
		<tbody>
		
															
						</tbody>
					</table>				
				</div>
				
				<div class="page-navigation">
					${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
				
			
			</div>

        <table class="table">
      <tr>
         <td width="50%">
            <button type="button" class="btn" onclick="loaction.href='${pageContext.request.contextPath}/syllabus/list.do?subjectNo=10045?no=${dto.num}&page=${page}';">수정</button>
            <button type="button" class="btn" onclick="deleteBoard();">삭제</button>
         </td>
         <td align="right">
            <button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/syllabus/list.do?subjectNo=10045?no=${query}';">리스트</button>
         </td>
      </tr>
   </table>
</div>
            

</body>
</html>