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
.body-container {
	max-width: 800px;
}
</style>

<script type="text/javascript">
function searchList() {
	const f = document.searchForm
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

				<div class="row">&nbsp;</div>
				<div class="row">
					<div class="col-xl-2 col-lg-3 col-md-4 bg-dark bg-gradient pt-1">
						<!-- 왼쪽 사이드바 자리 -->
						<jsp:include page="/WEB-INF/views/layout/l_sidebar.jsp" />
					</div>
					<!-- 본문 -->
					<div class="col-xl-10 col-lg-9 col-md-8">
						<div class="body-container">
							<div class="body-title">
								<h3 class="title">
									<i class="bi bi-person-square"></i> 조교 신청
								</h3>
							</div>
							<div class="body-main">
								<div class="row board-list-header">
									<div class="col-12 text-center" style="margin: 50px 0px;">
										<form class="row" name="searchForm"
											action="${pageContext.request.contextPath}/mypage/assistant.do" method="post">
											<div class="col-auto p-1" style="display: inline-block;">

												<select name="condition" class="form-select">
													<option value="year"
														${condition=="year"?"selected='selected'":""}>2023년</option>

												</select>
											</div>
											<div class="col-auto p-1" style="display: inline-block;">
												<select name="condition" class="form-select">
													<option value="f"
														${condition=="f"?"selected='selected'":""}>1학기</option>
													<option value="s"
														${condition=="s"?"selected='selected'":""}>2학기</option>
												</select>
											</div>

											<div class="col-auto p-1" style="display: inline-block;">
												<select name="condition" class="form-select">
													<option value="subjectName"
														${condition=="subjectName"?"selected='selected'":""}>강좌명</option>
												</select>
											</div>
											<div class="col-auto p-1" style="display: inline-block;">
												<input type="text" name="keyword" value="${keyword}"
													class="form-control">
											</div>
											<div class="col-auto p-1" style="display: inline-block;">
												<button type="button" class="btn btn-light"
													onclick="searchList()">
													<i class="bi bi-search"></i>
												</button>
											</div>
										</form>

									</div>
								</div>
							</div>

                   
							<table class="table table-hover board-list">
								<thead class="table-light">
									<tr style="text-align: center;">
										<th class="year" width="60px;">연도</th>
										<th class="semester" width="60px;">학기</th>
										<th class="department" width="150px;">학과</th>
										<th class="subname">과목명</th>
										<th class="prof" width="80px;">교수</th>
										<th class="apply" width="200px;">조교신청</th>
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
											<td><button type="button" class="btn btn-light"
													onclick="location.href=${pageContext.request.contextPath}/mypage/assistant_ok.do?subjectNo=${dto.subjectNo}">신청</button></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>


							<div class="page-navigation">${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
							</div>


                        

						</div>
					</div>
					<!-- 본문 끝 -->
				</div>
			</div>

		</section>
	</main>
</body>
</html>