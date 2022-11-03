package com.exam;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;


@WebServlet("/exam/*")
public class ExamServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();
		
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if(info==null) {
			forward(req,resp,"/WEB-INF/views/member/login.jsp");
			return;
		}

		// uri에 따른 작업 구분
		if (uri.indexOf("exam.do") != -1) {
			examInsert(req, resp);
		} else if (uri.indexOf("send.do") != -1) {
			formSend(req, resp);		
		}else if (uri.indexOf("check.do") != -1) {
			examCheck(req, resp);		
		}
	}
		
	

		private void examInsert(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
			forward(req, resp, "/WEB-INF/views/exam/exam.jsp");
		}
		
		private void formSend(HttpServletRequest req, HttpServletResponse resp) {
			
			
		}

		private void examCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
			forward(req, resp, "/WEB-INF/views/exam/examCheck.jsp");
		}

}

	
