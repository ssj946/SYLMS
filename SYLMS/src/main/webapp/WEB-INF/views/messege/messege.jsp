<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>SYLMS</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>
<style type="text/css">
.body-container {
	max-width: 800px;
}

.hidden{
	display: none;
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
	<jsp:include page="/WEB-INF/views/layout/header.jsp"/>
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
			<div class="col-xl-8 col-lg-6 col-md-6">
			<div class="row ms-3 me-1 pt-3 mt-3 mb-5 gap-3">
			
			<div class="body-container">	
			<div class="body-title">
				<h3><i class="bi bi-book-half"></i> 쪽지함 </h3>
			</div>
			
			<div class="body-main">
		        <div class="row board-list-header">
		            <div class="col-auto me-auto">${dataCount}개(${page}/${total_page} 페이지)</div>
		            <div class="col-auto">&nbsp;</div>
		        </div>				
				
				<table class="table table-hover board-list">
					<thead class="table-light">
						<tr>
							<th class="name">작성자</th>
							<th class="subject">내용</th>
							<th class="date">작성일</th>
						</tr>
					</thead>
					
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
				
				<div class="page-navigation">
					${dataCount == 0 ? "쪽지가 없습니다." : paging}
				</div>

				<div class="row board-list-footer">
					<div class="col">
						<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/messege/receive.do';">새로고침</button>
					</div>
					<div class="col-6 text-center">
						<form class="row" name="searchForm" action="${pageContext.request.contextPath}/messege/receive.do" method="post">
							<div class="col-auto p-1">
								<select name="condition" class="form-select">															
									<option value="name" ${condition=="sendName"?"selected='selected'":""}>작성자</option>								
								</select>
							</div>
							<div class="col-auto p-1">
								<input type="text" name="keyword" value="${keyword}" class="form-control">
							</div>
							<div class="col-auto p-1">
								<button type="button" class="btn btn-light" onclick="searchList()">검색</button>
							</div>
						</form>
					</div>
					<div class="col text-end">
						<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/messege/send.do';">쪽지보내기</button>
					</div>
				</div>

			</div>
		</div>
			
			</div>
			</div>
			<!-- 본문 끝 -->
			</div>
		</div>
	</section>
</main>
<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>