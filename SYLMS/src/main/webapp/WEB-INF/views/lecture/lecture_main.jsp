<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
$(function(){
	let w=$(".resultLayout").children(".card").length+1;
	
	$(".week_plus_btn").click(function(){
		$(".resultLayout").append('<div class="card mb-2"><h5 class="card-header">'+w+'주차</h5><div class="card-body">  <textarea class="form-control" rows="3"></textarea></div></div>');
		w++;
	});	
	
	$(".week_del_btn").click(function(){
		$(".resultLayout").children(".card").last().remove();
		w--;
	});	
	
	$(".content_plus_btn").click(function(){
		$(".resultLayout").children(".card").last().children(".card-body").append('<br><textarea class="form-control" row="3"></textarea>');
	});	
	
	$(".content_del_btn").click(function(){
		$(".resultLayout").children(".card").last().children(".card-body").children("textarea").last().remove();
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
			<div class="row p-4">
			<!-- classroom header 자리 -->
			<jsp:include page="/WEB-INF/views/layout/classroom_header.jsp"/>
			</div>
			<div class="row">
			<!-- 강의 사이드바 자리 -->
			<div class="col-xl-2 col-md-2 col-lg-2 bg-black bg-gradient" style="box-shadow: none; height: 150vh;">
			<jsp:include page="/WEB-INF/views/layout/lecture_sidebar.jsp"/>
			</div>
			
			<!-- 본문 -->
			<div class="col-xl-10 col-md-10 col-lg-10 gap-3 ms-auto">
				<div class="ms-1 me-1 pt-3 mt-3 mb-5">
					<div class="card mb-3">
					  <div class="card-header fw-bold fs-6 bg-navy bg-gradient text-white">
					    <i class="fas fa-rectangle-list fa-lg bg-navy"></i>&nbsp;강좌 개요
					  </div>
					  <div class="card-body m-auto">
					    <ul class="list-group list-group-horizontal text-center fw-bold">
					    <li><ul>
					    <li class="list-group-item"><a href="#"><i class="fas fa-microphone fa-3x"></i></a></li>
					    <li class="list-group-item">강의공지</li>
					    </ul></li>
					    <li><ul>
					    <li class="list-group-item"><a href="#"><i class="fas fa-clipboard-question fa-3x"></i></a></li>
					    <li class="list-group-item">질의응답</li>
					    </ul></li>
					    
					    <li><ul>
					    <li class="list-group-item"><a href="#"><i class="fas fa-pen fa-3x"></i></a></li>
					    <li class="list-group-item">이번주 강의</li>
					    </ul></li>
					    
					    <li><ul>
					    <li class="list-group-item"><a href="#"><i class="fas fa-calendar fa-3x"></i></a></li>
					    <li class="list-group-item">주차별 학습활동</li>
					    
					    </ul></li>
					    
					    <li><ul>
					    <li class="list-group-item"><a href="#"><i class="fas fa-map fa-3x"></i></a></li>
					    <li class="list-group-item">강의계획서</li>
					    </ul></li>
						</ul>
					  </div>
					</div>
					
					<div class="card mb-3">
					  <div class="card-header fw-bold fs-6 bg-navy bg-gradient text-white">
					    <i class="fas fa-pen fa-lg bg-navy"></i>&nbsp;이번주 강의
					  </div>
					  <div class="card-body">
					    <h5 class="card-title">Special title treatment</h5>
					    <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
					  </div>
					</div>
					
					<div class="card mb-3">
					  <div class="card-header fw-bold fs-6 bg-navy bg-gradient text-white">
					    <i class="fas fa-clipboard-question fa-lg"></i>&nbsp;질의 응답
					  </div>
					  <div class="card-body">
					    <h5 class="card-title">Special title treatment</h5>
					    <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
					  </div>
					</div>
					
					<div class="card mb-3">
					  <div class="card-header fw-bold fs-6 bg-navy bg-gradient text-white">
					    <i class="fas fa-calendar fa-lg"></i>&nbsp;주차 별 학습 활동
					  </div>
					  <div class="card-body">
					    <div class="resultLayout"></div>
					    <div class="btn-group ms-auto " role="group" aria-label="Basic mixed styles example">
						  <button type="button" class="btn btn-outline-primary week_plus_btn">주차 추가</button>
						  <button type="button" class="btn btn-outline-danger week_del_btn">주차 삭제</button>
						  <button type="button" class="btn btn-outline-primary content_plus_btn">내용 추가</button>
						  <button type="button" class="btn btn-outline-danger content_del_btn">내용 삭제</button>
						</div>
					  </div>
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