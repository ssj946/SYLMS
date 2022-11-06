﻿<%@ page contentType="text/html; charset=UTF-8"%>
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
</style>
<script type="text/javascript">
$(function(){
	const now = new Date();
	let year = now.getFullYear()*1+1;
	$("#syear").append("<option value="+year+">"+year+"년</option>");
});
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
							<div class="col-9">
					<!-- 본문 -->
							<c:if test="${fn:length(sessionScope.member.userId) == 8}">
							<div class="card mt-3">
								<h5 class="card-header bg-navy bg-gradient text-light">조교신청</h5>
								<div class="card-body">
									<form class="row" name="searchForm" action="${pageContext.request.contextPath}/mypage/assistant.do" method="post">
										<div class="d-flex justify-content-center align-items-center">
											<div class="col-auto p-1">
												<select class="form-select" name="year" id="syear">
													
												</select>
										   	</div>

											<div class="col-auto p-1" >
												<select class="form-select" name="semester" id="semester">
													<option value="1" ${tsemester==1?"selected='selected'":""}>1학기</option>
													<option value="2" ${tsemester==2?"selected='selected'":""}>2학기</option>
												</select>
											</div>
											<div class="col p-1">
												<input type="text" name="keyword" value="${keyword}"
													class="form-control" placeholder="강좌명">
											</div>
											<div class="col-auto p-1">
												<button type="submit" class="btn btn-light applybtn">
												<i class="bi bi-search"></i></button>
	
											</div>
                                        </div>
											</form>
                                        <div class="card p-2 m-2">
										<table class="table table-hover board-list ho-list text-center">
										
												<tr class="bg-navy bg-gradient text-light">
													<th class="year" width="5%">연도</th>
													<th class="semester" width="5%">학기</th>
													<th class="department" width="15%">학과</th>
													<th class="subname">과목명</th>
													<th class="prof" width="10%">교수</th>
													<th class="apply" width="20%">조교신청</th>
												</tr>
						
											<tbody>
												<c:forEach var="dto" items="${list}" varStatus="status">
													<tr>
														<td>${dto.year}</td>
														<td>${dto.semester}</td>
														<td>${dto.department}</td>
														<td>${dto.subject}</td>
														<td>${dto.professor}</td>
														<td><button type="submit" class="btn btn-light"
																onclick=" if(confirm('조교신청을 하시겠습니까?')) location.href='${pageContext.request.contextPath}/mypage/assistant_ok.do?subjectNo=${dto.subjectNo}&mode=apply';">신청</button></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<div class="page-navigation">${dataCount == 0 ? "신청할 과목이 없습니다." : paging}
										</div>
										</div>

									
								</div>
							</div>
						</c:if>
						<c:if test="${fn:length(sessionScope.member.userId) != 8}">
							<div class="card mt-3">
								<h5 class="card-header bg-navy bg-gradient text-light">조교신청내역</h5>
								<div class="card-body">
								<form method="post" class="row" name="searchForm" action="${pageContext.request.contextPath}/mypage/assistant.do">
								<div class="d-flex justify-content-center align-items-center">
									<div class="col-auto p-1">
										<select class="col-auto form-select" id="syear" name="year">
											<option value="1">2023년</option>
										</select>
									</div>

									<div class="col-auto p-1">
										<select class="form-select" id="semester" name="semester">
											<option value="1">1학기</option>
											<option value="2">2학기</option>
										</select>
									</div>
										<div class="col p-1">
											<input type="text" name="keyword" value="${keyword}"
												class="form-control" placeholder="강좌명">
										</div>
									<div class="col-auto p-1">
										<button type="submit" class="btn btn-light">
											<i class="bi bi-search"></i>
										</button>
									</div>
									</div>
									</form>
									<div class="card p-2 m-2">
									<table class="table table-hover board-list text-center">
									
											<tr class="bg-navy bg-gradient text-white">
												<th class="year" width="5%">연도</th>
												<th class="semester" width="5%">학기</th>
												<th class="department" width="15%">학과</th>
												<th class="subname">과목명</th>
												<th class="name" width="10%">이름</th>
												<th class="reg_date" width="15%">신청일</th>
												<th class="station" width="10%">상태</th>
												<th class="apply" width="15">조교신청</th>
											</tr>
							
										<tbody>
											<c:forEach var="dto" items="${aplist}" varStatus="status">
												<tr>
													<td>${dto.year}</td>
													<td>${dto.semester}</td>
													<td>${dto.department}</td>
													<td>${dto.subject}</td>
													<td>${dto.name}</td>
													<td>${dto.reg_date}</td>
													<td>${dto.ENABLE == "0" ? "신청":""}</td>
													<td><button type="submit" class="btn btn-light"
															onclick=" if(confirm('승인 하시겠습니까?'))  location.href='${pageContext.request.contextPath}/mypage/assistant_ok.do?applicationNum=${dto.applicationNum}&mode=approve';">승인</button></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
									<div class="page-navigation">${dataCount == 0 ? "승인할 신청목록이 없습니다." : paging}
									</div>
									</div>
								</div>
							</div>
						</c:if>
						<c:if test="${fn:length(sessionScope.member.userId) == 8 }">
							<div class="card mt-3">
								<h5 class="card-header bg-navy bg-gradient text-light">신청내역 · 승인내역</h5>
								<div class="card-body">
								<div class="card p-2">
									<table class="table table-hover board-list text-center">
							
											<tr class="bg-navy bg-gradient text-white">
												<th class="year" width="5%">연도</th>
												<th class="semester" width="5%">학기</th>
												<th class="department" width="15%">학과</th>
												<th class="subname">과목명</th>
												<th class="prof" width="10%">교수</th>
												<th class="apply" width="10%">상태</th>
											</tr>
							
	
										<tbody>
											<c:forEach var="dto" items="${alist}" varStatus="status">
												<tr>
													<td>${dto.year}</td>
													<td>${dto.semester}</td>
													<td>${dto.department}</td>
													<td>${dto.subject}</td>
													<td>${dto.professor}</td>
													<c:if test="${dto.ENABLE == 1 }">
														<td>승인</td>
													</c:if>
													<c:if test="${dto.ENABLE == 2 }">
														<td>반려</td>
													</c:if>
													<c:if test="${dto.ENABLE == 0 }">
														<td>신청</td>
													</c:if>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
								</div>
							</div>
						</c:if>
						<c:if test="${fn:length(sessionScope.member.userId) != 8}">
							<div class="card mt-3">
								<h5 class="card-header bg-navy bg-gradient text-light">조교내역</h5>
								<div class="card-body">
									<form class="row" name="searchForm" 
										action="${pageContext.request.contextPath}/mypage/assistant.do"
										method="post">
										<div class="d-flex justify-content-center align-items-center ">
										<div class="col-auto p-1">
											<select class="form-select" id="syear" name="year">
												<option value="1">2023년</option>
											</select>
										</div>

										<div class="col-auto p-1">
											<select class="form-select" id="semester" name="semester">
												<option value="1">1학기</option>
												<option value="2">2학기</option>
											</select>
										</div>
										<div class="col p-1">
											<input type="text" name="keyword" value="${keyword}" class="form-control" placeholder="강좌명">
										</div>
										<div class="col-auto p-1">
											<button type="submit" class="btn btn-light" onclick="searchList()"><i class="bi bi-search"></i>
											</button>
										</div>
										</div>
									</form>
										<div class="card p-2 m-2">
										<table class="table table-hover board-list text-center" >
												<tr class="bg-navy bg-gradient text-light">
													<th class="year" width="5%">연도</th>
													<th class="semester" width="5%">학기</th>
													<th class="department" width="15%">학과</th>
													<th class="subname">과목명</th>
													<th class="name" width="10%">이름</th>
													<th class="station" width="10%">상태</th>
													<th class="apply" width="15%">조교신청</th>
												</tr>
											<tbody>
												<c:forEach var="dto" items="${aslist}" varStatus="status">
													<tr>
														<td>${dto.year}</td>
														<td>${dto.semester}</td>
														<td>${dto.department}</td>
														<td>${dto.subject}</td>
														<td>${dto.name}</td>
														<c:if test="">

														</c:if>
														<td>${dto.ENABLE == "1" ? "승인":""}</td>
														<td><button type="submit" class="btn btn-light"
																onclick="  if(confirm('조교자격을 취소하시겠습니까?')) location.href='${pageContext.request.contextPath}/mypage/assistant_ok.do?applicationNum=${dto.applicationNum}&mode=cancel';">취소</button></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
										<div class="page-navigation">${dataCount == 0 ? "확인할 조교가 없습니다." : paging}
										</div>
										</div>
								</div>
							</div>
						</c:if>
						</div>
							<!-- 오른쪽 사이드바 자리 -->
						<div class="col-3 mt-3"><jsp:include page="/WEB-INF/views/layout/r_sidebar.jsp" /></div>
					</div>
					</div>
					</div>
				</div>
				<div class="col-1"></div>
				</div>
			</div>
		</div>
				
				<!-- 본문 끝 -->
	</section>
</main>
<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>
</html>