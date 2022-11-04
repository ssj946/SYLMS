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
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp" />
<style type="text/css">
list-style
:
 
none
;
</style>

<script type="text/javascript">

</script>
</head>

<body>

	<header>
		<jsp:include page="/WEB-INF/views/layout/header.jsp" />
	</header>

	<main>
		<section>
			<div class="container-fluid">
				<div class="row" style="line-height: 1.5rem">&nbsp;</div>
				<div class="row">
					<div class="col-lg-1 bg-dark bg-gradient">
						<!-- brief 사이드바 자리 -->
						<jsp:include page="/WEB-INF/views/layout/brief_sidebar.jsp" />
					</div>
					<div class="col-lg-11 ms-auto">
						<div class="row">
							<!-- classroom header 자리 -->
							<jsp:include page="/WEB-INF/views/layout/classroom_header.jsp" />
						</div>
						<div class="row">
							<!-- 강의 사이드바 자리 -->
							<div class="col-lg-2 bg-black bg-gradient"
								style="box-shadow: none; height: 150vh;">
								<jsp:include page="/WEB-INF/views/layout/lecture_sidebar.jsp" />
							</div>

							<!-- 본문 -->
							<div class="col-lg-10 gap-3 ms-auto">
								<div class="ms-1 me-1 pt-3 mt-3 mb-5">
									<h3>
										<i class="fas fa-clipboard-question  fa-lg mr-3"></i> 수업계획서
									</h3>

								</div>
								<hr>


							    <div class="p-2">
									<form name="curriculumForm" method="post" action="${pageContext.request.contextPath}/syllabus/updateCurriculum_ok.do">
									<table class="table write-form mt-5 border-top">
										<tr>		
											<td class="table-light col-sm-3" scope="row">과목명</td>
											<td>
												<input type="text" name="subjectName" class="form-control" value="${dto.subjectName}" required="required" readonly="readonly" style="border: none;">
											</td>
										</tr>
										
										<tr>
											<td class="table-light col-sm-2" scope="row">학점</td>
							                <td>
							                	<input type="text" name="credit" class="form-control" value="${dto.credit}" required="required" readonly="readonly" style="border: none;">
							                </td>
										</tr>

										<tr>
											<td class="table-light col-sm-2" scope="row">강의실</td>
							                <td>
							                	<input type="text" name="lecturePlace" class="form-control" value="${dto.lecturePlace}" required="required" readonly="readonly" style="border: none;">
							                </td>
										</tr>
																				
										<tr>
											<td class="table-light col-sm-2" scope="row">과제비율</td>
							                <td>
							                 	<input type="text" name="assignmentRate" class="form-control" value="${dto.assignmentRate }" required="required" pattern="\d{1,3}">
							                 </td>
										</tr>
										<tr>
											<td class="table-light col-sm-2" scope="row">중간고사비율</td>
							               <td>
							               	<input type="text" name="middleRate" class="form-control" value="${dto.middleRate }" required="required" pattern="\d{1,3}">
							               </td>
										</tr>	
										<tr>
											<td class="table-light col-sm-2" scope="row">기말고사비율</td>
							               <td>
							               	<input type="text" name="finalRate" class="form-control" value="${dto.finalRate}" required="required" pattern="\d{1,3}">
							               </td> 
							  			</tr>
							  			
										<tr>
											<td class="table-light col-sm-2" scope="row">강의타임</td>
											<td>
												<select name="lectureType" class="form-select">
													<option value="오프라인" ${dto.lectureType== "오프라인" ? "selected='selected'":"" }>오프라인</option>
													<option value="온라인" ${dto.lectureType== "온라인" ? "selected='selected'":"" }>온라인</option>
													<option value="블렌디드" ${dto.lectureType== "블렌디드" ? "selected='selected'":"" }>블렌디드</option>
												</select>
											</td>
										</tr>
																				
										<tr>
											<td class="table-light col-sm-2" scope="row">선수과목</td>
											<td>
												<input type="text" name="precondition" class="form-control" value="${dto.precondition}">
											</td>
										</tr>
										
										<tr>
											<td class="table-light col-sm-2" scope="row">주교재</td>
											<td>
												<input type="text" name="textbook" class="form-control" value="${dto.textbook}" required="required">
											</td>
										</tr>
																														
									</table>
									<table class="table table-borderless">
										<tr>
											<td class="text-center">
												<input type="hidden" name="subjectNo" value="${dto.subjectNo}">
												<button type="submit" class="btn btn-light">수정완료</button>
												<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/syllabus/listProfessor.do';">수정취소</button>
											</td>
										</tr>
									</table>
									</form>	
								</div>	

						</div>
					</div>
				</div>
				<!-- 본문 끝 -->
				</div>
			</div>

		</section>
	</main>
	<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>
</html>

