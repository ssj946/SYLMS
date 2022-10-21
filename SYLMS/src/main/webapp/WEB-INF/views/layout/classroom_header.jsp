<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<style type="text/css">
    .container {
    	padding-left: 90px;
	    line-height: 250px;
   		width: 100%;
    	height: 250px;
		background: #eee;
    	text-align: left;
	}
	.card-img { width: 90px; height: 90px;}
	
	.professor{
	font-weight: 900;
	font-size: 25px;
}
</style>
	</head>
	<body>
    	<div class="container">
    		<div class="professor">
    		<img src="${pageContext.request.contextPath}/resources/images/profile.png" class="card-img">
    		김정권교수
    		</div>
		</div>
	</body>
</html>