<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>spring</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp"/>

<style type="text/css">
.body-container {
	max-width: 800px;
}
</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board2.css" type="text/css">

<script type="text/javascript">
function sendOk() {
    const f = document.noticeForm;
	let str;
	
    str = f.subject.value.trim();
    if(!str) {
        alert("제목을 입력하세요. ");
        f.subject.focus();
        return;
    }

    str = f.content.value.trim();
    if(!str) {
        alert("내용을 입력하세요. ");
        f.content.focus();
        return;
    }

    f.action = "${pageContext.request.contextPath}/haksa/${mode}_ok.do";
    f.submit();
}

<c:if test="${mode=='update'}">
function deleteFile(fileNum) {
	if(! confirm("파일을 삭제 하시겠습니까 ?")) {
		return;
	}
	
	let query = "num=${dto.num}&fileNum=" + fileNum + "&page=${page}&size=${size}";
	let url = "${pageContext.request.contextPath}/haksa/deleteFile.do?" + query;
	location.href = url;
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
				<form name="noticeForm" method="post" enctype="multipart/form-data">
					<table class="table write-form mt-5">
						<tr>
							<td class="table-light col-sm-2" scope="row">제 목</td>
							<td>
								<input type="text" name="subject" class="form-control" value="${dto.subject}">
							</td>
						</tr>

						<tr>
							<td class="table-light col-sm-2" scope="row">공지여부</td>
							<td>
								<input type="checkbox" class="form-check-input" name="notice" id="notice" value="1" ${dto.notice==1 ? "checked='checked' ":"" } >
								<label class="form-check-label" for="notice"> 공지</label>
							</td>
						</tr>
	        
						<tr>
							<td class="table-light col-sm-2" scope="row">작성자명</td>
	 						<td>
								<p class="form-control-plaintext">${sessionScope.member.userName}</p>
							</td>
						</tr>
	
						<tr>
							<td class="table-light col-sm-2" scope="row">내 용</td>
							<td>
								<textarea name="content" id="content" class="form-control">${dto.content}</textarea>
							</td>
						</tr>
						
						<tr>
							<td class="table-light col-sm-2">첨&nbsp;&nbsp;&nbsp;&nbsp;부</td>
							<td> 
								<input type="file" name="selectFile" multiple="multiple" class="form-control">
							</td>
						</tr>
						
						<c:if test="${mode=='update'}">
							<c:forEach var="vo" items="${listFile}">
								<tr>
									<td class="table-light col-sm-2" scope="row">첨부된파일</td>
									<td> 
										<p class="form-control-plaintext">
											<a href="javascript:deleteFile('${vo.fileNum}');"><i class="bi bi-trash"></i></a>
											${vo.originalFilename}
										</p>
									</td>
								</tr>
							</c:forEach> 
						</c:if>
						
					</table>
					
					<table class="table table-borderless">
	 					<tr>
							<td class="text-center">
								<button type="button" class="btn btn-dark" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}&nbsp;<i class="bi bi-check2"></i></button>
								<button type="reset" class="btn btn-light">다시입력</button>
								<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/haksa/list.do?size=${size}';">${mode=='update'?'수정취소':'등록취소'}&nbsp;<i class="bi bi-x"></i></button>
								<input type="hidden" name="size" value="${size}">
								<c:if test="${mode=='update'}">
									<input type="hidden" name="num" value="${dto.num}">
									<input type="hidden" name="page" value="${page}">
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