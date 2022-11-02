package com.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class MainDAO {
	private Connection conn = DBConn.getConnection();
	
	//해당 학기 수강목록 불러오기
		public List<MainDTO> subjectName(String studentcode) throws SQLException{
			PreparedStatement pstmt= null;
			String sql;
			ResultSet rs=null;
			List<MainDTO> list = new ArrayList<>();
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
					MainDTO dto= new MainDTO();
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
		
		//학생 - 할일 목록 가져오기 (기간은 현재주 일주일치, 불러올것은 과제)
		public List<MainDTO> assignmentList(String studentcode) {
			List<MainDTO> list = new ArrayList<MainDTO>();
			PreparedStatement pstmt= null;
			String sql;
			ResultSet rs=null;
			
			try {
				sql = " SELECT assignmentName, TO_DATE(endDate, 'YYYY-MM-DD') - TO_DATE(startDate, 'YYYY-MM-DD') AS dday "
						+ " FROM REGISTERSUBJECT r JOIN subject s ON s.subjectNo=r.subjectNo  "
						+ " JOIN account a ON a.id=studentcode "
						+ " JOIN assignment ag ON ag.subjectNo = s.subjectNo "
						+ " WHERE STUDENTcode = ?  AND TO_DATE(endDate, 'YYYY-MM-DD') - TO_DATE(startDate, 'YYYY-MM-DD') >= 0 ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,studentcode);
				
				rs= pstmt.executeQuery();
				
				while(rs.next()) {
					MainDTO dto= new MainDTO();
					dto.setAssignmentName(rs.getString("assignmentName"));
					dto.setDday(Integer.parseInt(rs.getString("dday")));				
					
					list.add(dto);
				}
				
			}  catch (Exception e) {
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
