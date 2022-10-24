package com.lecture;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;

@WebServlet("/lecture/*")
public class LectureServlet extends MyServlet {
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
		if (uri.indexOf("main.do") != -1) {
			lectureNavForm(req, resp);
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

	protected void lectureNavForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 강의실 메인화면
		LectureDAO dao= new LectureDAO();
		List<LectureDTO> list= null;
		List<LectureDTO> hlist= null;
		
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		try {
			list= dao.registerSubject(info.getUserId());
			hlist= dao.registerHistory(info.getUserId());
			req.setAttribute("list", list);
			req.setAttribute("hlist", hlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		String path = "/WEB-INF/views/lecture/lecture_nav.jsp";
		forward(req, resp, path);
	}
	
	protected void classroomForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 강의실
		LectureDAO dao= new LectureDAO();
		String SubjectNo = req.getParameter("subjectNo");
		LectureDTO dto = new LectureDTO();
		List<LectureDTO> list=null;
		List<LectureDTO> wlist=null;
		try {
			dto= dao.readSubject(SubjectNo);
			
			req.setAttribute("professorName", dto.getProfessorname());
			req.setAttribute("semester", dto.getSemester());
			req.setAttribute("subjectName", dto.getSubjectName());
			req.setAttribute("credit", dto.getCredit());
			req.setAttribute("syear", dto.getSyear());
			
			list = dao.readLecture(SubjectNo);
			req.setAttribute("lectureList", list);
			
			wlist=dao.thisweekLecture(SubjectNo);
			req.setAttribute("thisweekList", wlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String path = "/WEB-INF/views/lecture/lecture_main.jsp";
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
