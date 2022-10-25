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
			<div class="row">
			<!-- classroom header 자리 -->
			<jsp:include page="/WEB-INF/views/layout/classroom_header.jsp"/>
			</div>
			<div class="row">
			<!-- 강의 사이드바 자리 -->
			<div class="col-lg-2 bg-black bg-gradient" style="box-shadow: none; height: 150vh;">
			<jsp:include page="/WEB-INF/views/layout/lecture_sidebar.jsp"/>
			</div>
			
			<!-- 본문 -->
			<div class="col-lg-10 gap-3 ms-auto">
				<div class="ms-1 me-1 pt-3 mt-3 mb-5">
					<h2>시간표 및 수업계획조회 화면</h2>
					</div>
					<div class="panel panel-info">
						<div class="panel-heading">
							<h3 class="panel-title text-center">
								<strong>조회 전 알림사항</strong>
							</h3>
						</div>
						<div class="panel-body">
							<p> 1. 수강신청기간 동안 교수명 클릭시, 강의평가 결과가 조회됩니다.</p>
							<p> 2. 과목코드에 마우스를 올리면 과목 해설이 표시됩니다.</p>
							<p> 3. The English title will be shown when you place your mouse pointer on Korean subject name.</p>
						</div>
					</div>
					<hr>
					<form class="form-inline" action="">
						<div class="form-group">
							<label>학년도 학기</label>
							<select name="semester" class="form-control">
								<option value="0" selected> -- 전체 -- </option>
								<option value="1">2022년 2학기</option>
								<option value="2">2022년 1학기</option>
								<option value="3">2021년 2학기</option>
								<option value="4">2021년 1학기</option>
								<option value="5">2020년 2학기</option>
								<option value="6">2020년 1학기</option>
								<option value="7">2019년 2학기</option>
							</select>
						</div>
						<div class="form-group">
							<label>전공선택</label>
							<select class="form-control">
								<option> -- 전체 -- </option>
								<option>멀티미디어학과</option>
								<option>컴퓨터소프트웨어학과</option>
								<option>드론학과</option>
							</select>
						</div>
					</form>
					<hr>
					<div class="panel panel-info">
						<div class="panel-heading"><strong>시간표 및 수업계획서</strong></div>
						<div class="panel-body">
							<p>학기 : <strong>2022년 2학기</strong></p>
							<p>학과 : <strong>컴퓨터소프트웨어학과</strong></p>
							<p></p>
						</div>
						<table class="table table-bordered">
						<colgroup>
							<col width="2%">
							<col width="8%">
							<col width="5%">
							<col width="8%">
							<col width="5%">
							<col width="*%">
							<col width="15%">
							<col width="8%">
							<col width="12%">
						</colgroup> 
						<thead>
							<tr>
								<th class="text-center">No</th>
								<th class="text-center">과목번호</th>
								<th class="text-center">학과번호</th>
								<th class="text-center">이수구분</th>
								<th class="text-center">학점</th>
								<th class="text-center">강의명</th>
								<th class="text-center">강의시간/강의실</th>
								<th class="text-center">담당교수</th>
								<th class="text-center">비고</th>
								
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="text-center">1</td>
								<td class="text-center">[A040]</td>
								<td class="text-center"><a href="">P010001</a></td>
								<td class="text-center">전공</td>
								<td class="text-center">3</td>
								<td class="text-center"><a href="">이산수학</a></td>
								<td class="text-center">공학관301 월 4~6</td>
								<td class="text-center"><a href="">박지윤</a></td>
								<td class="text-center">
									<button type="button" class="btn btn-warning btn-xs text-center">수업계획서</button>
								</td>
							</tr>
							<tr>
								<td class="text-center">2</td>
								<td class="text-center">[A040]</td>
								<td class="text-center"><a href="">P010731</a></td>
								<td class="text-center">교양</td>
								<td class="text-center">2</td>
								<td class="text-center"><a href="">인터넷 마켓팅</a></td>
								<td class="text-center">공학관 307 금 1~2</td>
								<td class="text-center"><a href="">황정현</a></td>
								<td class="text-center">
									<button type="button" class="btn btn-warning btn-xs text-center">수업계획서</button>
								</td>
							</tr>
							<tr>
							<td class="text-center">4</td>
								<td class="text-center">[A132]</td>
								<td class="text-center"><a href="">J101001</a></td>
								<td class="text-center">전공</td>
								<td class="text-center">3</td>
								<td class="text-center"><a href="">컴퓨터 네트워킹</a></td>
								<td class="text-center">공학관307 수 1~3</td>
								<td class="text-center"><a href="">김정길</a></td>
								<td class="text-center">
									<button type="button" class="btn btn-warning btn-xs text-center">수업계획서</button>
								</td>
								
							</tr>
							<tr>
								<td class="text-center">4</td>
								<td class="text-center">[D132]</td>
								<td class="text-center"><a href="">J101001</a></td>
								<td class="text-center">전공</td>
								<td class="text-center">3</td>
								<td class="text-center"><a href="">임베디드</a></td>
								<td class="text-center">공학관305 수 4~6</td>
								<td class="text-center"><a href="">김점구</a></td>
								<td class="text-center">
									<button type="button" class="btn btn-warning btn-xs text-center">수업계획서</button>
								</td>
							</tr>
						</tbody>
					</table>
					

					
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