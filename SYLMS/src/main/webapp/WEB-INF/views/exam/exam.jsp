<%@ page contentType="text/html; charset=UTF-8"%>
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
ul {
	list-style: none;
}
</style>

<script type="text/javascript">
function sendOk() {
	const f = document.updateForm;
	
	str = f.subjectNo.value;
	if (!f.subjectNo.value.trim()) {
		alert("과목코드를 입력 하세요. ");
		f.subjectNo.focus();
		return;
	}
	
	f.action = "${pageContext.request.contextPath}/exam/send.do";
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
					<div class="col-xl-10 col-md-10 col-lg-10 gap-3 ms-auto ">
						<div class="ms-1 me-1 pt-3 mt-3 mb-3">
							<div class="card-body p-4 text-center">
								<c:if test="${fn:length(sessionScope.member.userId) != 8}">
									<div class="row">
										<div class="col-3">&nbsp;</div>
										<div class="col-6">
											<div class="card">
											<form name="updateForm" method="post" action="${pageContext.request.contextPath}/exam/send.do">
												<div class="card-header bg-navy bg-gradient text-white">
													<h5>과목코드 입력</h5>
												</div>
												<div class="card-body">
													<br> <input name="subjectNo" class="form-control" placeholder="과목코드를 입력하세요." value="${dto.subjectNo}">
													<br>
													<button type="button" class="btn btn-outline-primary codeBtn" onclick="sendOk()">시험점수 입력하러가기</button>
													<br>
												</div>
												</form>
											</div>
										</div>
										<div class="col-3">&nbsp;</div>
									</div>
									<br>
									<br>
								</c:if>
							</div>
							<!-- 본문 끝 -->
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
	<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>
</html>