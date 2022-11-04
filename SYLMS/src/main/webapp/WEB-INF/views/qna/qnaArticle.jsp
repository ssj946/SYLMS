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

ul{

list-style: none;
}
</style>

<script type="text/javascript">
<c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">
function deleteBoard() {
    if(confirm("게시글을 삭제 하시 겠습니까 ? ")) {
	    let query = "articleNo=${dto.articleNo}&${query}";
	    let url = "${pageContext.request.contextPath}/qna/delete.do?" + query;
    	location.href = url;
    }
}
</c:if>
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
				<div class="col-xl-10 col-md-10 col-lg-10 gap-3 ms-auto">
					<div class="ms-1 me-1 pt-3 mt-3 mb-5">
					
				<div class="body-title">
					<h3><i class="fas fa-clipboard-question  fa-lg mr-3"></i> 질문과 대답 </h3>
				</div>
				
				<div class="body-main">
					
					<table class="table">
				
						<thead>
							<tr>
								<td colspan="2" align="center">
									${dto.title}
								</td>
							</tr>
						</thead>
						
						<tbody>
							<tr>
								<td width="50%">
									이름 : ${dto.name}
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
							<tr>
								<td colspan="2">
									이전글 :
									<c:if test="${not empty preReadDto}">
										<a href="${pageContext.request.contextPath}/qna/qnaArticle.do?subjectNo=${subjectNo}&articleNo=${preReadDto.articleNo}">${preReadDto.title}</a>
									</c:if>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									다음글 :
									<c:if test="${not empty nextReadDto}">
										<a href="${pageContext.request.contextPath}/qna/qnaArticle.do?subjectNo=${subjectNo}&articleNo=${nextReadDto.articleNo}">${nextReadDto.title}</a>
									</c:if>
								</td>
							</tr>
						</tbody>
					</table>
					<table class="table table-borderless">
						<tr>
							<td width="50%">
								
								<c:choose>
									<c:when test="${sessionScope.member.userId==dto.userId}">
										<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/lecture/notice/update.do?articleNo=${dto.articleNo}&page=${page}';">수정</button>
									</c:when>
									<c:otherwise>
										<button type="button" class="btn btn-light" disabled="disabled">수정</button>
									</c:otherwise>
								</c:choose>
						    	
								<c:choose>
						    		<c:when test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">
						    			<button type="button" class="btn btn-light" onclick="deleteBoard();">삭제</button>
						    		</c:when>
						    		<c:otherwise>
						    			<button type="button" class="btn btn-light" disabled="disabled">삭제</button>
						    		</c:otherwise>
						    	</c:choose>
							</td>
							<td class="text-end">
								<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/qna/qna.do?subjectNo=${subjectNo}';">리스트</button>
							</td>
						</tr>
					</table>
					
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
<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>