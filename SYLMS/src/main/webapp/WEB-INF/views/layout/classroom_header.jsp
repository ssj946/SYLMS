<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<style type="text/css">

#professor{
	background-image: url("${pageContext.request.contextPath}/resources/images/pattern.png");
	font-size: 25px;
	border-style: none;
}
.card-img { width: 90px; height: 90px;}

</style>

<body>


<div class=" py-5 px-5 rounded mt-3 mb-3" id=professor>
	 		<h6 class="text-start">&nbsp;&nbsp;${syear}년도 ${semester}학기</h6>
				<img src="${pageContext.request.contextPath}/resources/images/profile.png" class="card-img">
	 		<h1 class="fw-bold">${subjectName}</h1>
	 		<h5 class="fas fa-lg"> ${professorName} 교수님</h5>
	 		<br>
 		</div>
</html>