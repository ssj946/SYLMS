package com.mypage;

import java.io.File;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyUploadServlet;
import com.util.MyUtil;
import com.util.MyUtilBootstrap;

@MultipartConfig
@WebServlet("/file/*")
public class FileFolderServlet extends MyUploadServlet {

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

		if (uri.indexOf("file.do") != -1) {
			filelist(req, resp);
		}

	}

	private void filelist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 페이징, 검색, 다운로드
		req.setCharacterEncoding("utf-8");
		MyUtil util = new MyUtilBootstrap();
		String cp = req.getContextPath();

		FileFolderDAO dao = new FileFolderDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}
		
		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "assignment";
        
			
						
		try {

			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null) {
				current_page = Integer.parseInt(page);
			}
			
			//검색
			
			String year = req.getParameter("year");
		
			
			String keyword = req.getParameter("keyword");
			if (keyword == null) {
				keyword = "";
			}
			
		

			// GET방식이라 디코딩
			if (req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}

			// 한페이지 표시할 데이터 개수
			int size = 10;

			// 전체 데이터 개수
			int dataCount;
			int total_page;
			
			String id = info.getUserId();
			
			if(keyword.length() != 0) {
				dataCount = dao.dataCount(year, info.getUserId(), keyword);
			} else {
				dataCount = dao.dataCount(year, info.getUserId());

			}

			// 전체 페이지 수
			total_page = util.pageCount(dataCount, size);

			if (current_page > total_page) {
				current_page = total_page;
			}

			// 게시글 가져오기
			int offset = (current_page - 1) * size;
			if (offset < 0)
				offset = 0;

			List<FileFolderDTO> flist = null;

			if (keyword.length() != 0) {
				flist = dao.listfile(offset, size, year, info.getUserId(), keyword);
			} else {
				flist = dao.listfile(offset, size, year, info.getUserId());
			}
			
		
			String query = "";
			if (keyword.length() != 0) {
				query =  "keyword=" + URLEncoder.encode(keyword, "utf-8");
			}

			String listUrl = cp + "/mypage/file.do";
			if (query.length() != 0) {
				listUrl += "?" + query;
			}

			String paging = util.paging(current_page, total_page, listUrl);

			
			req.setAttribute("flist", flist);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("paging", paging);
			req.setAttribute("year", year);
			req.setAttribute("keyword", keyword);;
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

			String path = "/WEB-INF/views/mypage/files.jsp";
			forward(req, resp, path);

		}

	}


