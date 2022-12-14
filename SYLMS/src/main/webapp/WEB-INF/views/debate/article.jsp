<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">
function deleteBoard() {
    if(confirm("게시글을 삭제 하시 겠습니까 ? ")) {
	    let query = "articleNo=${dto.articleNo}&subjectNo=${subjectNo}";
	    let url = "${pageContext.request.contextPath}/debate/delete.do?" + query;
    	location.href = url;
    }
}
</c:if>
</script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/board2.css" type="text/css">

<script type="text/javascript">
function login() {
	location.href="${pageContext.request.contextPath}/member/login.do";
}

function ajaxFun(url, method, query, dataType, fn) {
	$.ajax({
		type:method,
		url:url,
		data:query,
		dataType:dataType,
		success:function(data) {
			fn(data);
		},
		beforeSend:function(jqXHR) {
			jqXHR.setRequestHeader("AJAX", true);
		},
		error:function(jqXHR) {
			if(jqXHR.status === 403) {
				login();
				return false;
			} else if(jqXHR.status === 400) {
				alert("요청 처리가 실패 했습니다.");
				return false;
			}
	    	
			console.log(jqXHR.responseText);
		}
	});
}

//페이징 처리
$(function(){
	listPage(1);
});

function listPage(page) {
	let url = "${pageContext.request.contextPath}/debate/listReply.do";
	let query = "subjectNo=${subjectNo}&articleNo=${dto.articleNo}&pageNo="+page;
	let selector = "#listReply";
	
	const fn = function(data){
		$(selector).html(data);
	};
	ajaxFun(url, "get", query, "html", fn);
}

//댓글 등록
$(function(){
	$(".btnSendReply").click(function(){
		let articleNo = "${dto.articleNo}";
		const $tb = $(this).closest("table");
		let content = $tb.find("textarea").val().trim();
		if(! content) {
			$tb.find("textarea").focus();
			return false;
		}
		content = encodeURIComponent(content);
		
		let url = "${pageContext.request.contextPath}/debate/insertReply.do";
		let query = "subjectNo=${subjectNo}&articleNo=" + articleNo + "&content=" + content + "&answer=0";
		
		const fn = function(data){
			$tb.find("textarea").val("");
			
			let state = data.state;
			if(state === "true") {
				listPage(1);
			} else if(state === "false") {
				alert("댓글을 추가 하지 못했습니다.");
			}
		};
		
		ajaxFun(url, "post", query, "json", fn);
	});
});

//댓글 삭제
$(function(){
	$("body").on("click", ".deleteReply", function(){
		if(! confirm("댓글을 삭제하시겠습니까 ? ")) {
		    return false;
		}
		
		let replyNo = $(this).attr("data-replyNo");
		let page = $(this).attr("data-pageNo");
		
		let url = "${pageContext.request.contextPath}/debate/deleteReply.do";
		let query = "replyNo="+replyNo;
		
		const fn = function(data){
			// let state = data.state;
			listPage(page);
		};
		
		ajaxFun(url, "post", query, "json", fn);
	});
});


//댓글 좋아요 / 싫어요
$(function(){
	$("body").on("click", ".btnSendReplyLike", function(){
		let replyNo = $(this).attr("data-replyNo");
		let replyLike = $(this).attr("data-replyLike");
		const $btn = $(this);
		
		let msg = "의견에 공감하지 않습니까 ? (취소불가)";
		if(replyLike === "1") {
			msg="의견에 공감하십니까 ? (취소불가)";
		}
		
		if(! confirm(msg)) {
			return false;
		}
		
		let url = "${pageContext.request.contextPath}/debate/insertReplyLike.do";
		let query = "replyNo=" + replyNo + "&replyLike=" + replyLike;
		
		const fn = function(data){
			let state = data.state;
			if(state === "true") {
				let likeCount = data.likeCount;
				let disLikeCount = data.disLikeCount;
				
				$btn.parent("td").children().eq(0).find("span").html(likeCount);
				$btn.parent("td").children().eq(1).find("span").html(disLikeCount);
			} else if(state === "liked") {
				alert("공감 여부는 한번만 가능합니다.");
			} else {
				alert("공감 여부 처리가 실패했습니다.");
			}
		};
		
		ajaxFun(url, "post", query, "json", fn);
	});
});



//댓글별 답글 리스트
function listReplyAnswer(answer) {
	let url = "${pageContext.request.contextPath}/debate/listReplyAnswer.do";
	let query = "answer=" + answer;
	let selector = "#listReplyAnswer" + answer;
	
	const fn = function(data){
		$(selector).html(data);
	};
	ajaxFun(url, "get", query, "html", fn);
}



//댓글별 답글 개수
function countReplyAnswer(answer) {
	let url = "${pageContext.request.contextPath}/debate/countReplyAnswer.do";
	let query = "answer=" + answer;
	
	const fn = function(data){
		let count = data.count;
		let selector = "#answerCount"+answer;
		$(selector).html(count);
	};
	
	ajaxFun(url, "post", query, "json", fn);
}


//답글 버튼(댓글별 답글 등록폼 및 답글리스트)
$(function(){
	$("body").on("click", ".btnReplyAnswerLayout", function(){
		const $trReplyAnswer = $(this).closest("tr").next();
		// const $trReplyAnswer = $(this).parent().parent().next();
		// const $answerList = $trReplyAnswer.children().children().eq(0);
		
		let isVisible = $trReplyAnswer.is(':visible');
		let replyNo = $(this).attr("data-replyNo");
			
		if(isVisible) {
			$trReplyAnswer.hide();
		} else {
			$trReplyAnswer.show();
         
			// 답글 리스트
			listReplyAnswer(replyNo);
			
			// 답글 개수
			countReplyAnswer(replyNo);
		}
	});
	
});

