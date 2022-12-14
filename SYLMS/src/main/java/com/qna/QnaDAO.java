package com.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class QnaDAO {
	private Connection conn = DBConn.getConnection();

	public QnaDTO readSubject(String subjectNo) throws SQLException{
		
		PreparedStatement pstmt= null;
		String sql;
		ResultSet rs=null;
		QnaDTO dto = new QnaDTO();
		try {
			sql= "SELECT s.id, name, subjectname, credit, TO_CHAR(syear,'YYYY') syear, semester FROM subject s "
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
	

  //질문과 답변 
	public void insertQna(QnaDTO dto) throws SQLException {
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
					+ " VALUES ( ?,'00003', ?, ?, ?, ?, SYSDATE, 0)";
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
						+ " VALUES (subject_bbs_file_seq.NEXTVAL, ?, ? , ?, '00003', ? )";
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
	
	// 질문과답변 개수
	public int dataCount(String subjectNo, String id) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
	
		try {
			if(id == null || id.length() == 0) {
				sql = "SELECT NVL(COUNT(*), 0) FROM subject_bbs WHERE bbsCode = '00003' AND subjectNo = ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, subjectNo);			
			} else {
				sql = "SELECT NVL(COUNT(*), 0) FROM subject_bbs WHERE bbsCode = '00003' AND subjectNo = ? AND id = ? ";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, subjectNo);
				pstmt.setString(2, id);
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

	// 질문에 데이터 개수 
	public int dataCount(String subjectNo, String condition, String keyword, String id) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
	
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM subject_bbs s "
					+ " JOIN account a ON s.ID=a.ID "
					+ " WHERE bbsCode = '00003' AND subjectNo = ? ";
			if (condition.equals("all")) {
				sql += " AND INSTR(title, ?) >= 1 OR INSTR(content, ?) >= 1 ";
			} else if (condition.equals("reg_date")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += " AND TO_CHAR(reg_date, 'YYYYMMDD') = ? ";
			} else {
				sql += " AND INSTR(" + condition + ", ?) >= 1 ";
			}
			
			if(id != null && id.length() != 0) {
				sql += " AND  s.id = ? ";
			}
	
			pstmt = conn.prepareStatement(sql);
						
			pstmt.setString(1, subjectNo);
			pstmt.setString(2, keyword);
			if(id != null && id.length() != 0) {
				if (condition.equals("all")) {
					pstmt.setString(3, keyword);
					pstmt.setString(4, id);
				} else {
					pstmt.setString(3, id);
				}
			} else {
				if (condition.equals("all")) {
					pstmt.setString(3, keyword);
				}				
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
	
	// 리스트
	public List<QnaDTO> listQna(String subjectNo, int offset, int size, String id) {
		List<QnaDTO> list = new ArrayList<QnaDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
	
		try {
			sb.append(" SELECT b.articleNo, b.ID, title, hitCount, TO_CHAR(reg_date, 'YYYY-MM-DD') reg_date, a.name, NVL(replyCount, 0) replyCount ");
			sb.append(" FROM subject_bbs b ");
			sb.append(" JOIN account a ON b.ID = a.ID ");
			sb.append(" LEFT OUTER JOIN ( ");
			sb.append("      SELECT articleNo, COUNT(*) replyCount FROM SUBJECT_BBS_REPLY GROUP BY articleNo ");
			sb.append(" ) r ON b.articleNo = r.articleNo ");
			sb.append(" WHERE bbsCode = '00003' AND subjectNo = ?  ");
			if(id != null && id.length() != 0) {
				sb.append(" AND b.id = ? ");
			}
			sb.append(" ORDER BY reg_date DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
	
			
			pstmt = conn.prepareStatement(sb.toString());
			
			if(id != null && id.length() != 0) {
				pstmt.setString(1, subjectNo);
				pstmt.setString(2, id);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, size);
			} else {
				pstmt.setString(1, subjectNo);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, size);				
			}
	
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				QnaDTO dto = new QnaDTO();
	
				dto.setArticleNo(rs.getString("articleNo"));
				dto.setUserId(rs.getString("id"));
				dto.setTitle(rs.getString("title"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setReg_date(rs.getString("reg_date"));
				dto.setName(rs.getString("name"));
				dto.setReplyCount(rs.getInt("replyCount"));
	
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
	
	// 질문에서 리스트
	public List<QnaDTO> listQna(String subjectNo, int offset, int size, String condition, String keyword, String id) {
		List<QnaDTO> list = new ArrayList<QnaDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
	
		try {
			sb.append(" SELECT b.articleNo, b.ID, title, hitCount, TO_CHAR(reg_date, 'YYYY-MM-DD') reg_date, a.name, NVL(replyCount, 0) replyCount ");
			sb.append(" FROM subject_bbs b ");
			sb.append(" JOIN account a ON b.ID = a.ID ");
			sb.append(" LEFT OUTER JOIN ( ");
			sb.append("      SELECT articleNo, COUNT(*) replyCount FROM SUBJECT_BBS_REPLY GROUP BY articleNo ");
			sb.append(" ) r ON b.articleNo = r.articleNo ");
			sb.append(" WHERE bbsCode = '00003' AND subjectNo = ?  ");
			
			if (condition.equals("all")) {
				sb.append(" AND INSTR(title, ?) >= 1 OR INSTR(content, ?) >= 1 ");
			} else if (condition.equals("reg_date")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append(" AND TO_CHAR(reg_date, 'YYYYMMDD') = ?");
			} else {
				sb.append(" AND INSTR(" + condition + ", ?) >= 1 ");
			}
			
			if(id != null && id.length() != 0) {
				sb.append(" AND b.id = ? ");
			}
			sb.append(" ORDER BY articleNo DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			if(id != null && id.length() != 0) {
				if (condition.equals("all")) {
					
					pstmt.setString(1, subjectNo);
					pstmt.setString(2, keyword);
					pstmt.setString(3, keyword);
					pstmt.setString(4, id);
					pstmt.setInt(5, offset);
					pstmt.setInt(6, size);
				} else {
					pstmt.setString(1, subjectNo);
					pstmt.setString(2, keyword);
					pstmt.setString(3, id);
					pstmt.setInt(4, offset);
					pstmt.setInt(5, size);
				}
			} else {
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
			}
	
			rs = pstmt.executeQuery();
	
			while (rs.next()) {
				QnaDTO dto = new QnaDTO();
	
				dto.setArticleNo(rs.getString("articleNo"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setReg_date(rs.getString("reg_date")); // yyyy-MM-dd HH:mm:ss
				dto.setReplyCount(rs.getInt("replyCount"));
	
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
	
	
	// 질문 보기
	public QnaDTO readQna(String articleNo) {
		QnaDTO dto = null;
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
				dto = new QnaDTO();
	
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

	//이전글
	public QnaDTO preReadQna(String subjectNo, String articleNo, String condition, String keyword, String id) {
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			
			if (keyword != null && keyword.length() != 0) {
				sb.append(" SELECT articleNo, title ");
				sb.append(" FROM subject_bbs b ");
				sb.append(" JOIN account a ON b.Id = a.Id ");
				sb.append(" WHERE ( articleNo > ? ) AND subjectNo = ? AND bbsCode = '00003' ");
				if (condition.equals("all")) {
					sb.append("   AND ( INSTR(title, ?) >= 1 OR INSTR(content, ?) >= 1 ) ");
				} else if (condition.equals("reg_date")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sb.append("   AND ( TO_CHAR(reg_date, 'YYYYMMDD') = ? ) ");
				} else {
					sb.append("   AND ( INSTR(" + condition + ", ?) >= 1 ) ");
				}
				if(id != null && id.length() != 0) {
					sb.append(" AND b.id = ? ");
				}
				sb.append(" ORDER BY articleNo ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setString(1, articleNo);
				pstmt.setString(2, subjectNo);
				pstmt.setString(3, keyword);
				if(id != null && id.length() != 0) {
					if (condition.equals("all")) {
						pstmt.setString(4, keyword);
						pstmt.setString(5, id);
					} else {
						pstmt.setString(4, id);
					}
				} else {
					if (condition.equals("all")) {
						pstmt.setString(4, keyword);
					}
				}
				
				
			} else {
				sb.append(" SELECT articleNo, title FROM subject_bbs b ");
				sb.append(" JOIN account a ON b.Id = a.Id ");
				sb.append(" WHERE articleNo > ? AND subjectNo = ? AND bbsCode = '00003' ");
				if(id != null && id.length() != 0) {
					sb.append(" AND b.id = ? ");
				}
				sb.append(" ORDER BY articleNo ASC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setString(1, articleNo);
				pstmt.setString(2, subjectNo);
				if(id != null && id.length() != 0) {
					pstmt.setString(3, id);
				}
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new QnaDTO();
				
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
	public QnaDTO nextReadQna(String subjectNo, String articleNo, String condition, String keyword, String id) {
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			if (keyword != null && keyword.length() != 0) {
				sb.append(" SELECT articleNo, title ");
				sb.append(" FROM subject_bbs b ");
				sb.append(" JOIN account a ON b.Id = a.Id ");
				sb.append(" WHERE ( articleNo < ? ) AND subjectNo = ? AND bbsCode = '00003' ");
				if (condition.equals("all")) {
					sb.append("   AND ( INSTR(title, ?) >= 1 OR INSTR(content, ?) >= 1 ) ");
				} else if (condition.equals("reg_date")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sb.append("   AND ( TO_CHAR(reg_date, 'YYYYMMDD') = ? ) ");
				} else {
					sb.append("   AND ( INSTR(" + condition + ", ?) >= 1 ) ");
				}
				if(id != null && id.length() != 0) {
					sb.append(" AND b.id = ? ");
				}
				sb.append(" ORDER BY articleNo DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setString(1, articleNo);
				pstmt.setString(2, subjectNo);
				pstmt.setString(3, keyword);
				if(id != null && id.length() != 0) {
					if (condition.equals("all")) {
						pstmt.setString(4, keyword);
						pstmt.setString(5, id);
					} else {
						pstmt.setString(4, id);
					}
				} else {
					if (condition.equals("all")) {
						pstmt.setString(4, keyword);
					}
				}
			} else {
				sb.append(" SELECT articleNo, title FROM subject_bbs b ");
				sb.append(" JOIN account a ON b.Id = a.Id ");
				sb.append(" WHERE articleNo < ? AND subjectNo = ? AND bbsCode = '00003' ");
				if(id != null && id.length() != 0) {
					sb.append(" AND b.id = ? ");
				}
				sb.append(" ORDER BY articleNo DESC ");
				sb.append(" FETCH FIRST 1 ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setString(1, articleNo);
				pstmt.setString(2, subjectNo);
				if(id != null && id.length() != 0) {
					pstmt.setString(3, id);
				}
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new QnaDTO();
				
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
	public void updateHitCount(String articleNo) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE subject_bbs SET hitCount=hitCount+1 WHERE articleNo=? ";
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

	// 수정
	public void updateQna(QnaDTO dto) throws SQLException {
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
				sql = "INSERT INTO subject_bbs_File(fileNo, articleNo, saveFilename, originalFilename) VALUES (subject_bbs_file_seq.NEXTVAL, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				
				for (int i = 0; i < dto.getSaveFiles().length; i++) {
					pstmt.setString(1, dto.getArticleNo());
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

	// 삭제
	public void deleteQna(String articleNo) throws SQLException {
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

	// 파일 첨부 보기
	public List<QnaDTO> listQnaFile(String articleNo) {
		List<QnaDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT fileNo, saveFilename, originalFilename FROM subject_bbs_file WHERE articleNo = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, articleNo);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				 QnaDTO dto = new QnaDTO();
				dto.setFileNo(rs.getString("fileNo"));
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
	
	// 파일첨부 읽어오기
	public QnaDTO readQnaFile(String fileNo) {
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT fileNo, saveFilename, originalFilename FROM subject_bbs_file WHERE fileNo = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, fileNo);
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new QnaDTO();

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
	

	public void deleteQnaFile(String mode, String articleNo) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
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

	// 답변 등록
	public void insertReply(ReplyDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO SUBJECT_BBS_REPLY(replyNo, subjectNo, content, articleNo, id, reg_date, answer, bbscode) "
					+ " VALUES (SUBJECT_BBS_REPLY_seq.NEXTVAL, ?, ?, ?, ?, SYSDATE, 0, '00003')";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getSubjectNo());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getArticleNo());
			pstmt.setString(4, dto.getId());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		}
	}
	
	// 답변 수정
	public void updateReply(ReplyDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE SUBJECT_BBS_REPLY SET content = ? WHERE replyNo = ? AND id = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getContent());
			pstmt.setString(2, dto.getReplyNo());
			pstmt.setString(3, dto.getId());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		}
	}	
	
	// 답변
	public ReplyDTO readReply(String replyNo, String mode) {
		ReplyDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT replyNo, subjectNo, content, articleNo, a.id, name, reg_date "
					+ " FROM SUBJECT_BBS_REPLY r JOIN account a ON r.id=a.id  ";
			
			if(mode.equals("reply")) {
				sql += " WHERE replyNo = ? ";
			} else {
				sql += " WHERE articleNo = ? ";
			}
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, replyNo);

			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new ReplyDTO();
				
				dto.setReplyNo(rs.getString("replyNo"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setSubjectNo(rs.getString("subjectNo"));
				dto.setContent(rs.getString("content"));
				dto.setReg_date(rs.getString("reg_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
	
	// 답변 삭제
	public void deleteReply(String replyNo, String id) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM SUBJECT_BBS_REPLY "
					+ " WHERE replyNo = ? AND id = ?  ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, replyNo);
			pstmt.setString(2, id);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}		
		
	}	
	
}
