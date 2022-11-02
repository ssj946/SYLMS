<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces ="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
	let url = "${pageContext.request.contextPath}/r_sidebar/todo.do";
	let query = "assignmentName="+assignmentName+"&dday="+dday;
	const fn = function todo(data){
		let out;
		for(let item of data.todolist){
			let assignmentName = item.assignmentName;
			let dday = item.dday;
			
			out += "<li class='list-group-item list-group-item-action d-flex justify-content-between align-items-start '>";
			out += "<div class='ms-2 me-auto'>";
			out += "<a href='#'>"+assignmentName+"</a></div>";
			out += "<span class='badge bg-primary rounded-pill'>"+dday+"</span>";
			out += "</li>";
					
		}
		$(".todoList").append(out);		
	}
});


</script>

<div class="row ms-1 me-1 pt-3 mt-3 mb-5 gap-3">
	
	<div class="card pt-2 pb-2 ">
	
		<div class="card-header text-center fw-bold fs-5">
		할 일
		</div>
		
		
		<ul class="todoList  list-group list-group-flush list-group-numbered list-group-item-action active" aria-current="true">
			
		</ul>
		
	</div>
	
	
</div>

	<!-- 오른쪽 사이드바 끝 -->