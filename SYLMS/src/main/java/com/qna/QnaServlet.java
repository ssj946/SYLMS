package com.qna;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;
import com.util.MyUtilBootstrap;

@MultipartConfig
@WebServlet("/qna/*")
public class QnaServlet extends MyUploadServlet {
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
		pathname = root + "uploads" + File.separator + "ㅂ";

		// uri에 따른 작업 구분
		if (uri.indexOf("qna.do") != -1) {
			qnaForm(req, resp);
		} else if (uri.indexOf("qnaWrite.do") != -1) {
			qnaWriteForm(req, resp);
		} else if (uri.indexOf("qnawrite_ok.do") != -1) {
			qnaWriteSubmit(req, resp);
		} else if (uri.indexOf("qnaArticle.do") != -1) {
			qnaArticleForm(req, resp);
		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("deleteFile.do") != -1) {
			deleteFile(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if (uri.indexOf("deleteList.do") != -1) {
			deleteList(req, resp);
		}
	}

	// 질문과 대답 리스트 완료
	protected void qnaForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAO();
		String subjectNo = req.getParameter("subjectNo");

		QnaDTO dto1 = new QnaDTO();
		MyUtil util = new MyUtilBootstrap();
		String cp = req.getContextPath();

		try {
			dto1 = dao.readSubject(subjectNo);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("professorName", dto1.getProfessorname());
			req.setAttribute("semester", dto1.getSemester());
			req.setAttribute("subjectName", dto1.getSubjectName());
			req.setAttribute("syear", dto1.getSyear());

			// 페이지 번호
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
			} // GET방식이라 디코딩
			if (req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}

			// 전체 데이터 개수
			int dataCount;
			if (keyword.length() != 0) {
				dataCount = dao.dataCount(subjectNo, condition, keyword);
			} else {
				dataCount = dao.dataCount(subjectNo);
			}

			// 한페이지 표시할 데이터 개수
			String pageSize = req.getParameter("size");
			int size = pageSize == null ? 10 : Integer.parseInt(pageSize);

			// 전체 페이지 수
			int total_page = util.pageCount(dataCount, size);

			if (current_page > total_page) {
				current_page = total_page;
			}

			// 게시글 가져오기
			int offset = (current_page - 1) * size;
			if (offset < 0)
				offset = 0;

			List<QnaDTO> list;
			if (keyword.length() != 0) {
				list = dao.listQna(subjectNo, offset, size, condition, keyword);
			} else {
				list = dao.listQna(subjectNo, offset, size);
			}

			// Qna리스트
			List<QnaDTO> listQna = null;
			listQna = dao.listQna(subjectNo);
			for (QnaDTO dto : listQna) {
				dto.setReg_date(dto.getReg_date().substring(0, 10));
			}

			// 파일

			String query = "";
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}

			// 페이징처리
			String listUrl = cp + "/qna/qna.do?subjectNo=" + subjectNo;
			String qnaUrl = cp + "/qna/qna.do?subjectNo=" + subjectNo + "?page=" + current_page;
			if (query.length() != 0) {
				listUrl += "?" + query;
				qnaUrl += "&" + query;
			}

			String paging = util.paging(current_page, total_page, listUrl);

