package com.notice;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;
import com.util.MyUtilBootstrap;


@WebServlet("/notice/*")
public class NoticeServlet extends MyServlet {
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
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
		}
	}	
		
	protected void noticeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 공지사항 리스트
		NoticeDAO dao = new NoticeDAO();
		MyUtil util = new MyUtilBootstrap();
		String cp = req.getContextPath();
		
		
		try {
			String subjectNo = req.getParameter("subjectNo");
			
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
	
	protected void noticeWriteForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 공지사항 작성
		String subjectNo = req.getParameter("subjectNo");
		req.setAttribute("subjectNo", subjectNo );
		req.setAttribute("mode", "write");
		forward(req, resp, "/WEB-INF/views/notice/noticeWrite.jsp");
	}
	
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
		
		

		try {
			NoticeDTO dto = new NoticeDTO();
			
			dto.setUserId(info.getUserId());
			dto.setTitle(req.getParameter("title"));
			dto.setContent(req.getParameter("content"));
			dto.setSubjectNo(req.getParameter("subjectNo"));
			
			
			dao.insertNotice(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/notice/notice.do?subjectNo=" + subjectNo);
	}
	
	protected void noticeArticleForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 공지사항 보기
		String path = "/WEB-INF/views/notice/noticeArticle.jsp";
		forward(req, resp, path);
	}
	
	
}	