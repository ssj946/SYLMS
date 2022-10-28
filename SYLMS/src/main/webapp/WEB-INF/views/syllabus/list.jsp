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

list-style: none;

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
			<div class="col-lg-2 bg-black bg-gradient" style="box-shadow: none; height: 150vh;">
			<jsp:include page="/WEB-INF/views/layout/lecture_sidebar.jsp"/>
			</div>
			
			<!-- 본문 -->
			 	<div class="col-lg-10 gap-3 ms-auto">
				<div class="ms-1 me-1 pt-3 mt-3 mb-5">
					<h3><i class="fas fa-clipboard-question  fa-lg mr-3"></i> 수업계획서 </h3>
					
					</div>
					<div class="panel panel-info">
						<div class="panel-heading">
							<h3 class="panel-title text-center">
								<strong>강의 전 알림사항</strong>
							</h3>
						</div>
						<div class="panel-body">
							<p> 1. 교수명 클릭시,조회됩니다.</p>
							<p> 2. 과목명 표시됩니다.</p>
							<p> 3. The English title will be shown when you place your mouse pointer on Korean subject name.</p>
						</div>
					</div>
					<hr>
					<form class="form-inline" action="">
						<div class="form-group">
							<label>학년도 학기</label>
							<select name="semester" class="form-control">
								<option value="0" selected> -- 전체 -- </option>
								<option value="1">2022년 2학기</option>
				
							</select>
						</div>
						<div class="form-group">
							<label>전공선택</label>
							<select class="form-control">
								<option> -- 전체 -- </option>
								<option>컴퓨터소프트웨어학과</option>
							</select>
						</div>
					</form>
					<hr>
					<div class="panel panel-info">
						<div class="panel-heading"><strong>시간표 및 수업계획서</strong></div>
						<div class="panel-body">
							<p>학기 : <strong>2022년 2학기</strong></p>
							<p>학과 : <strong>컴퓨터소프트웨어학과</strong></p>
							<p></p>
						</div>
					
					<div class="body-main">
		        <div class="row board-list-header">
		            <div class="col-auto me-auto">${dataCount}개(${page}/${total_page} 페이지)</div>
		            <div class="col-auto">&nbsp;</div>
		        </div>				
				
				<table class="table table-hover board-list">
					<thead class="table-light">
						<tr>
							<th class="no">번호</th>
							<th class="subjectName">과목명</th>
							<th class="openDate">개강일자</th>
							<th class="semester">학기</th>
							<th class="lecturePlace">강의실</th>
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
								<td>${dto.openDate}</td>
								<td>${dto.semester}</td>
								<td>${dto.lecturePlace}</td>
								<td>${dto.assignmentRate}</td>
							    <td>${dto.middleRate}</td>
								<td>${dto.finalRate}</td>
									
							</tr>
						</c:forEach>
						
														
						</tbody>
					</table>				
				</div>
				</div>
				</div>
				</div>
			<!-- 본문 끝 -->
			</div>
			</div>				
				</div>
		
	
	</section>
</main>
<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>