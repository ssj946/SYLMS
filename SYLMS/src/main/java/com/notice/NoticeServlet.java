package com.notice;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;


@WebServlet("/notice/*")
public class NoticeServlet extends MyServlet {
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
		if (uri.indexOf("notice.do") != -1) {
			noticeForm(req, resp);
		} else if (uri.indexOf("noticeWrite.do") != -1) {
			noticeWriteForm(req, resp);
		} else if (uri.indexOf("noticeArticle.do") != -1) {
			noticeArticleForm(req, resp);
		}
	}	
		
	protected void noticeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 공지사항 리스트
		String path = "/WEB-INF/views/lecture/notice.jsp";
		forward(req, resp, path);
	}
	
	protected void noticeWriteForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 공지사항 작성
		String path = "/WEB-INF/views/lecture/noticeWrite.jsp";
		forward(req, resp, path);
	}
	
	protected void noticeArticleForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 공지사항 보기
		String path = "/WEB-INF/views/lecture/noticeArticle.jsp";
		forward(req, resp, path);
	}
	
	
}	