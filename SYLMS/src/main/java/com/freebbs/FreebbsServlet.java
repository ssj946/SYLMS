package com.freebbs;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.member.SessionInfo;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;
import com.util.MyUtilBootstrap;

@MultipartConfig
@WebServlet("/freebbs/*")
public class FreebbsServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;
	private String pathname;

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

		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "freebbs";

		if (uri.indexOf("freebbs.do") != -1) {
			freebbsForm(req, resp);
		} else if (uri.indexOf("freebbsWrite.do") != -1) {
			freebbsWriteForm(req, resp);
		} else if (uri.indexOf("freebbswrite_ok.do") != -1) {
			freebbsWriteSubmit(req, resp);
		} else if (uri.indexOf("freebbsArticle.do") != -1) {
			freebbsArticleForm(req, resp);
		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("deleteFile.do") != -1) {
			deleteFile(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if (uri.indexOf("download.do") != -1) {
			download(req, resp);
		} else if (uri.indexOf("deleteList.do") != -1) {
			deleteList(req, resp);
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

	private void countReplyAnswer(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		FreebbsDAO dao = new FreebbsDAO();
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

	private void deleteReplyAnswer(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		deleteReply(req, resp);

	}

	private void listReplyAnswer(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		FreebbsDAO dao = new FreebbsDAO();

		try {
			long answer = Long.parseLong(req.getParameter("answer"));

			List<ReplyDTO> listReplyAnswer = dao.listReplyAnswer(answer);

			for (ReplyDTO dto : listReplyAnswer) {
				dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
			}

			req.setAttribute("listReplyAnswer", listReplyAnswer);

			forward(req, resp, "/WEB-INF/views/freebbs/listReplyAnswer.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendError(400);
	}

	private void insertReplyAnswer(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		insertReply(req, resp);

	}

	private void countReplyLike(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FreebbsDAO dao = new FreebbsDAO();
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

	private void insertReplyLike(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		FreebbsDAO dao = new FreebbsDAO();

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
			e.printStackTrace();
			if (e.getErrorCode() == 1) {
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

	private void deleteReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FreebbsDAO dao = new FreebbsDAO();

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

	private void listReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FreebbsDAO dao = new FreebbsDAO();
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

			int offset = (current_page - 1) * size;
			if (offset < 0)
				offset = 0;

			List<ReplyDTO> listReply = dao.listReply(articleNo, offset, size);

			for (ReplyDTO dto : listReply) {
				dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
			}

			String paging = util.pagingMethod(current_page, total_page, "listPage");

			req.setAttribute("listReply", listReply);
			req.setAttribute("pageNo", current_page);
			req.setAttribute("replyCount", replyCount);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			req.setAttribute("articleNo", articleNo);

			forward(req, resp, "/WEB-INF/views/debate/listReply.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendError(400);

	}

	private void insertReply(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FreebbsDAO dao = new FreebbsDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String state = "false";

		try {

			try {
				ReplyDTO dto = new ReplyDTO();

				String articleNo = req.getParameter("articleNo");
				String subjectNo = req.getParameter("subjectNo");
				dto.setUserId(info.getUserId());
				dto.setContent(req.getParameter("content"));
				dto.setSubjectNo(subjectNo);
				dto.setArticleNo(articleNo);
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

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void freebbsWriteSubmit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		FreebbsDAO dao = new FreebbsDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		String subjectNo = req.getParameter("subjectNo");

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/freebbs/freebbs.do?subjectNo=" + subjectNo);
			return;
		}

		try {
			FreebbsDTO dto = new FreebbsDTO();

			dto.setUserId(info.getUserId());
			dto.setTitle(req.getParameter("title"));
			dto.setContent(req.getParameter("content"));
			dto.setSubjectNo(req.getParameter("subjectNo"));

			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				String[] saveFiles = map.get("saveFilenames");
				String[] originalFiles = map.get("originalFilenames");
				dto.setSaveFiles(saveFiles);
				dto.setOriginalFiles(originalFiles);
			}
			dao.insertFreebbs(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/freebbs/freebbs.do?subjectNo=" + subjectNo);

	}

	private void freebbsArticleForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String cp = req.getContextPath();

		String page = req.getParameter("page");
		String size = req.getParameter("size");
		String query = "page=" + page + "&size=" + size;

		FreebbsDAO dao = new FreebbsDAO();

		try {

			String subjectNo = req.getParameter("subjectNo");

			FreebbsDTO dto1 = new FreebbsDTO();

			dto1 = dao.readSubject(subjectNo);

			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("professorName", dto1.getProfessorname());
			req.setAttribute("semester", dto1.getSemester());
			req.setAttribute("subjectName", dto1.getSubjectName());
			req.setAttribute("syear", dto1.getSyear());

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

			// 조회수
			dao.updateHitCount(articleNo, subjectNo);

			FreebbsDTO dto = dao.readfreebbs(articleNo);

			if (dto == null) {
				resp.sendRedirect(cp + "/freebbs/freebbs.do?" + query);
				return;
			}

			dto.setContent(dto.getContent().replaceAll("\n", "<br>"));

			FreebbsDTO preReadDto = dao.preReadfreebbs(dto.getSubjectNo(), dto.getArticleNo(), condition, keyword);
			FreebbsDTO nextReadDto = dao.nextReadfreebbs(dto.getSubjectNo(), dto.getArticleNo(), condition, keyword);

			List<FreebbsDTO> listFile = dao.listfreebbsFile(articleNo);

			req.setAttribute("dto", dto);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("condition", condition);
			req.setAttribute("keyword", keyword);
			req.setAttribute("query", query);
			req.setAttribute("page", page);
			req.setAttribute("size", size);
			req.setAttribute("name", dto.getName());
			req.setAttribute("preReadDto", preReadDto);
			req.setAttribute("nextReadDto", nextReadDto);
			req.setAttribute("listFile", listFile);

			forward(req, resp, "/WEB-INF/views/freebbs/article.jsp");
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/freebbs/freebbs.do?" + query);
	}

	private void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		FreebbsDAO dao = new FreebbsDAO();

		String subjectNo = req.getParameter("subjectNo");
		String articleNo = req.getParameter("articleNo");

		try {

			FreebbsDTO dto = dao.readfreebbs(articleNo);
			if (dto == null) {
				resp.sendRedirect(cp + "/freebbs/freebbs.do?subjectNo=" + subjectNo);
				return;
			}

			if (!dto.getUserId().equals(info.getUserId())) {
				resp.sendRedirect(cp + "/freebbs/freebbs.do?subjectNo=" + subjectNo);
				return;
			}

			List<FreebbsDTO> listFile = dao.listfreebbsFile(articleNo);

			req.setAttribute("dto", dto);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("listFile", listFile);
			req.setAttribute("articleNo", articleNo);

			req.setAttribute("mode", "update");

			forward(req, resp, "/WEB-INF/views/freebbs/write.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/freebbs/freebbs.do?subjectNo=" + subjectNo + "&articleNo=" + articleNo);

	}

	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		String subjectNo = req.getParameter("subjectNo");

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/freebbs/freebbs.do?subjectNo=" + subjectNo);
			return;
		}

		FreebbsDAO dao = new FreebbsDAO();

		String articleNo = req.getParameter("articleNo");

		try {

			FreebbsDTO dto = new FreebbsDTO();

			dto.setArticleNo(req.getParameter("articleNo"));
			dto.setTitle(req.getParameter("title"));
			dto.setContent(req.getParameter("content"));
			dto.setSubjectNo(subjectNo);

			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if (map != null) {
				String[] saveFiles = map.get("saveFilenames");
				String[] originalFiles = map.get("originalFilenames");
				dto.setSaveFiles(saveFiles);
				dto.setOriginalFiles(originalFiles);
			}

			dao.updatefreebss(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/freebbs/freebbs.do?subjectNo=" + subjectNo + "&articleNo=" + articleNo);

	}

	private void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		String subjectNo = req.getParameter("subjectNo");

		FreebbsDAO dao = new FreebbsDAO();

		try {
			String articleNo = req.getParameter("articleNo");
			String fileNo = req.getParameter("fileNo");

			FreebbsDTO dto = dao.readfreebbsFile(fileNo);
			if (dto != null) {
				FileManager.doFiledelete(pathname, dto.getSaveFilename());

				dao.deleteFreebbsFile("one", fileNo);
			}

			resp.sendRedirect(cp + "/freebbs/update.do?subjectNo=" + subjectNo + "&articleNo=" + articleNo);
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

		String articleNo = req.getParameter("articleNo");

		resp.sendRedirect(cp + "/freebbs/freebbs.do?subjectNo=" + subjectNo + "&articleNo=" + articleNo);

	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		String subjectNo = req.getParameter("subjectNo");

		FreebbsDAO dao = new FreebbsDAO();

		String query = "subjectNo=" + subjectNo;

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

			FreebbsDTO dto = dao.readfreebbs(articleNo);
			if (dto == null) {
				resp.sendRedirect(cp + "/freebbs/freebbs.do?" + query);
				return;
			}

			List<FreebbsDTO> listFile = dao.listfreebbsFile(articleNo);
			for (FreebbsDTO vo : listFile) {
				FileManager.doFiledelete(pathname, vo.getSaveFilename());
			}

			dao.deleteFreebbsFile("all", articleNo);

			dao.deletefreebbs(articleNo);

		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/freebbs/freebbs.do?" + query);
	}

	private void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FreebbsDAO dao = new FreebbsDAO();
		boolean b = false;

		try {
			String fileNo = req.getParameter("fileNo");

			FreebbsDTO dto = dao.readfreebbsFile(fileNo);
			if (dto != null) {
				b = FileManager.doFiledownload(dto.getSaveFilename(), dto.getOriginalFilename(), pathname, resp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!b) {
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print("<script>alert('파일다운로드가 실패 했습니다.');history.back();</script>");
		}

	}

	private void deleteList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

	private void freebbsWriteForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		String size = req.getParameter("size");

		try {

			String subjectNo = req.getParameter("subjectNo");

			FreebbsDAO dao = new FreebbsDAO();
			FreebbsDTO dto1 = new FreebbsDTO();

			dto1 = dao.readSubject(subjectNo);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("professorName", dto1.getProfessorname());
			req.setAttribute("semester", dto1.getSemester());
			req.setAttribute("subjectName", dto1.getSubjectName());
			req.setAttribute("syear", dto1.getSyear());
			req.setAttribute("size", size);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("mode", "write");

		} catch (Exception e) {
			e.printStackTrace();
		}

		forward(req, resp, "/WEB-INF/views/freebbs/write.jsp");
	}

	private void freebbsForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		FreebbsDAO dao = new FreebbsDAO();
		String subjectNo = req.getParameter("subjectNo");

		FreebbsDTO dto1 = new FreebbsDTO();
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
			System.out.println(page);
			int current_page = 1;
			if (page != null) {
				current_page = Integer.parseInt(page);
			}

			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "all";
				keyword = "";
			}
			if (req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}

			int dataCount;
			if (keyword.length() != 0) {
				dataCount = dao.dataCount(subjectNo, condition, keyword);

			} else {
				dataCount = dao.dataCount(subjectNo);
			}

			int size = 10;

			int total_page = util.pageCount(dataCount, size);

			if (current_page > total_page) {
				current_page = total_page;
			}

			int offset = (current_page - 1) * size;
			if (offset < 0)
				offset = 0;

			List<FreebbsDTO> list;
			if (keyword.length() != 0) {
				list = dao.listfreebbs(subjectNo, offset, size, condition, keyword);

			} else {
				list = dao.listfreebbs(subjectNo, offset, size);
			}

			List<FreebbsDTO> listFreebbs = null;
			listFreebbs = dao.listfreebbs(subjectNo);
			for (FreebbsDTO dto : listFreebbs) {
				dto.setReg_date(dto.getReg_date().substring(0, 10));
			}

			long gap;
			Date curDate = new Date();
			SimpleDateFormat tt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			for (FreebbsDTO dto : list) {
				Date date = tt.parse(dto.getReg_date());
				gap = (curDate.getTime() - date.getTime()) / (1000 * 60 * 60 * 24);
				dto.setGap(gap);

				dto.setReg_date(dto.getReg_date().substring(0, 10));
			}

			String query = "";
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}

			String listUrl = cp + "/freebbs/freebbs.do?subjectNo=" + subjectNo; // 공지리스트 주소
			String freebbsUrl = cp + "/freebbs/freebbs.do?subjectNo=" + subjectNo + "?page=" + current_page;
			if (query.length() != 0) {
				listUrl += "?" + query;
				freebbsUrl += "&" + query;
			}

			String paging = util.paging(current_page, total_page, listUrl);

			req.setAttribute("query", query);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("list", list);
			req.setAttribute("listFreebbs", listFreebbs);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			req.setAttribute("condition", condition);
			req.setAttribute("keyword", keyword);
			req.setAttribute("freebbsUrl", freebbsUrl);

		} catch (Exception e) {
			e.printStackTrace();

		}

		String path = "/WEB-INF/views/freebbs/list.jsp";
		forward(req, resp, path);

	}

}
