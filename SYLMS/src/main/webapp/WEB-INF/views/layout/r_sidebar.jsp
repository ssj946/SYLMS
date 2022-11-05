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
		$(".todoList").append(out);		
	}
	ajaxFun(url, "get", query, "json", fn);
});


</script>
	<div class="card p-2" style="min-height: 100vh">
				<div class="card-header bg-primary text-white text-center fw-bold fs-5">
				할 일
				</div>
				<ul class="todoList  list-group list-group-flush list-group-numbered list-group-item-action active" aria-current="true">
					<c:if test="${fn:length(sessionScope.member.userId) == 8 }">	
								
							
					</c:if>
				</ul>
	</div>
	<!-- 오른쪽 사이드바 끝 -->