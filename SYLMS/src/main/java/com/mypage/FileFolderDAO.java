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
			sql = " SELECT NVL (COUNT(*), 0)   "
					+ " FROM assignmentUploadFile au "
					+ " JOIN assignmentSubmit ass "
					+ " ON au.as_submitNo = ass.as_submitNo "
					+ " JOIN assignment am "
					+ " ON ass.asNo  = am.asNo "
					+ " JOIN subject s "
					+ " ON am.subjectNo = s.subjectNo "
					+ " JOIN registerSubject r "
					+ " ON r.subjectNo = s.subjectNo "
					+ " WHERE r.studentCode = ?  ";

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
					+ "	 FROM assignmentUploadFile au "
					+ "	 JOIN assignmentSubmit ass "
					+ "	 ON au.as_submitNo = ass.as_submitNo "
					+ "	 JOIN assignment am "
					+ "	 ON ass.asNo  = am.asNo "
					+ "	 JOIN subject s "
					+ "	 ON am.subjectNo = s.subjectNo "
					+ "	 JOIN registerSubject r "
					+ "	 ON r.subjectNo = s.subjectNo "
					+ "	 WHERE r.studentCode = ? AND TO_CHAR(submit_date,'YYYY') = ? ";
			
			
				if ( keyword != null ) {
					sql += " AND INSTR(o_name, ?) >= 1 ";
	
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
			sql = " SELECT  au.o_name, s.subjectName, TO_CHAR(submit_date, 'YYYY-MM-DD') submit_date, fileNum "
					+ " FROM assignmentUploadFile au "
					+ " JOIN assignmentSubmit ass "
					+ " ON au.as_submitNo = ass.as_submitNo "
					+ " JOIN assignment am  "
					+ " ON ass.asNo  = am.asNo "
					+ " JOIN subject s "
					+ " ON am.subjectNo = s.subjectNo "
					+ " JOIN registerSubject r "
					+ " ON r.subjectNo = s.subjectNo "
					+ " WHERE r.studentCode =  ? "
					+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY "; 

			

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, id);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);

			rs = pstmt.executeQuery();

			while (rs.next()) {
			FileFolderDTO dto = new FileFolderDTO();

		  dto.setFileNum(rs.getString("fileNum"));	
		  dto.setSubjectName(rs.getString("subjectName"));
		  dto.setOriginName(rs.getString("o_name"));
		  dto.setSubmitDate(rs.getString("submit_date"));

			
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
			sql = "  SELECT  au.o_name, s.subjectName, TO_CHAR(submit_date, 'YYYY-MM-DD') submit_date, fileNum"
					+ "	FROM assignmentUploadFile au "
					+ "	JOIN assignmentSubmit ass "
					+ "	ON au.as_submitNo = ass.as_submitNo "
					+ "	JOIN assignment am  "
					+ " ON ass.asNo  = am.asNo "
					+ "	JOIN subject s "
					+ "	ON am.subjectNo = s.subjectNo "
					+ "	JOIN registerSubject r "
					+ "	ON r.subjectNo = s.subjectNo "
					+ "	WHERE r.studentCode =  ?  AND TO_CHAR(submit_date,'YYYY') = ?  ";
				
					
					if ( keyword != null ) {
						sql += " AND INSTR(o_name, ?) >= 1 ";
		
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

		 	  dto.setFileNum(rs.getString("fileNum"));	
			  dto.setSubjectName(rs.getString("subjectName"));
			  dto.setOriginName(rs.getString("o_name"));
			  dto.setSubmitDate(rs.getString("submit_date"));
			
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
	
	public FileFolderDTO filedownload(String fileNum) {
		FileFolderDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			
			sql = " SELECT fileNum, O_NAME, S_NAME FROM assignmentUploadFile WHERE fileNum = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, fileNum);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new FileFolderDTO();
				
				dto.setFileNum(rs.getString("fileNum"));
				dto.setOriginName(rs.getString("O_NAME"));
				dto.setSaveName(rs.getString("S_NAME"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
		return dto;

	}
	
	
}
