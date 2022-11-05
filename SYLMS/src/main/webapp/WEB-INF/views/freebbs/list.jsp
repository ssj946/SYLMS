
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
.page-link {
	color: #000;
	background-color: #fff;
	border: 1px solid #ccc;
}

.page-item.active .page-link {
	z-index: 1;
	color: #555;
	font-weight: bold;
	background-color: #f1f1f1;
	border-color: #ccc;
}

.page-link:focus, .page-link:hover {
	color: #000;
	background-color: #fafafa;
	border-color: #ccc;
}

.new {
	height: 15px;
	padding-left: 3px;
	padding-bottom: 3px;
}

.table-light {
	text-align: center;
}

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

						<!-- classroom header 자리 -->
						<div class="row">
							<jsp:include page="/WEB-INF/views/layout/classroom_header.jsp" />
						</div>
						<div class="row">
							<!-- 강의 사이드바 자리 -->
							<div class="col-xl-2 col-md-2 col-lg-2 bg-black bg-gradient"
								style="box-shadow: none;">
								<jsp:include page="/WEB-INF/views/layout/lecture_sidebar.jsp" />
							</div>

							<!-- 본문 -->
							<div class="col-lg-10 gap-3 ms-auto">
								<div class="ms-5 me-5 pt-3 mt-4 mb-5">
									<div class="card">
										<div class="card-header fw-bold">
											<h3>
												<i class="fa-solid fa-user-group"></i> 자유게시판
											</h3>

										</div>
										<div class="card-body py-5">
											<p class="text-end m-0 pe-5">${dataCount}개(${page}/${total_page}
												페이지)</p>
											<br>
											<div class="row card-body px-5 py-0">
												<table class="table text-center">
													<tr>
														<th style="width: 10%">번호</th>
														<th style="width: 55%">제목</th>
														<th style="width: 10%">작성자</th>
														<th style="width: 15%">작성일</th>
														<th style="width: 10%">조회수</th>
													</tr>

													<c:forEach var="dto" items="${list}" varStatus="status">
														<tr>
															<td>${dataCount - (page-1) * size - status.index}</td>
															<td class="text-start"><a
																href="${pageContext.request.contextPath}/freebbs/freebbsArticle.do?subjectNo=${subjectNo}&articleNo=${dto.articleNo}"
																class="text-reset ps-4">${dto.title}</a> <c:if
																	test="${not empty dto.saveFilename}">
																	<img
																		src="${pageContext.request.contextPath}/resources/images/paperclip.png">
																</c:if> <c:if test="${dto.gap<2}">
																	<img
																		src="${pageContext.request.contextPath}/resources/images/new.png"
																		class="new">
																</c:if></td>
															<td>${dto.name}</td>
															<td>${dto.reg_date}</td>
															<td>${dto.hitCount}</td>
														</tr>
													</c:forEach>
												</table>
											</div>
											<div class="row">
												<div class="page-navigation m-auto">${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
												</div>
											</div>
											<div class="row text-center">
												<div class="col-2"></div>
												<div class="col-8">
													<form name="searchForm"
														action="${pageContext.request.contextPath}/freebbs/freebbs.do"
														method="post">
														<div class="row">
															<div class="col-3 p-1">
																<select name="condition" class="form-select">
																	<option value="all"
																		${condition=="all"?"selected='selected'":""}>제목+내용</option>
																	<option value="reg_date"
																		${condition=="reg_date"?"selected='selected'":""}>등록일</option>
																	<option value="title"
																		${condition=="title"?"selected='selected'":""}>제목</option>
																	<option value="content"
																		${condition=="content"?"selected='selected'":""}>내용</option>
																</select>
															</div>

															<div class="col-7 p-1">
																<input type="text" name="keyword" value="${keyword}"
																	class="form-control">
															</div>
															<div class="col-auto p-1">
																<input type="hidden" name="subjectNo"
																	value="${subjectNo}">
															</div>
															<div class="col-auto p-1">
																<button type="button" class="btn btn-light"
																	onclick="searchList()">
																	<i class="bi bi-search"></i>
																</button>
															</div>

														</div>
													</form>
												</div>

												<div class="col-2"></div>
											</div>
											<br> <br>
											<div class="row">
												<div class="col-2 m-auto">
													<button type="button" class="btn btn-light"
														onclick="location.href='${pageContext.request.contextPath}/freebbs/freebbs.do?subjectNo=${subjectNo}';">새로고침</button>
												</div>
												<div class="col-8 text-center p-0"></div>
												<div class=" col-2 text-end m-auto">
													<button type="button" class="btn btn-light"
														onclick="location.href='${pageContext.request.contextPath}/freebbs/freebbsWrite.do?subjectNo=${subjectNo}';">글올리기</button>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<!-- 본문 끝 -->
						</div>
					</div>
				</div>
			</div>

		</section>
	</main>
	<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>
</html>