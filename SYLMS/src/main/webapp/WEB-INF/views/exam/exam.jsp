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
										<!-- <c:if test="${fn:length(sessionScope.member.userId) != 8}"> -->
										<div class="card-header fs-6 text-center p-2">
											<h5 class="card-header">시험성적입력</h5>
											<div class="card-body">
												<form class="row" name="searchForm"
													action="${pageContext.request.contextPath}/exam/exam.do"
													method="post">
													<div
														class="d-flex justify-content-center align-items-center">
														<div class="s-1 p-1">
															<select class="form-select" name="year" id="syear">
																<option value="2023">2023년</option>
															</select>
														</div>
														<div class="col-auto p-1">
															<button type="submit" class="btn btn-light applybtn">
																<i class="bi bi-search"></i>
															</button>

														</div>
													</div>
													<table class="table table-hover board-list ho-list"
														style="margin-top: 50px;">
														<thead class="table-light">
															<tr style="text-align: center;">
																<th class="" width="200px;">과목번호</th>
																<th class="" width="200px;">학생학번</th>
																<th class="prof" width="150px;">성적번호</th>
																<th class="subname">시험종류</th>
																<th class="" width="150px;">점수</th>																
															</tr>
														</thead>
														<tbody>
															<c:forEach var="dto" items="${list}" varStatus="status">
																<tr>
																	<td>${dto.year}</td>
																	<td>${dto.semester}</td>
																	<td>${dto.department}</td>
																	<td>${dto.subject}</td>
																	<td>${dto.professor}</td>
																	<td><button type="submit" class="btn btn-light"
																			onclick=" if(confirm('조교신청을 하시겠습니까?')) location.href='${pageContext.request.contextPath}/mypage/assistant_ok.do?subjectNo=${dto.subjectNo}&mode=apply';">신청</button></td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
													<div class="page-navigation">${dataCount == 0 ? "신청할 과목이 없습니다." : paging}
													</div>

												</form>
											</div>
										</div>
									</div>
									<!--</c:if>			-->
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