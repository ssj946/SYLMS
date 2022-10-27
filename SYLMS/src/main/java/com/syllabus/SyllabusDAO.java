package com.syllabus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import com.util.DBConn;

public class SyllabusDAO {
	private Connection conn = DBConn.getConnection();

	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT COUNT(*) FROM curriculum";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}

		}

		return result;
	}
	
	public List<SyllabusDTO> listBoard(int offset, int size) {
		List<SyllabusDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = " SELECT subjectNo,lecturePlace, semester, assignmentRate, middleRate,finalRate, "
					+ " TO_CHAR(openDate, 'YYYY-MM-DD') openDate " 
					+ " FROM curriculum b "		
					+ " ORDER BY subjectNo DESC "
					+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, offset);
			
			pstmt.setInt(2, size);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				SyllabusDTO dto = new SyllabusDTO();
				dto.setSubjectNo(rs.getString("SubjectNo"));
				// dto.setSubjectName(rs.getString("subjectName"));
				dto.setOpenDate(rs.getString("openDate"));
				dto.setSemester(rs.getInt("semester"));
				dto.setLecturePlace(rs.getString("lecturePlace"));
				//dto.setLecturePlace(rs.getString("lecturePlace"));
				dto.setAssignmentRate(rs.getInt("assignmentRate"));
				dto.setMiddleRate(rs.getInt("middleRate"));
				dto.setFinalRate(rs.getInt("finalRate"));
				
				list.add(dto);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

		return list;
	}
}
