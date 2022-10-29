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
.body-container {
	max-width: 800px;
}
</style>

<script type="text/javascript">
	function searchList() {
		const f = document.searchForm
		f.submit();
	}
	
	
	
	function ajaxFun(url, method, query, dataType, fn) {
		$.ajax({
			type:method,
			url:url,
			data:query,
			dataType:dataType,
			success:function(data){
				fn(data);
			},
			beforeSend:function(jqXHR) {
				// jqXHR.setRequestHeader("AJAX", true);
			},
			error:function(jqXHR) {
				console.log(jqXHR.responseText);
			}
		});
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
					<div class="col-xl-10 col-lg-9 col-md-8">
						<div class="card pt-2 pb-2 ps-2 pe-2" style="margin: 50px 0px;">
							<h5 class="card-header">조교신청</h5>
							<div class="card-body">
								<form class="row" name="searchForm"
									action="${pageContext.request.contextPath}/mypage/assistant.do"
									method="post">

									<div class="s-1" style="display: inline-block; width: 150px;">
										<select class="form-select" id="syear">
											<option value="1">2023년</option>
										</select>
									</div>

									<div class="s-1" style="display: inline-block;  width: 150px;">
										<select class="form-select" id="semester">
											<option value="1">1학기</option>
											<option value="2">2학기</option>
										</select>
									</div>
									<div class="s-1" style="display: inline-block;  width: 150px;">
										<select class="form-select" id="subjectName">
											<option value="subjectName"
												${condition=="subjectName"?"selected='selected'":""}>강좌명</option>
										</select>
									</div>
									<div class="col-auto p-1" style="display: inline-block;  width: 150px;">
										<input type="text" name="keyword" value="${keyword}"
											class="form-control">
									</div>
									<div class="col-auto p-1" style="display: inline-block;  width: 150px;">
										<button type="button" class="btn btn-light"
											onclick="searchList()">
											<i class="bi bi-search"></i>
										</button>
									</div>

                   
							<table class="table table-hover board-list" style="margin-top: 50px;">
								<thead class="table-light">
									<tr style="text-align: center;">
										<th class="year" width="60px;">연도</th>
										<th class="semester" width="60px;">학기</th>
										<th class="department" width="150px;">학과</th>
										<th class="subname">과목명</th>
										<th class="prof" width="80px;">교수</th>
										<th class="apply" width="200px;">조교신청</th>
									</tr>
								</thead>

								<tbody>
									<c:forEach var="dto" items="${list}" varStatus="status">
										<tr>
											<td>${dto.year}</td>
											<td>${dto.semester}</td>
											<td>${dto.department}</td>
											<td>${dto.subject}</td>
											<td>${dto.professor}</td>
											<td><button type="submit" class="btn btn-light"
													onclick="location.href='${pageContext.request.contextPath}/mypage/assistant_ok.do?subjectNo=${dto.subjectNo}';">신청</button></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>


							<div class="page-navigation">${dataCount == 0 ? "신청할 과목이 없습니다." : paging}
							</div>




								</form>
							</div>
						</div>



						<div class="card pt-2 pb-2 ps-2 pe-2" style="margin: 50px 0px;">
							<h5 class="card-header">신청내역 · 승인내역</h5>
							<div class="card-body">

									<table class="table table-hover board-list" style="margin-top: 50px;">
								<thead class="table-light">
									<tr style="text-align: center;">
										<th class="year" width="60px;">연도</th>
										<th class="semester" width="60px;">학기</th>
										<th class="department" width="150px;">학과</th>
										<th class="subname">과목명</th>
										<th class="prof" width="80px;">교수</th>
										<th class="apply" width="200px;">승인여부</th>
									</tr>
								</thead>

								<tbody>
									<c:forEach var="dto" items="${alist}" varStatus="status">
										<tr>
											<td >${dto.year}</td>
											<td>${dto.semester}</td>
											<td>${dto.department}</td>
											<td>${dto.subject}</td>
											<td>${dto.professor}</td>
											<td>${dto.ENABLE == "1" ? "승인":"반려"}</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>

							</div>
						</div>

					</div>
					<!-- 본문 끝 -->
				</div>
			</div>
		</section>
	</main>
</body>
</html>