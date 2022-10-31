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
	
	//관리자가 보낸 알람 가져오기
	public List<AllNotificationDTO> listAlert() {
		List<AllNotificationDTO> list = new ArrayList<AllNotificationDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = " SELECT noticeCode, content, reg_date "
					+ " FROM notice "
					+ " WHERE id = 'admin' AND TO_CHAR(reg_date,'YYYYMMDD') = TO_CHAR(SYSDATE,'YYYYMMDD') "
					+ " ORDER BY noticeCode DESC ";
			
			pstmt = conn.prepareStatement(sql);
			
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				AllNotificationDTO dto = new AllNotificationDTO();
				
				dto.setNoticeCode(rs.getString("noticeCode"));
				dto.setContent(rs.getString("content"));
				dto.setReg_date(rs.getString("reg_date"));
				
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
	
	//오늘날짜의 알림이 있으면 알림 표시
	public int alertCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT COUNT(*) FROM notice "
					+ "WHERE TO_CHAR(reg_date,'YYYYMMDD') = TO_CHAR(SYSDATE,'YYYYMMDD')";
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
}
