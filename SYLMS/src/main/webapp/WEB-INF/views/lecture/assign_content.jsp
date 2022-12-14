<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>SYLMS</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>
<style type="text/css">
ul{
list-style: none;
}
</style>

<script type="text/javascript">
function goBack(){
	let query="subjectNo=${subjectNo}";
	location.href="${pageContext.request.contextPath}/lecture/classroom.do?"+query;	
}

$(function(){
	if(${mode == 'update'||mode == 'write'}){
		$("input").attr("readonly",false);
		$("textarea").attr("readonly",false);
	}
	$("#week").val("${contentDTO.week}").prop("selected", true);
	$("#type").val("${contentDTO.part}").prop("selected", true);
	
});

function login() {
	location.href="${pageContext.request.contextPath}/member/login.do";
}

function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type:method,
		url:url,
		data:query,
		dataType:dataType,
		success:function(data) {
			fn(data);
		},
		beforeSend:function(jqXHR) {
			jqXHR.setRequestHeader("AJAX", true);
		},
		error:function(jqXHR) {
			if(jqXHR.status === 403) {
				login();
				return false;
			} else if(jqXHR.status === 400) {
				alert("요청 처리가 실패 했습니다.");
				return false;
			}
			
			console.log(jqXHR.responseText);
		}
	});
}

$(function(){
	$(".save_btn").click(function(){
		const f = document.assignment_submit;
		f.action = "${pageContext.request.contextPath}/lecture/assignment_ok.do";
		f.method = "POST";
		f.submit();
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
					<div class="col-auto bg-dark bg-gradient rounded" style="min-height: 100vh">
						<!-- 왼쪽 사이드바 자리 -->
						<jsp:include page="/WEB-INF/views/layout/brief_sidebar.jsp" />
					</div>
					<div class="col">
						<jsp:include page="/WEB-INF/views/layout/header2.jsp" />
						<jsp:include page="/WEB-INF/views/layout/classroom_header.jsp" />
						<jsp:include page="/WEB-INF/views/layout/lecture_index.jsp" />
						<div class="card mb-3">
							<div class="card-header rounded fw-bold fs-6 bg-navy bg-gradient text-center text-white p-2">
							   <h4 class="d-inline"> <i class="fas fa-pen-ruler fa-lg bg-navy"></i>&nbsp;</h4>
							</div>
							<div class="card-body p-4">
							<div class= "mb-3 row d-flex">
								<label for="week" class="col-2 col-form-label text-center fw-bold fs-4">등록 일자</label>
								<div class="col-4 ">
									<input type="date" class="form-control h-100 mb-2" readonly="readonly" id="s_date" value="${adto.reg_date}">
								</div>
								<label for="week" class="col-2 col-form-label text-center fw-bold fs-4">종료 일자</label>
								<div class="col-4 ">
									<input type="date" class="form-control h-100 mb-2" readonly="readonly" id="e_date" value="${adto.end_date}">
								</div>
							</div>
							<hr>
							<div class="mb-3 row">
								<label for="title" class="col-2 col-form-label text-center fw-bold fs-4">과제 제목</label>
								<div class="col-10 d-flex align-content-center">
								<input class="h-100 form-control mb-2" id="title" readonly="readonly" value="${adto.title}" placeholder="제목을 입력해주세요.">
								</div>
							</div>
						  	<hr>
							<textarea id="content" class="form-control" readonly="readonly" placeholder="내용을 입력해주세요." rows="20">${adto.content}</textarea>
							</div>
						</div>
						<div class="card mb-3">
						  <div class="card-header rounded fw-bold fs-6 bg-navy bg-gradient text-center text-white p-2">
						   <h4 class="d-inline"> <i class="fas fa-file-pen fa-lg bg-navy"></i>&nbsp;</h4>
						  </div>
						  <div class="card-body p-4">
						  <c:if test="${fn:length(sessionScope.member.userId) == 8}">
						  <form name="assignment_submit" enctype="multipart/form-data">
						  <h4 class="mb-3 fw-bold">제출 내용</h4> 
						  <textarea name="content" class="form-control" placeholder="내용을 입력해주세요." rows="10">${asdto.content}</textarea>
						  <br>
						 <h4 class="mb-3 fw-bold">제출 파일</h4> 

						 <c:forEach var="file" items="${filelist}">
						 <div class="form-control mb-2">
						 <a href="${pageContext.request.contextPath}/lecture/download.do?fileNo=${file.fileNo}&asNo=${adto.asNo}">${file.originalfilename}</a>
						 <label class="d-inline float-end form-label"><a class="text-danger" href="${pageContext.request.contextPath}/lecture/file_delete.do?fileNo=${file.fileNo}&asNo=${adto.asNo}&subjectNo=${subjectNo}">삭제</a></label>
						 </div>
						 </c:forEach>
						 <input name="file" type="file" class="form-control">
						 <input type="hidden" name="subjectNo"  value="${subjectNo}">
						 <input type="hidden" name="asNo"  value="${adto.asNo}">
						 </form>
						 </c:if>
						  <br>
						  
						  <c:if test="${fn:length(sessionScope.member.userId) != 8}">
						  	<div>
						  		<table class="table">
						  			<tr>
						  				<th style="width:10%">번호</th>
						  				<th style="width:50%">제목</th>
						  				<th style="width:10%">작성자</th>
						  				<th style="width:20%">제출시간</th>
						  				<th style="width:10%">지각제출여부</th>
						  			</tr>
						  		</table>
						  	</div>
						  </c:if>
						  <div class="text-end">
						  	<c:if test="${fn:length(sessionScope.member.userId) == 8}">
							  <button type="button" class="btn btn-outline-primary save_btn">저장</button>
							</c:if>
							  <button type="button" class="btn btn-outline-dark" onclick="goBack();">뒤로가기</button>
						  </div>
						  </div>
						</div>
					<!-- 본문 -->
					</div>
					</div>
					</div>
				</div>
				<div class="col-1"></div>
			</div>
		</div>
				
				<!-- 본문 끝 -->
	</section>
</main>
<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>
</html>

