package com.mypage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

public class MypageDAO {
	private Connection conn = DBConn.getConnection();

	public MypageDTO readMember(String id) {
       MypageDTO dto = null;
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       StringBuilder sb = new StringBuilder();
       
       try {
		sb.append(" SELECT distinct ");
		sb.append(" a.id, a.name, a.pwd, a.tel, a.email, TO_CHAR(a.birth, 'yyyy-mm-dd') birth, a.filename ,d.departmentName  ");
		sb.append(" FROM account a ");
		sb.append(" LEFT outer JOIN professor p ");
		sb.append(" ON a.id = p.id ");
		sb.append(" LEFT outer JOIN subject s ");
		sb.append(" ON p.id = s.id ");
		sb.append(" LEFT outer JOIN department d ");
		sb.append(" ON s.departmentNum = d.departmentNum ");
		sb.append(" WHERE a.id = ? ");
		
		pstmt = conn.prepareStatement(sb.toString());
		
		pstmt.setString(1, id);
		
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			dto = new MypageDTO();
			
			dto.setUserId(rs.getString("id"));
			dto.setName(rs.getString("name"));
			dto.setPwd(rs.getString("pwd"));
			dto.setTel(rs.getString("tel"));
			dto.setEmail(rs.getString("email"));
			dto.setBirth(rs.getString("birth"));
			dto.setFileName(rs.getString("filename"));
			dto.setDepartmentName(rs.getString("departmentName"));
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
			
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
			}
		}
	}
       
       return dto;
	}

	public void updateMember(MypageDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE account SET pwd = ?, tel = ?, email = ?,  filename=?  WHERE id = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getPwd());
			pstmt.setString(2, dto.getTel());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getFileName());
			pstmt.setString(5, dto.getUserId());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
	}

}
