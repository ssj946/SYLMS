<%@ page contentType="text/html; charset=UTF-8"%>
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
ul {
	list-style: none;
}
</style>

<script type="text/javascript">
	function check() {
		const f = document.searchForm;
		
		f.action = "${pageContext.request.contextPath}/exam/send_ok.do";
		f.submit();
	}
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
						<div class="col-xl-10 col-md-10 col-lg-10 gap-3 ms-auto ">
							<div class="ms-1 me-1 pt-3 mt-3 mb-3">
								<div class="card mb-3">
									<c:if test="${fn:length(sessionScope.member.userId) != 8}">
										<div class="card-header fs-6 text-center p-2">
											<h5 class="card-header">시험성적입력</h5>
											<div class="card-body">
												<form class="row" name="searchForm" method="post">
													<table class="table table-hover board-list ho-list"
														style="margin-top: 50px;">
														<thead class="table-light">
															<tr style="text-align: center;">
																<th class="subNo" width="150px;">과목번호</th>
																<th class="stdNo" width="150px;">학생학번</th>
																<th class="grdNo"width="150px;">성적번호</th>
																<th class="extp" width="50%">시험종류</th>
																<th class="score" width="150px;">점수</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach var="dto" items="${list}" varStatus="status">
																<tr>
																	<td><input type="text" name="subjectNo"
																		value="${dto.subjectNo}" id="userId"
																		class="form-control" style="width: 60%;"
																		readonly="readonly"></td>
																	<td><input type="text" name="studentCodes"
																		value="${dto.studentCode}"
																		class="form-control" style="width: 60%;"
																		readonly="readonly"></td>
																	<td><input type="text" name="gradeCodes"
																		value="${dto.gradeCode}"
																		class="form-control" style="width: 60%;"
																		readonly="readonly"></td>
																	<td><input type="text" name="examTypes"
																		class="form-control" value="${dto.examType }"></td>
																	<td><input type="text" name="scores"
																		class="form-control" value="${dto.score }"
																		style="width: 60%;"></td>
																</tr>
															</c:forEach>
														</tbody>
													</table>

													<table class="table">
														<tr>
															<td class="right">
																<button type="button" class="btn" onclick="check()">시험
																	성적 등록</button>
															</td>
														</tr>
													</table>
												</form>
											</div>
										</div>
									</c:if>
								</div>
							</div>
							<!-- 본문 끝 -->
						</div>
						</div>
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