package com.lecture;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyServlet;

@WebServlet("/lecture/*")
public class LectureServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();

		// uri에 따른 작업 구분
		if (uri.indexOf("main.do") != -1) {
			lectureRoomForm(req, resp);
		} else if (uri.indexOf("classroom.do") != -1) {
			classroomForm(req, resp);
		} else if (uri.indexOf("assignment.do") != -1) {
			assignmentForm(req, resp);
		} else if (uri.indexOf("attend.do") != -1) {
			attendForm(req, resp);
		} else if (uri.indexOf("debate.do") != -1) {
			debateForm(req, resp);
		} else if (uri.indexOf("exam.do") != -1) {
			examForm(req, resp);
		} else if (uri.indexOf("general.do") != -1) {
			generalForm(req, resp);
		} else if (uri.indexOf("notice.do") != -1) {
			noticeForm(req, resp);
		} else if (uri.indexOf("qna.do") != -1) {
			qnaForm(req, resp);
		} else if (uri.indexOf("syllabus.do") != -1) {
			syllabusForm(req, resp);
		}
	}

	protected void lectureRoomForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 강의실 메인화면
		String path = "/WEB-INF/views/lecture/lecture_main.jsp";
		forward(req, resp, path);
	}
	
	protected void classroomForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 강의실
		String path = "/WEB-INF/views/lecture/classroom.jsp";
		forward(req, resp, path);
	}
	
	protected void assignmentForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 과제게시판
		String path = "/WEB-INF/views/lecture/assignment.jsp";
		forward(req, resp, path);
	}
	
	protected void attendForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 출석
		String path = "/WEB-INF/views/lecture/attend.jsp";
		forward(req, resp, path);
	}

	protected void debateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 토론게시판
		String path = "/WEB-INF/views/lecture/debate.jsp";
		forward(req, resp, path);
	}

	protected void examForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 시험
		String path = "/WEB-INF/views/lecture/exam.jsp";
		forward(req, resp, path);
	}

	protected void generalForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 자유게시판
		String path = "/WEB-INF/views/lecture/general.jsp";
		forward(req, resp, path);
	}

	protected void noticeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 공지사항
		String path = "/WEB-INF/views/lecture/notice.jsp";
		forward(req, resp, path);
	}

	protected void qnaForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 질의응답
		String path = "/WEB-INF/views/lecture/qna.jsp";
		forward(req, resp, path);
	}

	protected void syllabusForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 강의계획서
		String path = "/WEB-INF/views/lecture/syllabus.jsp";
		forward(req, resp, path);
	}
	


}
