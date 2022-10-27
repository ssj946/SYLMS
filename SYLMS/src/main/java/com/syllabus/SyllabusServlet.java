package com.syllabus;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyServlet;
import com.util.MyUtil;
import com.util.MyUtilBootstrap;

@WebServlet("/syllabus/*")
public class SyllabusServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri = req.getRequestURI();

		if (uri.indexOf("list.do") != -1) {
			list(req, resp);
		}
	}

	protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		  SyllabusDAO dao = new  SyllabusDAO();
	        MyUtil util = new MyUtilBootstrap();

			String cp = req.getContextPath();
			
			try {
				String page = req.getParameter("page");
				int current_page = 1;
				if (page != null) {
					current_page = Integer.parseInt(page);
				}
				

				// 전체 데이터 개수
				int dataCount;
					dataCount = dao.dataCount();
				
				// 전체 페이지 수
				int size = 10;
				int total_page = util.pageCount(dataCount, size);
				if (current_page > total_page) {
					current_page = total_page;
				}

				// 게시물 가져오기
				int offset = (current_page - 1) * size;
				if(offset < 0) offset = 0;
				
				List<SyllabusDTO> list = null;
				list = dao.listBoard(offset, size);

				String query = "";
				/*
				if (keyword.length() != 0) {
					query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
				}
				*/

				// 페이징 처리
				String listUrl = cp + "/syllabus/list.do";
				if (query.length() != 0) {
					listUrl += "?" + query;
				}

				String paging = util.paging(current_page, total_page, listUrl);

				// 포워딩할 JSP에 전달할 속성
				req.setAttribute("list", list);
				req.setAttribute("page", current_page);
				req.setAttribute("total_page", total_page);
				req.setAttribute("dataCount", dataCount);
				req.setAttribute("size", size);
				req.setAttribute("paging", paging);
				//req.setAttribute("condition", condition);
				//req.setAttribute("keyword", keyword);
				
			} catch (Exception e) {
				e.printStackTrace();
			}

				
		forward(req, resp, "/WEB-INF/views/syllabus/list.jsp");
	}
	
	protected void test(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
}	

		