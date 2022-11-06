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

list-style: none;

</style>

<script type="text/javascript">
$(function(){
	$(".subjectRegister").change(function(){
		let subjectNo = $(this).val();
		location.href="${pageContext.request.contextPath}/syllabus/list.do?subjectNo="+subjectNo;
	});
})
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
						
					<!-- 본문 -->
					<div class="card mt-3">
		<div class="card-header bg-navy bg-gradient text-white">
			<h5 class="d-inline"><i class="fas fa-map  fa-lg "></i> 수업계획서 </h5>			
		</div>
		<div class="card-body">

			
					<form class="form-inline" action="">
					
						<div class="form-group">
							<label>학년도 학기</label>
							<label>${syear} 년도 </label>
							<label>${semester }학기</label>
						</div>
						
						<div class="form-group">
							<label>수업선택</label>
							<select class="form-control subjectRegister" >
								<c:forEach var="vo" items="${listRegister }">
									<option value="${vo.subjectNo}" ${vo.subjectNo == dto.subjectNo ? "selected='selected'" :"" }> ${vo.subjectName } </option>
								</c:forEach>					
							</select>
						</div>
						
					</form>
					<hr>
				<table class="table">
					<tr>
						<td class="w-10 bg-light">과목명</td>
						<td class="w-40">${dto.subjectName}</td>
						<td class="w-10 bg-light">담당교수</td>
						<td class="w-40">${dto.name}</td>						
					</tr>

					<tr>
						<td class="bg-light">개강일자</td>
						<td>${dto.openDate}</td>
						<td class="bg-light">학기</td>
						<td>${dto.semester}</td>						
					</tr>
					
					<tr>
						<td class="bg-light">강의실</td>
						<td colspan="3">${dto.lecturePlace}</td>						
					</tr>
					
					<tr>
						<td class="bg-light">강의타임</td>
						<td>${dto.lectureType}</td>
						<td class="bg-light">선수과목</td>
						<td>${dto.precondition}</td>						
					</tr>

					<tr>
						<td class="bg-light">주교제</td>
						<td colspan="3">${dto.textbook}</td>						
					</tr>
																
					<tr>
						<td class="bg-light">학점</td>
						<td>${dto.credit}</td>
						<td class="bg-light">과제비율</td>
						<td>${dto.assignmentRate}%</td>						
					</tr>
	
					<tr>
						<td class="bg-light">중간고사비율</td>
						<td>${dto.middleRate}%</td>
						<td class="bg-light">기말고사비율</td>
						<td>${dto.finalRate}%</td>						
					</tr>
				</table>
        </div>
        </div>
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

