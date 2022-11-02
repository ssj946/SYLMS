package com.mypage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.util.DBConn;

public class ScheduleDAO {
	private Connection conn = DBConn.getConnection();
	
	//학생의 수강과목 시간표
	
	public List<ScheduleDTO> listSchedule(String id) throws SQLException {
 
		PreparedStatement pstmt= null;
		String sql;
		ResultSet rs=null;
		List<ScheduleDTO> list = new ArrayList<>();
		
		
		try {
			
			sql = " select schedule, lecturePlace, ac.name , sc.subjectNo, s.subjectName, "
					+ "  case when dayWeek = '월요일' then 1 "
					+ "       when dayWeek = '화요일' then 2 "
					+ "       when dayWeek = '수요일' then 3 "
					+ "       when dayWeek = '목요일' then 4 "
					+ "       when dayWeek = '금요일' then 5 "
					+ " end AS week "
					+ " from SCHEDULE sc "
					+ " JOIN SUBJECT s "
					+ " on s.subjectNo = sc.subjectNo "
					+ " JOIN registerSubject r "
					+ " on r.subjectNo = s.subjectNo "
					+ " JOIN professor p "
					+ " on p.id = s.id "
					+ " JOIN account ac "
					+ " on ac.id = p.id "
					+ " WHERE studentCode =  ? "
					+ " AND TO_CHAR(sYear,'YYYY') = TO_CHAR(SYSDATE,'YYYY')  "
					+ " AND sYear < SYSDATE "
					+ " AND SYSDATE<sYear+(interval '4' month) "; 
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,id);
			      
			rs= pstmt.executeQuery();
			
			
			while (rs.next()) {
				ScheduleDTO dto = new ScheduleDTO();
				
				dto.setSchedule(rs.getString("schedule"));
				dto.setDayWeek(rs.getString("week"));
				
				dto.setLecturePlace(rs.getString("lecturePlace"));
				dto.setProfessorName(rs.getString("name"));
				dto.setSubjectNo(rs.getString("subjectNo"));
				dto.setSubjectName(rs.getString("subjectName"));
				
				
				list.add(dto);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		
		
		return list;
		
	}
	


	
	
	//교수님의 강의 시간표 
	
	public List<ScheduleDTO> prolistSchedule(String id) throws SQLException {
		 
		PreparedStatement pstmt= null;
		String sql;
		ResultSet rs=null;
		List<ScheduleDTO> plist = new ArrayList<>();
		
		
		try {
			
			sql = " select lecturePlace, s.SUBJECTNAME , s.subjectNo,  schedule, "
					+ "  case when dayWeek = '월요일' then 1 "
					+ "	  when dayWeek = '화요일' then 2  "
					+ "	  when dayWeek = '수요일' then 3 "
					+ "	  when dayWeek = '목요일' then 4 "
					+ "	  when dayWeek = '금요일' then 5 "
					+ "	 end AS week "
					+ "from  subject s "
					+ "left outer JOIN SCHEDULE sc "
					+ "ON s.subjectNo = sc.subjectNo "
					+ "WHERE s.ID = ? "
					+ "AND TO_CHAR(sYear,'YYYY') = TO_CHAR(SYSDATE,'YYYY')  "
					+ "AND sYear < SYSDATE "
					+ "AND SYSDATE<sYear+(interval '4' month) "; 
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1,id);
			      
			rs= pstmt.executeQuery();
			
			
			while (rs.next()) {
				ScheduleDTO dto = new ScheduleDTO();
				
					dto.setLecturePlace(rs.getString("lecturePlace"));
					dto.setSubjectName(rs.getString("subjectName"));
					dto.setSubjectNo(rs.getString("subjectNo"));
					dto.setSchedule(rs.getString("schedule"));
				    dto.setDayWeek(rs.getString("week"));
				
				
				plist.add(dto);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		
		
		return plist;
		
	}
	
}
