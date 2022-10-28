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
.body-container {
	max-width: 800px;
}

</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board2.css" type="text/css">
</head>

<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"/>
</header>
	
<main>
<section>
	
			<!-- 본문 -->
			 	<div class="col-lg-10 gap-3 ms-auto">
				<div class="ms-1 me-1 pt-3 mt-3 mb-5">
					<h3><i class="fas fa-clipboard-question  fa-lg mr-3"></i> 수업계획서 </h3>
					
					</div>
					<div class="panel panel-info">
						<div class="panel-heading">
							<h3 class="panel-title text-center">
								<strong>수업계획서는 수정 반영으로 나타냅니다.</strong>
							</h3>
						</div>
						<div class="panel-body">
				
					</div>
						
						</div>
					<hr>
					<div class="panel panel-info">
						<div class="panel-heading"><strong>첨부파일</strong></div>
						<div class="panel-body">
						
							<p></p>
				<div class="panel panel-info">
						<div class="panel-heading"><strong>강의소개영상</strong></div>
						<div class="panel-body">
						
						</div>
					<div class="panel panel-info">
						<div class="panel-heading"><strong>강의소개서</strong></div>
						<div class="panel-body">
		        </div>				
				
				
				<table class="table table-hover board-list">
					<thead class="table-light">
						<tr>
							<th class="no">번호</th>
							<th class="subjectName">과목명</th>
							<th class="name">담당교수</th>
							<th class="openDate">개강일자</th>
							<th class="semester">학기</th>
							<th class="lecturePlace">강의실</th>
							<th class="credit">학점</th>
							<th class="assignmentRate">과제비율</th>
							<th class="middleRate">중간고사비율</th>
							<th class="finalRate">기말고사비율 </th>	
						</tr>
					</thead>		
						<tbody>	
						<c:forEach var="dto" items="${list}" varStatus="status">
							<tr>
								<td>${dataCount - (page-1) * size - status.index}</td>
								<td class="left">
									${dto.subjectName}
								</td>
								<td>${dto.name}</td>
								<td>${dto.openDate}</td>
								<td>${dto.semester}</td>
								<td>${dto.lecturePlace}</td>
								<td>${dto.credit}</td>
								<td>${dto.assignmentRate}</td>
							    <td>${dto.middleRate}</td>
								<td>${dto.finalRate}</td>
									
							</tr>
						</c:forEach>
						
														
						</tbody>
					</table>				
				</div>
				
				<div class="page-navigation">
					${dataCount == 0 ? "등록된 게시물이 없습니다." : paging}
				</div>
				
				</div>
				</div>
				</div>
			<!-- 본문 끝 -->
			</div>
	
	
	</section>
</main>
<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>