			req.setAttribute("query", query);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("list", list);
			req.setAttribute("listQna", listQna);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			req.setAttribute("condition", condition);
			req.setAttribute("keyword", keyword);
			req.setAttribute("qnaUrl", qnaUrl);

		} catch (Exception e) {
			e.printStackTrace();
		}

		String path = "/WEB-INF/views/qna/qna.jsp";
		forward(req, resp, path);
	}

	// 질문과 대답 작성폼
	protected void qnaWriteForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 질문과 대답
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		String size = req.getParameter("size");

		// 교수
		if (!info.getUserId().matches("\\d{5}")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		try {

			String subjectNo = req.getParameter("subjectNo");

			QnaDAO dao = new QnaDAO();
			QnaDTO dto1 = new QnaDTO();

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

		forward(req, resp, "/WEB-INF/views/qna/qnaWrite.jsp");
	}

	// 질문과대답 작성보내기
	protected void qnaWriteSubmit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 질문과대답 저장
		QnaDAO dao = new QnaDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		String subjectNo = req.getParameter("subjectNo");

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/qna/qna.do?subjectNo=" + subjectNo);
			return;
		}

		// 교수만 등록,,,
		if (!info.getUserId().matches("\\d{5}")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		try {
			QnaDTO dto = new QnaDTO();

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

			dao.insertQna(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/qna/qna.do?subjectNo=" + subjectNo);
	}

	// 글 보기 폼 완료
	protected void qnaArticleForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 공지사항 보기
		String cp = req.getContextPath();

		String page = req.getParameter("page");
		String size = req.getParameter("size");
		String query = "page=" + page + "&size=" + size;

		QnaDAO dao = new QnaDAO();

		try {
			String subjectNo = req.getParameter("subjectNo");
			
			QnaDTO dto1 = new QnaDTO();
			
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

				// 게시물 가져오기
				QnaDTO dto = dao.readQna(articleNo);
				if (dto == null) {
					resp.sendRedirect(cp + "/notice/notice.do?" + query);
					return;
				}

				dto.setContent(dto.getContent().replaceAll("\n", "<br>"));
				
				// 이전글/다음글
			    QnaDTO preReadDto = dao.preReadQna(dto.getSubjectNo(), dto.getArticleNo(), condition, keyword);
				QnaDTO nextReadDto = dao.nextReadQna(dto.getSubjectNo(), dto.getArticleNo(), condition, keyword);

				// 파일
				
				List<QnaDTO> listFile = dao.listQnaFile(articleNo);
				
				
				req.setAttribute("dto", dto);
				req.setAttribute("subjectNo", subjectNo );
				req.setAttribute("condition", condition);
				req.setAttribute("keyword", keyword);
				req.setAttribute("query", query);
				req.setAttribute("page", page);
				req.setAttribute("size", size);
				req.setAttribute("name", dto.getName());
				req.setAttribute("preReadDto", preReadDto);
				req.setAttribute("nextReadDto", nextReadDto);
				req.setAttribute("listFile",listFile);
				
				
				
				forward(req, resp, "/WEB-INF/views/qna/qnaArticle.jsp");
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		
			resp.sendRedirect(cp + "/qna/qna.do?" + query);
		}

	// 수정
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		// 교수만 등록,,,
		if (!info.getUserId().matches("\\d{5}")) {
			resp.sendRedirect(cp + "/qna/qna.do");
			return;
		}

		QnaDAO dao = new QnaDAO();

		String subjectNo = req.getParameter("subjectNo");
		String articleNo = req.getParameter("articleNo");
		try {

			QnaDTO dto = dao.readQnaFile(articleNo);
			if (dto == null) {
				resp.sendRedirect(cp + "/qna/qna.do?subjectNo=" + subjectNo);
				return;
			}

			// 게시물을 올린 사용자가 아니면
			if (!dto.getUserId().equals(info.getUserId())) {
				resp.sendRedirect(cp + "/qna/qna.do?subjectNo=" + subjectNo);
				return;
			}

			// 파일
			List<QnaDTO> listFile = dao.listQnaFile(articleNo);

			req.setAttribute("dto", dto);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("listFile", listFile);
			req.setAttribute("articleNo", articleNo);

			req.setAttribute("mode", "update");

			forward(req, resp, "/WEB-INF/views/qna/qnaWrite.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/qna/qna.do?subjectNo=" + subjectNo + "&articleNo=" + articleNo);

	}

	// 수정보내기
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		String subjectNo = req.getParameter("subjectNo");

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/qna/qna.do?subjectNo=" + subjectNo);
			return;
		}

		if (!info.getUserId().matches("\\d{5}")) {
			resp.sendRedirect(cp + "/qna/qna.do?subjectNo=" + subjectNo);
			return;
		}

		QnaDAO dao = new QnaDAO();

		String articleNo = req.getParameter("articleNo");

		try {
			QnaDTO dto = new QnaDTO();

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
			dao.updateQna(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/qna/qna.do?subjectNo=" + subjectNo + "&articleNo=" + articleNo);

	}

	// 파일만 지우기
	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		String subjectNo = req.getParameter("subjectNo");

		// 교수만
		if (!info.getUserId().matches("\\d{5}")) {
			resp.sendRedirect(cp + "/qna/qna.do?subjectNo=" + subjectNo);
			return;
		}

		QnaDAO dao = new QnaDAO();

		try {
			String articleNo = req.getParameter("articleNo");
			String fileNo = req.getParameter("fileNo");

			QnaDTO dto = dao.readQnaFile(fileNo);
			if (dto != null) {
				// 파일삭제
				FileManager.doFiledelete(pathname, dto.getSaveFilename());

				// 테이블 파일 정보 삭제
				dao.deleteQnaFile("one", fileNo);
			}

			// 다시 수정 화면으로
			resp.sendRedirect(cp + "/qna/update.do?subjectNo=" + subjectNo + "&articleNo=" + articleNo);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		String articleNo = req.getParameter("articleNo");

		resp.sendRedirect(cp + "/qna/qna.do?subjectNo=" + subjectNo + "&articleNo=" + articleNo);

	}

	// 지우기 완료
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 삭제
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		String subjectNo = req.getParameter("subjectNo");

		// 교수만 삭제
		if (!info.getUserId().matches("\\d{5}")) {
			resp.sendRedirect(cp + "/qna/qna.do?subjectNo=" + subjectNo);
			return;
		}

		QnaDAO dao = new QnaDAO();

		// String page = req.getParameter("page");
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

			QnaDTO dto = dao.readQnaFile(articleNo);
			if (dto == null) {
				resp.sendRedirect(cp + "/qna/qna.do?" + query);
				return;
			}

			// 파일삭제
			List<QnaDTO> listFile = dao.listQnaFile(articleNo);
			for (QnaDTO vo : listFile) {
				FileManager.doFiledelete(pathname, vo.getSaveFilename());
			}
			dao.deleteQnaFile("all", articleNo);

			// 게시글 삭제
			dao.deleteQna(articleNo);

		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/qna/qna.do?" + query);
	}

	// 파일 다운로드 완료
	protected void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 파일 다운로드
		QnaDAO dao = new QnaDAO();
		boolean b = false;

		try {
			String fileNo = req.getParameter("fileNo");

			QnaDTO dto = dao.readQnaFile(fileNo);
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

	// 리스트에서 지우기
	protected void deleteList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}
}