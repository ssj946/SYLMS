<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces ="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- 오른쪽 사이드바  -->

<script type="text/javascript">
function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type : method,
		url : url,
		data : query,
		dataType : dataType,
		success : function(data) {
			fn(data);
		},
		beforeSend : function(jqXHR) {
			jqXHR.setRequestHeader("AJAX", true);
		},
		error : function(jqXHR) {
			console.log(jqXHR.responseText);
		}
	});
}


$(function () {	
	let asName = $("#asName").val();
	let dday = $("#dday").val();
	let url = "${pageContext.request.contextPath}/r_sidebar/todo.do";
	let query = "asName="+asName+"&dday="+dday;
	
	const fn = function todo(data){
		let out="";
		for(let item of data.todo){
			let asName = item.asName;
			let dday = item.dday;
			
			out += "<li class='list-group-item list-group-item-action d-flex justify-content-between align-items-start '>";
			out += "<div class='ms-2 me-auto'>";
			out += "<a href='#'>"+asName+"</a></div>";
			out += "<span class='badge bg-primary rounded-pill'>"+"D-"+dday+"</span>";
			out += "</li>";
					
		}
		if(data.todo.length==0){
			out ="<li class='text-center border rounded'><br>할 일이 없습니다.<br>&nbsp;</li>";
		}
		$(".todoList").append(out);		
	}
	ajaxFun(url, "get", query, "json", fn);
});


</script>
	<div class="card p-2">
				<div class="card-header bg-navy bg-gradient text-light text-center fw-bold fs-5 rounded">
				할 일
				</div>
				<div class="card-body p-0">
				<ul class="todoList list-group list-group-numbered list-group-item-action active" aria-current="true">	
						
				</ul>
				</div>

	</div>
	<!-- 오른쪽 사이드바 끝 -->