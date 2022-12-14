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

function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type:method,
		url:url,
		data:query,
		dataType:dataType,
		success:function(data) {
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
	const now = new Date();
	
	let year = now.getFullYear();
	let month = now.getMonth()+1; 
	
	if(month<10){
		month = month+"";
		month = 0+month;
	}
	
	let day = now.getDate();
	
	if(day<10){
		day = day+"";
		day = 0+day;
	}
	
	let date = year+"-"+month+"-"+day;
	$("#date").val(date);
	
});

$(function(){
	$("#attendCode_gen").val(${dto.attend_pass});
	
	
	$(".attend_gen_btn").click(function(){
		let url = "${pageContext.request.contextPath}/lecture/attend_gen.do?";
		let query ="subjectNo=${subjectNo}";
		
		location.href=url+query;
		
		
	});
	
	$(".attend_btn").click(function(){
		let submitCode = $("#attendCode").val().trim();
		if(!submitCode){
			 $("#attendCode").focus();
			 return false;
		}
		
		
		let url = "${pageContext.request.contextPath}/lecture/attend_ok.do?";
		let query = "subjectNo=${subjectNo}&attendNo=${dto.attendNo}&submitCode="+submitCode;
		
		location.href=url+query;
	});
	
	$(".btn_attend_list").click(function(){
		let mode = $(this).val().trim();
		let url="${pageContext.request.contextPath}/lecture/attend_list.do";
		let query= "mode="+mode;
		
		const fn = function(data){
			let out;
			let page =1;
			for(let item of data.list){
				let subjectName = item.subjectName;
				let studentcode = item.studentcode;
				let attend_time = item.attend_time;
				let attend_pass = item.attend_pass;
				
				out += "<tr class='attend_append'><td>"+page+"</td>";
				out += "<td>"+subjectName+"</td>";
				out += "<td>"+studentcode+"</td>";
				out += "<td>"+attend_time+"</td>";
				out += "<td>"+attend_pass+"</td></tr>";
				page++;
			}
			if(data.list.length==0){
				out = "<td colspan='5' class='attend_append'><h4><br>데이터가 없습니다.<br></h4></td>";
			}
			$(".attend_append").remove();
			$(".attend_list").append(out);
			page=0;
		}
		ajaxFun(url, "GET", query, "JSON", fn);
	});
	
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
					<div class="col-auto bg-dark bg-gradient rounded" style="min-height: 100vh">
						<!-- 왼쪽 사이드바 자리 -->
						<jsp:include page="/WEB-INF/views/layout/brief_sidebar.jsp" />
					</div>
					<div class="col">
						<jsp:include page="/WEB-INF/views/layout/header2.jsp" />
						<jsp:include page="/WEB-INF/views/layout/classroom_header.jsp" />
						<jsp:include page="/WEB-INF/views/layout/lecture_index.jsp" />
						<!-- 본문 시작 -->
						<div class="card ">
							<div class="card-header rounded fw-bold fs-6 bg-navy bg-gradient text-center text-white p-2">
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
							<div class="col-4 ">
								<input class="form-control h-100" type="date" readonly id="date">
							</div>
							
							</div>
							<hr>
							</div>
							<div class="card-body ps-4 pe-4 text-center">
							<c:if test="${fn:length(sessionScope.member.userId) !=8 }">
								<div class="row p-4">
										<div class="col-3">&nbsp;</div>
										<div class="col-6">
										<div class="card">
											<div class="card-header rounded bg-navy bg-gradient text-white">
												<h5>출석코드 생성</h5>
											</div>
											<div class="card-body">
											<br>
												<input class="form-control" placeholder="출석코드를 입력하세요." id="attendCode_gen" readonly="readonly">
											<br>
											<c:if test="${empty dto.attend_pass }">
												<button class="btn btn-outline-primary attend_gen_btn">생성하기</button>
											</c:if>
											<c:if test="${not empty dto.attend_pass }">
												<button class="btn btn-outline-primary attend_gen_btn" disabled>생성하기</button>
											</c:if>
												
												
											<br>
											</div>
										</div>
										</div>
										<div class="col-3">&nbsp;</div>
								</div>
								<br>
								<br>
							</c:if>
							<c:if test="${ fn:length(sessionScope.member.userId) == 8 }">
								<div class="row ps-4 pe-4">
									<div class="col-3">&nbsp;</div>
									<div class="col-6">
									<div class="card">
										<div class="card-header rounded bg-navy bg-gradient text-white">
											<h5>출석하기</h5>
										</div>
										<div class="card-body">
										<br>
											<input class="form-control" placeholder="출석코드를 입력하세요." id="attendCode">
										<br>
											<button class="btn btn-outline-primary attend_btn">출석하기</button>										
										<br>
										</div>
									</div>
									</div>
									<div class="col-3">&nbsp;</div>
								</div>
							</c:if>
								<br>
								<br>
								<div class="row">
									<div class="col-2">&nbsp;</div>
									<div class="col-8">
									
									<button class="btn btn_attend_list" value="attend"><i class="fas fa-check fa-3x text-success p-4"></i></button>
									<button class="btn btn_attend_list" value="absent"><i class="fas fa-x fa-3x text-danger  p-4"></i></button>
									<button class="btn btn_attend_list" value="run"><i class="fas fa-person-running fa-3x text-warning  p-4"></i></button>

									</div>
									<div class="col-2">&nbsp;</div>
								</div>
								<div class="row p-4">
								<div class="card p-2">
										<c:if test="${fn:length (sessionScope.member.userId) !=8}">
										<h4>데이터가 없습니다.</h4>
										</c:if>
										<c:if test="${fn:length (sessionScope.member.userId) ==8}">
										<table class="table text-center attend_list">
											<tr class="bg-navy text-light bg-gradient">
												<th style="width:10%">번호</th>
												<th style="width:40%">강의명</th>
												<th style="width:15%">학번</th>
												<th style="width:25%">출석시간</th>
												<th style="width:10%">처리</th>
											</tr>
										</table>
										</c:if>
									</div>
								</div>
							</div>
							</div>
					<!-- 본문 -->
					</div>
					</div>
					</div>
				</div>
				<div class="col-1"></div>
			</div>
		</div>
				
				<!-- 본문 끝 -->
	</section>
</main>
<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>
</html>

