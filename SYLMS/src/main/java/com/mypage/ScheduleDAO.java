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
	
	public List<ScheduleDTO> listSchedule(String studentcode) throws SQLException {
 
		PreparedStatement pstmt= null;
		String sql;
		ResultSet rs=null;
		List<ScheduleDTO> list = new ArrayList<>();
		
		
		try {
			
			sql = " select schedule, dayWeek, lecturePlace, ac.name , sc.subjectNo, s.subjectName "
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
			
			pstmt.setString(1,studentcode);
			      
			rs= pstmt.executeQuery();
			
			
			while (rs.next()) {
				ScheduleDTO dto = new ScheduleDTO();
				
				dto.setSchedule(rs.getString("schedule"));
				dto.setDayWeek(rs.getString("dayWeek"));
				dto.setLecturePlace("lecturePlace");
				dto.setProfessorName("name");
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
	
	
	
}
