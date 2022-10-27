package com.lecture;

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
		} else if (uri.indexOf("history.do") != -1) {
			registerHistory(req, resp);
		} else if (uri.indexOf("content.do") != -1) {
			content(req, resp);
		} else if (uri.indexOf("content_write.do") != -1) {
			contentWrite(req, resp);
		} else if (uri.indexOf("content_update.do") != -1) {
			contentUpdate(req, resp);
		} else if (uri.indexOf("content_delete.do") != -1) {
			contentUpdate(req, resp);
		}
	}

	protected void lectureNavForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 강의실 네비게이션
		LectureDAO dao= new LectureDAO();
		List<LectureDTO> list= null;
		List<LectureDTO> hlist= null;
		
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		try {
			list=dao.registerSubject(info.getUserId());
			hlist= dao.attendHistory(info.getUserId());
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
		String subjectNo = req.getParameter("subjectNo");
		
		LectureDTO dto = new LectureDTO();
		List<LectureDTO> list=null;
		List<LectureDTO> wlist=null;
		try {
			dto= dao.readSubject(subjectNo);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("professorName", dto.getProfessorname());
			req.setAttribute("semester", dto.getSemester());
			req.setAttribute("subjectName", dto.getSubjectName());
			req.setAttribute("credit", dto.getCredit());
			req.setAttribute("syear", dto.getSyear());
			
			list = dao.readLecture(subjectNo);
			req.setAttribute("lectureList", list);
			
			wlist=dao.thisweekLecture(subjectNo);
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

	protected void registerHistory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 수강 기록
		LectureDAO dao= new LectureDAO();
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		List<LectureDTO> list=null;
		try {
			String syear= req.getParameter("syear");
			String semester= req.getParameter("semester");
			list= dao.registerHistory(info.getUserId(), syear, semester);
			
			JSONObject job = new JSONObject();
			job.put("list", list);
			
			resp.setContentType("text/html; charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print(job.toString());
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendError(400);
	}
	
	protected void content(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 강의 하나 받아오기
		LectureDAO dao= new LectureDAO();	
		
		
		
		String subjectNo = req.getParameter("subjectNo");
		String bbsNum = req.getParameter("bbsNum");
		try {
			LectureDTO dto = new LectureDTO();
			dto= dao.readSubject(subjectNo);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("professorName", dto.getProfessorname());
			req.setAttribute("semester", dto.getSemester());
			req.setAttribute("subjectName", dto.getSubjectName());
			req.setAttribute("credit", dto.getCredit());
			req.setAttribute("syear", dto.getSyear());
			
			LectureDTO contentDTO = new LectureDTO();
			contentDTO= dao.readContent(bbsNum);
			
			req.setAttribute("contentDTO", contentDTO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String path = "/WEB-INF/views/lecture/content.jsp";
		forward(req, resp, path);
		
	}
	
	protected void contentWrite(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 강의게시판 글쓰기
		LectureDAO dao= new LectureDAO();
		String cp =req.getContextPath();
		
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if(info.getUserId().matches("/^\\d{8}$/")) {
			resp.sendRedirect(cp + "/");
			return;
		}
		
		String subjectNo = req.getParameter("subjectNo");
		String bbsNum = req.getParameter("bbsNum");
		try {
			LectureDTO dto = new LectureDTO();
			dto= dao.readSubject(subjectNo);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("professorName", dto.getProfessorname());
			req.setAttribute("semester", dto.getSemester());
			req.setAttribute("subjectName", dto.getSubjectName());
			req.setAttribute("credit", dto.getCredit());
			req.setAttribute("syear", dto.getSyear());
			
			LectureDTO contentDTO = new LectureDTO();
			contentDTO= dao.readContent(bbsNum);
			
			req.setAttribute("contentDTO", contentDTO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String path = "/WEB-INF/views/lecture/content.jsp";
		forward(req, resp, path);
	}
	
	protected void contentUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 강의게시판 수정
		LectureDAO dao= new LectureDAO();
		
		String cp =req.getContextPath();
		
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if(info.getUserId().matches("/^\\d{8}$/")) {
			resp.sendRedirect(cp + "/");
			return;
		}
		
		String subjectNo = req.getParameter("subjectNo");
		String bbsNum = req.getParameter("bbsNum");
		
		try {
			LectureDTO dto = new LectureDTO();
			dto= dao.readSubject(subjectNo);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("professorName", dto.getProfessorname());
			req.setAttribute("semester", dto.getSemester());
			req.setAttribute("subjectName", dto.getSubjectName());
			req.setAttribute("credit", dto.getCredit());
			req.setAttribute("syear", dto.getSyear());
			
			LectureDTO contentDTO = new LectureDTO();
			contentDTO= dao.readContent(bbsNum);
			
			req.setAttribute("contentDTO", contentDTO);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String path = "/WEB-INF/views/lecture/content.jsp";
		forward(req, resp, path);
	}
	
	protected void contentDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 강의게시판 지우기
		LectureDAO dao= new LectureDAO();
		
		String cp =req.getContextPath();
		
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if(info.getUserId().matches("/^\\d{8}$/")) {
			resp.sendRedirect(cp + "/");
			return;
		}
		
		String subjectNo = req.getParameter("subjectNo");
		String bbsNum = req.getParameter("bbsNum");
		try {
			LectureDTO dto = new LectureDTO();
			dto= dao.readSubject(subjectNo);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("professorName", dto.getProfessorname());
			req.setAttribute("semester", dto.getSemester());
			req.setAttribute("subjectName", dto.getSubjectName());
			req.setAttribute("credit", dto.getCredit());
			req.setAttribute("syear", dto.getSyear());
			
			LectureDTO contentDTO = new LectureDTO();
			contentDTO= dao.readContent(bbsNum);
			
			req.setAttribute("contentDTO", contentDTO);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String path = "/WEB-INF/views/lecture/content.jsp";
		forward(req, resp, path);
	}
	
	

}


