<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

<script type="text/javascript">
function goBack(){
	let query="subjectNo=${subjectNo}";
	location.href="${pageContext.request.contextPath}/lecture/classroom.do?"+query;	
}

function login() {
	location.href="${pageContext.request.contextPath}/member/login.do";
}
var list;
function ajaxResult(url, method, query, dataType, fn) {
	$.ajax({
		type:method,
		url:url,
		data:query,
		dataType:dataType,
		success:function(data) {
			list=data;
			fn(data);
		},
		beforeSend:function(jqXHR) {
			jqXHR.setRequestHeader("AJAX", true);
		},
		error:function(jqXHR) {
			if(jqXHR.status === 403) {
				login();
				return false;
			} else if(jqXHR.status === 400) {
				alert("요청 처리가 실패 했습니다.");
				return false;
			}
			
			console.log(jqXHR.responseText);
		}
	});
}

$(function(){
	$(".date_search").click(function(){
		let date = $("#date").val().trim();
		if(!date){
			$("#date").focus();
			return false;
		}
		let url="${pageContext.request.contextPath}/lecture/attend_manage_ok.do";
		let query= "subjectNo="+${subjectNo}+"&date="+date;
		
		const fn = function(data){
			let out;
			let page =1;
			for(let item of data.list){
				let subjectName = item.subjectName;
				let studentcode = item.studentcode;
				let attend_time = item.attend_time;
				let attend_pass = item.attend_pass;
				
				let attend;
				let absent;
				let run;
				if(attend_pass == "출석"){
					attend ="checked";
				} else if(attend_pass == "결석"){
					absent = "checked";
				} else if(attend_pass == "조퇴"){
					run = "checked";
				}
				
				
				out += "<tr class='attend_item'>";
				out += "<td>"+page+"</td>";
				out += "<td>"+subjectName+"</td>";
				out += "<td><input class='form-check-input' name='attend"+page+"' "+attend+" value='출석' type='radio'></td>";
				out += "<td><input class='form-check-input' name='attend"+page+"' "+absent+" value='결석' type='radio'></td>";
				out += "<td><input class='form-check-input' name='attend"+page+"' "+run+" value='조퇴' type='radio'></td>";
				out += "<td>"+studentcode+"</td>";
				out += "<td>"+attend_time+"</td>";
				out += "</tr>";
				
				page++;
			}
			$(".attend_item").remove();
			$(".attend_list").append(out);
			page=0;
		}
		ajaxResult(url, "GET", query, "JSON", fn);
		
		
	});
	
$(function(){
	$(".attend_save").click(function(){
		let url = "${pageContext.request.contextPath}/lecture/attendance_modify.do";
		let idx=1;
		
		for(let item of list.list){
			let s= "attend";
			s="input[name="+s+idx+"]:checked";
			item.attend_pass = $(s).val();
			idx++;
		}
		
		query="list="+JSON.stringify(list.list)+"&subjectNo=${subjectNo}";
		
		$.ajax({
			url:url,
			type:"POST",
			dataType:"JSON",
			data: query,
			crossDomain:true,
			beforeSend:function(jqXHR) {
				jqXHR.setRequestHeader("AJAX", true);
			},
			success : function(data){
				alert('저장되었습니다.');
			},
			error: function(jqXHR){
				if(jqXHR.status === 403) {
					login();
					return false;
				} else if(jqXHR.status === 400) {
					alert("요청 처리가 실패 했습니다.");
					return false;
				}
				
				console.log(jqXHR.responseText);
			}
		});
	});
	
});
	
	
});

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
			
			<!-- classroom header 자리 -->
			<div class="row">
			<jsp:include page="/WEB-INF/views/layout/classroom_header.jsp"/>
			</div>
			<div class="row">
				<!-- 강의 사이드바 자리 -->
				<div class="col-xl-2 col-md-2 col-lg-2 bg-black bg-gradient" style="box-shadow: none;">
				<jsp:include page="/WEB-INF/views/layout/lecture_sidebar.jsp"/>
				</div>
				
				<!-- 본문 -->
				<div class="col-xl-10 col-md-10 col-lg-10 gap-3 ms-auto " style="min-height:100vh">
					<div class="ms-1 me-1 pt-3 mt-3 mb-5">
						<div class="card mb-3">
							<div class="card-header fw-bold fs-6 bg-navy bg-gradient text-center text-white p-2">
							   <h5> <i class="fas fa-clipboard-user fa-lg bg-navy"></i>&nbsp;</h5>
							</div>
							<div class="card-body p-4">
							<div class= "mb-3 row d-flex">
								<label for="week" class="col-2 col-form-label text-center fw-bold fs-4" >강의 이름</label>
								<div class="col-10">
									<input class="form-control h-100" readonly value="${subjectName}">
								</div>
							</div>
							<hr>
							<div class= "mb-3 row d-flex">
							<label for="week" class="col-2 col-form-label text-center fw-bold fs-4">담당 교수</label>
							<div class="col-4 ">
								<input class="form-control h-100" readonly value="${professorName}">
							</div>
							<label for="week" class="col-2 col-form-label text-center fw-bold fs-4">출석 날짜</label>
							<div class="col-3 ">
								<input class="form-control h-100" type="date" id="date">
							</div>
							<div class="col-1 text-center d-flex justify-content-center">
								<button class="btn date_search"><i class="bi bi-search fa-lg"></i></button>
							</div>
							
							</div>
							<hr>
							</div>
							<div class="card-body p-4 text-center">								
								<div class="row">
									<div class="col-2">&nbsp;</div>
									<div class="col-8">&nbsp;
									<div class="card">
										<div class="card-body">
										<table class="table text-center attend_list">
											<tr class="bg-navy bg-gradient text-white">
												<th style="width:5%">번호</th>
												<th style="width:30%">강의명</th>
												<th style="width:5%"><i class="fas fa-check text-success fa-lg"></i></th>
												<th style="width:5%"><i class="fas fa-x text-danger fa-lg"></i></th>
												<th style="width:5%"><i class="fas fa-person-running text-warning fa-lg"></i></th>
												<th style="width:20%">학번</th>
												<th style="width:30%">출석시간</th>
											</tr>
											<tr class="attend_item" >
												<td class="attend_item" colspan="7"><br><br><br><h4>데이터가 없습니다.</h4><br><br><br></td>
											</tr>

										</table>
										</div>
									</div>
									<br>
									<button class="d-block btn btn-primary ms-auto attend_save">저장</button>
									</div>	
									</div>								
									<div class="col-2">&nbsp;</div>
								</div>
							
								
						
								<br>
								<br>
							
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