<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
  <div class="container-fluid">
    <button class="navbar-toggler ms-auto" type="button" data-bs-toggle="collapse" data-bs-target="#topbar" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse" id="topbar">
    
    </div>
    <div class="collapse navbar-collapse" >
    <div class="col-xl-2 col-lg-3 col-md-4"></div>
    <div class="col-xl-7 col-lg-6 col-md-4"></div>
    <div class="col-xl-3 col-lg-3 col-md-4">
    <ul class="navbar-nav ms-0 mb-2 mb-lg-0">
      
	<c:if test="${empty sessionScope.member}">
		<li class="nav-item ms-auto">
			<a class="nav-link" href="javascript:dialogLogin();" title="로그인"><i class="fas fa-lock text-muted fa-lg text-warning"></i></a>
		</li>
	</c:if>
				
	
     <c:if test="${sessionScope.member.userId == 'admin'}">
     <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle" href="#" title="관리" id="administrator" role="button" data-bs-toggle="dropdown" aria-expanded="false"><i class="fas fa-gear text-muted fa-lg"></i>&nbsp;</a>
      <ul class="dropdown-menu" aria-labelledby="administrator">
        <li><a class="dropdown-item" href="#">관리자님, 환영합니다.</a></li>
        <li><hr class="dropdown-divider"></li>
        <li><a class="dropdown-item" href="#">강의실 관리</a></li>
        <li><a class="dropdown-item" href="#">커뮤니티 관리</a></li>
      </ul>&nbsp;&nbsp;
     </li>
     </c:if>
     <c:if test="${not empty sessionScope.member}">
        <li class="nav-item">
          <a class="nav-link" href="#" id="mypage" title="마이페이지">
          &nbsp;<i class="fas fa-user text-muted fa-lg">&nbsp;</i></a>
        </li>
        <li class="nav-item">
          <a class="position-relative nav-link " title="알림" href="#">
            &nbsp;&nbsp;<i class="fas fa-bell text-muted fa-lg"></i>
            <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">99+
            	<span class="visually-hidden">unread messages</span>
  			</span>
          </a> &nbsp;&nbsp;
        <li class="nav-item">
          <a class="nav-link position-relative" href="#" title="메시지">&nbsp;&nbsp;&nbsp;&nbsp;<i class="fas fa-comments text-muted fa-lg"></i>
          <span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">99+
            	<span class="visually-hidden">unread messages</span>
  			</span>
          </a>&nbsp;&nbsp;
        </li>
        
        <li class="nav-item ms-auto">
          <a class="nav-link" href="${pageContext.request.contextPath}/member/logout.do" title="로그아웃">&nbsp;<i class="fas fa-sign-out-alt text-danger fa-lg"></i>&nbsp;</a>
        </li>
       </c:if>
      </ul>
      </div>
    </div>
  </div>
</nav>		
