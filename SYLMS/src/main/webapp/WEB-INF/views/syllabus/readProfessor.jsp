<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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


<script type="text/javascript">

</script>

</head>
<body>

   <div class="title">
      <h3><i class="fas fa-map  fa-lg mr-3"></i> 수업계획서 </h3>
					</div>
					<div class="panel panel-info">
						<div class="panel-heading">
							<h3 class="panel-title text-center">
								<strong>수업전 수업계획서는 수정 반영은 포텔에서 해야합니다.</strong>
							</h3>
						</div>
						
						 <p>
                <input type="file" name="file_0"/>
            </p> 
        </div>				
		<div class="div1">
			<table border = "1" style="border-collapse : collapse">
				<tr>
					<td style="background-color : #ede7e7"> 영상목록  </td>
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
		</div>
		</body>					
			<!-- 본문 -->
		<div class="board" style="border-collapse : collapse">
   <div class="title">
      <h3><i class="bi bi-envelope"></i> 강의계획서 </h3>
	</div>
		</div>
     <!-- 과목 정보 -->
     
     	<div class="body-main">
				<form name="mailForm" method="post">
					<table class="table write-form mt-5">
			<tr>		
				<td class="table-light col-sm-3" scope="row">과목명</td>
				<td>
				<input type="text" name="title" maxlength="100" class="form-control" value="${dto.subjectName}">
				</td>
			</tr>
			
			<tr>
				<td class="table-light col-sm-2" scope="row">담당교수</td>
				<td>
				<input type="text" name="title" maxlength="100" class="form-control" value="${dto.name}">
				</td>
			</tr>
			
			<tr>
				<td class="table-light col-sm-2" scope="row">학점</td>
                <td>
                <input type="text" name="title" maxlength="100" class="form-control" value="${dto.credit }">
                </td>
			</tr>
			<tr>
				<td class="table-light col-sm-2" scope="row">과제비율</td>
                <td>
                 <input type="text" name="title" maxlength="100" class="form-control" value="${dto.assignmentRate }">
                 </td>
			</tr>
			<tr>
				<td class="table-light col-sm-2" scope="row">중간고사비율</td>
               <td>
               <input type="text" name="title" maxlength="100" class="form-control" value="${dto.middleRate }">
               </td>
			</tr>	
			<tr>
				<td class="table-light col-sm-2" scope="row">기말고사비율</td>
               <td>
               <input type="text" name="title" maxlength="100" class="form-control" value="${dto.finalRate}">
               </td> 
  			</tr>
			
		</table>
		</form>
		
</div>	


</body>
</html>