//댓글별 답글 등록
$(function(){
	$("body").on("click", ".btnSendReplyAnswer", function(){
		let articleNo = "${dto.articleNo}";
		let replyNo = $(this).attr("data-replyNo");
		const $td = $(this).closest("td");
		
		let content = $td.find("textarea").val().trim();
		if(! content) {
			$td.find("textarea").focus();
			return false;
		}
		content = encodeURIComponent(content);
		
		let url = "${pageContext.request.contextPath}/debate/insertReply.do";
		let query = "subjectNo=${subjectNo}&articleNo=" + articleNo + "&content=" + content + "&answer=" + replyNo;
		
		const fn = function(data){
			$td.find("textarea").val("");
			
			let state = data.state;
			if(state === "true") {
				listReplyAnswer(replyNo);
				countReplyAnswer(replyNo);
			}
		};
		
		ajaxFun(url, "post", query, "json", fn);
	});
});

//댓글별 답글 삭제
$(function(){
	$("body").on("click", ".deleteReplyAnswer", function(){
		if(! confirm("댓글을 삭제하시겠습니까 ? ")) {
		    return false;
		}
		
		let replyNo = $(this).attr("data-replyNo");
		let answer = $(this).attr("data-answer");
		
		let url = "${pageContext.request.contextPath}/debate/deleteReply.do";
		let query = "replyNo=" + replyNo;
		
		const fn = function(data){
			listReplyAnswer(answer);
			countReplyAnswer(answer);
		};
		
		ajaxFun(url, "post", query, "json", fn);
	});
});


</script>
</head>

<body>

<main>
	<section>
		<div class="container-fluid">
			<div class="row">
				<div class="col-1"></div>
				<div class="col-10">
					<div class="card p-2">
					<div class="row ps-3 pe-1">
					<div class="col-auto bg-dark bg-gradient rounded" style="min-height: 100vh">
						<!-- 왼쪽 사이드바 자리 -->
						<jsp:include page="/WEB-INF/views/layout/brief_sidebar.jsp" />
					</div>
					<div class="col">
						<jsp:include page="/WEB-INF/views/layout/header2.jsp" />
						<jsp:include page="/WEB-INF/views/layout/classroom_header.jsp" />
						<jsp:include page="/WEB-INF/views/layout/lecture_index.jsp" />
						
					<!-- 본문 -->
					
					<div class="card">
				<div class="card-header text-light bg-navy bg-gradient">
					<h5 class="d-inline ps-2"><i class="fa-solid fa-people-line fa-lg"></i> 토론게시판 </h5>
				</div>
				
				<div class="card-body">
					
					<table class="table">
				
						<thead>
							<tr>
								<td colspan="2" align="center" style="font-weight: bold; font-size: medium;">
									${dto.title}
								</td>
							</tr>
						</thead>
						
						<tbody>
							<tr>
								<td width="50%">
									이름 : ${dto.name}
								</td>
								<td align="right">
									${dto.reg_date} | 조회 ${dto.hitCount}
								</td>
							</tr>
							<tr>
								<td colspan="2" valign="top" height="200">
									${dto.content}
								</td>
							</tr>
						</tbody>
					</table>
					<table class="table table-borderless">
						<tr>
							<td width="50%">
								
								<c:choose>
									<c:when test="${sessionScope.member.userId==dto.userId}">
										<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/debate/update.do?subjectNo=${subjectNo}&articleNo=${dto.articleNo}';">수정</button>
									</c:when>
									<c:otherwise>
										<button type="button" class="btn btn-light" disabled="disabled">수정</button>
									</c:otherwise>
								</c:choose>
						    	
								<c:choose>
						    		<c:when test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">
						    			<button type="button" class="btn btn-light" onclick="deleteBoard();">삭제</button>
						    		</c:when>
						    		<c:otherwise>
						    			<button type="button" class="btn btn-light" disabled="disabled">삭제</button>
						    		</c:otherwise>
						    	</c:choose>
							</td>
							<td class="text-end">
								<button type="button" class="btn btn-light" onclick="location.href='${pageContext.request.contextPath}/debate/list.do?subjectNo=${subjectNo}';">목록</button>
							</td>
						</tr>
					</table>
					
					<div class="reply">
					<form name="replyForm" method="post">
						<div class='form-header'>
							<span class="bold">의견</span><span> - 타인을 비방하거나 개인정보를 유출하는 글의 게시를 삼가해 주세요.</span>
						</div>
						
						<table class="table table-borderless reply-form">
							<tr>
								<td>
									<textarea class='form-control' name="content"></textarea>
								</td>
							</tr>
							<tr>
							   <td align='right'>
							        <button type='button' class='btn btn-light btnSendReply'>의견 등록</button>
							    </td>
							 </tr>
						</table>
					</form>
					
					<div id="listReply"></div>
				</div>
					
					
					
				</div>
				</div>
					</div>
					</div>
					</div>
				</div>
				<div class="col-1"></div>
			</div>
		</div>
				
				<!-- 본문 끝 -->
	</section>
</main>
<jsp:include page="/WEB-INF/views/layout/staticFooter.jsp" />
</body>
</html>

