package com.freebbs;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyUploadServlet;

@MultipartConfig
@WebServlet("/freebbs/*")
public class FreebbsServlet extends MyUploadServlet {
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
		pathname = root + "uploads" + File.separator + "freebbs";
		
		
		if(uri.indexOf("freebbs.do") != -1) {
			freebbsForm(req, resp);
		}else if(uri.indexOf("freebbsWrite.do") != -1) {
			freebbsWriteForm(req, resp);
		} else if (uri.indexOf("freebbswrite_ok.do") != -1) {
			freebbsWriteSubmit(req, resp);
		} else if (uri.indexOf("freebbsArticle.do") != -1) {
			freebbsArticleForm(req, resp);
		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("deleteFile.do") != -1) {
			deleteFile(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if (uri.indexOf("download.do") != -1) {
			download(req, resp);
		} else if (uri.indexOf("deleteList.do") != -1) {
			deleteList(req, resp);
		}
		
		
		
		
		
		
		
		
		
		
	}


		private void freebbsWriteSubmit(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}


		private void freebbsArticleForm(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}


		private void updateForm(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}


		private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}


		private void deleteFile(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}


		private void delete(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}


		private void download(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}


		private void deleteList(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}


	
	
	
	private void freebbsWriteForm(HttpServletRequest req, HttpServletResponse resp) {
		
		
	}


	private void freebbsForm(HttpServletRequest req, HttpServletResponse resp) {
		
		
	}
	
  
	
}
