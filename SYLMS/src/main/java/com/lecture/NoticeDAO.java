package com.lecture;

import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

public class NoticeDAO {
	private Connection conn = DBConn.getConnection();
	
	// 공지작성
	public void insertNotice(NoticeDTO dto, String mode) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO subject_bbs(articleNo, subjectNo, bbsCode, "
					+ " ID, title, content, reg_date, hitCount) "
					+ " VALUES (subject_bbs.NEXTVAL, ?, 00001, ?, ?, ?, SYSDATE, 0)";
			pstmt = conn.prepareStatement(sql);
			
			//pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getTitle());
			pstmt.setString(4, dto.getContent());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}
		
	}

}
