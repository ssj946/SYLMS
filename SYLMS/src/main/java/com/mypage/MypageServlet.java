package com.mypage;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.MyServlet;

@WebServlet("/mypage/*")
public class MypageServlet extends MyServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		if (uri.indexOf("pwd.do") != -1) {
		    pwdForm(req, resp);
		}else if(uri.indexOf("pwd_ok.do") != -1) {
			pwdSumbit(req, resp);
		}else if(uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		}else if(uri.indexOf("file.do") != -1) {
			fileuploadForm(req, resp);
		}else if(uri.indexOf("class.do") != -1) {
			scheduleForm(req, resp);
		}else if(uri.indexOf("assistant.do") != -1) {
			assistantForm(req, resp);
		}
		
	}
	private void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/mypage/pwdSubmit.jsp";
		forward(req, resp, path);
		
		
	}
	
	private void pwdSumbit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
	}

	
	
	
	
	
	
	

	protected void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {
	
		String path = "/WEB-INF/views/mypage/pwdSubmit.jsp";
		forward(req, resp, path);
		
	}

	protected void fileuploadForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	protected void scheduleForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	protected void assistantForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException  {

	}

	

	

	
}
