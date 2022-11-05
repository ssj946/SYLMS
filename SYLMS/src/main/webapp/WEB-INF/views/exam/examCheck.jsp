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
							<div class="col-lg-2 bg-dark bg-gradient"
								style="box-shadow: none; height: 150vh;">
								<jsp:include page="/WEB-INF/views/layout/lecture_sidebar.jsp" />
							</div>
							<!-- 본문 -->
							<div class="col-xl-10 col-md-10 col-lg-10 gap-3 ms-auto ">
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
		</section>
	</main>
	<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>
</html>