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

<script type="text/javascript">
$(function(){
	$(".subjectRegister").change(function(){
		let subjectNo = $(this).val();
		location.href="${pageContext.request.contextPath}/syllabus/list.do?subjectNo="+subjectNo;
	});
})
</script>
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
					<h3><i class="fas fa-map  fa-lg mr-3"></i> 수업계획서 </h3>
					
					</div>
					<div class="panel panel-info">
						<div class="panel-heading">
							<h3 class="panel-title text-center">
								<strong>강의 전 알림사항</strong>
							</h3>
						</div>
						<div class="panel-body">
							<p> 1. 학생명 수업선택시,조회됩니다.</p>
							<p> 2. 과목명 표시됩니다.</p>
							<p> 3. Students, please check</p>
						</div>
					</div>
					
							 <p>
                <input type="file" name="file_0"/>
            </p> 
			<table border = "1" style="border-collapse : collapse">
				<tr>
					<td style="background-color : #ede7e7"> 강의소개영상  </td>
					<td>
						<select id="movie_select" onchange="play();">
			<!--select의 option에 변화가 생기면 onchange에 있는 play()가 실행됨   -->
							<option>:::VIDEO PLAYLIST:::</option>
						</select>				
					</td>
				</tr>	
			</table>
					<hr>
					<div align="center">
				<video src="" id="my_video" width="320" height="240" controls></video>	
		</div>
					<form class="form-inline" action="">
						<div class="form-group">
							<label>학년도 학기</label>
							<label>${syear} 년도 </label>
							<label>${semester }학기</label>
						</div>
						<div class="form-group">
							<label>수업선택</label>
							<select class="form-control subjectRegister" >
								<c:forEach var="vo" items="${listRegister }">
									<option value="${vo.subjectNo}" ${vo.subjectNo == dto.subjectNo ? "selected='selected'" :"" }> ${vo.subjectName } </option>
								</c:forEach>
							
							</select>
						</div>
					</form>
					<hr>
				<table class="table">
					<tr>
						<td class="w-10 bg-light">과목명</td>
						<td class="w-40">${dto.subjectName}</td>
						<td class="w-10 bg-light">담당교수</td>
						<td class="w-40">${dto.name}</td>						
					</tr>

					<tr>
						<td class="bg-light">개강일자</td>
						<td>${dto.openDate}</td>
						<td class="bg-light">학기</td>
						<td>${dto.semester}</td>						
					</tr>
					
					<tr>
						<td class="bg-light">강의실</td>
						<td colspan="3">${dto.lecturePlace}</td>						
					</tr>
						
					<tr>
						<td class="bg-light">학점</td>
						<td>${dto.credit}</td>
						<td class="bg-light">과제비율</td>
						<td>${dto.assignmentRate}</td>						
					</tr>
	
					<tr>
						<td class="bg-light">중간고사비율</td>
						<td>${dto.middleRate}</td>
						<td class="bg-light">기말고사비율</td>
						<td>${dto.finalRate}</td>						
					</tr>
				</table>
        </div>				
		<div class="col-lg-10 gap-3 ms-auto">
					
					<div class="body-main">
		
				
					</div>																				
</div>
				
					</div>
					
				</div>
				</div>
				</div>
			<!-- 본문 끝 -->
					
				
		
	
	</section>
</main>
<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>