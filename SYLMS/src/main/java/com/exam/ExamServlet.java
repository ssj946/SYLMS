package com.exam;

import java.io.IOException;
import java.util.List;

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

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}

		// uri에 따른 작업 구분
		if (uri.indexOf("exam.do") != -1) {
			examForm(req, resp);
		} else if (uri.indexOf("send.do") != -1) {
			fillForm(req, resp);
		} else if (uri.indexOf("send_ok.do") != -1) {
			formSend(req, resp);
		} else if (uri.indexOf("check.do") != -1) {
			examCheck(req, resp);
		}
	}

	private void examForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (info.getUserId().matches("\\d{8}")) {
			resp.sendRedirect(cp + "/lecture/main.do");
			return;
		}

		try {

		} catch (Exception e) {
			e.printStackTrace();
		}
		forward(req, resp, "/WEB-INF/views/exam/exam.jsp");

	}

	private void fillForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ExamDAO dao = new ExamDAO();
		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (info.getUserId().matches("\\d{8}")) {
			resp.sendRedirect(cp + "/lecture/main.do");
			return;
		}

		try {
			String subjectNo = req.getParameter("subjectNo");

			ExamDTO dto = new ExamDTO();

			dto.setSubjectNo(req.getParameter("subjectNo"));

			dao.codeList(dto);

			List<ExamDTO> list = null;
			list = dao.listBoard(subjectNo);

			req.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		forward(req, resp, "/WEB-INF/views/exam/examSend.jsp");

	}

	private void formSend(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ExamDAO dao = new ExamDAO();
		String cp = req.getContextPath();
		

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/exam/exam.do");
			return;
		}

		try {
			String gradeCode = req.getParameter("gradeCode");
			ExamDTO dto = new ExamDTO();
			dto.setGradeCode(req.getParameter("gradeCode"));
			dto.setScore(Integer.parseInt(req.getParameter("score")));
			dto.setExamType(req.getParameter("extp"));
			dao.examInsert(dto, gradeCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		forward(req, resp, "/WEB-INF/views/exam/exam.jsp");
	}

	private void examCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ExamDAO dao = new ExamDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		try {
			List<ExamDTO> list = null;
			list = dao.readExam(info.getUserId());

			req.setAttribute("list2", list);

		} catch (Exception e) {
			e.printStackTrace();
		}

		forward(req, resp, "/WEB-INF/views/exam/examCheck.jsp");
	}

}
