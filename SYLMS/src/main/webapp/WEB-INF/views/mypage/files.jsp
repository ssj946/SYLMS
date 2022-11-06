﻿﻿<%@ page contentType="text/html; charset=UTF-8"%>
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

<body>

<main>
	<section>
		<div class="container-fluid">
			<div class="row">
				<div class="col-1"></div>
				<div class="col-10">
					<div class="card p-2">
					<div class="row ps-3 pe-1">
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
							<h5 class="d-inline card-header bg-navy bg-gradient text-white">파일함</h5>
							<div class="card-body">

							<!-- 검색버튼  -->
							<form class="row" name="searchForm" action="${pageContext.request.contextPath}/file/file.do" method="post">
								<div class="d-flex justify-content-center align-items-center">
									<div class="col-auto p-1">
										<select class="form-select" name="year" id="syear">
											<option value="2022">2022년</option>
										</select>
									</div>
									<div class="col p-1">
										<input type="text" name="keyword" value="${keyword}"
											class="form-control" placeholder="파일명">
									</div>
									<div class="col-auto p-1">
										<button type="submit" class="btn btn-light applybtn">
											<i class="bi bi-search"></i>
										</button>

										</div>
									</div>

								</form>
								</div>
								<!-- 검색버튼  끝 -->
								<table class="table  mt-4">
										<tr class="bg-navy bg-gradient text-white text-center">
											<th class="year">파일</th>
											<th class="semester" >과목</th>
											<th class="subname">등록일</th>
										</tr>

										<c:forEach var="dto" items="${flist}" varStatus="status">
											<tr>
												<td><a href="${pageContext.request.contextPath}/file/download.do?fileNo=${dto.fileNo}">${dto.originName}</a></td>
												<td>${dto.subjectName}</td>
												<td>${dto.submitDate}</td>
											</tr>
											</c:forEach>
									</table>

									<div class="page-navigation">${dataCount == 0 ? "파일이 없습니다." : paging}
									</div>

							</div>
						</div>
							<!-- 오른쪽 사이드바 자리 -->
						<div class="col-3 mt-3"><jsp:include page="/WEB-INF/views/layout/r_sidebar.jsp" /></div>
						</div>
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