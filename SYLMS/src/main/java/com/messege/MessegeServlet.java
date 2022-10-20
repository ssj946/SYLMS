package com.messege;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;
import com.util.MyUtilBootstrap;

@WebServlet("/messege/*")
public class MessegeServlet extends MyServlet{
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		forward(req, resp, "/WEB-INF/views/messege/messege.jsp");
		
		if (uri.indexOf("receive.do") != -1) {
			received(req, resp);
		} else if (uri.indexOf("send.do") != -1) {
			sendForm(req, resp);
		} else if (uri.indexOf("send_ok.do") != -1) {
			submit(req, resp);
		}
		
	}

	private void sendForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
	}

	private void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
	}

	private void received(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MessegeDAO dao = new MessegeDAO();
		MyUtil util = new MyUtilBootstrap();
		
		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 전체 데이터 개수
			int dataCount = dao.dataCount();
			
			// 전체 페이지 수
			int size = 5;
			int total_page = util.pageCount(dataCount, size);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		forward(req, resp, "/WEB-INF/views/messege/messege.jsp");
		
	}

}
