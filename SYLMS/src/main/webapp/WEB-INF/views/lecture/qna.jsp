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
			<div class="row">
			<!-- classroom header 자리 -->
			<jsp:include page="/WEB-INF/views/layout/classroom_header.jsp"/>
			</div>
			<div class="row">
			<!-- 강의 사이드바 자리 -->
			<div class="col-lg-2 bg-dark bg-gradient" style="box-shadow: none; height: 150vh;">
			<jsp:include page="/WEB-INF/views/layout/lecture_sidebar.jsp"/>
			</div>
			
			<!-- 본문 -->
			<div class="col-lg-10 gap-3 ms-auto">
				<div class="ms-1 me-1 pt-3 mt-3 mb-5">
				<h2>강철남</h2>
				
	<div id="fh5co-main">
		<div class="container">
			<div class="row">
				<div class="col-md-3">
					<form name="form1" method="post"
						action="${contextPath}/qna/qnaMain">
						<div class="input-group">
							<!-- USE TWITTER TYPEAHEAD JSON WITH API TO SEARCH -->

							<input class="form-control" id="system-search" name="keyword"
								value="${qmap.keyword }" placeholder="Search for" required>
							<span class="input-group-btn">
								<button type="submit" class="btn btn-default">Search</button>
							</span>
						</div>
					</form>
				</div>
				<div class="col-sm-11">
					<table class="table table-list-search">
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>작성자</th>
								<th>작성날짜</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${qmap.list}" var="qna">
								<tr>
									<td>${qna.qna_code}</td>
									<td><a
										href="${contextPath}/qna/qnaBoardView?qna_code=${qna.qna_code}&curPage=${qmap.qnaPager.curPage}&searchOption=${qmap.searchOption}&keyword=${qmap.keyword}">${qna.qna_title}</a></td>
									<td>${qna.qna_mmid}</td>
									<td><fmt:formatDate value="${qna.qna_date}"
											pattern="yyyy-MM-dd HH:mm:ss" /></td>
								</tr>
							</c:forEach>

							<tr>
								<td colspan="4"><c:if test="${qmap.qnaPager.curBlock > 1}">
										<a href="javascript:qlist('1')">[처음]</a>
									</c:if> <c:if test="${qmap.qnaPager.curBlock > 1}">
										<a href="javascript:qlist('${qmap.qnaPager.prevPage}')">[이전]</a>
									</c:if> <c:forEach var="num" begin="${qmap.qnaPager.blockBegin}"
										end="${qmap.qnaPager.blockEnd}">
										<c:choose>
											<c:when test="${num == qmap.qnaPager.curPage}">
												<span style="color: blue">${num}</span>&nbsp;
											</c:when>
											<c:otherwise>
												<a href="javascript:qlist('${num}')">${num}</a>&nbsp;
											</c:otherwise>
										</c:choose>
									</c:forEach> 
									<c:if test="${qmap.qnaPager.curBlock <= qmap.qnaPager.totBlock}">
										<a href="javascript:qlist('${qmap.qnaPager.nextPage}')">[다음]</a>
									</c:if> 
									<c:if test="${qmap.qnaPager.curPage <= qmap.qnaPager.totPage}">
										<a href="javascript:qlist('${qmap.qnaPager.totPage}')">[끝]</a>
									</c:if>
								
										<button type="button" class="optionbtn-write" id="qnaWrite">글쓰기</button>	
								</td>
							</tr>
						</tbody>
						<tfoot>
							<tr>
								<th colspan="4" class="massageTitle">건의사항</th>
							</tr>
							<tr>
								<td colspan="4">
									 <form name="form1" method="post" action="${contextPath}/qna/msInsert?${_csrf.parameterName}=${_csrf.token}">
							            <div >
							                <textarea class="message" id="ms_content" name="ms_content" placeholder="내용을 입력해 주세요"></textarea>
							                <input type="submit" class="btn-red" id="btnSend" value="전송">
					                        <sec:authentication property="authorities" var="auth"/>
					                        <c:if test="${auth == '[ROLE_ADMIN]'}">
							                	<button type="button" class="btn-red" id="msMain">문의내역</button>
							                </c:if>
							        	</div>
									</form>
									
								</td>
							</tr>
						</tfoot>
					</table>
				</div>
			</div>
		</div>
		<div class="mainfooter">
				<div class="teamLog">
					<img src="${contextPath }/resources/images/g-icon.png" style="width: 250px; height: 200px">
				</div>
				<div class="teamPlanation">
					<ul class="list-unstyled">
						<li><p>
								<a href="#">교수님</a>
							</p></li>
						<li><p>이메일</p></li>
						<li>© Copyright Goreuruek All Rights Reserved.</li>
					</ul>
				</div>
			</div>
	</div>
				</div>
			<!-- 본문 끝 -->
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