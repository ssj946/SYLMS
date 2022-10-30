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
				sql = " INSERT INTO subject_bbs_file(fileNo, saveFilename, originalFilename, articleNo, bbsCode, subjectNo, fileSize) "
						+ " VALUES (subject_bbs_file_seq.NEXTVAL, ?, ? , ?, '00001', ?, ? )";
				pstmt = conn.prepareStatement(sql);
				
				for ( int i = 0; i< dto.getSaveFiles().length; i++) {
					pstmt.setString(1, dto.getSaveFiles()[i]);
					pstmt.setString(2, dto.getOriginalFiles()[i]);
					pstmt.setString(3, dto.getArticleNo());
					pstmt.setString(4, dto.getSubjectNo());
					pstmt.setString(5, dto.getFileSize());
					
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
			sql = "SELECT NVL(COUNT(*), 0) FROM subject_bbs WHERE subjectNo = ? ";
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
					+ " WHERE subjectNo = ? ";
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
	
	/**
	 * 
	 * @param subjectNo
	 * @param offset	건너뛸개수 ?개수를 건너뛰고 
	 * @param size		가져올개수 몇개를 가져오겠읍니다
	 * @return			게시글리스트
	 */
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
			sb.append(" WHERE subjectNo = ?  ");
			sb.append(" ORDER BY reg_date DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

			
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

	
	/**
	 * 
	 * @param subjectNo
	 * @param offset
	 * @param size
	 * @param condition
	 * @param keyword
	 * @return
	 */
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
/*	
	// 이전글
	public NoticeDTO preReadNotice(long num, String condition, String keyword) {
		NoticeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			if (keyword != null && keyword.length() != 0) {
				sb.append(" SELECT num, subject ");
				sb.append(" FROM notice n ");
				sb.append(" JOIN member1 m ON n.userId = m.userId ");
				sb.append(" WHERE ( num > ? ) ");
				if (condition.equals("all")) {
					sb.append("   AND ( INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ) ");
				} else if (condition.equals("reg_date")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sb.append("   AND ( TO_CHAR(reg_date, 'YYYYMMDD') = ? ) ");
				} else {
					sb.append("   AND ( INSTR(" + condition + ", ?) >= 1 ) ");
				}
				sb.append(" ORDER BY num ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, num);
				pstmt.setString(2, keyword);
				if (condition.equals("all")) {
					pstmt.setString(3, keyword);
				}
			} else {
				sb.append(" SELECT num, subject FROM notice ");
				sb.append(" WHERE num > ? ");
				sb.append(" ORDER BY num ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, num);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new NoticeDTO();
				
				dto.setNum(rs.getLong("num"));
				dto.setSubject(rs.getString("subject"));
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
	public NoticeDTO nextReadNotice(long num, String condition, String keyword) {
		NoticeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			if (keyword != null && keyword.length() != 0) {
				sb.append(" SELECT num, subject ");
				sb.append(" FROM notice n ");
				sb.append(" JOIN member1 m ON n.userId = m.userId ");
				sb.append(" WHERE ( num < ? ) ");
				if (condition.equals("all")) {
					sb.append("   AND ( INSTR(subject, ?) >= 1 OR INSTR(content, ?) >= 1 ) ");
				} else if (condition.equals("reg_date")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sb.append("   AND ( TO_CHAR(reg_date, 'YYYYMMDD') = ? ) ");
				} else {
					sb.append("   AND ( INSTR(" + condition + ", ?) >= 1 ) ");
				}
				sb.append(" ORDER BY num DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, num);
				pstmt.setString(2, keyword);
				if (condition.equals("all")) {
					pstmt.setString(3, keyword);
				}
			} else {
				sb.append(" SELECT num, subject FROM notice ");
				sb.append(" WHERE num < ? ");
				sb.append(" ORDER BY num DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setLong(1, num);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new NoticeDTO();
				
				dto.setNum(rs.getLong("num"));
				dto.setSubject(rs.getString("subject"));
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
*/
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
/*
	public void updateNotice(NoticeDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE notice SET notice=?, subject=?, content=? "
					+ " WHERE num=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getNotice());
			pstmt.setString(2, dto.getSubject());
			pstmt.setString(3, dto.getContent());
			pstmt.setLong(4, dto.getNum());
			
			pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;

			if (dto.getSaveFiles() != null) {
				sql = "INSERT INTO noticeFile(fileNum, num, saveFilename, originalFilename) VALUES (noticeFile_seq.NEXTVAL, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				for (int i = 0; i < dto.getSaveFiles().length; i++) {
					pstmt.setLong(1, dto.getNum());
					pstmt.setString(2, dto.getSaveFiles()[i]);
					pstmt.setString(3, dto.getOriginalFiles()[i]);
					
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
 */


}
