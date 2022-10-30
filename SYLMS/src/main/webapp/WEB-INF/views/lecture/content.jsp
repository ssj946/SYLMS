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

$(function(){
	if(${mode == 'update'||mode == 'write'}){
		$("input").attr("readonly",false);
		$("textarea").attr("readonly",false);
	}
	$("#week").val("${contentDTO.week}").prop("selected", true);
	$("#type").val("${contentDTO.part}").prop("selected", true);
	
});

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
	$(".save_btn").click(function(){
		let subjectNo = "${subjectNo}";
		
		let title =$("#title").val().trim();
		if(!title){
			$("#title").focus();
			return false;
		}
		let content = $("#content").val().trim();
		if(!content){
			$("#content").focus();
			return false;
		}
		let week = $("#week").val();
		if(!week){
			$("#week").focus();
			return false;
		}
		
		let type= $("#type").val();
		if(!type){
			$("#type").focus();
			return false;
		}
		
		let sdate = $("#s_date").val().trim();
		if(!sdate){
			$("#s_date").focus();
			return false;
		}
		let edate = $("#e_date").val().trim();
		if(!edate){
			$("#e_date").focus();
			return false;
		}
		
		//내용을 encoding
		title = encodeURIComponent(title);
		content = encodeURIComponent(content);
		week = encodeURIComponent(week);
		type = encodeURIComponent(type);
		sdate = encodeURIComponent(sdate);
		edate = encodeURIComponent(edate);
		
		
		
		let query = "subjectNo="+subjectNo+"&title="+title+"&content="+content+"&week="+week+"&type="+type+"&sdate="+sdate+"&edate="+edate;
		
		const fn = function(data){
			if(data.state==="true"){
			alert("강의 등록에 성공했습니다.");
			location.href="${pageContext.request.contextPath}/lecture/classroom.do?subjectNo="+subjectNo;
			}
		}
		
		if("${mode}"==="write"){
			let url = "${pageContext.request.contextPath}/lecture/content_submit.do";
			ajaxFun(url, "post", query, "json", fn);
		}
		
		if("${mode}"==="update"){
			let url = "${pageContext.request.contextPath}/lecture/content_update_ok.do";
			ajaxFun(url, "post", query, "json", fn);
		}
		
		if("${mode}"==="view"){
			location.href="{pageContext.request.contextPath}/lecture/content_update.do?subjectNo=${subjectNo}&bbsNum=${contentDTO.bbsNum}";
		}
		
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
				<div class="col-xl-10 col-md-10 col-lg-10 gap-3 ms-auto">
					<div class="ms-1 me-1 pt-3 mt-3 mb-5">
						<div class="card mb-3">
							<div class="card-header fw-bold fs-6 bg-navy bg-gradient text-center text-white p-2">
							   <h5> <i class="fas fa-tv fa-lg bg-navy"></i>&nbsp;</h5>
							</div>
							<div class="card-body p-4">
							<div class= "mb-3 row d-flex">
								<label for="week" class="col-2 col-form-label text-center fw-bold fs-4">강의 주차</label>
								<div class="col-4">
									<select class="form-select h-100 mb-2" id="week">
										<option value="1">1주차</option>
										<option value="2">2주차</option>
										<option value="3">3주차</option>
										<option value="4">4주차</option>
										<option value="5">5주차</option>
										<option value="6">6주차</option>
										<option value="7">7주차</option>
										<option value="8">8주차</option>
										<option value="9">9주차</option>
										<option value="10">10주차</option>
										<option value="11">11주차</option>
										<option value="12">12주차</option>
										<option value="13">13주차</option>
										<option value="14">14주차</option>
										<option value="15">15주차</option>
										<option value="16">16주차</option>
									</select>
								</div>
								<label for="week" class="col-2 col-form-label text-center fw-bold fs-4">종류</label>
								<div class="col-4 ">
									<select class="form-select h-100 mb-2" id="type">
										<option>동영상강의</option>
										<option>강의자료</option>
									</select>
								</div>
							</div>
							<hr>
							<div class= "mb-3 row d-flex">
								<label for="week" class="col-2 col-form-label text-center fw-bold fs-4">시작 일자</label>
								<div class="col-4">
									<input type="date" class="form-control h-100 mb-2" readonly="readonly" id="s_date" value="${contentDTO.start_date}">
								</div>
								<label for="week" class="col-2 col-form-label text-center fw-bold fs-4">종료 일자</label>
								<div class="col-4 ">
									<input type="date" class="form-control h-100 mb-2" readonly="readonly" id="e_date" value="${contentDTO.end_date}">
								</div>
							</div>
							<hr>
							<div class="mb-3 row">
								<label for="title" class="col-2 col-form-label text-center fw-bold fs-4">강의 제목</label>
								<div class="col-10 d-flex align-content-center">
								<input class="h-100 form-control mb-2" id="title" readonly="readonly" value="${contentDTO.title}" placeholder="제목을 입력해주세요.">
								</div>
							</div>
						  	<hr>
						  	<br>
							<video src="${pageContext.request.contextPath}/resources/videos/cat.mp4" class="w-100 rounded" controls></video>
							</div>
						</div>
						<div class="card mb-3">
						  <div class="card-header fw-bold fs-6 bg-navy bg-gradient text-center text-white p-2">
						   <h5> <i class="fas fa-file-pen fa-lg bg-navy"></i>&nbsp;</h5>
						  </div>
						  <div class="card-body p-4">
						  <h4 class="mb-3 fw-bold">강의 내용</h4> <textarea id="content" class="form-control" readonly="readonly" placeholder="내용을 입력해주세요." rows="10">${contentDTO.content}</textarea>
						  <br>
						 <h4 class="mb-3 fw-bold">강의 자료</h4> <input type="file" class="form-control">
						  <br>
						  <div class="text-end">
						  	<c:if test="${fn:length(sessionScope.member.userId) != 8}">
							  <button type="button" class="btn btn-outline-primary save_btn">
								  <c:if test="${mode == 'write'}">저장</c:if>
								  <c:if test="${mode == 'update'|| mode == 'view'}">수정</c:if>
							  </button>
							  <button type="button" class="btn btn-outline-danger" onclick="content_delete();">삭제</button>
							</c:if>
							  <button type="button" class="btn btn-outline-dark" onclick="goBack();">뒤로가기</button>
						  </div>
						  
						  </div>
						</div>
					</div>
					</div>
					<!-- 본문 끝 -->
			</div>
			</div>
			</div>
			</div>
	
	</section>
</main>


<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>

