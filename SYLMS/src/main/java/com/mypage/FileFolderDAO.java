package com.mypage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class FileFolderDAO {

	private Connection conn = DBConn.getConnection();
	
	//데이터 카운터 
	public int dataCount(String id ) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {  
			sql = " SELECT NVL (COUNT(*), 0)  "
					+ "FROM registerSubject r "
					+ "left outer JOIN subject s "
					+ "ON s.subjectNo = r.subjectNo "
					+ "JOIN assignment a "
					+ "ON s.subjectNo  = a.subjectNo "
					+ "JOIN assignmentSubmit am "
					+ "ON am.assignmentNum  = a.assignmentNum "
					+ "JOIN assignmentUploadFile au "
					+ "ON am.assignmentNum  = au.assignmentNum "
					+ "WHERE r.studentCode = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, id);
		
			

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
	
	//데이터 카운터 - 키워드 
	public int dataCount(String year, String id, String keyword ) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {  
			sql = " SELECT NVL (COUNT(*), 0)  "
					+ "FROM registerSubject r "
					+ "left outer JOIN subject s "
					+ "ON s.subjectNo = r.subjectNo "
					+ "JOIN assignment a "
					+ "ON s.subjectNo  = a.subjectNo "
					+ "JOIN assignmentSubmit am "
					+ "ON am.assignmentNum  = a.assignmentNum "
					+ "JOIN assignmentUploadFile au "
					+ "ON am.assignmentNum  = au.assignmentNum "
					+ "WHERE r.studentCode = ? AND TO_CHAR(submitDate,'YYYY') = ?  ";
			
			
				if ( keyword != null ) {
					sql += " AND INSTR(s_name, ?) >= 1 ";
	
				} 

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, id);
			pstmt.setString(2, year);
			pstmt.setString(3, keyword);
			

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
	
	//리스트 
	List<FileFolderDTO> listfile(int offset, int size, String id) {
		List<FileFolderDTO> plist = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = " SELECT subjectName, s_name, TO_CHAR(submitDate,'YYYY-MM-DD') submitDate, s.subjectNo "
					+ " FROM registerSubject r "
					+ " left outer JOIN subject s  "
					+ " ON s.subjectNo = r.subjectNo "
					+ " JOIN assignment a "
					+ " ON s.subjectNo  = a.subjectNo "
					+ " JOIN assignmentSubmit am "
					+ " ON am.assignmentNum  = a.assignmentNum "
					+ " JOIN assignmentUploadFile au "
					+ " ON am.assignmentNum  = au.assignmentNum "
					+ " WHERE r.studentCode = ? "
					+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY "; 

			

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, id);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);

			rs = pstmt.executeQuery();

			while (rs.next()) {
			FileFolderDTO dto = new FileFolderDTO();

		  dto.setSubjectName(rs.getString("subjectName"));
		  dto.setFname(rs.getString("s_name"));
		  dto.setSubmitDate(rs.getString("submitDate"));
		  dto.setSubjectNo(rs.getString("subjectNo"));
			
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
	
	//리스트 - 키워드 
	
	List<FileFolderDTO> listfile(int offset, int size,  String year ,  String id, String keyword) {
		List<FileFolderDTO> plist = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = " SELECT subjectName, s_name, TO_CHAR(submitDate,'YYYY-MM-DD') submitDate, s.subjectNo "
					+ " FROM registerSubject r "
					+ " left outer JOIN subject s  "
					+ " ON s.subjectNo = r.subjectNo "
					+ " JOIN assignment a "
					+ " ON s.subjectNo  = a.subjectNo "
					+ " JOIN assignmentSubmit am "
					+ " ON am.assignmentNum  = a.assignmentNum "
					+ " JOIN assignmentUploadFile au "
					+ " ON am.assignmentNum  = au.assignmentNum "
					+ " WHERE r.studentCode = ? AND TO_CHAR(submitDate,'YYYY') = ? ";
					
					if ( keyword != null ) {
						sql += " >= AND INSTR(s_name, ?) 1 ";
		
					} 
					
					sql += " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY "; 

			

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, id);
			pstmt.setString(2, year);
			pstmt.setString(3, keyword);
			pstmt.setInt(4, offset);
			pstmt.setInt(5, size);

			rs = pstmt.executeQuery();

			while (rs.next()) {
			FileFolderDTO dto = new FileFolderDTO();

		  dto.setSubjectName(rs.getString("subjectName"));
		  dto.setFname(rs.getString("s_name"));
		  dto.setSubmitDate(rs.getString("submitDate"));
		  dto.setSubjectNo(rs.getString("subjectNo"));
			
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
