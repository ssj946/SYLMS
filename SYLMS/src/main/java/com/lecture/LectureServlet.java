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
		} else if (uri.indexOf("lecture.do") != -1) {
			lectureForm(req, resp);
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
	
	protected void lectureForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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


}
