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
		let syear = $("#reg_history option:selected").attr("data-nx");
		let semester = $("#reg_history option:selected").attr("data-ny");
		let url="${pageContext.request.contextPath}/lecture/history.do";
		let query= "syear="+syear+"&semester="+semester;
		
		const fn=function(data){
			
			let out;
			for(let item of data.list){
				let credit = item.credit;
				let semester = item.semester;
				let subjectName = item.subjectName;
				let subjectNo = item.subjectNo;
				let syear = item.syear;
				
				out += "<tr class='history_append'><td><a href='${pageContext.request.contextPath}/lecture/classroom.do?subjectNo="+subjectNo+"'>"+subjectName+"</a></td>";
				out += "<td>"+credit+"</td>";
				out += "<td>"+syear+"</td>";
				out += "<td>"+semester+"</td></tr>";
				
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

	<header>
		<jsp:include page="/WEB-INF/views/layout/header.jsp" />
	</header>

	<main>
		<section>
			<div class="container-fluid">

				<div class="row">&nbsp;</div>
				<div class="row">
					<div class="col-xl-2 col-lg-3 col-md-4 bg-dark bg-gradient pt-1" style="height: 100vh">
						<!-- 왼쪽 사이드바 자리 -->
						<jsp:include page="/WEB-INF/views/layout/l_sidebar.jsp" />
					</div>

					<!-- 본문 -->
					
					<div class="col-xl-8 col-lg-6 col-md-6">
						<div class="row ms-3 me-1 pt-3 mt-3 mb-5 gap-3">
							<div class="card pt-2 pb-2 ps-2 pe-2">
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
														수강 기록이 없습니다.
											</c:if>
											<c:if test="${not empty list}">
												<table class="table table-hover">
												<tr class="bg-light text-center">
													<th class="w-50">과목명</th>
													<th>학점</th>
													<th>년도</th>
													<th>학기</th>												
												</tr>
												<c:forEach var="dto" items="${list}" varStatus="status">
													<tr class="text-center">													
														<td><a href="${pageContext.request.contextPath}/lecture/classroom.do?subjectNo=${dto.subjectNo}">${dto.subjectName}</a>
														<td>${dto.credit}</td>
														<td>${dto.syear}</td>
														<td>${dto.semester}</td>
													</tr>
												</c:forEach>
												</table>
											</c:if>
											</div>
										</div>
									</div>
								</div>
							</div>
							
							<div class="card pt-2 pb-2 ps-2 pe-2">
								<div class="accordion">
									<div class="accordion-item">
										<h2 class="accordion-header">
											<button class="accordion-button fw-bold" type="button" id="lecture"
												data-bs-toggle="collapse" data-bs-target="#lecture_history"
												aria-expanded="true" aria-controls="lecture_history">
												강의 History</button>
										</h2>
										<div id="lecture_history"
											class="accordion-collapse collapse show"
											aria-labelledby="lecture" data-bs-parent="#lecture_history">
											<div class="accordion-body text-center">
												<select class="d-inline form-select w-75" id="reg_history">
												<c:if test="${empty hlist}">
														<option>-</option>
												</c:if>
												<c:forEach var="dto" items="${hlist}">
													<option data-nx="${dto.syear}" data-ny="${dto.semester}">${dto.syear} - ${dto.semester}학기</option>
												</c:forEach>
												</select>
												<button type="button" class="d-inline btn btn-outline-primary ms-auto reg_history_btn">조회</button>
												<p>&nbsp;</p>
												
												<c:if test="${empty hlist}">
														수강 기록이 없습니다.
												</c:if>
												<c:if test="${not empty hlist}">
												<table class="table table-hover history_list">
												<tr class="bg-light">
													<th class="w-50">과목명</th>
													<th>학점</th>
													<th>년도</th>
													<th>학기</th>												
												</tr>
													
												</table>
												</c:if>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<!-- 본문 끝 -->
					<!-- 오른쪽 사이드바 자리 -->
					<div class="col-xl-2 col-lg-3 col-md-3 ms-auto">
						<jsp:include page="/WEB-INF/views/layout/r_sidebar.jsp" />
					</div>
				</div>
			</div>
		</section>
	</main>
	<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>
</html>

