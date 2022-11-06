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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board2.css" type="text/css">

<script type="text/javascript">
function sendOk() {
    const f = document.boardForm;
	let str;
	
    str = f.content.value.trim();
    if(!str) {
        alert("내용을 입력하세요. ");
        f.content.focus();
        return;
    }

	f.action = "${pageContext.request.contextPath}/qna/${mode}_ok.do"; 
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
					<div class="col-auto bg-dark bg-gradient rounded" style="min-height: 100vh">
						<!-- 왼쪽 사이드바 자리 -->
						<jsp:include page="/WEB-INF/views/layout/brief_sidebar.jsp" />
					</div>
					<div class="col">
						<jsp:include page="/WEB-INF/views/layout/header2.jsp" />
						<jsp:include page="/WEB-INF/views/layout/classroom_header.jsp" />
						<jsp:include page="/WEB-INF/views/layout/lecture_index.jsp" />
						
					<!-- 본문 -->
					<div class="card">
						<div class= "card-header fw-bold">
							<h3><i class="fas fa-clipboard-question  fa-lg mr-3"></i> 답변등록 </h3>
						</div>
						
						<div class="card-body">
						
							<form name="boardForm" method="post"  enctype="multipart/form-data">
								<table class="table write-form mt-5">
									<tr>
										<td class="table-light col-sm-2" scope="row">질문 제목</td>
										<td>
											<p class="form-control-planntext">${dto.title}</p>
										</td>
									</tr>
				        
									<tr>
										<td class="table-light col-sm-2" scope="row">질문 학생</td>
				 						<td>
											<p class="form-control-plaintext">${dto.name}</p>
										</td>
									</tr>
				
									<tr>
										<td class="table-light col-sm-2">질문 내용</td>
										<td>
											<p class="form-control-planntext" style="white-space: pre-wrap;">${dto.content}</p>
										</td>
									</tr>
									
									<tr>
										<td class="table-light col-sm-2">답변 내용</td>
										<td>
											<textarea name="content" class="form-control">${replyDto.content}</textarea>
										</td>
									</tr>
									
								
								</table>
								
								<table class="table table-borderless">
				 					<tr>
										<td class="text-center">
											<button type="button" class="btn btn-dark" onclick="sendOk();">${mode=='qnaReplyUpdate'?'수정완료':'답변등록'}&nbsp;<i class="bi bi-check2"></i></button>
											<button type="reset" class="btn btn-light">다시입력</button>
											<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/qna/qna.do?subjectNo=${subjectNo}';">${mode=='Update'?'수정취소':'등록취소'}&nbsp;<i class="bi bi-x"></i></button>
											<input type="hidden" name="subjectNo" value="${subjectNo}">
											<input type="hidden" name="articleNo" value="${articleNo}">
											<input type="hidden" name="page" value="${page}">
											<c:if test="${mode=='qnaReplyUpdate'}">
												<input type="hidden" name="replyNo" value="${replyDto.replyNo}">
											</c:if>
										</td>
									</tr>
								</table>
							</form>
						
						</div>
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

