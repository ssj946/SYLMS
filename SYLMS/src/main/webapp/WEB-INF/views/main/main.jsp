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
		<div class="row">
		<jsp:include page="/WEB-INF/views/layout/l_sidebar.jsp"/>

			<div class="col-xl-8 col-lg-6 col-md-6">
				<div class="row pt-md-5 mt-md-3 mb-5">
					<div class="col-xl-3 col-sm-6 p-2">
						<div class="card">
							<div class="card-body">
								<div class="d-flex justify-content-between">
									<i class="fas fa-shopping-cart fa-3x text-warning"></i>
									<div class="text-right text-secondary">
									<h5>Sales</h5>
									<h3>$135,000</h3>
									</div>
								</div>
							</div>	
							<div class="card-footer text-secondary">
								<i class="fas fa-sync mr-3"></i>
								<span>Updated Now</span>
							</div>	
						</div>
					</div>
					
					<div class="col-xl-3 col-sm-6 p-2">
						<div class="card">
							<div class="card-body">
								<div class="d-flex justify-content-between">
									<i class="fas fa-shopping-cart fa-3x text-warning"></i>
									<div class="text-right text-secondary">
									<h5>Sales</h5>
									<h3>$135,000</h3>
									</div>
								</div>
							</div>	
							<div class="card-footer text-secondary">
								<i class="fas fa-sync mr-3"></i>
								<span>Updated Now</span>
							</div>	
						</div>
					</div>
					
					<div class="col-xl-3 col-sm-6 p-2">
						<div class="card">
							<div class="card-body">
								<div class="d-flex justify-content-between">
									<i class="fas fa-shopping-cart fa-3x text-warning"></i>
									<div class="text-right text-secondary">
									<h5>Sales</h5>
									<h3>$135,000</h3>
									</div>
								</div>
							</div>	
							<div class="card-footer text-secondary">
								<i class="fas fa-sync mr-3"></i>
								<span>Updated Now</span>
							</div>	
						</div>
					</div>
					
					<div class="col-xl-3 col-sm-6 p-2">
						<div class="card">
							<div class="card-body">
								<div class="d-flex justify-content-between">
									<i class="fas fa-shopping-cart fa-3x text-warning"></i>
									<div class="text-right text-secondary">
									<h5>Sales</h5>
									<h3>$135,000</h3>
									</div>
								</div>
							</div>	
							<div class="card-footer text-secondary">
								<i class="fas fa-sync mr-3"></i>
								<span>Updated Now</span>
							</div>	
						</div>
					</div>
				</div>
			</div>
			<jsp:include page="/WEB-INF/views/layout/r_sidebar.jsp"/>
			</div>
		</div>
	
	</section>
</main>

<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp"/>
</body>
</html>