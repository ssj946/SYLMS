package com.mypage;

import java.io.File;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
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
import com.util.MyUtil;
import com.util.MyUtilBootstrap;

@MultipartConfig
@WebServlet("/mypage/*")
public class MypageServlet extends MyUploadServlet {

	private static final long serialVersionUID = 1L;

	private String pathname;

	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (info == null) {
			forward(req, resp, "/WEB-INF/views/member/login.jsp");
			return;
		}

		if (uri.indexOf("pwd.do") != -1) {
			pwdForm(req, resp);
		} else if (uri.indexOf("pwd_ok.do") != -1) {
			pwdSubmit(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		}else if (uri.indexOf("file.do") != -1) {
			fileuploadForm(req, resp);
		} else if (uri.indexOf("class.do") != -1) {
			scheduleForm(req, resp);
		} else if (uri.indexOf("assistant.do") != -1) {
			assistantForm(req, resp);
		} else if (uri.indexOf("assistant_ok.do") != -1) {
			assistantSubmit(req, resp);
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
			if (dto == null) {
				session.invalidate();
				resp.sendRedirect(cp + "/");
				return;
			}

			String userPwd = req.getParameter("userPwd");
			String mode = req.getParameter("mode");

			if (!dto.getPwd().equals(userPwd)) {

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

	// 수정 완료
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
			String fileName = req.getParameter("fileName");
			dto.setFileName(fileName);

			Part p = req.getPart("selectFile");

			Map<String, String> map = doFileUpload(p, pathname);
			if (map != null) {
				String imagefilename = map.get("saveFilename");
				FileManager.doFiledelete(pathname, fileName);
				dto.setFileName(imagefilename);
			}

			dao.updateMember(dto);

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/");
	}

	protected void fileuploadForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {


	}

	protected void scheduleForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	protected void assistantForm(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		
		req.setCharacterEncoding("utf-8");
		
		
		
		MypageDAO dao = new MypageDAO();

		MyUtil util = new MyUtilBootstrap();

		String cp = req.getContextPath();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		try {
			// 페이지 번호
			String page = req.getParameter("page");
			int current_page = 1;
			if (page != null) {
				current_page = Integer.parseInt(page);
			}

			// 검색
			String year = req.getParameter("year");
			if(year==null) {
				Calendar cal = Calendar.getInstance();
				int y = cal.get(Calendar.YEAR)+1;
				year = Integer.toString(y);
			}
			String semester= req.getParameter("semester");
			if(semester==null) {
				semester = "1";
			}
			//int형으로 변환
			int tyear = Integer.parseInt(year);
			int tsemester = Integer.parseInt(semester);
			
			String keyword = req.getParameter("keyword");
			if (keyword == null) {
				keyword = "";
			}


			// GET방식이라 디코딩
			if (req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}

			// 한페이지 표시할 데이터 개수
			int size = 10;

			// 전체 데이터 개수
			int dataCount;
			int total_page;

			if(keyword.length() != 0) {
				dataCount = dao.dataCount(tyear, tsemester, keyword);
			}else {
				dataCount = dao.dataCount(tyear, tsemester);
			}
			
			
			// 전체 페이지 수
			total_page = util.pageCount(dataCount, size);

			if (current_page > total_page) {
				current_page = total_page;
			}

			// 게시글 가져오기
			int offset = (current_page - 1) * size;
			if (offset < 0)
				offset = 0;

			List<MypageDTO> list = null;

			if(keyword.length() != 0) {
				list = dao.listSubmit(offset, size, keyword, tyear, tsemester);
			}else {
				list = dao.listSubject(offset, size, tyear, tsemester);
			}
			
			
			
			List<MypageDTO> alist = null;
			String id = info.getUserId();

			alist = dao.readAssitance(id);

			List<MypageDTO> aplist = null;
			// 신청자
			int ENABLE = 0;

			
			if(keyword.length() != 0) {
				aplist = dao.approvelist(offset, size, ENABLE, info.getUserId(), tyear, tsemester, keyword);
			}else {
				aplist = dao.approvelist(offset, size, ENABLE, info.getUserId(), tyear, tsemester);
			}
			
			
			// 조교
			ENABLE = 1;
			List<MypageDTO> aslist = null;
					
			if(keyword.length() != 0) {
				aslist = dao.approvelist(offset, size, ENABLE, info.getUserId(), tyear, tsemester, keyword );

			}else {
				aslist = dao.approvelist(offset, size, ENABLE, info.getUserId(), tyear, tsemester);
			}		
					
			String query = "";
			if (keyword.length() != 0) {
				query =  "keyword=" + URLEncoder.encode(keyword, "utf-8");
			}

			String listUrl = cp + "/mypage/assistant.do";
			if (query.length() != 0) {
				listUrl += "?" + query;
			}

			String paging = util.paging(current_page, total_page, listUrl);

			req.setAttribute("aslist", aslist); // 조교목록
			req.setAttribute("aplist", aplist); // 신청자목록
			req.setAttribute("list", list); // 신청화면
			req.setAttribute("alist", alist);// 승인내역, 신청내역
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("size", size);
			req.setAttribute("paging", paging);
			req.setAttribute("tyear", tyear);
			req.setAttribute("tsemester", tsemester);
			req.setAttribute("keyword", keyword);

		} catch (Exception e) {
			e.printStackTrace();
		}

		String path = "/WEB-INF/views/mypage/assistant.jsp";
		forward(req, resp, path);

	}

	private void assistantSubmit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		MypageDAO dao = new MypageDAO();

		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String mode = req.getParameter("mode");

		String cp = req.getContextPath();

		if (info == null) {
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		String subjectNo = req.getParameter("subjectNo");
		String applicationNum = req.getParameter("applicationNum");

		String id = info.getUserId();

		try {

			if (mode.equals("approve")) {
				dao.approve(applicationNum);

			} else if (mode.equals("cancel")) {
				dao.cancel(applicationNum);

			} else if (mode.equals("apply")) {
				dao.insertassiantance(subjectNo, id);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/");

	}

}
