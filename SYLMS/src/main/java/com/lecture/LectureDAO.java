package com.lecture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class LectureDAO {
	private Connection conn = DBConn.getConnection();
	
	//현재 학기 수강목록 불러오기 - 학생
	public List<LectureDTO> registerSubject(String studentcode) throws SQLException{
		PreparedStatement pstmt= null;
		String sql;
		ResultSet rs=null;
		List<LectureDTO> list = new ArrayList<>();
		try {
			sql= "SELECT s.subjectNo, subjectName, credit, TO_CHAR(sYear,'YYYY') sYear, semester, studentcode, name "
					+ " FROM REGISTERSUBJECT r"
					+ " JOIN subject s "
					+ " ON s.subjectNo=r.subjectNo "
					+ " JOIN account a "
					+ " ON a.id=studentcode "
					+ " WHERE STUDENTcode = ?"
					+ " AND TO_CHAR(sYear,'YYYY')=TO_CHAR(SYSDATE,'YYYY') "
					+ " AND sYear < SYSDATE"
					+ " AND SYSDATE<sYear+(interval '4' month)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,studentcode);
			
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				LectureDTO dto= new LectureDTO();
				dto.setSubjectNo(rs.getString("subjectNo"));
				dto.setSubjectName(rs.getString("subjectName"));
				dto.setCredit(Integer.parseInt(rs.getString("credit")));
				dto.setSyear(Integer.parseInt(rs.getString("syear")));
				dto.setSemester(Integer.parseInt(rs.getString("semester")));
				dto.setStudentcode(rs.getString("studentcode"));
				dto.setProfessorname(rs.getString("name"));
				
				
				list.add(dto);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
		
	}
	
	//수강학기 목록 불러오기 - 학생
		public List<LectureDTO> attendHistory(String studentcode) throws SQLException{
			List<LectureDTO> list = new ArrayList<>();
			PreparedStatement pstmt =null;
			ResultSet rs= null;
			String sql;
			try {
				sql= "SELECT STUDENTCODE, TO_CHAR(syear,'YYYY')syear FROM REGISTERSUBJECT r "
						+ " JOIN subject s ON s.SUBJECTNO =r.SUBJECTNO "
						+ " WHERE STUDENTCODE = ? "
						+ " GROUP BY studentcode, TO_CHAR(syear,'YYYY') "
						+ " ORDER BY syear";
				
				pstmt=conn.prepareStatement(sql);
				
				pstmt.setString(1, studentcode);
				
				rs=pstmt.executeQuery();
				
				while(rs.next()) {
					LectureDTO dto = new LectureDTO();
					dto.setSyear(Integer.parseInt(rs.getString("syear")));
					list.add(dto);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			return list;
		}
	
	//해당 학기 수강내역 - 학생
	public List<LectureDTO> registerHistory(String studentcode, String syear, String semester) throws SQLException{
		PreparedStatement pstmt= null;
		String sql;
		ResultSet rs=null;
		List<LectureDTO> list = new ArrayList<>();
		try {
			sql= "SELECT s.subjectNo, subjectName, credit, TO_CHAR(sYear,'YYYY') sYear, semester, studentcode, name "
					+ " FROM REGISTERSUBJECT r"
					+ " JOIN subject s "
					+ " ON s.subjectNo=r.subjectNo "
					+ " JOIN account a "
					+ " ON a.id=studentcode "
					+ " WHERE STUDENTcode = ? AND TO_CHAR(sYear,'YYYY') = ? AND semester = ? "
					+ " ORDER BY syear, semester ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,studentcode);
			pstmt.setString(2,syear);
			pstmt.setString(3,semester);
			
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				LectureDTO dto= new LectureDTO();
				dto.setSubjectNo(rs.getString("subjectNo"));
				dto.setSubjectName(rs.getString("subjectName"));
				dto.setCredit(Integer.parseInt(rs.getString("credit")));
				dto.setSyear(Integer.parseInt(rs.getString("syear")));
				dto.setSemester(Integer.parseInt(rs.getString("semester")));
				dto.setStudentcode(rs.getString("studentcode"));
				dto.setProfessorname(rs.getString("name"));
				
				
				list.add(dto);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
		
	}
	
	//현재 학기 수강목록 불러오기 - 교수, 조교
	public List<LectureDTO> registerSubject_pro(String id) throws SQLException{
		PreparedStatement pstmt= null;
		String sql;
		ResultSet rs=null;
		List<LectureDTO> list = new ArrayList<>();
		try {
			sql= "SELECT subjectNo, subjectName, credit, TO_CHAR(syear,'YYYY') syear, semester FROM subject s "
					+ " JOIN account a on s.id= a.id "
					+ " WHERE a.id= ? "
					+ " AND TO_CHAR(sYear,'YYYY')=TO_CHAR(SYSDATE,'YYYY') "
					+ " AND sYear < SYSDATE "
					+ " AND SYSDATE<sYear+(interval '4' month)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,id);
			
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				LectureDTO dto= new LectureDTO();
				dto.setSubjectNo(rs.getString("subjectNo"));
				dto.setSubjectName(rs.getString("subjectName"));
				dto.setCredit(Integer.parseInt(rs.getString("credit")));
				dto.setSyear(Integer.parseInt(rs.getString("syear")));
				dto.setSemester(Integer.parseInt(rs.getString("semester")));
				
				
				list.add(dto);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
		
	}
	
	//수강학기 목록 불러오기 - 교수, 조교
	public List<LectureDTO> attendHistory_pro(String id) throws SQLException{
		List<LectureDTO> list = new ArrayList<>();
		PreparedStatement pstmt =null;
		ResultSet rs= null;
		String sql;
		try {
			sql= "SELECT TO_CHAR(syear,'YYYY') syear FROM subject s "
					+" JOIN account a on s.id= a.id "
					+" WHERE a.id= ? "
					+" GROUP BY TO_CHAR(syear,'YYYY')";
			
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				LectureDTO dto = new LectureDTO();
				dto.setSyear(Integer.parseInt(rs.getString("syear")));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return list;
	}
	
	
	//해당 학기 수강내역 - 교수, 조교
	public List<LectureDTO> registerHistory_pro(String id, String syear, String semester) throws SQLException{
		PreparedStatement pstmt= null;
		String sql;
		ResultSet rs=null;
		List<LectureDTO> list = new ArrayList<>();
		try {
			sql= "SELECT subjectNo, subjectName, credit, TO_CHAR(syear,'YYYY') syear, semester, name FROM subject s "
					+ "JOIN account a on s.id= a.id "
					+ "WHERE a.id= ? "
					+ "AND TO_CHAR(syear, 'YYYY') = ? AND Semester = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,id);
			pstmt.setString(2,syear);
			pstmt.setString(3,semester);
			
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				LectureDTO dto= new LectureDTO();
				dto.setSubjectNo(rs.getString("subjectNo"));
				dto.setSubjectName(rs.getString("subjectName"));
				dto.setCredit(Integer.parseInt(rs.getString("credit")));
				dto.setSyear(Integer.parseInt(rs.getString("syear")));
				dto.setSemester(Integer.parseInt(rs.getString("semester")));
				dto.setProfessorname(rs.getString("name"));
				
				list.add(dto);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
		
	}	
	
		
	//전체학기 목록 불러오기 - 관리자
	public List<LectureDTO> attendHistory_admin() throws SQLException{
		List<LectureDTO> list = new ArrayList<>();
		PreparedStatement pstmt =null;
		ResultSet rs= null;
		String sql;
		try {
			sql= "SELECT TO_CHAR(syear,'YYYY')syear FROM Subject "
					+ " GROUP BY TO_CHAR(syear,'YYYY') "
					+ " ORDER BY syear";
			
			pstmt=conn.prepareStatement(sql);
			
			rs=pstmt.executeQuery();
			
			while(rs.next()) {
				LectureDTO dto = new LectureDTO();
				dto.setSyear(Integer.parseInt(rs.getString("syear")));
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return list;
	}
	
	//해당학기 전체 수강내역 - 관리자
	public List<LectureDTO> registerHistory_admin(String syear, String semester) throws SQLException{
		PreparedStatement pstmt= null;
		String sql;
		ResultSet rs=null;
		List<LectureDTO> list = new ArrayList<>();
		try {
			sql= "SELECT subjectNo, subjectName, credit, TO_CHAR(syear,'YYYY') syear, semester, name FROM subject s "
					+ " JOIN account a ON a.id = s.id "
					+ " WHERE TO_CHAR(syear, 'YYYY') = ? AND Semester = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,syear);
			pstmt.setString(2,semester);
			
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				LectureDTO dto= new LectureDTO();
				dto.setSubjectNo(rs.getString("subjectNo"));
				dto.setSubjectName(rs.getString("subjectName"));
				dto.setCredit(Integer.parseInt(rs.getString("credit")));
				dto.setSyear(Integer.parseInt(rs.getString("syear")));
				dto.setSemester(Integer.parseInt(rs.getString("semester")));
				dto.setProfessorname(rs.getString("name"));
				
				list.add(dto);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
		
	}
	
	
		
	
	//강의정보 불러오기
	public LectureDTO readSubject(String subjectNo) throws SQLException{
		
		PreparedStatement pstmt= null;
		String sql;
		ResultSet rs=null;
		LectureDTO dto = new LectureDTO();
		try {
			sql= "SELECT s.id, name, subjectname, credit, TO_CHAR(syear,'YYYY')syear, semester FROM subject s "
					+ " JOIN account a ON a.id=s.id "
					+ " WHERE subjectNo= ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, subjectNo);
			rs= pstmt.executeQuery();
			if(rs.next()) {
				dto.setProfessorname(rs.getString("name"));
				dto.setSubjectName(rs.getString("subjectname"));
				dto.setCredit(Integer.parseInt(rs.getString("credit")));
				dto.setSemester(Integer.parseInt(rs.getString("semester")));
				dto.setSyear(Integer.parseInt(rs.getString("syear")));
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return dto; 
	}
	
	//강의 목록
	public List<LectureDTO> readLecture(String subjectNo) throws SQLException{
		List<LectureDTO> list =new ArrayList<>();
		PreparedStatement pstmt= null;
		String sql;
		ResultSet rs=null;
		
		try {
			sql="SELECT l.bbsNum, subjectNo, title, content, week, part, savefilename, "
					+ " TO_CHAR(reg_date,'YYYY-MM-DD') reg_date, "
					+ " TO_CHAR(start_date,'YYYY-MM-DD') start_date, "
					+ " TO_CHAR(end_date,'YYYY-MM-DD')end_date "
					+ " FROM LECTURE_BBS l "
					+ " LEFT OUTER JOIN LECTURE_BBS_FILE f on l.bbsnum=f.bbsnum "
					+ " WHERE subjectNo=? "
					+ " ORDER BY week,part ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, subjectNo);
			
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				LectureDTO dto = new LectureDTO();
				dto.setBbsNum(rs.getString("bbsNum"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setWeek(rs.getString("week"));
				dto.setPart(rs.getString("part"));
				dto.setSavefilename(rs.getString("savefilename"));
				dto.setReg_date(rs.getString("reg_date"));
				dto.setStart_date(rs.getString("start_date"));
				dto.setEnd_date(rs.getString("end_date"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
	}
	
	//이번주 강의 목록
	public List<LectureDTO> thisweekLecture(String subjectNo) throws SQLException{
		List<LectureDTO> list =new ArrayList<>();
		PreparedStatement pstmt= null;
		String sql;
		ResultSet rs=null;
		
		try {
			sql="SELECT l.bbsNum, subjectNo, title, content, week, part, savefilename, "
					+ " TO_CHAR(reg_date,'YYYY-MM-DD') reg_date, "
					+ " TO_CHAR(start_date,'YYYY-MM-DD') start_date, "
					+ " TO_CHAR(end_date,'YYYY-MM-DD')end_date "
					+ " FROM LECTURE_BBS l "
					+ " LEFT OUTER JOIN LECTURE_BBS_FILE f on l.bbsnum=f.bbsnum "
					+ " WHERE subjectNo=? AND SYSDATE-start_date < 7 "
					+ " ORDER BY week,part,reg_date ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, subjectNo);
			
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				LectureDTO dto = new LectureDTO();
				dto.setBbsNum(rs.getString("bbsNum"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setReg_date(rs.getString("reg_date"));
				dto.setStart_date(rs.getString("start_date"));
				dto.setEnd_date(rs.getString("end_date"));
				dto.setWeek(rs.getString("week"));
				dto.setPart(rs.getString("part"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
	}
	
	//강의 하나 불러오기
	public LectureDTO readContent(String bbsNum) throws SQLException{
		PreparedStatement pstmt= null;
		String sql;
		ResultSet rs=null;
		LectureDTO dto =new LectureDTO();
		try {
			sql="SELECT l.bbsNum, subjectNo, title, content, week, part, savefilename,"
					+ " TO_CHAR(reg_date,'YYYY-MM-DD') reg_date, "
					+ " TO_CHAR(start_date,'YYYY-MM-DD') start_date, "
					+ " TO_CHAR(end_date,'YYYY-MM-DD')end_date "
					+ " FROM LECTURE_BBS l "
					+ " LEFT OUTER JOIN LECTURE_BBS_FILE f on l.bbsnum=f.bbsnum "
					+ " WHERE l.bbsNum=? ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, bbsNum);
			rs= pstmt.executeQuery();
			
			
			if(rs.next()) {
				dto.setBbsNum(bbsNum);
				dto.setSubjectNo(rs.getString("subjectNo"));
				dto.setContent(rs.getString("content"));
				dto.setTitle(rs.getString("title"));
				dto.setWeek(rs.getString("week"));
				dto.setPart(rs.getString("part"));
				dto.setSavefilename(rs.getString("savefilename"));
				dto.setReg_date(rs.getString("reg_date"));
				dto.setStart_date(rs.getString("start_date"));
				dto.setEnd_date(rs.getString("end_date"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
				if(rs!=null) {
					try {
						rs.close();
					} catch (Exception e2) {
					}
				}
				
				if(pstmt!=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
			}
		
		return dto;
	}
	
	//강의 등록하기
	public void insertLecture(LectureDTO dto) throws SQLException{
		PreparedStatement pstmt= null;
		String sql;
		try {
			sql = "INSERT INTO LECTURE_BBS (bbsNum, subjectNo, title, content, reg_date, start_date, end_date, week, part, hitcount) VALUES (LECTURE_BBS_seq.NEXTVAL, "
					+ " ?, ? , ? , SYSDATE, ?, ?, ?, ?, 0)";
			
			pstmt= conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSubjectNo());
			pstmt.setString(2, dto.getTitle());
			pstmt.setString(3, dto.getContent());
			pstmt.setString(4, dto.getStart_date());
			pstmt.setString(5, dto.getEnd_date());
			pstmt.setString(6, dto.getWeek());
			pstmt.setString(7, dto.getPart());
			
			pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
	}
	
	//강의 수정하기
		public void updateLecture(LectureDTO dto) throws SQLException{
			PreparedStatement pstmt= null;
			String sql;
			try {
				sql = "UPDATE LECTURE_BBS SET title = ?, content = ?, reg_date = SYSDATE, start_date = TO_DATE(?,'yyyy-mm-dd'), end_date = TO_DATE(?,'yyyy-mm-dd'), week= ? , part = ? WHERE bbsNum=?";
				
				pstmt= conn.prepareStatement(sql);
				pstmt.setString(1, dto.getTitle());
				pstmt.setString(2, dto.getContent());
				pstmt.setString(3, dto.getStart_date());
				pstmt.setString(4, dto.getEnd_date());
				pstmt.setString(5, dto.getWeek());
				pstmt.setString(6, dto.getPart());
				
				pstmt.setString(7, dto.getBbsNum());
				pstmt.executeUpdate();
				
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(pstmt!=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
			}
		}
	//강의 삭제하기
		public void deleteLecture(String bbsNum) throws SQLException{
			PreparedStatement pstmt= null;
			String sql;
			try {
				sql= "DELETE FROM LECTURE_BBS WHERE bbsNum=?";
				
				pstmt= conn.prepareStatement(sql);
				pstmt.setString(1, bbsNum);
				pstmt.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(pstmt!=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
			}
		}
}



