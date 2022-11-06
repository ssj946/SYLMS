<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
</style>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board2.css" type="text/css">

<script type="text/javascript">
<c:if test="${sessionScope.member.userId=='admin'}">
	function deleteBoard() {
	    if(confirm("게시글을 삭제 하시 겠습니까 ? ")) {
		    let query = "num=${dto.num}&${query}";
		    let url = "${pageContext.request.contextPath}/haksa/delete.do?" + query;
	    	location.href = url;
	    }
	}
</c:if>
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
					<div class="col-12">
			<!-- 본문 -->
			<div class="col-lg-12">
			<div class="card mt-3">
					<div class= "card-header fw-bold">
						<h3><i class="fa-solid fa-landmark fa-1x"></i> 학사공지 </h3>
				    </div>
				<div class="card-body py-3">  
				
				<table class="table">
					<thead>
						<tr>
							<td colspan="2" align="center">
								${dto.subject}
							</td>
						</tr>
					</thead>
					
					<tbody>
						<tr>
							<td width="50%">
								이름 : ${dto.userName}
							</td>
							<td align="right">
								${dto.reg_date} | 조회 ${dto.hitCount}
							</td>
						</tr>
						
						<tr>
							<td colspan="2" valign="top" height="200">
								${dto.content}
							</td>
						</tr>
						
						<c:forEach var="vo" items="${listFile}">
							<tr>
								<td colspan="2">
									파&nbsp;&nbsp;일 :
									<a href="${pageContext.request.contextPath}/haksa/download.do?fileNum=${vo.fileNum}">${vo.originalFilename}</a>
								</td>
							</tr>
						</c:forEach>
						
						<tr>
							<td colspan="2"  style="border-top-width:medium;  border-top-color: #5a5a5a;" >
								이전글 :
								<c:if test="${not empty preReadDto}">
									<a href="${pageContext.request.contextPath}/haksa/article.do?${query}&num=${preReadDto.num}">${preReadDto.subject}</a>
								</c:if>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								다음글 :
								<c:if test="${not empty nextReadDto}">
									<a href="${pageContext.request.contextPath}/haksa/article.do?${query}&num=${nextReadDto.num}">${nextReadDto.subject}</a>
								</c:if>
							</td>
						</tr>
						
					</tbody>
				</table>
				
				<table class="table table-borderless">
					<tr>
						<td width="50%">
							<c:if test="${sessionScope.member.userId==dto.userId}">
								<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/haksa/update.do?num=${dto.num}&page=${page}&size=${size}';">수정</button>
							</c:if>
					    	
				    		<c:if test="${sessionScope.member.userId=='admin'}">
				    			<button type="button" class="btn btn-light" onclick="deleteBoard();">삭제</button>
				    		</c:if>
						</td>
						<td class="text-end">
							<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/haksa/list.do?${query}';">리스트</button>
						</td>
					</tr>
				</table>
				
			</div>
			</div>
			</div>
			</div>
			</div>
			</div>
			</div>
			</div>
			</div>
		</div>
	</div>
	</section>
</main>

<footer>
	<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</footer>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>