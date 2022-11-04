package com.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class NoticeDAO {
	private Connection conn = DBConn.getConnection();
	
	
	// 과목정보 불러오기
	public NoticeDTO readSubject(String subjectNo) throws SQLException{
		
		PreparedStatement pstmt= null;
		String sql;
		ResultSet rs=null;
		NoticeDTO dto = new NoticeDTO();
		try {
			sql= "SELECT s.id, name, subjectname, credit, TO_CHAR(syear,'YYYY')syear, semester FROM subject s "
					+ " JOIN account a ON a.id=s.id "
					+ " WHERE subjectNo= ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, subjectNo);
			rs= pstmt.executeQuery();
			if(rs.next()) {
				dto.setProfessorname(rs.getString("name"));
				dto.setSubjectName(rs.getString("subjectname"));
				dto.setCredit(Integer.parseInt(rs.getString("credit")));
				dto.setSemester(Integer.parseInt(rs.getString("semester")));
				dto.setSyear(Integer.parseInt(rs.getString("syear")));
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return dto; 
	}
	
	
	// 공지작성
	public void insertNotice(NoticeDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql, seq;
		
		try {
			sql = "SELECT subject_bbs_seq.NEXTVAL FROM dual";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			seq = null;
			if (rs.next()) {
				seq = rs.getString(1);
			}
			dto.setArticleNo(seq);
			
			rs.close();
			pstmt.close();
			rs = null;
			pstmt = null;
			
			sql = "INSERT INTO subject_bbs(articleNo, bbsCode, subjectNo,  "
					+ " ID, title, content, reg_date, hitCount) "
					+ " VALUES ( ?,'00001', ?, ?, ?, ?, SYSDATE, 0)";
			pstmt = conn.prepareStatement(sql);
			
			
			pstmt.setString(1, dto.getArticleNo());
			pstmt.setString(2, dto.getSubjectNo());
			pstmt.setString(3, dto.getUserId());
			pstmt.setString(4, dto.getTitle());
			pstmt.setString(5, dto.getContent());
			
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			if (dto.getSaveFiles() != null) {
				sql = " INSERT INTO subject_bbs_file(fileNo, saveFilename, originalFilename, articleNo, bbsCode, subjectNo) "
						+ " VALUES (subject_bbs_file_seq.NEXTVAL, ?, ? , ?, '00001', ? )";
				pstmt = conn.prepareStatement(sql);
				
				for ( int i = 0; i< dto.getSaveFiles().length; i++) {
					pstmt.setString(1, dto.getSaveFiles()[i]);
					pstmt.setString(2, dto.getOriginalFiles()[i]);
					pstmt.setString(3, dto.getArticleNo());
					pstmt.setString(4, dto.getSubjectNo());
					
					pstmt.executeUpdate();
				}
			}
			
			
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
	
	// 공지개수
	public int dataCount(String subjectNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM subject_bbs WHERE subjectNo = ? AND bbsCode = '00001' ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subjectNo);
			
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

	// 검색에서의 데이터 개수
	public int dataCount(String subjectNo, String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM subject_bbs s "
					+ " JOIN account a ON s.ID=a.ID "
					+ " WHERE subjectNo = ? AND bbsCode = '00001' ";
			if (condition.equals("all")) {
				sql += " AND INSTR(title, ?) >= 1 OR INSTR(content, ?) >= 1 ";
			} else if (condition.equals("reg_date")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += " AND TO_CHAR(reg_date, 'YYYYMMDD') = ? ";
			} else {
				sql += " AND INSTR(" + condition + ", ?) >= 1 ";
			}
			

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, subjectNo);
			pstmt.setString(2, keyword);
			if (condition.equals("all")) {
				pstmt.setString(3, keyword);
			}

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
	
	
	// 공지 리스트
	public List<NoticeDTO> listNotice(String subjectNo, int offset, int size) {
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			
			sb.append(" SELECT articleNo, b.ID, title, hitCount, reg_date, a.name ");
			sb.append(" FROM subject_bbs b ");
			sb.append(" JOIN account a ON b.ID = a.ID ");
			sb.append(" WHERE subjectNo = ? AND bbsCode = '00001' ");
			sb.append(" ORDER BY reg_date DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			/*
			sb.append(" SELECT b.articleNo, b.ID, title, hitCount, reg_date, f.fileNo ");
			sb.append(" FROM subject_bbs b ");
			sb.append(" JOIN account a ON b.ID = a.ID ");
			sb.append(" JOIN subject_bbs_file f ON b.articleNo = f.articleNo ");
			sb.append(" WHERE b.subjectNo = ?  ");
			sb.append(" ORDER BY reg_date DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
*/
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, subjectNo);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				NoticeDTO dto = new NoticeDTO();

				dto.setArticleNo(rs.getString("articleNo"));
				dto.setUserId(rs.getString("id"));
				dto.setTitle(rs.getString("title"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setReg_date(rs.getString("reg_date"));
				dto.setName(rs.getString("name"));

				list.add(dto);
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

		return list;
	}

	
	// 검색에서 리스트
	public List<NoticeDTO> listNotice(String subjectNo, int offset, int size, String condition, String keyword) {
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT articleNo, b.ID, title, hitCount, reg_date, a.name");
			sb.append(" FROM subject_bbs b ");
			sb.append(" JOIN account a ON b.ID = a.ID ");
			sb.append(" WHERE subjectNo = ?  ");
			
			if (condition.equals("all")) {
				sb.append(" AND INSTR(title, ?) >= 1 OR INSTR(content, ?) >= 1 ");
			} else if (condition.equals("reg_date")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append(" AND TO_CHAR(reg_date, 'YYYYMMDD') = ?");
			} else {
				sb.append(" AND INSTR(" + condition + ", ?) >= 1 ");
			}
			sb.append(" ORDER BY articleNo DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			if (condition.equals("all")) {
				
				pstmt.setString(1, subjectNo);
				pstmt.setString(2, keyword);
				pstmt.setString(3, keyword);
				pstmt.setInt(4, offset);
				pstmt.setInt(5, size);
			} else {
				pstmt.setString(1, subjectNo);
				pstmt.setString(2, keyword);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, size);
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				NoticeDTO dto = new NoticeDTO();

				dto.setArticleNo(rs.getString("articleNo"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setReg_date(rs.getString("reg_date")); // yyyy-MM-dd HH:mm:ss

				list.add(dto);
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

		return list;
	}
	
	
	// 공지 목록
	public List<NoticeDTO> listNotice(String subjectNo) {
		List<NoticeDTO> list = new ArrayList<NoticeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT articleNo, b.ID, title, hitCount, reg_date ");
			sb.append(" FROM subject_bbs b ");
			sb.append(" JOIN account a ON b.ID = a.ID ");
			sb.append(" WHERE subjectNo = ? ");
			sb.append(" ORDER BY reg_date DESC ");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, subjectNo);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				NoticeDTO dto = new NoticeDTO();

				dto.setArticleNo(rs.getString("articleNo"));
				dto.setUserId(rs.getString("id"));
				dto.setTitle(rs.getString("title"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setReg_date(rs.getString("reg_date")); 

				list.add(dto);
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
		
		
		
		return list;
	}

	// 공지보기
	public NoticeDTO readNotice(String articleNo) {
		NoticeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT articleNo, bbsCode, subjectNo, a.Id, title, content, reg_date, hitcount, a.name "
					+ " FROM subject_bbs b "
					+ " JOIN account a ON b.Id=a.Id "
					+ " WHERE articleNo = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, articleNo);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new NoticeDTO();

				dto.setArticleNo(rs.getString("articleNo"));
				dto.setBbsCode(rs.getString("bbsCode"));
				dto.setSubjectNo(rs.getString("subjectNo"));
				dto.setUserId(rs.getString("id"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setReg_date(rs.getString("reg_date")); 
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setName(rs.getString("name"));

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

		return dto;
	}

	// 이전글
	public NoticeDTO preReadNotice(String subjectNo, String articleNo, String condition, String keyword) {
		NoticeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			
			if (keyword != null && keyword.length() != 0) {
				sb.append(" SELECT articleNo, title ");
				sb.append(" FROM subject_bbs b ");
				sb.append(" JOIN account a ON b.Id = a.Id ");
				sb.append(" WHERE ( articleNo > ? ) AND subjectNo = ? ");
				if (condition.equals("all")) {
					sb.append("   AND ( INSTR(title, ?) >= 1 OR INSTR(content, ?) >= 1 ) ");
				} else if (condition.equals("reg_date")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sb.append("   AND ( TO_CHAR(reg_date, 'YYYYMMDD') = ? ) ");
				} else {
					sb.append("   AND ( INSTR(" + condition + ", ?) >= 1 ) ");
				}
				sb.append(" ORDER BY articleNo ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setString(1, articleNo);
				pstmt.setString(2, subjectNo);
				pstmt.setString(3, keyword);
				if (condition.equals("all")) {
					pstmt.setString(4, keyword);
				}
			} else {
				sb.append(" SELECT articleNo, title FROM subject_bbs ");
				sb.append(" WHERE articleNo > ? AND subjectNo = ?");
				sb.append(" ORDER BY articleNo ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setString(1, articleNo);
				pstmt.setString(2, subjectNo);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new NoticeDTO();
				
				dto.setArticleNo(rs.getString("articleNo"));
				dto.setTitle(rs.getString("title"));
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

		return dto;
	}

	// 다음글
	public NoticeDTO nextReadNotice(String subjectNo, String articleNo, String condition, String keyword) {
		NoticeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			if (keyword != null && keyword.length() != 0) {
				sb.append(" SELECT articleNo, title ");
				sb.append(" FROM subject_bbs b ");
				sb.append(" JOIN account a ON b.Id = a.Id ");
				sb.append(" WHERE ( articleNo < ? ) AND subjectNo = ? ");
				if (condition.equals("all")) {
					sb.append("   AND ( INSTR(title, ?) >= 1 OR INSTR(content, ?) >= 1 ) ");
				} else if (condition.equals("reg_date")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sb.append("   AND ( TO_CHAR(reg_date, 'YYYYMMDD') = ? ) ");
				} else {
					sb.append("   AND ( INSTR(" + condition + ", ?) >= 1 ) ");
				}
				sb.append(" ORDER BY articleNo DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setString(1, articleNo);
				pstmt.setString(2, subjectNo);
				pstmt.setString(3, keyword);
				if (condition.equals("all")) {
					pstmt.setString(4, keyword);
				}
			} else {
				sb.append(" SELECT articleNo, title FROM subject_bbs ");
				sb.append(" WHERE articleNo < ? AND subjectNo = ?");
				sb.append(" ORDER BY articleNo DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setString(1, articleNo);
				pstmt.setString(2, subjectNo);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new NoticeDTO();
				
				dto.setArticleNo(rs.getString("articleNo"));
				dto.setTitle(rs.getString("title"));
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

		return dto;
	}

	// 조회수
	public void updateHitCount(String articleNo, String subjectNo) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE subject_bbs SET hitCount=hitCount+1 WHERE articleNo=? AND subjectNo=? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, articleNo);
			pstmt.setString(2, subjectNo);
			
			pstmt.executeUpdate();

		} catch (SQLException e) {
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

	// 수정
	public void updateNotice(NoticeDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE subject_bbs SET title=?, content=? "
					+ " WHERE articleNo=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getArticleNo());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;

			if (dto.getSaveFiles() != null) {
				sql = " INSERT INTO subject_bbs_file(fileNo, saveFilename, originalFilename, articleNo, bbsCode, subjectNo) "
						+ " VALUES (subject_bbs_file_seq.NEXTVAL, ?, ? , ?, '00001', ? )";
				pstmt = conn.prepareStatement(sql);
				
				for ( int i = 0; i< dto.getSaveFiles().length; i++) {
					pstmt.setString(1, dto.getSaveFiles()[i]);
					pstmt.setString(2, dto.getOriginalFiles()[i]);
					pstmt.setString(3, dto.getArticleNo());
					pstmt.setString(4, dto.getSubjectNo());
					
					pstmt.executeUpdate();
				}
			}

		} catch (SQLException e) {
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
 
	// 삭제
	public void deleteNotice(String articleNo) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "DELETE FROM subject_bbs WHERE articleNo = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, articleNo);
			
			pstmt.executeUpdate();

		} catch (SQLException e) {
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

	// 파일 첨부 보기 완료
	public List<NoticeDTO> listNoticeFile(String articleNo) {
		List<NoticeDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT fileNo, saveFilename, originalFilename FROM subject_bbs_file WHERE articleNo = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, articleNo);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				NoticeDTO dto = new NoticeDTO();

				dto.setFileNo(rs.getString("fileNo"));
				//dto.setArticleNo(rs.getString("articelNo"));
				dto.setSaveFilename(rs.getString("saveFilename"));
				dto.setOriginalFilename(rs.getString("originalFilename"));
				
				list.add(dto);
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

		return list;
	}
	
	// 파일첨부 읽어오기 완료
	public NoticeDTO readNoticeFile(String fileNo) {
		NoticeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT fileNo, saveFilename, originalFilename FROM subject_bbs_file WHERE fileNo = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, fileNo);
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new NoticeDTO();

				dto.setFileNo(rs.getString("fileNo"));
				dto.setSaveFilename(rs.getString("saveFilename"));
				dto.setOriginalFilename(rs.getString("originalFilename"));
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

		return dto;
	}
	
	// 파일만 수정부분에서 삭제
	public void deleteNoticeFile(String mode, String articleNo) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			/*
			if (mode.equals("all")) {
				sql = "DELETE FROM subject_bbs_file WHERE articleNo = ?";
			} else {
				sql = "DELETE FROM subject_bbs_file WHERE fileNo = ?";
			}*/
			
			sql = "DELETE FROM subject_bbs_file WHERE fileNo = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, articleNo);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}

	}

}
