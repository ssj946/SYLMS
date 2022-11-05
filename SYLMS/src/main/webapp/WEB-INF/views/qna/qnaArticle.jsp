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

<c:if test="${fn:length(sessionScope.member.userId) != 8 }">
function replyDelete() {
	if(confirm("답변을 삭제하시겟습니까 ? ")) {
		let url = "${pageContext.request.contextPath}/qna/qnaReplyDelete.do?replyNo=${replyDto.replyNo}&page=${page}&subjectNo=${subjectNo}";
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
			<div class="col-lg-10 gap-3 ms-auto">
				<div class="ms-5 me-5 pt-3 mt-4 mb-5">
					<div class="card">
						<div class= "card-header fw-bold">
							<h3><i class="fas fa-clipboard-question  fa-lg mr-3"></i> 질문과 답변 </h3>
						</div>
						
						<div class="card-body">
								
							<table class="table">
								<thead>
									<tr>
										<td colspan="2" align="center">
											<div class="row">
												<div class="col-sm-1 bg-primary me-1">
													<p class="form-control-plaintext text-white">Q.</p>
												</div>
												<div class="col bg-primary">
													<p class="form-control-plaintext text-white text-start">${dto.title }</p>
												</div>
											</div>
										</td>
									</tr>
								</thead>
								
								<tbody>
									<tr>
										<td width="50%">
											이름 : ${dto.name}
										</td>
										<td align="right">
											문의 일자 : ${dto.reg_date}
										</td>
									</tr>
									<tr>
										<td colspan="2" valign="top" height="130">
											${dto.content}
										</td>
									</tr>
								</tbody>
							</table>
							
							<c:if test="${not empty replyDto}">
								<table class="table">
									<thead>
										<tr>
											<td colspan="2" align="center">
												<div class="row">
													<div class="col-sm-1 bg-primary me-1">
														<p class="form-control-plaintext text-white">A.</p>
													</div>
													<div class="col bg-primary">
														<p class="form-control-plaintext text-white text-start">${dto.title}</p>
													</div>
												</div>
											</td>
										</tr>
									</thead>
									
									<tbody>
										<tr>
											<td width="50%">
												이름 : ${replyDto.name}
											</td>
											<td align="right">
												답변 일자 : ${replyDto.reg_date}
											</td>
										</tr>
										<tr>
											<td colspan="2" valign="top" height="130">
												${replyDto.content}
											</td>
										</tr>
									</tbody>
								</table>
							</c:if>
									
							<table class="table">	
								<tbody>
									<tr>
										<td colspan="2">
											이전글 :
											<c:if test="${not empty preReadDto}">
												<a href="${pageContext.request.contextPath}/qna/qnaArticle.do?${query}&articleNo=${preReadDto.articleNo}">${preReadDto.title}</a>
											</c:if>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											다음글 :
											<c:if test="${not empty nextReadDto}">
												<a href="${pageContext.request.contextPath}/qna/qnaArticle.do?${query}&articleNo=${nextReadDto.articleNo}">${nextReadDto.title}</a>
											</c:if>
										</td>
									</tr>
								</tbody>
							</table>
							
							<table class="table table-borderless">
								<tr>
									<td width="50%">
										<c:if test="${fn:length(sessionScope.member.userId) == 8 }">
											<c:if test="${empty replyDto}">
												<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/qna/qnaUpdate.do?articleNo=${dto.articleNo}&page=${page}&subjectNo=${subjectNo}';">질문수정</button>
											</c:if>
											<c:if test="${not empty replyDto}">
												<button type="button" class="btn btn-light" disabled="disabled">질문수정</button>
											</c:if>
											<button type="button" class="btn btn-light" onclick="deleteBoard();">질문삭제</button>
										</c:if>
										
										<c:if test="${fn:length(sessionScope.member.userId) != 8 }">
											<c:choose>
									    		<c:when test="${sessionScope.member.userId=='admin'}">
									    			<button type="button" class="btn btn-light" onclick="deleteBoard();">질문삭제</button>
									    		</c:when>
									    		<c:otherwise>
									    			<c:if test="${not empty replyDto}">
									    				<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/qna/qnaReplyUpdate.do?replyNo=${replyDto.replyNo}&articleNo=${dto.articleNo}&page=${page}&subjectNo=${subjectNo}';">답변수정</button>
									    				<button type="button" class="btn btn-light" onclick="replyDelete();">답변삭제</button>
									    			</c:if>
									    			<c:if test="${empty replyDto}">
									    				<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/qna/qnaReply.do?articleNo=${dto.articleNo}&page=${page}&subjectNo=${subjectNo}';">답변</button>
									    			</c:if>
									    		</c:otherwise>
									    	</c:choose>
										</c:if>
									</td>
									<td class="text-end">
										<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/qna/qna.do?${query}';">리스트</button>
									</td>
								</tr>
							</table>
						
						
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
<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>