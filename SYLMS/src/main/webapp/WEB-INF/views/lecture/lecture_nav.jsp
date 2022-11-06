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
function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type:method,
		url:url,
		data:query,
		dataType:dataType,
		success:function(data){
			fn(data);
		},
		beforeSend:function(jqXHR) {
			// jqXHR.setRequestHeader("AJAX", true);
		},
		error:function(jqXHR) {
			console.log(jqXHR.responseText);
		}
	});
}

$(function(){
	
	$(".reg_history_btn").click(function(){
		let syear = $("#syear option:selected").val();
		let semester = $("#semester option:selected").val();
		let url="${pageContext.request.contextPath}/lecture/history.do";
		let query= "syear="+syear+"&semester="+semester;
		
		const fn = function(data){
			
			let out;
			for(let item of data.list){
				let credit = item.credit;
				let semester = item.semester;
				let subjectName = item.subjectName;
				let subjectNo = item.subjectNo;
				let syear = item.syear;
				let professorName = item.professorname
				
				out += "<tr class='history_append'><td><a href='${pageContext.request.contextPath}/lecture/classroom.do?subjectNo="+subjectNo+"'>"+subjectName+"</a></td>";
				out += "<td>"+credit+"</td>";
				out += "<td>"+syear+"</td>";
				out += "<td>"+semester+"</td>";
				out += "<td>"+professorName+"</td></tr>";
				
			}
			$(".history_append").remove();
			$(".history_list").append(out);
		}
		ajaxFun(url, "GET", query, "JSON", fn);
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
					<div class="col-2 bg-dark bg-gradient rounded" style="min-height: 100vh">
						<!-- 왼쪽 사이드바 자리 -->
						<jsp:include page="/WEB-INF/views/layout/l_sidebar.jsp" />
					</div>
					<div class="col-10">
						<jsp:include page="/WEB-INF/views/layout/header2.jsp" />
						<div class="row">
							<div class="col-9">
					<!-- 본문 -->
							<div class="card mt-3 mb-3 p-2">
								<div class="accordion" id="lecture_all">
									<div class="accordion-item">
										<h2 class="accordion-header" id="lecture">
											<button class="accordion-button fw-bold" type="button"
												data-bs-toggle="collapse" data-bs-target="#lecture_list"
												aria-expanded="true" aria-controls="lecture_list">
												이번학기 강의</button>
										</h2>
										<div id="lecture_list"
											class="accordion-collapse collapse show"
											aria-labelledby="lecture" data-bs-parent="#lecture_all">
											<div class="accordion-body text-center">
											<c:if test="${empty list}">
												<br>
												<h4>수강 기록이 없습니다.</h4>
												<br>
											</c:if>
											<c:if test="${not empty list}">
												<table class="table table-hover">
												<tr class="bg-navy bg-gradient text-light text-center">
													<th class="w-50">과목명</th>
													<th>학점</th>
													<th>년도</th>
													<th>학기</th>	
													<th>출석</th>											
												</tr>
												<c:forEach var="dto" items="${list}" varStatus="status">
													<tr class=" text-center">													
														<td><a href="${pageContext.request.contextPath}/lecture/classroom.do?subjectNo=${dto.subjectNo}">${dto.subjectName}</a>
														<td>${dto.credit}</td>
														<td>${dto.syear}</td>
														<td>${dto.semester}</td>
														<td><a href="${pageContext.request.contextPath}/lecture/attend.do?subjectNo=${dto.subjectNo}"><i class="fas fa-clipboard-user fa-lg"></i></a>
													</tr>
												</c:forEach>
												</table>
											</c:if>
											</div>
										</div>
									</div>
								</div>
							</div>
							
							<div class="card mt-3 mb-3 p-2">
								<div class="accordion">
									<div class="accordion-item">
										<h2 class="accordion-header">
											<button class="accordion-button fw-bold" type="button" id="lecture"
												data-bs-toggle="collapse" data-bs-target="#lecture_history"
												aria-expanded="false" aria-controls="lecture_history">
												강의 History</button>
										</h2>
										<div id="lecture_history"
											class="accordion-collapse collapse show"
											aria-labelledby="lecture" data-bs-parent="#lecture_history">
											<div class="accordion-body text-center">
											<div class="d-flex justify-content-center">
												<div class="p-2">
												<select class="form-select" id="syear" >
												<c:if test="${empty hlist}">
														<option>-</option>
												</c:if>
												<c:forEach var="dto" items="${hlist}">
													<option value="${dto.syear}">${dto.syear}</option>
												</c:forEach>
												</select>
												</div>
												
												<div class="p-2 fs-5 mt-auto">
												<span>학년도</span>
												</div>
												
												<div class="p-2">
												<select class="form-select" id="semester">
													<option value="1">1</option>
													<option value="2">2</option>
												</select>
												</div>
												
												<div class="p-2 fs-5 mt-auto">
												<span>학기</span>
												</div>
												
												<div class="p-2">
												<button type="button" class="btn btn-outline-primary  reg_history_btn">조회</button>
												</div>
											</div>
												<p>&nbsp;</p>	
												<c:if test="${empty hlist}">
														<h4>수강 기록이 없습니다.</h4>
												</c:if>
												<c:if test="${not empty hlist}">
												<table class="table table-hover history_list">
												<tr class="bg-navy bg-gradient text-light">
													<th style="width:60%">과목명</th>
													<th>학점</th>
													<th>년도</th>
													<th>학기</th>	
													<th>담당교수</th>											
												</tr>
													
												</table>
												</c:if>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
							<!-- 오른쪽 사이드바 자리 -->
						<div class="col-3 mt-3 ">
						<jsp:include page="/WEB-INF/views/layout/r_sidebar.jsp" /></div></div>
					</div>
					</div>
				</div>
				</div>
				</div>
				<div class="col-1"></div>
			</div>
				
				<!-- 본문 끝 -->
	</section>
</main>
<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>
</html>