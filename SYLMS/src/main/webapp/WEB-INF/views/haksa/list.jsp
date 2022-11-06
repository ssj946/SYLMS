﻿<%@ page contentType="text/html; charset=UTF-8" %>
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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board2.css" type="text/css">

<script type="text/javascript">
function changeList() {
    const f = document.listForm;
    f.page.value="1";
    f.action="${pageContext.request.contextPath}/haksa/list.do";
    f.submit();
}

function searchList() {
	const f = document.searchForm;
	f.submit();
}

<c:if test="${sessionScope.member.userId=='admin'}">
$(function(){
	$("#chkAll").click(function(){
		if($(this).is(":checked")) {
			$("input[name=nums]").prop("checked", true);
		} else {
			$("input[name=nums]").prop("checked", false);
		}
	});
	
	$("#btnDeleteList").click(function(){
		let cnt = $("input[name=nums]:checked").length;
		if(cnt===0) {
			alert("삭제할 게시물을 먼저 선택하세요.");
			return false;
		}
		
		if(confirm("선택한 게시물을 삭제 하시겠습니까 ?")) {
			const f = document.listForm;
			f.action="${pageContext.request.contextPath}/haksa/deleteList.do";
			f.submit();
		}
	});
});
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
				<div class="card-body py-5">    
				<form name="listForm" method="post" >
			        <div class="row board-list-header">
			            <div class="col-auto me-auto">
							<c:if test="${sessionScope.member.userId=='admin'}">
								<button type="button" class="btn btn-light" id="btnDeleteList" title="삭제"><i class="bi bi-trash"></i></button>
							</c:if>
							<c:if test="${sessionScope.member.userId!='admin'}">
								<p class="form-control-plaintext">
									${dataCount}개(${page}/${total_page} 페이지)
								</p>
							</c:if>
			            </div>
			            <div class="col-auto">
							<c:if test="${dataCount!=0 }">
								<select name="size" class="form-select" onchange="changeList();">
									<option value="5"  ${size==5 ? "selected='selected' ":""}>5개씩 출력</option>
									<option value="10" ${size==10 ? "selected='selected' ":""}>10개씩 출력</option>
									<option value="20" ${size==20 ? "selected='selected' ":""}>20개씩 출력</option>
									<option value="30" ${size==30 ? "selected='selected' ":""}>30개씩 출력</option>
									<option value="50" ${size==50 ? "selected='selected' ":""}>50개씩 출력</option>
								</select>
							</c:if>
							<input type="hidden" name="page" value="${page}">
							<input type="hidden" name="condition" value="${condition}">
							<input type="hidden" name="keyword" value="${keyword}">
						</div>
			        </div>				
					
					<table class="table table-hover board-list">
						<thead class="table-light">
							<tr>
								<c:if test="${sessionScope.member.userId=='admin'}">
									<th class="chk">
										<input type="checkbox" class="form-check-input" name="chkAll" id="chkAll">        
									</th>
								</c:if>
								<th class="num">번호</th>
								<th class="subject">제목</th>
								<th class="name">작성자</th>
								<th class="date">작성일</th>
								<th class="hit">조회수</th>
							</tr>
						</thead>
						
						<tbody>
							<c:forEach var="dto" items="${listNotice}">
								<tr>
									<c:if test="${sessionScope.member.userId=='admin'}">
										<td>
											<input type="checkbox" class="form-check-input" name="nums" value="${dto.num}">
										</td>
									</c:if>
									<td><span class="badge bg-primary">공지</span></td>
									<td class="left">
										<a href="${articleUrl}&num=${dto.num}" class="text-reset">${dto.subject}</a>
									</td>
									<td>${dto.userName}</td>
									<td>${dto.reg_date}</td>
									<td>${dto.hitCount}</td>
								</tr>
							</c:forEach>
						
							<c:forEach var="dto" items="${list}" varStatus="status">
								<tr>
									<c:if test="${sessionScope.member.userId=='admin'}">
										<td>
											<input type="checkbox" class="form-check-input" name="nums" value="${dto.num}">
										</td>
									</c:if>
									<td>${dataCount - (page-1) * size - status.index}</td>
									<td class="left">
										<a href="${articleUrl}&num=${dto.num}" class="text-reset">${dto.subject}</a>
										<c:if test="${dto.gap<2}"><img src="${pageContext.request.contextPath}/resources/images/new.png" class="new"></c:if>
									</td>
									<td>${dto.userName}</td>
									<td>${dto.reg_date}</td>
									<td>${dto.hitCount}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</form>
				
				<div class="page-navigation">
					${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
				</div>

				<div class="row board-list-footer">
					<div class="col">
						<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/haksa/list.do';">새로고침</button>
					</div>
					<div class="col-6 text-center">
						<form class="row" name="searchForm" action="${pageContext.request.contextPath}/haksa/list.do" method="post">
							<div class="col-auto p-1">
								<select name="condition" class="form-select">
									<option value="all" ${condition=="all"?"selected='selected'":""}>제목+내용</option>
									<option value="userName" ${condition=="userName"?"selected='selected'":""}>작성자</option>
									<option value="reg_date" ${condition=="reg_date"?"selected='selected'":""}>등록일</option>
									<option value="subject" ${condition=="subject"?"selected='selected'":""}>제목</option>
									<option value="content" ${condition=="content"?"selected='selected'":""}>내용</option>
								</select>
							</div>
							<div class="col-auto p-1">
								<input type="text" name="keyword" value="${keyword}" class="form-control">
							</div>
							<div class="col-auto p-1">
								<input type="hidden" name="size" value="${size}">
								<button type="button" class="btn btn-light" onclick="searchList()"> <i class="bi bi-search"></i> </button>
							</div>
						</form>
					</div>
					<div class="col text-end">
						<c:if test="${sessionScope.member.userId=='admin'}">
							<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/haksa/write.do?size=${size}';">글올리기</button>
						</c:if>
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
	</div>
	</div>
	</section>
</main>
<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>