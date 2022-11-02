package com.mypage;

import java.io.IOException;
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

@WebServlet("/schedule/*")
public class ScheduleServlet extends MyServlet{
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
		
		
		if(uri.indexOf("schedule.do") != -1 ) {
			scheduleForm(req, resp);
		}
		
		
	}

	
	//학생 시간표 
	private void scheduleForm(HttpServletRequest req, HttpServletResponse resp) throws  ServletException, IOException {
		
		ScheduleDAO dao = new ScheduleDAO();
		ScheduleDTO dto = new  ScheduleDTO();
		MyUtil util = new MyUtilBootstrap();
		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		
		try {
			
			List<ScheduleDTO> list = null;
			String id = info.getUserId();
		         	
			
			list = dao.listSchedule(id);
			
		   req.setAttribute("list", list);
	
		   List<ScheduleDTO> plist = null;
		   
		   plist = dao.prolistSchedule(id);
		   
		   req.setAttribute("plist", plist);
		   
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String path = "/WEB-INF/views/schedule/scheduleForm.jsp";
		forward(req, resp, path);
		
	}

	
	
	
	
}
