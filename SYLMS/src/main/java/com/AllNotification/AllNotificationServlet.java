package com.AllNotification;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.member.SessionInfo;
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
		List<AllNotificationDTO> notiList = null;
		// 알림내용출력	
		
		try {
			//String noticeCode = req.getParameter("noticeCode");
			
			notiList = dao.listAlert();
			
			dao.readDate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.setContentType("text/html;charset=utf-8");

		JSONObject job = new JSONObject();
		job.put("notiList", notiList);

		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}

	private void countForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		AllNotificationDAO dao = new AllNotificationDAO();
		// 오늘 알람이 있는 경우
		
		int alertCount = 0;

		try {			
			alertCount = dao.alertCount();
			req.setAttribute("alertCount", alertCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.setContentType("text/html;charset=utf-8");

		JSONObject job = new JSONObject();
		job.put("count", alertCount);

		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}
	
	

}
