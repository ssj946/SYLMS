
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
.page-link {
  color: #000; 
  background-color: #fff;
  border: 1px solid #ccc; 
}

.page-item.active .page-link {
 z-index: 1;
 color: #555;
 font-weight:bold;
 background-color: #f1f1f1;
 border-color: #ccc;
 
}

.page-link:focus, .page-link:hover {
  color: #000;
  background-color: #fafafa; 
  border-color: #ccc;
}

.new { height: 15px; padding-left: 3px; padding-bottom: 3px; }

.table-light { text-align: center; }


ul{
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
	<jsp:include page="/WEB-INF/views/layout/header.jsp"/>
</header>
	
<main>
<section>
	<div class="container-fluid">
		<div class="row" style="line-height: 1.5rem">&nbsp;</div>
		<div class="row">
			<div class="col-lg-1 bg-dark bg-gradient" >
			<!-- brief 사이드바 자리 -->
			<jsp:include page="/WEB-INF/views/layout/brief_sidebar.jsp"/>
			</div>
			<div class="col-lg-11 ms-auto">
			
			<!-- classroom header 자리 -->
			<div class="row">
			<jsp:include page="/WEB-INF/views/layout/classroom_header.jsp"/>
			</div>
			<div class="row">
			<!-- 강의 사이드바 자리 -->
			<div class="col-xl-2 col-md-2 col-lg-2 bg-black bg-gradient" style="box-shadow: none;">
			<jsp:include page="/WEB-INF/views/layout/lecture_sidebar.jsp"/>
			</div>
			
			<!-- 본문 -->
			<div class="col-lg-10 gap-3 ms-auto">
				<div class="ms-5 me-5 pt-3 mt-4 mb-5">
				
			<div>
				<h3><i class="fas fa-microphone fa-1x"></i> 공지사항 </h3>
			</div>
			
			<div>
		        <div class="row board-list-header">
		            <div class="col-auto me-auto">${dataCount}개(${page}/${total_page} 페이지)</div>
		            <div class="col-auto">&nbsp;</div>
		        </div>				
		<div>
				<table  class= "card table text-center">
					
						<tr class="row">
							<th class="col-1">번호</th>
							<th class="col-7">제목</th>
							<th class=" col-1" >작성자</th>
							<th class=" col-2">작성일</th>
							<th class="col-1">조회수</th>
						</tr>
					
					<c:forEach var="dto" items="${list}" varStatus="status">
						<tr>
							<td class="w-10" >${dataCount - (page-1) * size - status.index}</td>
							<td class="left w-70">
								<a href="${pageContext.request.contextPath}/notice/noticeArticle.do?subjectNo=${subjectNo}&articleNo=${dto.articleNo}" class="text-reset ps-4">${dto.title}</a>
								<c:if test="${dto.gap<2}"><img src="${pageContext.request.contextPath}/resources/images/new.png" class="new"></c:if>
							</td>
							<td class="w-5"  >${dto.name}</td>
							<td class="w-10" >${dto.reg_date}</td>
							<td class="w-5" >${dto.hitCount}</td>
						</tr>
					</c:forEach>
					
				</table>
		</div>		
				<div class="page-navigation">
					${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
				</div>

				<div class="row board-list-footer">
					<div class="col">
						<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/notice/notice.do?subjectNo=${subjectNo}';">새로고침</button>
					</div>
					<div class="col-6 text-center">
						<form class="row" name="searchForm" action="${pageContext.request.contextPath}/notice/notice.do" method="post">
							<div class="col-auto p-1">
								<select name="condition" class="form-select">
									<option value="all" ${condition=="all"?"selected='selected'":""}>제목+내용</option>
									<option value="reg_date" ${condition=="reg_date"?"selected='selected'":""}>등록일</option>
									<option value="title" ${condition=="title"?"selected='selected'":""}>제목</option>
									<option value="content" ${condition=="content"?"selected='selected'":""}>내용</option>
								</select>
							</div>
							<div class="col-auto p-1">
								<input type="text" name="keyword" value="${keyword}" class="form-control">
							</div>
							<div class="col-auto p-1">
								<input type="hidden" name="subjectNo" value="${subjectNo}">
								<button type="button" class="btn btn-light" onclick="searchList()"> <i class="bi bi-search"></i> </button>
							</div>
						</form>
					</div>
					<div class="col text-end">
							<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/notice/noticeWrite.do?subjectNo=${subjectNo }';">글올리기</button>
					</div>
				</div>

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
<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>