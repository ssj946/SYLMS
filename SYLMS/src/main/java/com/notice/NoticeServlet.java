package com.notice;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
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

import com.member.SessionInfo;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;
import com.util.MyUtilBootstrap;

@MultipartConfig
@WebServlet("/notice/*")
public class NoticeServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;
	
	private String pathname;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();
		//String cp = req.getContextPath();
		
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if(info==null) {
			forward(req,resp,"/WEB-INF/views/member/login.jsp");
			return;
		}
		
		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "notice";
		

		// uri에 따른 작업 구분 
		if (uri.indexOf("notice.do") != -1) {
			noticeForm(req, resp);
		} else if (uri.indexOf("noticeWrite.do") != -1) {
			noticeWriteForm(req, resp);
		} else if (uri.indexOf("noticewrite_ok.do") != -1) {
			noticeWriteSubmit(req, resp);
		} else if (uri.indexOf("noticeArticle.do") != -1) {
			noticeArticleForm(req, resp);
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
		}
	}	

	// 공지사항 리스트 완료
	protected void noticeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		NoticeDAO dao = new NoticeDAO();
		String subjectNo = req.getParameter("subjectNo");
		
		NoticeDTO dto1 = new NoticeDTO();
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
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 검색
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if(condition == null) {
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
			if(offset < 0) offset = 0;
			
			List<NoticeDTO> list;
			if (keyword.length() != 0) {
				list = dao.listNotice(subjectNo, offset, size, condition, keyword);
			} else {
				list = dao.listNotice(subjectNo, offset, size);
			}
			
			// 공지리스트
			List<NoticeDTO> listNotice = null;
			listNotice = dao.listNotice(subjectNo);
			for (NoticeDTO dto : listNotice) {
				dto.setReg_date(dto.getReg_date().substring(0, 10));
			}
			
			// new이미지 뜨게하는거
			long gap;
			Date curDate = new Date();
			SimpleDateFormat tt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			for (NoticeDTO dto : list) {
				Date date = tt.parse(dto.getReg_date());
				gap = (curDate.getTime() - date.getTime()) / (1000 * 60 * 60 *24);
				dto.setGap(gap);
				
				dto.setReg_date(dto.getReg_date().substring(0, 10));
			}
			
			// 파일있으면 파일이미지
			
			String query = "";
			if(keyword.length() != 0) {
				query = "condition="+condition+"&keyword="+URLEncoder.encode(keyword, "utf-8");
			}
			
			// 페이징처리
			String listUrl = cp + "/notice/notice.do?subjectNo=" + subjectNo; // 공지리스트 주소
			String noticeUrl = cp + "/notice/notice.do?subjectNo="+subjectNo+"?page="+current_page; // 이게 뭔지,,,
			if(query.length() != 0) {
				listUrl += "?" + query;
				noticeUrl += "&"+query;
			}
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			
			req.setAttribute("query", query);
			req.setAttribute("subjectNo", subjectNo );
			req.setAttribute("list", list);
			req.setAttribute("listNotice", listNotice);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			req.setAttribute("condition", condition);
			req.setAttribute("keyword", keyword);
			req.setAttribute("noticeUrl", noticeUrl);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String path = "/WEB-INF/views/notice/notice.jsp";
		forward(req, resp, path);
	}
	
	// 공지사항 작성폼 완료
	protected void noticeWriteForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 공지사항 작성
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		String size = req.getParameter("size");
		
		// 교수만 등록,,,
		if (!info.getUserId().matches("\\d{5}")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		try {
			
			String subjectNo = req.getParameter("subjectNo");
			
			NoticeDAO dao = new NoticeDAO();
			NoticeDTO dto1 = new NoticeDTO();
			
			dto1 = dao.readSubject(subjectNo);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("professorName", dto1.getProfessorname());
			req.setAttribute("semester", dto1.getSemester());
			req.setAttribute("subjectName", dto1.getSubjectName());
			req.setAttribute("syear", dto1.getSyear());
			req.setAttribute("size", size);
			
			req.setAttribute("subjectNo", subjectNo );
			req.setAttribute("mode", "write");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/notice/noticeWrite.jsp");
	}
	
	// 공지사항 작성보내기폼 완료
	protected void noticeWriteSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 공지사항글 저장
		NoticeDAO dao = new NoticeDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		String subjectNo = req.getParameter("subjectNo");
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+ "/notice/notice.do?subjectNo=" + subjectNo);
			return;
		}
		
		// 교수만 등록,,,
		if (!info.getUserId().matches("\\d{5}")) {
			resp.sendRedirect(cp + "/");
			return;
		}
		
		try {
			NoticeDTO dto = new NoticeDTO();
			
			dto.setUserId(info.getUserId());
			dto.setTitle(req.getParameter("title"));
			dto.setContent(req.getParameter("content"));
			dto.setSubjectNo(req.getParameter("subjectNo"));
			
			Map<String, String[]> map = doFileUpload(req.getParts(),pathname);
			if( map != null) {
				String[] saveFiles = map.get("saveFilenames");
				String[] originalFiles = map.get("originalFilenames");
				dto.setSaveFiles(saveFiles);
				dto.setOriginalFiles(originalFiles);
			}
			
			dao.insertNotice(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/notice/notice.do?subjectNo=" + subjectNo);
	}
	
	// 글 보기 폼 완료
	protected void noticeArticleForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 공지사항 보기
		String cp = req.getContextPath();

		String page = req.getParameter("page");
		String size = req.getParameter("size");
		String query = "page=" + page + "&size=" + size;

		NoticeDAO dao = new NoticeDAO();

		try {
			String subjectNo = req.getParameter("subjectNo");
			
			NoticeDTO dto1 = new NoticeDTO();
			
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
			NoticeDTO dto = dao.readNotice(articleNo);
			if (dto == null) {
				resp.sendRedirect(cp + "/notice/notice.do?" + query);
				return;
			}

			dto.setContent(dto.getContent().replaceAll("\n", "<br>"));

			// 이전글/다음글
			NoticeDTO preReadDto = dao.preReadNotice(dto.getSubjectNo(), dto.getArticleNo(), condition, keyword);
			NoticeDTO nextReadDto = dao.nextReadNotice(dto.getSubjectNo(), dto.getArticleNo(), condition, keyword);

			// 파일
			
			List<NoticeDTO> listFile = dao.listNoticeFile(articleNo);
			
			
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
			
			
			
			forward(req, resp, "/WEB-INF/views/notice/noticeArticle.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/notice/notice.do?" + query);
	}
	
	// 수정
	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		// 교수만 등록,,,
		if (!info.getUserId().matches("\\d{5}")) {
			resp.sendRedirect(cp + "/notice/notice.do");
			return;
		}
		
		NoticeDAO dao = new NoticeDAO();
		
		
		
		String subjectNo = req.getParameter("subjectNo");
		String articleNo = req.getParameter("articleNo");
		try {
			
			NoticeDTO dto = dao.readNotice(articleNo);
			if (dto == null) {
				resp.sendRedirect(cp + "/notice/notice.do?subjectNo=" + subjectNo);
				return;
			}
			
			// 게시물을 올린 사용자가 아니면
			if (! dto.getUserId().equals(info.getUserId())) {
				resp.sendRedirect(cp + "/notice/notice.do?subjectNo=" + subjectNo);
				return;
			}
			
			// 파일
			List<NoticeDTO> listFile = dao.listNoticeFile(articleNo);
			
			req.setAttribute("dto", dto);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("listFile", listFile);
			req.setAttribute("articleNo", articleNo);
			
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/notice/noticeWrite.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/notice/notice.do?subjectNo=" + subjectNo+"&articleNo="+articleNo);
		
		
	}
	
	// 수정보내기
	protected void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	HttpSession session = req.getSession();
	SessionInfo info = (SessionInfo) session.getAttribute("member");
	
	String cp = req.getContextPath();
	String subjectNo = req.getParameter("subjectNo");
	
	if(req.getMethod().equalsIgnoreCase("GET")) {
		resp.sendRedirect(cp + "/notice/notice.do?subjectNo="+subjectNo);
		return;
	}
	
	if (!info.getUserId().matches("\\d{5}")) {
		resp.sendRedirect(cp + "/notice/notice.do?subjectNo="+subjectNo);
		return;
	}
	
	NoticeDAO dao = new NoticeDAO();
	
	String articleNo = req.getParameter("articleNo");
	
	try {
		NoticeDTO dto = new NoticeDTO();
		
		dto.setArticleNo(req.getParameter("articleNo"));
		dto.setTitle(req.getParameter("title"));
		dto.setContent(req.getParameter("content"));
		dto.setSubjectNo(subjectNo);
		
		Map<String , String[]> map = doFileUpload(req.getParts(), pathname);
		if(map != null) {
			String[] saveFiles = map.get("saveFilenames");
			String[] originalFiles = map.get("originalFilenames");
			dto.setSaveFiles(saveFiles);
			dto.setOriginalFiles(originalFiles);
		}
		dao.updateNotice(dto);
		
	} catch (Exception e) {
		e.printStackTrace();
	}


	resp.sendRedirect(cp + "/notice/notice.do?subjectNo=" + subjectNo+"&articleNo="+articleNo);
	
	}
	
	// 파일만 지우기
	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		String subjectNo = req.getParameter("subjectNo");
		
		// 교수만
		if (!info.getUserId().matches("\\d{5}")) {
			resp.sendRedirect(cp + "/notice/notice.do?subjectNo="+subjectNo);
			return;
		}
		
		NoticeDAO dao = new NoticeDAO();
		
		try {
			String articleNo = req.getParameter("articleNo");
			String fileNo = req.getParameter("fileNo");
			
			NoticeDTO dto = dao.readNoticeFile(fileNo);
			if(dto != null) {
				// 파일삭제
				FileManager.doFiledelete(pathname, dto.getSaveFilename());
				
				// 테이블 파일 정보 삭제
				dao.deleteNoticeFile("one", fileNo);
			}
			
			// 다시 수정 화면으로
			resp.sendRedirect(cp + "/notice/update.do?subjectNo=" + subjectNo+"&articleNo="+articleNo);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String articleNo = req.getParameter("articleNo");
		
		resp.sendRedirect(cp + "/notice/notice.do?subjectNo=" + subjectNo+"&articleNo="+articleNo);
		
	}
	
	// 지우기 완료
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
		// 삭제
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		String subjectNo = req.getParameter("subjectNo");

		// 교수만 삭제
		if (!info.getUserId().matches("\\d{5}")) {
			resp.sendRedirect(cp + "/notice/notice.do?subjectNo="+subjectNo);
			return;
		}
		
		NoticeDAO dao = new NoticeDAO();

		//String page = req.getParameter("page");
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

			NoticeDTO dto = dao.readNotice(articleNo);
			if (dto == null) {
				resp.sendRedirect(cp + "/notice/notice.do?" + query);
				return;
			}

			// 파일삭제
			List<NoticeDTO> listFile = dao.listNoticeFile(articleNo);
			for (NoticeDTO vo : listFile) {
				FileManager.doFiledelete(pathname, vo.getSaveFilename());
			}
			dao.deleteNoticeFile("all", articleNo);

			// 게시글 삭제
			dao.deleteNotice(articleNo);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/notice/notice.do?"+query);
	}
	
	// 파일 다운로드 완료
	protected void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 파일 다운로드 
		NoticeDAO dao = new NoticeDAO();
		boolean b = false;
		
		try {
			String fileNo = req.getParameter("fileNo");
			
			NoticeDTO dto = dao.readNoticeFile(fileNo);
			if (dto != null) {
				b = FileManager.doFiledownload(dto.getSaveFilename(), dto.getOriginalFilename(), pathname, resp);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(!b) {
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print("<script>alert('파일다운로드가 실패 했습니다.');history.back();</script>");
		}
	}
	
	// 리스트에서 지우기,,?
	protected void deleteList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	
	
}	