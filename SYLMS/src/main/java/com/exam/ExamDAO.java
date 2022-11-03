package com.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lecture.LectureDTO;
import com.util.DBConn;

public class ExamDAO {
	private Connection conn = DBConn.getConnection();

	// 시험 성적입력
	public void examInsert(LectureDTO dto, String subjectNo, String stuendtCode) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		try {
			sql = "INSERT INTO exam (examCode, score, examType, s_time, e_time, gradeCode) "
					+ " SELECT exam_seq.NEXTVAL, score, examType, s_time, e_time, gradeCode FROM grades g "
					+ " WHERE a.subjectNo = ? AND stuendtCode = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subjectNo);
			pstmt.setString(2, stuendtCode);
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

	// 시험 성적확인
	public LectureDTO examCheck(String gradeCode) {
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;
		LectureDTO dto = new LectureDTO();
		try {
			sql = "SELECT examCode, score, examType " + " FROM exam " + " WHERE gradeCode = ? "
					+ " ORDER BY attendNo DESC " + " FETCH FIRST 1 ROWS ONLY ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, gradeCode);
			rs = pstmt.executeQuery();

			if (rs.next()) {

				//dto.setExamCode(rs.getString("examCode"));
				//dto.setScore(Integer.parseInt(rs.getString("score")));
				//dto.setExamType(rs.getString("examType"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}
}
