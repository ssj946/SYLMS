<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

	<header>
		<jsp:include page="/WEB-INF/views/layout/header.jsp" />
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

								<div class="title">
									<h3>
										<span>|</span> 쪽지
									</h3>
								</div>

								<table class="table table-border table-article">
									<thead>
										<tr>
											<td colspan="2" align="center">${dto.sendName}님이 보낸 쪽지</td>
										</tr>
									</thead>

									<tbody>
										<tr>
											<td width="50%">이름 : ${dto.sendId }</td>
											<td align="right">${dto.sendDate }</td>
										</tr>

										<tr>
											<td colspan="2" valign="top" height="200">${dto.content }
											</td>
										</tr>


									</tbody>
								</table>

								<div class="page-navigation">${dataCount == 0 ? "쪽지가 없습니다." : paging}
								</div>

								<div class="row board-list-footer">
									<div class="col text-end">
										<button type="button" class="btn btn-light"
											onclick="location.href='${pageContext.request.contextPath}/messege/receive.do?${query}';">뒤로가기</button>
										<button type="button" class="btn btn-light"
											onclick="location.href='${pageContext.request.contextPath}/messege/send.do';">쪽지보내기</button>
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
	<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>
</html>