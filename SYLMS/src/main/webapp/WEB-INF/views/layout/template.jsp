<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>SYLMS</title>
<jsp:include page="/WEB-INF/views/layout/staticHeader.jsp" />
<style type="text/css">
</style>
<script type="text/javascript">
function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type:method,
		url:url,
		data:query,
		dataType:dataType,
		success:function(data){
			fn(data);
		},
		beforeSend:function(jqXHR) {
			// jqXHR.setRequestHeader("AJAX", true);
		},
		error:function(jqXHR) {
			console.log(jqXHR.responseText);
		}
	});
}

$(function(){
	
	$(".reg_history_btn").click(function(){
		let syear = $("#syear option:selected").val();
		let semester = $("#semester option:selected").val();
		let url="${pageContext.request.contextPath}/lecture/history.do";
		let query= "syear="+syear+"&semester="+semester;
		
		const fn = function(data){
			
			let out;
			for(let item of data.list){
				let credit = item.credit;
				let semester = item.semester;
				let subjectName = item.subjectName;
				let subjectNo = item.subjectNo;
				let syear = item.syear;
				let professorName = item.professorname
				
				out += "<tr class='history_append'><td><a href='${pageContext.request.contextPath}/lecture/classroom.do?subjectNo="+subjectNo+"'>"+subjectName+"</a></td>";
				out += "<td>"+credit+"</td>";
				out += "<td>"+syear+"</td>";
				out += "<td>"+semester+"</td>";
				out += "<td>"+professorName+"</td></tr>";
				
			}
			$(".history_append").remove();
			$(".history_list").append(out);
		}
		ajaxFun(url, "GET", query, "JSON", fn);
	});

});

</script>
</head>

<body>

	<header>
		<jsp:include page="/WEB-INF/views/layout/header.jsp" />
	</header>

	<main>
		<section>
			<div class="container-fluid">

				<div class="row">&nbsp;</div>
				<div class="row">
					<div class="col-xl-2 col-lg-3 col-md-4 bg-dark bg-gradient pt-1" style="height: 100vh">
						<!-- 왼쪽 사이드바 자리 -->
						<jsp:include page="/WEB-INF/views/layout/l_sidebar.jsp" />
					</div>

					<!-- 본문 -->
					
					<div class="col-xl-8 col-lg-6 col-md-6" style="min-height:100vh">
						<div class="row ms-3 me-1 pt-3 mt-3 mb-5 gap-3">
							
						</div>
					</div>
					<!-- 본문 끝 -->
					<!-- 오른쪽 사이드바 자리 -->
					<div class="col-xl-2 col-lg-3 col-md-3 ms-auto">
						<jsp:include page="/WEB-INF/views/layout/r_sidebar.jsp" />
					</div>
				</div>
			</div>
		</section>
	</main>
	<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>
</html>

