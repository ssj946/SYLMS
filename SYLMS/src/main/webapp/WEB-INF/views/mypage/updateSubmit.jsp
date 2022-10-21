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
</style>
</head>

<body>

<header>
	<jsp:include page="/WEB-INF/views/layout/header.jsp"/>
</header>
	
<main>
<section>
	<div class="container-fluid">

		<div class="row">&nbsp;</div>
		<div class="row">
			<div class="col-xl-2 col-lg-3 col-md-4 sidebar pt-1">
			<!-- 왼쪽 사이드바 자리 -->
			<jsp:include page="/WEB-INF/views/layout/l_sidebar.jsp"/>
			</div>
		
			<!-- 본문 -->
					<div class="col-xl-10 col-lg-9 col-md-8">
					
						<form name="memberForm" method="post">
							<div class="row mb-3">
								<label class="col-sm-2 col-form-label" for="userId">아이디</label>
								<div class="col-sm-10 userId-box"></div>
							</div>
							<div class="col-5 pe-1">
									<input type="text" name="userId" id="userId" class="form-control" value="${dto.userId}" 
											${mode=="update" ? "readonly='readonly' ":""}
											placeholder="아이디">
								</div>

						</form>
					</div>


					<!-- 본문 끝 -->
		</div>
	  </div> 
	</section>
</main>
<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>