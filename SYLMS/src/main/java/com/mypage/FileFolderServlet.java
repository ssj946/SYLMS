package com.mypage;

import java.io.File;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.util.FileManager;
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
		
		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "lecture";

		if (uri.indexOf("file.do") != -1) {
			filelist(req, resp);
		} else if(uri.indexOf("download.do") != -1) {
			download(req, resp);
		}

	}

	private void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		FileFolderDAO dao = new FileFolderDAO();
		boolean b = false;
		
		try {
			String fileNum = req.getParameter("fileNum");
			
			FileFolderDTO dto = dao.filedownload(fileNum);
			
			
			
			if (dto != null) {
				b = FileManager.doFiledownload(dto.getSaveName(), dto.getOriginName(), pathname, resp);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (!b) {
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print("<script>alert('????????????????????? ?????? ????????????.');history.back();</script>");
		}
		
	}

	private void filelist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// ?????????, ??????, ????????????
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
			
			//??????
			
			String year = req.getParameter("year");
		
			
			String keyword = req.getParameter("keyword");
			if (keyword == null) {
				keyword = "";
			}
			
		

			// GET???????????? ?????????
			if (req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}

			// ???????????? ????????? ????????? ??????
			int size = 10;

			// ?????? ????????? ??????
			int dataCount;
			int total_page;
			
			String id = info.getUserId();
			
			if(keyword.length() != 0) {
				dataCount = dao.dataCount(year, info.getUserId(), keyword);
			} else {
				dataCount = dao.dataCount( info.getUserId());

			}

			// ?????? ????????? ???
			total_page = util.pageCount(dataCount, size);

			if (current_page > total_page) {
				current_page = total_page;
			}

			// ????????? ????????????
			int offset = (current_page - 1) * size;
			if (offset < 0)
				offset = 0;

			List<FileFolderDTO> flist = null;

			if (keyword.length() != 0) {
				flist = dao.listfile(offset, size, year, info.getUserId(), keyword);
			} else {
				flist = dao.listfile(offset, size, info.getUserId());
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


