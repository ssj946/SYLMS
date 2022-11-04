package com.debate;

import java.io.IOException;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;
import com.util.MyUtilBootstrap;

@WebServlet("/debate/*")
public class DebateServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		
		if (uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if (uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		} else if (uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if (uri.indexOf("insertReply.do") != -1) {
			// 댓글 추가
			insertReply(req, resp);
		} else if (uri.indexOf("listReply.do") != -1) {
			// 댓글 리스트
			listReply(req, resp);
		} else if (uri.indexOf("deleteReply.do") != -1) {
			// 댓글 삭제
			deleteReply(req, resp);
		} else if (uri.indexOf("insertReplyLike.do") != -1) {
			// 댓글 좋아요/싫어요 추가
			insertReplyLike(req, resp);
		} else if (uri.indexOf("countReplyLike.do") != -1) {
			// 댓글 좋아요/싫어요 개수
			countReplyLike(req, resp);
		} else if (uri.indexOf("insertReplyAnswer.do") != -1) {
			// 댓글의 답글 추가
			insertReplyAnswer(req, resp);
		} else if (uri.indexOf("listReplyAnswer.do") != -1) {
			// 댓글의 답글 리스트
			listReplyAnswer(req, resp);
		} else if (uri.indexOf("deleteReplyAnswer.do") != -1) {
			// 댓글의 답글 삭제
			deleteReplyAnswer(req, resp);
		} else if (uri.indexOf("countReplyAnswer.do") != -1) {
			// 댓글의 답글 개수
			countReplyAnswer(req, resp);
		}
		
	}
	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 게시물 리스트
		DebateDAO dao = new DebateDAO();
		String subjectNo = req.getParameter("subjectNo");
		
		DebateDTO dto1 = new DebateDTO();
		MyUtil util = new MyUtilBootstrap();
		String cp = req.getContextPath();
		
		
		try {
			dto1 = dao.readSubject(subjectNo);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("professorName", dto1.getProfessorname());
			req.setAttribute("semester", dto1.getSemester());
			req.setAttribute("subjectName", dto1.getSubjectName());
			req.setAttribute("syear", dto1.getSyear());
			
			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 검색
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "all";
				keyword = "";
			}

			// GET 방식인 경우 디코딩
			if (req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}

			// 전체 데이터 개수
			int dataCount;
			if (keyword.length() == 0) {
				dataCount = dao.dataCount(subjectNo, condition, keyword);
			} else {
				dataCount = dao.dataCount(subjectNo);
			}
			
			// 전체 페이지 수
			int size = 10;
			int total_page = util.pageCount(dataCount, size);
			if (current_page > total_page) {
				current_page = total_page;
			}

			// 게시물 가져오기
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;
			
			List<DebateDTO> list = null;
			if (keyword.length() == 0) {
				list = dao.listBoard(subjectNo, offset, size);
			} else {
				list = dao.listBoard(subjectNo, offset, size, condition, keyword);
			}

			String query = "";
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}

			// 페이징 처리
			String listUrl = cp + "/debate/list.do?subjectNo=" + subjectNo;
			String articleUrl = cp + "/debate/article.do?subjectNo="+subjectNo+"page=" + current_page;
			if (query.length() != 0) {
				listUrl += "?" + query;
				articleUrl += "&" + query;
			}

			String paging = util.paging(current_page, total_page, listUrl);

			// 포워딩할 JSP에 전달할 속성
			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			req.setAttribute("condition", condition);
			req.setAttribute("keyword", keyword);
			
			// 걍,,넣어봄
			req.setAttribute("subjectNo", subjectNo );
			req.setAttribute("list", list);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			req.setAttribute("condition", condition);
			req.setAttribute("keyword", keyword);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		// JSP로 포워딩
		forward(req, resp, "/WEB-INF/views/debate/list.jsp");
	}

	protected void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글쓰기 폼
		try {
			
			String subjectNo = req.getParameter("subjectNo");
			
			DebateDAO dao = new DebateDAO();
			DebateDTO dto1 = new DebateDTO();
			
			dto1 = dao.readSubject(subjectNo);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("professorName", dto1.getProfessorname());
			req.setAttribute("semester", dto1.getSemester());
			req.setAttribute("subjectName", dto1.getSubjectName());
			req.setAttribute("syear", dto1.getSyear());
			
			req.setAttribute("subjectNo", subjectNo );
			req.setAttribute("mode", "write");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/debate/write.jsp");
	}

	protected void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글 저장
		DebateDAO dao = new DebateDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		String subjectNo = req.getParameter("subjectNo");
		
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/debate/list.do");
			return;
		}
		
		try {
			DebateDTO dto = new DebateDTO();

			// userId는 세션에 저장된 정보
			dto.setUserId(info.getUserId());

			// 파라미터
			dto.setTitle(req.getParameter("title"));
			dto.setContent(req.getParameter("content"));
			dto.setSubjectNo(req.getParameter(subjectNo));

			dao.insertBoard(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/debate/list.do?subjectNo="+subjectNo);
	}

	protected void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 글보기
		DebateDAO dao = new DebateDAO();
		MyUtil util = new MyUtilBootstrap();
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		String query = "page=" + page;

		try {
			String articleNo = req.getParameter("articleNo");
			String subjectNo = req.getParameter("subjectNo");
			
			DebateDTO dto1 = new DebateDTO();
			
			dto1 = dao.readSubject(subjectNo);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("professorName", dto1.getProfessorname());
			req.setAttribute("semester", dto1.getSemester());
			req.setAttribute("subjectName", dto1.getSubjectName());
			req.setAttribute("syear", dto1.getSyear());
			
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "all";
				keyword = "";
			}
			keyword = URLDecoder.decode(keyword, "utf-8");

			if (keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}

			// 조회수 증가
			dao.updateHitCount(articleNo, subjectNo);

			// 게시물 가져오기
			DebateDTO dto = dao.readBoard(articleNo, subjectNo);
			if (dto == null) { // 게시물이 없으면 다시 리스트로
				resp.sendRedirect(cp + "/debate/list.do?subjectNo=" +subjectNo + query);
				return;
			}
			dto.setContent(util.htmlSymbols(dto.getContent()));
			

			// 이전글 다음글
			DebateDTO preReadDto = dao.preReadBoard(dto.getSubjectNo(), dto.getArticleNo(), condition, keyword);
			DebateDTO nextReadDto = dao.nextReadBoard(dto.getSubjectNo(), dto.getArticleNo(), condition, keyword);

			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);
			req.setAttribute("preReadDto", preReadDto);
			req.setAttribute("nextReadDto", nextReadDto);
			

			// 포워딩
			forward(req, resp, "/WEB-INF/views/debate/article.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/debate/list.do?" + query);
	}

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 폼
		DebateDAO dao = new DebateDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();

		String page = req.getParameter("page");

			String subjectNo = req.getParameter("subjectNo");
			String articleNo = req.getParameter("articleNo");
		try {
			DebateDTO dto = dao.readBoard(articleNo, subjectNo);

			if (dto == null) {
				resp.sendRedirect(cp + "/debate/list.do?subjectNo="+subjectNo+"page=" + page);
				return;
			}

			// 게시물을 올린 사용자가 아니면
			if (! dto.getUserId().equals(info.getUserId())) {
				resp.sendRedirect(cp + "/debate/list.do?subjectNo="+subjectNo+"page=" + page);
				return;
			}

			req.setAttribute("dto", dto);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("articleNo", articleNo);
			req.setAttribute("page", page);
			req.setAttribute("mode", "update");

			forward(req, resp, "/WEB-INF/views/debate/write.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/debate/list.do?subjectNo="+subjectNo+"page=" + page);
	}

	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수정 완료
		DebateDAO dao = new DebateDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		String subjectNo = req.getParameter("subjectNo");
		String page = req.getParameter("page");

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/debate/list.do?subjectNo="+subjectNo+"page=" + page);
			return;
		}

		try {
			DebateDTO dto = new DebateDTO();
			
			dto.setArticleNo(req.getParameter("articleNo"));
			dto.setTitle(req.getParameter("title"));
			dto.setContent(req.getParameter("content"));

			dto.setUserId(info.getUserId());

			dao.updateBoard(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/debate/list.do?subjectNo="+subjectNo+"page=" + page);
	}

	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 삭제
		DebateDAO dao = new DebateDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		String subjectNo = req.getParameter("subjectNo");
		String page = req.getParameter("page");
		String query = "page=" + page;

		try {
			String articleNo = req.getParameter("articleNo");
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "all";
				keyword = "";
			}
			keyword = URLDecoder.decode(keyword, "utf-8");

			if (keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}

			dao.deleteBoard(articleNo, info.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/debate/list.do?subjectNo="+ subjectNo + query);
	}
	

	// 리플 리스트 - AJAX:TEXT
	protected void listReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DebateDAO dao = new DebateDAO();
		MyUtil util = new MyUtilBootstrap();

		try {
			String articleNo = req.getParameter("articleNo");
			String pageNo = req.getParameter("pageNo");
			int current_page = 1;
			if (pageNo != null) {
				current_page = Integer.parseInt(pageNo);
			}

			int size = 5;
			int total_page = 0;
			int replyCount = 0;

			replyCount = dao.dataCountReply(articleNo);
			total_page = util.pageCount(replyCount, size);
			if (current_page > total_page) {
				current_page = total_page;
			}

			// 리스트에 출력할 데이터
			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;
			
			List<ReplyDTO> listReply = dao.listReply(articleNo, offset, size);

			// 엔터를 <br>
			for (ReplyDTO dto : listReply) {
				dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
			}

			// 페이징 처리 : AJAX 용 - listPage : 자바스크립트 함수명
			String paging = util.pagingMethod(current_page, total_page, "listPage");

			req.setAttribute("listReply", listReply);
			req.setAttribute("pageNo", current_page);
			req.setAttribute("replyCount", replyCount);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			req.setAttribute("articleNo", articleNo);
			
			forward(req, resp, "/WEB-INF/views/bbs/listReply.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendError(400);
		
	}

	// 리플 또는 답글 저장 - AJAX:JSON
	protected void insertReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DebateDAO dao = new DebateDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String state = "false";
		try {
			ReplyDTO dto = new ReplyDTO();

			// String articleNo = req.getParameter("articleNo");
			dto.setArticleNo("articleNo");
			dto.setUserId(info.getUserId());
			dto.setContent(req.getParameter("content"));
			String answer = req.getParameter("answer");
			if (answer != null) {
				dto.setAnswer(Long.parseLong(answer));
			}
			
			dao.insertReply(dto);
			
			state = "true";
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONObject job = new JSONObject();
		job.put("state", state);

		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}

	// 리플 또는 답글 삭제 - AJAX:JSON
	protected void deleteReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DebateDAO dao = new DebateDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String state = "false";

		try {
			String replyNo = req.getParameter("replyNo");
			
			dao.deleteReply(replyNo, info.getUserId());
			
			state = "true";
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONObject job = new JSONObject();
		job.put("state", state);

		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}

	// 댓글 좋아요 / 싫어요 저장 - AJAX:JSON
	protected void insertReplyLike(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DebateDAO dao = new DebateDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String state = "false";
		int likeCount = 0;
		int disLikeCount = 0;

		try {
			String replyNo = req.getParameter("replyNo");
			int replyLike = Integer.parseInt(req.getParameter("replyLike"));

			ReplyDTO dto = new ReplyDTO();

			dto.setReplyNo(replyNo);
			dto.setUserId(info.getUserId());
			dto.setReplyLike(replyLike);

			dao.insertReplyLike(dto);

			Map<String, Integer> map = dao.countReplyLike(replyNo);

			if (map.containsKey("likeCount")) {
				likeCount = map.get("likeCount");
			}

			if (map.containsKey("disLikeCount")) {
				disLikeCount = map.get("disLikeCount");
			}

			state = "true";
		} catch (SQLException e) {
			if(e.getErrorCode() == 1) {
				state = "liked";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONObject job = new JSONObject();
		job.put("state", state);
		job.put("likeCount", likeCount);
		job.put("disLikeCount", disLikeCount);

		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}

	// 댓글 좋아요 / 싫어요 개수 - AJAX:JSON
	protected void countReplyLike(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DebateDAO dao = new DebateDAO();

		int likeCount = 0;
		int disLikeCount = 0;

		try {
			String replyNo = req.getParameter("replyNo");
			Map<String, Integer> map = dao.countReplyLike(replyNo);

			if (map.containsKey("likeCount")) {
				likeCount = map.get("likeCount");
			}

			if (map.containsKey("disLikeCount")) {
				disLikeCount = map.get("disLikeCount");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONObject job = new JSONObject();
		job.put("likeCount", likeCount);
		job.put("disLikeCount", disLikeCount);

		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}

	// 답글 저장 - AJAX:JSON
	protected void insertReplyAnswer(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		insertReply(req, resp);
	}

	// 리플의 답글 리스트 - AJAX:TEXT
	protected void listReplyAnswer(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DebateDAO dao = new DebateDAO();

		try {
			long answer = Long.parseLong(req.getParameter("answer"));

			List<ReplyDTO> listReplyAnswer = dao.listReplyAnswer(answer);

			// 엔터를 <br>(스타일 => style="white-space:pre;")
			for (ReplyDTO dto : listReplyAnswer) {
				dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
			}

			req.setAttribute("listReplyAnswer", listReplyAnswer);

			forward(req, resp, "/WEB-INF/views/bbs/listReplyAnswer.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendError(400);
	}

	// 리플 답글 삭제 - AJAX:JSON
	protected void deleteReplyAnswer(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		deleteReply(req, resp);
	}

	// 리플의 답글 개수 - AJAX:JSON
	protected void countReplyAnswer(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		DebateDAO dao = new DebateDAO();
		int count = 0;

		try {
			long answer = Long.parseLong(req.getParameter("answer"));
			count = dao.dataCountReplyAnswer(answer);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JSONObject job = new JSONObject();
		job.put("count", count);

		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}
}
