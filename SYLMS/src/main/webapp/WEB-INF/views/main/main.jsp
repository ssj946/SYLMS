﻿<%@ page contentType="text/html; charset=UTF-8"%>
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
</head>

<body>

<main>
	<section>
		<div class="container-fluid">
			<div class="row">
				<div class="col-1"></div>
				<div class="col-10">
					<div class="row">
					<div class="col-2 bg-dark bg-gradient rounded" style="min-height: 100vh">
						<!-- 왼쪽 사이드바 자리 -->
						<jsp:include page="/WEB-INF/views/layout/l_sidebar.jsp" />
					</div>
					<div class="col-10">
						<jsp:include page="/WEB-INF/views/layout/header2.jsp" />
						<div class="row">
							<div class="col-9">
					<!-- 본문 -->
							<div class="card mt-3 p-2">
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
												<tr class="bg-light text-center">
													<th class="w-50">과목명</th>
													<th>학점</th>
													<th>년도</th>
													<th>학기</th>	
													<th>출석</th>											
												</tr>
												<c:forEach var="dto" items="${list}" varStatus="status">
													<tr class="text-center">													
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
						</div>
							<!-- 오른쪽 사이드바 자리 -->
						<div class="col-3 mt-3"><jsp:include page="/WEB-INF/views/layout/r_sidebar.jsp" /></div>
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