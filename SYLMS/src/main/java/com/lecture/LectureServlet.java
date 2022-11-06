package com.lecture;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.member.SessionInfo;
import com.util.FileManager;
import com.util.MyUploadServlet;


@MultipartConfig
@WebServlet("/lecture/*")
public class LectureServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;
	
	private String pathname;

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

		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "lecture";
			
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
		} else if (uri.indexOf("content_submit.do") != -1) {
			contentWriteSubmit(req, resp);
		} else if (uri.indexOf("content_update.do") != -1) {
			contentUpdate(req, resp);
		} else if (uri.indexOf("content_update_ok.do") != -1) {
			contentUpdateSubmit(req, resp);
		} else if (uri.indexOf("content_delete.do") != -1) {
			contentDelete(req, resp);
		} else if (uri.indexOf("attend.do") != -1) {
			attendForm(req, resp);
		} else if (uri.indexOf("attend_gen.do") != -1) {
			attendGenerate(req, resp);
		} else if (uri.indexOf("attend_ok.do") != -1) {
			attend(req, resp);
		} else if (uri.indexOf("attend_list.do") != -1) {
			attendList(req, resp);
		} else if (uri.indexOf("attend_manage.do") != -1) {
			attendManager(req, resp);
		} else if (uri.indexOf("attend_manage_ok.do") != -1) {
			attendList_all(req, resp);
		} else if (uri.indexOf("attendance_modify.do") != -1) {
			modify_attendance(req, resp);
		} else if (uri.indexOf("assignment.do") != -1) {
			assignmentList(req, resp);
		} else if (uri.indexOf("assignment_view.do") != -1) {
			assignmentContent(req, resp);
		} else if (uri.indexOf("assignment_ok.do") != -1) {
			assignmentSubmit(req, resp);
		} else if (uri.indexOf("download.do") != -1) {
			download(req, resp);
		}  else if (uri.indexOf("file_delete.do") != -1) {
			deleteFile(req, resp);
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
			if(info.getUserId().matches("\\d{8}")){
				list = dao.registerSubject(info.getUserId());
				hlist = dao.attendHistory(info.getUserId());
			} else if (info.getUserId().matches("\\d{5}")) {
				list = dao.registerSubject_pro(info.getUserId());
				hlist = dao.attendHistory_pro(info.getUserId());
			} else {
				hlist = dao.attendHistory_admin();
			}
			
			req.setAttribute("list", list);
			req.setAttribute("hlist", hlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		String path = "/WEB-INF/views/lecture/lecture_nav.jsp";
		forward(req, resp, path);
	}
	
	protected void classroomForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 강의실 메인화면
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
		req.setCharacterEncoding("UTF-8");
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
		req.setCharacterEncoding("UTF-8");
		LectureDAO dao= new LectureDAO();
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		List<LectureDTO> list=null;
		try {
			String syear= req.getParameter("syear");
			String semester= req.getParameter("semester");
			
			if(info.getUserId().matches("\\d{8}")) {
				list= dao.registerHistory(info.getUserId(), syear, semester);
			} else if(info.getUserId().matches("\\d{5}")) {
				list= dao.registerHistory_pro(info.getUserId(), syear, semester);
			} else {
				list= dao.registerHistory_admin(syear, semester);
			}
			
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
		req.setCharacterEncoding("UTF-8");
		LectureDAO dao= new LectureDAO();	
		
		String subjectNo = req.getParameter("subjectNo");
		String bbsNum = req.getParameter("bbsNum");
		
		req.setAttribute("mode", "view");
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
		// 강의게시판 글쓰기 화면 띄우기
		req.setCharacterEncoding("UTF-8");
		LectureDAO dao= new LectureDAO();
		
		String cp =req.getContextPath();
		
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if(info.getUserId().matches("\\d{8}")) {
			resp.sendRedirect(cp + "/");
			return;
		}
		
		req.setAttribute("mode", "write");
		
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
	
	protected void contentWriteSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 강의게시판 글쓰기
		req.setCharacterEncoding("UTF-8");
		LectureDAO dao= new LectureDAO();
		String cp =req.getContextPath();
		
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if(info.getUserId().matches("\\d{8}")) {
			resp.sendRedirect(cp + "/");
			return;
		}
		
		
		
		String subjectNo = req.getParameter("subjectNo");
		
		String state ="false";
		try {
			LectureDTO dto = new LectureDTO();
			dto.setSubjectNo(subjectNo);
			dto.setTitle(req.getParameter("title"));
			dto.setContent(req.getParameter("content"));
			dto.setStart_date(req.getParameter("sdate"));
			dto.setEnd_date(req.getParameter("edate"));
			
			System.out.println("로그 end_date:"+dto.getEnd_date());
			
			dto.setWeek(req.getParameter("week"));
			dto.setPart(req.getParameter("type"));
			
			dao.insertLecture(dto);
			
			state="true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject job = new JSONObject();
		job.put("state", state);
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
		
	}
	
	protected void contentUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 강의게시판 글쓰기 화면 띄우기
		req.setCharacterEncoding("UTF-8");
		LectureDAO dao= new LectureDAO();
		
		String cp =req.getContextPath();
		
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if(info.getUserId().matches("\\d{8}")) {
			resp.sendRedirect(cp + "/");
			return;
		}
		
		req.setAttribute("mode", "update");
		
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
	
	protected void contentUpdateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 강의게시판 수정
		req.setCharacterEncoding("UTF-8");
		LectureDAO dao= new LectureDAO();
		String cp =req.getContextPath();
		
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if(info.getUserId().matches("\\d{8}")) {
			resp.sendRedirect(cp + "/");
			return;
		}
		
		
		
		String subjectNo = req.getParameter("subjectNo");
		String bbsNum = req.getParameter("bbsNum");
		
		String state ="false";
		try {
			LectureDTO dto = new LectureDTO();
			dto.setSubjectNo(subjectNo);
			dto.setTitle(req.getParameter("title"));
			dto.setContent(req.getParameter("content"));
			dto.setStart_date(req.getParameter("sdate"));
			dto.setEnd_date(req.getParameter("edate"));
			dto.setWeek(req.getParameter("week"));
			dto.setPart(req.getParameter("type"));
			dto.setBbsNum(bbsNum);
			
			dao.updateLecture(dto);
			
			state="true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject job = new JSONObject();
		job.put("state", state);
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
		
	}
	
	protected void contentDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 강의게시판 지우기
		req.setCharacterEncoding("UTF-8");
		LectureDAO dao= new LectureDAO();
		String cp =req.getContextPath();
		
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if(info.getUserId().matches("\\d{8}")) {
			resp.sendRedirect(cp + "/");
			return;
		}
		
		
		
		String subjectNo = req.getParameter("subjectNo");
		String bbsNum = req.getParameter("bbsNum");
		
		
		try {
			dao.deleteLecture(bbsNum);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/lecture/classroom.do?subjectNo="+subjectNo);
	}
	
	protected void attendForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("UTF-8");
		LectureDAO dao= new LectureDAO();
		String subjectNo = req.getParameter("subjectNo");
		
		LectureDTO dto = new LectureDTO();

		try {
			dto= dao.readSubject(subjectNo);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("professorName", dto.getProfessorname());
			req.setAttribute("semester", dto.getSemester());
			req.setAttribute("subjectName", dto.getSubjectName());
			req.setAttribute("credit", dto.getCredit());
			req.setAttribute("syear", dto.getSyear());
			
			LectureDTO dto2 = dao.attending(subjectNo);
			
			req.setAttribute("dto", dto2);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		String path = "/WEB-INF/views/lecture/attend.jsp";
		forward(req, resp, path);
		
	}
	
	protected void attendGenerate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("UTF-8");
		LectureDAO dao= new LectureDAO();
		String cp =req.getContextPath();
		
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		if(info.getUserId().matches("\\d{8}")) {
			resp.sendRedirect(cp + "/");
			return;
		}
		
		String subjectNo = req.getParameter("subjectNo");

		
		try {
			double rd = Math.random();
			
			int code= (int) (rd*9001)+1000;
			System.out.println(code);
			dao.generate_attendcode(subjectNo, code);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/lecture/attend.do?subjectNo="+subjectNo);
		
	}
	
	protected void attend(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("UTF-8");
		
		LectureDAO dao= new LectureDAO();
		String cp =req.getContextPath();
		
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		
		String subjectNo = req.getParameter("subjectNo");
		String attendNo = req.getParameter("attendNo");
		String submitCode = req.getParameter("submitCode");
		String studentCode = info.getUserId();
		
		try {
			dao.attendSubmit(submitCode, studentCode, attendNo);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/lecture/attend.do?subjectNo="+subjectNo);
		
	}
	
	protected void attendList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("UTF-8");
		
		LectureDAO dao= new LectureDAO();
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String mode = req.getParameter("mode");
		List<LectureDTO>list = null;
		System.out.println(mode);
		try {
			if(mode.equals("attend")) {
				list = dao.attendanceRecord_attend(info.getUserId());
				
			} else if (mode.equals("absent")) {
				list = dao.attendanceRecord_absent(info.getUserId());
				
			} else if (mode.equals("run")) {
				list = dao.attendanceRecord_run(info.getUserId());
				
			}
			JSONObject job = new JSONObject();
			job.put("list", list);
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print(job.toString());
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendError(400);
		
	}
	
	protected void attendManager(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("UTF-8");
		
		LectureDAO dao= new LectureDAO();
		String subjectNo = req.getParameter("subjectNo");
		
		LectureDTO dto = new LectureDTO();

		try {
			dto= dao.readSubject(subjectNo);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("professorName", dto.getProfessorname());
			req.setAttribute("semester", dto.getSemester());
			req.setAttribute("subjectName", dto.getSubjectName());
			req.setAttribute("credit", dto.getCredit());
			req.setAttribute("syear", dto.getSyear());
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		String path = "/WEB-INF/views/lecture/attend_check.jsp";
		forward(req, resp, path);
		
	}
	
	protected void attendList_all(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("UTF-8");
		
		LectureDAO dao= new LectureDAO();
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();
		
		String subjectNo = req.getParameter("subjectNo");
		
		if(info.getUserId().length()==8) {
			resp.sendRedirect(cp+"/lecture/classroom.do?subjectNo="+subjectNo);
		}
		
		String date = req.getParameter("date");
		System.out.println("날짜를 읽어왔습니다.");
		List<LectureDTO>list = null;
		
		try {
			list = dao.attendanceRecord_all(subjectNo, date);
			
			JSONObject job = new JSONObject();
			job.put("list", list);
			resp.setContentType("text/html;charset=utf-8");
			PrintWriter out = resp.getWriter();
			out.print(job.toString());
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendError(400);
		
	}
	
	protected void modify_attendance(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("UTF-8");
		
		LectureDAO dao= new LectureDAO();
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String cp = req.getContextPath();
		
		String subjectNo = req.getParameter("subjectNo");
		
		if(info.getUserId().length()==8) {
			resp.sendRedirect(cp+"/lecture/classroom.do?subjectNo="+subjectNo);
		}
		
		String list = req.getParameter("list");
		
		try {
			JSONArray jarr =new JSONArray(list);
			JSONObject jobj = new JSONObject();
			List<LectureDTO> mod_list = new ArrayList<>();
			for(int i =0; i<jarr.length();i++) {
				
				jobj= jarr.getJSONObject(i);
				
				LectureDTO dto = new LectureDTO();
				
				dto.setAttendNo(jobj.getString("attendNo"));
				dto.setStudentcode(jobj.getString("studentcode"));
				dto.setAttend_time(jobj.getString("attend_time"));
				dto.setSubjectNo(subjectNo);
				dto.setAttend_pass(jobj.getString("attend_pass"));
				
				mod_list.add(dto);
			}
			
			dao.attendanceRecord_updateAll(mod_list);
	       
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendError(400);
		
	}
	
	protected void assignmentList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("UTF-8");
		
		LectureDAO dao= new LectureDAO();
		String subjectNo = req.getParameter("subjectNo");
		
		LectureDTO dto = new LectureDTO();

		try {
			dto= dao.readSubject(subjectNo);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("professorName", dto.getProfessorname());
			req.setAttribute("semester", dto.getSemester());
			req.setAttribute("subjectName", dto.getSubjectName());
			req.setAttribute("credit", dto.getCredit());
			req.setAttribute("syear", dto.getSyear());
			
			List<LectureDTO> list = dao.list_assignment(subjectNo);
			
			req.setAttribute("list", list);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		String path = "/WEB-INF/views/lecture/assignment.jsp";
		forward(req, resp, path);
		
	}
	
	//과제창 띄우기
	protected void assignmentContent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("UTF-8");
		
		LectureDAO dao= new LectureDAO();
		String subjectNo = req.getParameter("subjectNo");
		String asNo = req.getParameter("asNo");
		
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		LectureDTO dto = new LectureDTO();

		try {
			dto= dao.readSubject(subjectNo);
			req.setAttribute("subjectNo", subjectNo);
			req.setAttribute("professorName", dto.getProfessorname());
			req.setAttribute("semester", dto.getSemester());
			req.setAttribute("subjectName", dto.getSubjectName());
			req.setAttribute("credit", dto.getCredit());
			req.setAttribute("syear", dto.getSyear());
			
			
			LectureDTO adto = dao.read_assignment(asNo);
			req.setAttribute("adto", adto);
			
			LectureDTO asdto = dao.read_assignmentSubmit(asNo, info.getUserId());
			req.setAttribute("asdto", asdto);
			
			List<LectureDTO> list = dao.fileList(asdto.getAs_submitNo());
			req.setAttribute("filelist", list);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		String path = "/WEB-INF/views/lecture/assign_content.jsp";
		forward(req, resp, path);
		
	}
	
	protected void assignmentSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 과제 제출
		req.setCharacterEncoding("UTF-8");
		
		LectureDAO dao= new LectureDAO();
		
		String cp =req.getContextPath();
		
		HttpSession session =req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String subjectNo = req.getParameter("subjectNo");
		String asNo = req.getParameter("asNo");
		String content = req.getParameter("content");
		LectureDTO dto = new LectureDTO();
		
		try {
			dto.setSubjectNo(subjectNo);
			dto.setAsNo(asNo);
			dto.setContent(content);
			dto.setStudentcode(info.getUserId());
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if( map != null) {
				String[] saveFiles = map.get("saveFilenames");
				String[] originalFiles = map.get("originalFilenames");
				dto.setSaveFiles(saveFiles);
				dto.setOriginalFiles(originalFiles);
			}
			dao.insert_assignment(dto);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/lecture/assignment.do?subjectNo="+subjectNo);
		
	}
	
	protected void download(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 파일 다운로드
		req.setCharacterEncoding("UTF-8");
		
		LectureDAO dao= new LectureDAO();		
		
		try {
			String fileNo = req.getParameter("fileNo");
			
			LectureDTO dto = dao.readFile(fileNo);
			FileManager.doFiledownload(dto.getSavefilename(), dto.getOriginalfilename(), pathname, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void deleteFile(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 파일 삭제
		req.setCharacterEncoding("UTF-8");
		
		LectureDAO dao= new LectureDAO();	
		
		String cp = req.getContextPath();
		String subjectNo = req.getParameter("subjectNo");
		String asNo = req.getParameter("asNo");
		
		
		try {
			String fileNo = req.getParameter("fileNo");
			
			LectureDTO dto = dao.readFile(fileNo);
			if(dto!=null) {
				FileManager.doFiledelete(pathname, dto.getSavefilename());
				dao.deleteFile(fileNo);
			}

			resp.sendRedirect(cp+"/lecture/assignment_view.do?subjectNo="+subjectNo+"&asNo="+asNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


