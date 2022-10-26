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
	
	//해당 학기 수강목록 불러오기
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
	
	//모든 학기 수강내역
	public List<LectureDTO> registerHistory(String studentcode) throws SQLException{
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
					+ " WHERE STUDENTcode = ? "
					+ " ORDER BY syear, semester ";

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
					+ " TO_CHAR(end_date,'YYYY-MM-DD')end_date "
					+ " FROM LECTURE_BBS l "
					+ " LEFT OUTER JOIN LECTURE_BBS_FILE f on l.bbsnum=f.bbsnum "
					+ " WHERE subjectNo=? AND SYSDATE-reg_date < 7 "
					+ " ORDER BY week,part ";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, subjectNo);
			
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				LectureDTO dto = new LectureDTO();
				dto.setBbsNum(rs.getString("bbsNum"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setReg_date(rs.getString("reg_date"));
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

}


