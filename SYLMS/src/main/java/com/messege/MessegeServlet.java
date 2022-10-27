package com.messege;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.MemberDTO;
import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;
import com.util.MyUtilBootstrap;

@WebServlet("/messege/*")
public class MessegeServlet extends MyServlet {
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

		if (uri.indexOf("receive.do") != -1) {
			received(req, resp);
		} else if (uri.indexOf("check.do") != -1) {
			check(req, resp);
		} else if (uri.indexOf("send.do") != -1) {
			sendForm(req, resp);
		} else if (uri.indexOf("send_ok.do") != -1) {
			submit(req, resp);
		} else if (uri.indexOf("read_ok.do") != -1) {
			readForm(req, resp);
		} else if (uri.indexOf("count.do") != -1) {
			countForm(req, resp);
		}

	}

	private void countForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MessegeDAO dao = new MessegeDAO();
		//읽지 않은 메세지 수
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		try {
			int messegeCount;			
			String userId = info.getUserId();
			messegeCount = dao.messegeCount(userId);
			req.setAttribute("messegeCount", messegeCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		forward(req, resp, "/WEB-INF/views/layout/header.jsp");
	}

	private void readForm(HttpServletRequest req, HttpServletResponse resp) {
		//메세지를 읽은 날짜를 넣기
		MessegeDAO dao = new MessegeDAO();
		
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void sendForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 메세지 작성폼
		MessegeDAO dao = new MessegeDAO();

		try {
			String id = req.getParameter("id");
			List<MemberDTO> listMember = dao.idList(id);

			req.setAttribute("listMember", listMember);

		} catch (Exception e) {
			e.printStackTrace();
		}

		forward(req, resp, "/WEB-INF/views/messege/messegeSend.jsp");

	}

	private void submit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 메세지 보내기
		MessegeDAO dao = new MessegeDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/messege/receive.do");
			return;
		}

		try {
			MessegeDTO dto = new MessegeDTO();

			// 회원아이디
			dto.setSendId(info.getUserId());

			dto.setReceiveId(req.getParameter("itemRight"));
			dto.setContent(req.getParameter("msg"));

			dao.sendMessege(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/messege/receive.do");

	}

	private void received(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 받은 메세지 리스트
		MessegeDAO dao = new MessegeDAO();
		MyUtil util = new MyUtilBootstrap();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		try {			
			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null) {
				current_page = Integer.parseInt(page);
			}

			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "userName";
				keyword = "";
			}

			if (req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}

			// 데이터 개수
			int dataCount;
			String userId = info.getUserId();
						
			if (keyword.length() == 0) {
				dataCount = dao.dataCount(userId);
			} else {
				dataCount = dao.dataCount(condition, keyword, userId);
			}

			// 전체 페이지 수
			int size = 10;
			int total_page = util.pageCount(dataCount, size);
			if (current_page > total_page) {
				current_page = total_page;
			}

			// 쪽지 가져오기
			int offset = (current_page - 1) * size;
			if (offset < 0)	offset = 0;
			
			List<MessegeDTO> list = null;
			if (keyword.length() == 0) {
				list = dao.listBoard(offset, size, userId);
			} else {
				list = dao.listBoard(offset, size, condition, keyword, userId);
			}

			String query = "";
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}

			// 페이징
			String listUrl = cp + "/messege/receive.do";
			String checkUrl = cp + "/messege/check.do?page=" + current_page;
			if (query.length() != 0) {
				listUrl += "?" + query;
				checkUrl += "&" + query;
			}
			String paging = util.paging(current_page, total_page, listUrl);
			
			// 포워딩할 JSP에 넘길 속성
			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("checkUrl", checkUrl);
			req.setAttribute("paging", paging);
			req.setAttribute("condition", condition);
			req.setAttribute("keyword", keyword);
			req.setAttribute("userId", userId);

		} catch (Exception e) {
			e.printStackTrace();
		}

		forward(req, resp, "/WEB-INF/views/messege/messege.jsp");

	}

	private void check(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 메세지 확인
		MessegeDAO dao = new MessegeDAO();

		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String page = req.getParameter("page");
		String query = "page=" + page;

		try {
			String sendID = req.getParameter("sendId");
			String userId = info.getUserId();
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "all";
				keyword = "";
			}
			keyword = URLDecoder.decode(keyword, "utf-8");

			if (keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}

			// 쪽지 가져오기
			MessegeDTO dto = dao.readMessege(sendID, userId);
			if (dto == null) { // 쪽지가 없으면 다시 리스트로
				resp.sendRedirect(cp + "/messege/receive.do?" + query);
				return;
			}
			
			// JSP로 전달할 속성
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);

			// 포워딩
			forward(req, resp, "/WEB-INF/views/messege/messegeCheck.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/messege/receive.do?" + query);

	}

}
