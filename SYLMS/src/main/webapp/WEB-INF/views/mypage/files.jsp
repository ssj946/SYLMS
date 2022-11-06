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
</style>

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
					<div class="col-xl-2 col-lg-3 col-md-4 bg-dark bg-gradient pt-1" style="height: 100vh">
						<!-- 왼쪽 사이드바 자리 -->
						<jsp:include page="/WEB-INF/views/layout/l_sidebar.jsp" />
					</div>

					<!-- 본문 -->
					<div class="col-xl-10 col-lg-9 col-md-8" style="min-height: 100vh">
						<div class="card pt-2 pb-2 ps-2 pe-2 mt-5">
							<h5 class="card-header">파일함</h5>
							<div class="card-body">
							<!-- 검색버튼  -->
							<form class="row" name="searchForm"
										action="${pageContext.request.contextPath}/file/file.do"
										method="post">
								<div class="d-flex justify-content-center align-items-center">
									<div class="s-1 p-1">
										<select class="form-select" name="year" id="syear">
											<option value="2022">2022년</option>
										</select>
									</div>
									<div class="col-auto p-1">
										<input type="text" name="keyword" value="${keyword}"
											class="form-control" placeholder="파일명">
									</div>
									<div class="col-auto p-1">
										<button type="submit" class="btn btn-light applybtn">
											<i class="bi bi-search"></i>
										</button>

									</div>
								</div>
								<!-- 검색버튼  끝 -->
								<table class="table  mt-4">
									<thead class="table-light">
										<tr style="text-align: center;">
											<th class="year">파일</th>
											<th class="semester" >과목</th>
											<th class="subname">등록일</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="dto" items="${flist}" varStatus="status">
											<tr>
												<td><a href="${pageContext.request.contextPath}/file/download.do?fileNo=${dto.fileNo}">${dto.originName}</a></td>
												<td>${dto.subjectName}</td>
												<td>${dto.submitDate}</td>
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
					<!-- 본문 끝 -->
					<!-- 오른쪽 사이드바 자리 -->
				</div>
			</div>
		</section>
	</main>
	<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>
</html>

