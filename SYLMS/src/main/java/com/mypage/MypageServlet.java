package com.mypage;

import java.io.File;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.member.SessionInfo;
import com.util.FileManager;
import com.util.MyUploadServlet;

@MultipartConfig
@WebServlet("/mypage/*")
public class MypageServlet extends MyUploadServlet {

	private static final long serialVersionUID = 1L;

	private String pathname;
	
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		
		if(info == null) { 
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}
		
		if (uri.indexOf("pwd.do") != -1) {
		    pwdForm(req, resp);
		}else if(uri.indexOf("pwd_ok.do") != -1) {
			pwdSubmit(req, resp);
		}else if(uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);	
		}else if(uri.indexOf("file.do") != -1) {
			fileuploadForm(req, resp);
		}else if(uri.indexOf("class.do") != -1) {
			scheduleForm(req, resp);
		}else if(uri.indexOf("assistant.do") != -1) {
			assistantForm(req, resp);
		}
		
	}
	
	

	private void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}
	
		
		String path = "/WEB-INF/views/mypage/pwdSubmit.jsp";
		forward(req, resp, path);
		
		
		
		
	}
	
	private void pwdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MypageDAO dao = new MypageDAO();
		HttpSession session = req.getSession();

		String cp = req.getContextPath();

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		try {
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			if (info == null) {
				resp.sendRedirect(cp + "/member/login.do");
				return;
			}
			
			MypageDTO dto = dao.readMember(info.getUserId());
			if(dto == null ) {
				session.invalidate();
				resp.sendRedirect(cp+ "/");
				return;
			}
			
			String userPwd = req.getParameter("userPwd");
			String mode = req.getParameter("mode");
			
			if ( ! dto.getPwd().equals(userPwd)) {
			  
				req.setAttribute("mode", mode);
				req.setAttribute("message", "패스워드가 일치하지 않습니다.");
				forward(req, resp, "/WEB-INF/views/mypage/pwdSubmit.jsp");
				return;
			}
		
	
            
			req.setAttribute("title", "학사 정보");
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "profile");
			forward(req, resp, "/WEB-INF/views/mypage/updateForm.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();

		}
		resp.sendRedirect(cp + "/");
	}
	

	//수정 완료
   private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		MypageDAO dao = new MypageDAO();
		HttpSession session = req.getSession();
		String cp = req.getContextPath();
		
		
		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "account";

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}
		try {
			
			SessionInfo info = (SessionInfo) session.getAttribute("member");

			MypageDTO dto = new MypageDTO();
			 
			dto.setUserId(info.getUserId());
			dto.setPwd(req.getParameter("pwd"));
			dto.setTel(req.getParameter("tel"));
			dto.setEmail(req.getParameter("email"));
			String fileName =  req.getParameter("fileName");
			dto.setFileName(fileName);
				
			Part p = req.getPart("selectFile");
		
			Map<String, String> map = doFileUpload(p, pathname);
			if(map != null) {
				String imagefilename =  map.get("saveFilename");
				FileManager.doFiledelete(pathname,fileName);
				dto.setFileName(imagefilename);
			}
			
			dao.updateMember(dto);
		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		resp.sendRedirect(cp+"/");
	}
	
	protected void fileuploadForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	protected void scheduleForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	protected void assistantForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {

	}

	

	

	
}
