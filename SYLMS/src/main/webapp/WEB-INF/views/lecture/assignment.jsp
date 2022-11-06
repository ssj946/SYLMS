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
	$(".as_write").click(function(){
		location.href="${pageContext.request.contextPath}/lecture/assignContent.do?subjectNo=${subjectNo}"
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
						<div class="card">
							<div class="card-header fw-bold fs-6 bg-navy bg-gradient text-center text-white p-2">
							   <h5 class="d-inline"><i class="fas fa-book-skull fa-lg bg-navy"></i>&nbsp;</h5>
							</div>
							<div class= "card-body">
							<table class="table text-center">
							<tr class="bg-light">
								<th style="width: 10%">번호</th>
								<th style="width: 70%">과제명</th>
								<th style="width: 10%">등록일</th>
								<th style="width: 10%">마감일</th>
							</tr>
							<c:if test="${empty list}">
								<tr>
									<td colspan="4"><br><br><h4>과제가 없습니다.</h4><br><br></td>
								</tr>
							</c:if>
							<c:forEach var="dto" items="${list}">
								<tr>
									<td>${dto.asNo}</td>
									<td><a href="${pageContext.request.contextPath}/lecture/assignment_view.do?subjectNo=${subjectNo}&asNo=${dto.asNo}">${dto.title}</a></td>
									<td>${dto.reg_date}</td>
									<td>${dto.end_date}</td>
								</tr>
								<tr>					
							</c:forEach>
							</table>
							<div class="d-flex justify-content-end">
								<c:if test="${fn:length(sessionScope.member.userId) !=8 }">
								<button class="btn btn-primary m-2 as_write">과제작성</button>
								</c:if>
								<button class="btn m-2">돌아가기</button>
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

