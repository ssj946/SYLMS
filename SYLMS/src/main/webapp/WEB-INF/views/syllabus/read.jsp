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
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<style type="text/css">
* { padding: 0; margin: 0; }
*, *::after, *::before { box-sizing: border-box; }

body {
   font-family:"Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
   font-size: 14px;
   color: #222;
}

a { color: #222; text-decoration: none; cursor: pointer; }
a:active, a:hover { color: #f28011; text-decoration: underline; }

/* form-control */
.btn {
   color: #333;
   border: 1px solid #999;
   background-color: #fff;
   padding: 5px 10px;
   border-radius: 4px;
   font-weight: 500;
   cursor:pointer;
   font-size: 14px;
   font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
   vertical-align: baseline;
}
.btn:active, .btn:focus, .btn:hover {
   background-color: #f8f9fa;
   color:#333;
}
.btn[disabled], fieldset[disabled] .btn {
   pointer-events: none;
   cursor: default;
   opacity: .65;
}

.form-control {
   border: 1px solid #999; border-radius: 4px; background-color: #fff;
   padding: 5px 5px; 
   font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
   vertical-align: baseline;
}
.form-control[readonly] { background-color:#f8f9fa; }

textarea.form-control { height: 170px; resize : none; }

.form-select {
   border: 1px solid #999; border-radius: 4px; background-color: #fff;
   padding: 4px 5px; 
   font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
   vertical-align: baseline;
}
.form-select[readonly] { background-color:#f8f9fa; }

textarea:focus, input:focus { outline: none; }
input[type=checkbox], input[type=radio] { vertical-align: middle; }

/* table */
.table { width: 100%; border-spacing: 0; border-collapse: collapse; }
.table th, .table td { padding-top: 10px; padding-bottom: 10px; }

.table-border thead > tr { border-top: 2px solid #212529; border-bottom: 1px solid #ced4da; }
.table-border tbody > tr { border-bottom: 1px solid #ced4da; }
.table-border tfoot > tr { border-bottom: 1px solid #ced4da; }
.td-border td { border: 1px solid #ced4da; }

/* board */
.board { margin: 30px auto; width: 700px; }

.title { width:100%; font-size: 16px; font-weight: bold; padding: 13px 0; }

.table-article { margin-top: 20px; }
.table-article tr > td { padding-left: 5px; padding-right: 5px; }
</style>

<script type="text/javascript">

</script>

</head>
<body>

	<div class="board">
   <div class="title">
      <h3><i class="fas fa-map  fa-lg mr-3"></i> 수업계획서 </h3>
					
					
					</div>
					<div class="panel panel-info">
						<div class="panel-heading">
							<h3 class="panel-title text-center">
								<strong>수업전 수업계획서는 수정 반영은 포텔에서 해야합니다. </strong>
							</h3>
						</div>
						
						
			            <table border = "1"  style="border-collapse : collapse">
				<tr>
					<td style="background-color : #ede7e7"> 강의소개 영상 </td>
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
					
			<!-- 본문 -->
			<div class="board">
	           <div class="title">
	    <h3><i class=></i> 평가항목 </h3>
	</div>
		



     <!-- 과목 정보 -->
     
     	
		<div class="board">
		<table border = "1" style="border-collapse : collapse;">

			<tr>
			
				<td class="table-light col-sm-2" scope="row">과목명</td>
				<td>
				<input type="text" name="title" maxlength="100" class="form-control" value="${dto.subjectName}">
				</td>
			</tr>
			
			<tr>
				<td class="table-light col-sm-2" scope="row">담당교수</td>
				<td>
				<p class="form-control-plaintext">${dto.id}</p>
				</td>
			</tr>
			
			<tr>
				<td align="center">학점</td>
                <td>${dto.credit }</td>
			</tr>
			<tr>
				<td align="center">과제비율</td>
                <td>${dto.assignmentRate }</td>
			</tr>
			<tr>
				<td align="center">중간고사비율</td>
               <td>${dto.middleRate }</td>
			</tr>
			
			<tr>
				<td align="center">기말고사비율</td>
                <td>${dto.finalRate}</td> 
  			</tr>
			
		</table>

</div>	
</div>
</div>


</body>
</html>