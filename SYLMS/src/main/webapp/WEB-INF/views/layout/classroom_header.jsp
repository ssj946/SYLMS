<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<style type="text/css">
.card-img { width: 90px; height: 90px;}
.bg_img{
	width: 100%;
	position: relative;
	}
.bg_img::before{
	content: "";
	background-image: url("https://cdn.pixabay.com/photo/2016/01/05/11/36/architecture-1122359_960_720.jpg");
	background-repeat: no-repeat;
	background-size: cover;
	opacity: 0.5;
	position: absolute;
	top:0px;
	left:0px;
	right:0px;
	bottom:0px;
	}
.bg_img h1{
    position: relative;
}
.bg_img h2{
    position: relative;
}
.bg_img h3{
    position: relative;
}
.bg_img h4{
    position: relative;
}
.bg_img h5{
    position: relative;
}
.bg_img h6{
    position: relative;
}
</style>

<body>


<div class="bg_img fw-bold py-5 px-5 rounded mt-3 mb-3 text-end">
	 		<h6 class="fw-bold">${syear}년도 ${semester}학기</h6>
	 		<h1 class="fw-bold">${subjectName}</h1>
	 		<h4 class="fw-bold"> ${professorName} 교수님</h4>
 		</div>
</html>