package com.main;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;

@WebServlet("/main.do")
public class MainServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String uri=req.getRequestURI();
		
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if(info==null) {
			forward(req,resp,"/WEB-INF/views/member/login.jsp");
			return;
		}
		
		if(uri.indexOf("main.do") != -1) {
			subjectList(req, resp);		
			
		}
	}


	protected void subjectList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		MainDAO dao = new MainDAO();
		List<MainDTO> list = null;
		List<MainDTO> list2 = null;
		
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		try {
			if(info.getUserId().matches("\\d{8}")){
				list = dao.registerSubject(info.getUserId());
			} else if (info.getUserId().matches("\\d{5}")) {
				list = dao.registerSubject_pro(info.getUserId());
			}
			
			
			req.setAttribute("list", list);
			req.setAttribute("list2", list2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/main/main.jsp");
		
	}
	

	
	
}
