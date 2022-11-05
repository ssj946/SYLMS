package com.AllNotification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class AllNotificationDAO {
	private Connection conn = DBConn.getConnection();
	
	//관리자가 보낸 공지리스트 가져오기 (지금으로부터 한달전까지)
	public List<AllNotificationDTO> listAlert() {
		List<AllNotificationDTO> list = new ArrayList<AllNotificationDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = " SELECT noticeCode, content, TO_CHAR(reg_date, 'YYYY-MM-DD') AS reg_date, url "
					+ " FROM notice1 "
					+ " WHERE reg_date >= TO_CHAR(ADD_MONTHS(SYSDATE, -1), 'YYYY-MM-DD') "
					+ " ORDER BY noticeCode DESC ";
			
			pstmt = conn.prepareStatement(sql);
			
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				AllNotificationDTO dto = new AllNotificationDTO();
				
				dto.setNoticeCode(rs.getString("noticeCode"));
				dto.setContent(rs.getString("content"));
				dto.setReg_date(rs.getString("reg_date"));
				dto.setUrl(rs.getString("url"));
				
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
	
	//읽지 않은 알림 갯수만 표시
	public int alertCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT COUNT(*) FROM notice1 "
					+ " WHERE readDate IS NULL ";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
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

		return result;
	}
	
	//알림버튼 누르면 읽은날짜 넣기
	public void readDate() throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE notice1 SET readDate = SYSDATE ";
			pstmt = conn.prepareStatement(sql);
			
			//pstmt.setString(1, noticeCode);
			
			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		}
	}
}
