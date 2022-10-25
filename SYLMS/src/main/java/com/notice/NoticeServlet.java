package com.notice;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
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
			
			String page = req.getParameter("page");
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if(condition == null) {
				condition = "all";
				keyword = "";
			}
			if (req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}
			
			// 한페이지 표시할 데이터 개수 
			String pageSize = req.getParameter("size");
			int size = pageSize == null ? 10 : Integer.parseInt(pageSize);

			int dataCount, total_page;

			if (keyword.length() != 0) {
				dataCount = dao.dataCount(condition, keyword);
			} else {
				dataCount = dao.dataCount();
			}
			total_page = util.pageCount(dataCount, size);

			if (current_page > total_page) {
				current_page = total_page;
			}

			int offset = (current_page - 1) * size;
			if(offset < 0) offset = 0;
			
			List<NoticeDTO> list;
			if (keyword.length() != 0) {
				list = dao.listNotice(offset, size, condition, keyword);
			} else {
				list = dao.listNotice(offset, size);
			}
			
			// 공지리스트
			List<NoticeDTO> listNotice = null;
			listNotice = dao.listNotice();
			for (NoticeDTO dto : listNotice) {
				dto.setReg_date(dto.getReg_date().substring(0, 10));
			}
			
			long gap;
			Date curDate = new Date();
			SimpleDateFormat tt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			for (NoticeDTO dto : list) {
				Date date = tt.parse(dto.getReg_date());
				gap = (curDate.getTime() - date.getTime()) / (1000 * 60 * 60);
				dto.setGap(gap);
				
				dto.setReg_date(dto.getReg_date().substring(0, 10));
			}
			
			String listUrl;
			
			
			listUrl = cp + "/notice/list.do?size=" + size;
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			
			
			req.setAttribute("list", list);
			req.setAttribute("listNotice", listNotice);
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
		
		
		forward(req, resp, "/WEB-INF/views/notice/notice.jsp");
	}
	
	protected void noticeWriteForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 공지사항 작성

		req.setAttribute("mode", "write");
		forward(req, resp, "/WEB-INF/views/notice/noticeWrite.jsp");
	}
	
	protected void noticeWriteSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 공지사항글 저장
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+ "/notice/notice.do");
			return;
		}
		
		NoticeDAO dao = new NoticeDAO();
		
		String size = req.getParameter("size");

		try {
			NoticeDTO dto = new NoticeDTO();
			
			dto.setUserId(info.getUserId());
			dto.setTitle(req.getParameter("title"));
			dto.setContent(req.getParameter("content"));
			
			
			dao.insertNotice(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/notice/notice.do?size=" + size);
	}
	
	protected void noticeArticleForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 공지사항 보기
		String path = "/WEB-INF/views/notice/noticeArticle.jsp";
		forward(req, resp, path);
	}
	
	
}	