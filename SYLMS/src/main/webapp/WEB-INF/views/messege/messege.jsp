﻿<%@ page contentType="text/html; charset=UTF-8"%>
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
					<div class="col-2 bg-dark bg-gradient rounded" style="min-height: 100vh">
						<!-- 왼쪽 사이드바 자리 -->
						<jsp:include page="/WEB-INF/views/layout/l_sidebar.jsp" />
					</div>
					<div class="col-10">
						<jsp:include page="/WEB-INF/views/layout/header2.jsp" />
						<div class="row">
							<div class="col-9">
					<!-- 본문 -->
							<div class="card mt-3">	
			<div class="card-header bg-navy bg-gradient text-light">
				<h5 class="d-inline"><i class="bi bi-book-half"></i> 쪽지함 </h5>
			</div>
			
			<div class="card-body">
		            <div class="text-end">${dataCount}개(${page}/${total_page} 페이지)</div>
				<div class="mt-2">
				<table class="table table-hover board-list  text-center">
					
						<tr class="bg-navy bg-gradient text-light">
						<th>번호</th>
							<th class="name">작성자</th>
							<th class="subject">내용</th>
							<th class="date">작성일</th>
						</tr>
					
					
					<tbody>
						<c:forEach var="dto" items="${list}" varStatus="status">
							<tr>
								<td class="hidden">${dto.messegeCode}</td>
								<td>${dto.sendName}</td>
								<td class="left">
									<a href="${checkUrl}&messegeCode=${dto.messegeCode}" class="text-reset">${dto.content}</a>
								</td>
								<td>${dto.sendDate}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				</div>
				<div class="page-navigation">
					${dataCount == 0 ? "쪽지가 없습니다." : paging}
				</div>

				<div class="row board-list-footer d-flex align-items-center">
					<div class="col-auto">
						<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/messege/receive.do';">새로고침</button>
					</div>
					<div class="col text-center">
						<form class="row d-flex align-items-center" name="searchForm" action="${pageContext.request.contextPath}/messege/receive.do" method="post">
							<div class="col-auto p-1">
								<select name="condition" class="form-select">															
									<option value="name" ${condition=="sendName"?"selected='selected'":""}>작성자</option>								
								</select>
							</div>
							<div class="col p-1">
								<input type="text" name="keyword" value="${keyword}" class="form-control">
							</div>
							<div class="col-auto p-1">
								<button type="button" class="btn btn-light" onclick="searchList()">검색</button>
							</div>
						</form>
					</div>
					<div class="col-auto text-end">
						<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/messege/send.do';">쪽지보내기</button>
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
		</div>
				
				<!-- 본문 끝 -->
	</section>
</main>
<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>
</html>