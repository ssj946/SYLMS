package com.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lecture.LectureDTO;
import com.messege.MessegeDTO;
import com.util.DBConn;

public class ExamDAO {
	private Connection conn = DBConn.getConnection();
	
	//해당 과목의 학생학번과 성적번호 리스트 
		public List<ExamDTO> codeList (String subjectNo){
			List<ExamDTO> list = new ArrayList<>();			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				sql = " SELECT gradeCode, studentCode FROM grades "
						+ " WHERE subjectNo = ? ";

				pstmt = conn.prepareStatement(sql);				
				pstmt.setString(1, subjectNo);
				rs = pstmt.executeQuery();

				while (rs.next()) {
					ExamDTO dto = new ExamDTO();

					dto.setGradeCode(rs.getString("gradeCode"));
					dto.setStudentCode(rs.getString("studentCode"));
					
					list.add(dto);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if(rs != null) {
					try {
						rs.close();
					} catch (Exception e2) {
					}
				}
				
				if(pstmt != null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
			}
			
			return list;
			
		}

	// 시험 성적입력
	public void examInsert(ExamDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		try {
			sql = "INSERT INTO exam (examCode, score, examType, gradeCode) "
					+ " VALUES (exam_seq.NEXTVAL, ?, ?, ?) ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, dto.getScore());
			pstmt.setString(2, dto.getExamType());
			pstmt.setString(3, dto.getGradeCode());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
	}
	
}
