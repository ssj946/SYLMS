package com.AllNotification;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.messege.MessegeDAO;
import com.util.MyServlet;

@WebServlet("/header/*")
public class AllNotificationServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

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
		
		if (uri.indexOf("notice.do") != -1) {
			notice(req, resp);
		} else if (uri.indexOf("count.do") != -1) {
			countForm(req, resp);
		}
		
	}

	private void notice(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AllNotificationDAO dao = new AllNotificationDAO();
		List<AllNotificationDTO> list = null;
		//알림내용출력
		
		
		try {
			list = dao.listAlert();
			req.setAttribute("listAlert", list);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		forward(req, resp, "/WEB-INF/views/layout/header.jsp");
	}
	
	private void countForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AllNotificationDAO dao = new AllNotificationDAO();
		//오늘 알람이 있는 경우
		
		try {
			int alertCount;			
			alertCount = dao.alertCount();
			req.setAttribute("alertCount", alertCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		forward(req, resp, "/WEB-INF/views/layout/header.jsp");
	}

}
