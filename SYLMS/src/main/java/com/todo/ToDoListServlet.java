package com.todo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.main.MainDAO;
import com.main.MainDTO;
import com.member.SessionInfo;
import com.util.MyServlet;

@WebServlet("/r_sidebar/*")
public class ToDoListServlet extends MyServlet {
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

		if (uri.indexOf("todo.do") != -1) {
			todoList(req, resp);
		}

	}

	private void todoList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MainDAO dao = new MainDAO();
		List<MainDTO> todolist = null;

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		try {
			todolist = dao.assignmentList(info.getUserId());

			req.setAttribute("todolist", todolist);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.setContentType("text/html;charset=utf-8");

		JSONObject job = new JSONObject();
		job.put("todo", todolist);

		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}

}
