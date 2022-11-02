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
</style>

</head>
<script type="text/javascript">
	
</script>
<body>

	<header>
		<jsp:include page="/WEB-INF/views/layout/header.jsp" />
	</header>

	<main>
		<section>
			<div class="container-fluid">

				<div class="row">&nbsp;</div>
				<div class="row">
					<div class="col-xl-2 col-lg-3 col-md-4 bg-dark bg-gradient pt-1"
						style="height: 100vh">
						<!-- 왼쪽 사이드바 자리 -->
						<jsp:include page="/WEB-INF/views/layout/l_sidebar.jsp" />
					</div>

					<!-- 본문 -->

					<div class="col-xl-8 col-lg-6 col-md-6" style="min-height: 100vh">
						<div class="row ms-3 me-1 pt-3 mt-3 mb-5 gap-3">
							<c:if test="${fn:length(sessionScope.member.userId) == 8}">
								<div class="card pt-2 pb-2 ps-2 pe-2" style="margin: 50px 0px;">
									<h5 class="card-header">강의 시간표</h5>
									<div class="card-body">
										<table class="table table-bordered mt-5">
											<thead class="table-light">
												<tr style="text-align: center;">
													<th style="width: 15%;">&nbsp;</th>
													<th style="width: 17%;">월</th>
													<th style="width: 17%;">화</th>
													<th style="width: 17%;">수</th>
													<th style="width: 17%;">목</th>
													<th style="width: 17%;">금</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="a" begin="9" end="18">
													<c:out value="<tr height='60'>" escapeXml="false" />
													<c:out
														value="<th class='text-center' style='vertical-align: middle;' >"
														escapeXml="false" />
															${a>=13 ? a-12 : a}
														<c:out value="</th>" escapeXml="false" />
													<c:forEach var="b" begin="1" end="5">
														<c:out value="<td class='text-center'>" escapeXml="false" />
														<c:forEach var="vo" items="${list}">
															<c:if test="${vo.schedule==a && vo.dayWeek==b}">
															  		${vo.subjectName}
															  		<br>
															  		${vo.lecturePlace}
															  	</c:if>
														</c:forEach>
														<c:out value="</td>" escapeXml="false" />
													</c:forEach>

													<c:out value="</tr>" escapeXml="false" />
												</c:forEach>

											</tbody>
										</table>
									</div>
								</div>
							</c:if>
							<c:if test="${fn:length(sessionScope.member.userId) != 8}">
								<div class="card pt-2 pb-2 ps-2 pe-2" style="margin: 50px 0px;">
									<h5 class="card-header">강의 시간표</h5>
									<div class="card-body">
										<table class="table table-bordered mt-5">
											<thead class="table-light">
												<tr style="text-align: center;">
													<th style="width: 15%;">&nbsp;</th>
													<th style="width: 17%;">월</th>
													<th style="width: 17%;">화</th>
													<th style="width: 17%;">수</th>
													<th style="width: 17%;">목</th>
													<th style="width: 17%;">금</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach var="a" begin="9" end="18">
													<c:out value="<tr height='60'>" escapeXml="false" />
													<c:out
														value="<th class='text-center' style='vertical-align: middle;' >"
														escapeXml="false" />
															${a>=13 ? a-12 : a}
														<c:out value="</th>" escapeXml="false" />
													<c:forEach var="b" begin="1" end="5">
														<c:out value="<td class='text-center'>" escapeXml="false" />
														<c:forEach var="vo" items="${plist}">
															<c:if test="${vo.schedule==a && vo.dayWeek==b}">
															  		${vo.subjectName}
															  		<br>
															  		${vo.lecturePlace}
															  	</c:if>
														</c:forEach>
														<c:out value="</td>" escapeXml="false" />
													</c:forEach>

													<c:out value="</tr>" escapeXml="false" />
												</c:forEach>

											</tbody>
										</table>
									</div>
								</div>
							</c:if>
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

