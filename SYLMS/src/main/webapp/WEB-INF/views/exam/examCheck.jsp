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
	function searchList() {
		const f = document.searchForm;
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
						<div class="col-xl-12 col-md-12 col-lg-12 gap-3 ms-auto ">
							<div class="ms-1 me-1 pt-3 mt-3 mb-3">
								<div class="card mb-3">
									<c:if test="${fn:length(sessionScope.member.userId) == 8}">
										<div class="card-header fs-6 text-center p-2">
											<h5 class="card-header">시험성적확인</h5>
											<div class="card-body">
												<form class="row" name="searchForm" 
													action="${pageContext.request.contextPath}/exam/send.do" method="post">
													<table class="table table-hover board-list ho-list" style="margin-top: 50px;">
														<thead class="table-light">
															<tr style="text-align: center;">
																<th class="subtp">시험종류</th>
																<th class="score">점수</th>
																<th class="gradeCode">성적코드</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach var="dto" items="${list2}" varStatus="status">
																<tr>
																	<td>${dto.examType }</td>
																	<td>${dto.score }</td>
																	<td>${dto.gradeCode }</td>
																</tr>
															</c:forEach>
														</tbody>
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