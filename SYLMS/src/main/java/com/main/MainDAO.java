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
}
