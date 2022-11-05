package com.debate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.util.DBConn;

public class DebateDAO {
	private Connection conn = DBConn.getConnection();

	// 과목정보 불러오기
	public DebateDTO readSubject(String subjectNo) throws SQLException{
			
			PreparedStatement pstmt= null;
			String sql;
			ResultSet rs=null;
			DebateDTO dto = new DebateDTO();
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
		
	// 데이터 추가 00002 - 토론게시판
	public void insertBoard(DebateDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "INSERT INTO subject_bbs(articleNo, bbsCode, subjectNo,  "
					+ " ID, title, content, reg_date, hitCount) "
					+ " VALUES ( subject_bbs_seq.NEXTVAL,'00002', ?, ?, ?, ?, SYSDATE, 0)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getSubjectNo());
			pstmt.setString(2, dto.getUserId());
			pstmt.setString(3, dto.getTitle());
			pstmt.setString(4, dto.getContent());
			
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

	// 데이터 개수
	public int dataCount(String subjectNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM subject_bbs WHERE subjectNo = ? AND bbsCode = '00002'";
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
					+ " WHERE subjectNo = ? AND bbsCode = '00002' ";
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

	// 게시물 리스트
	public List<DebateDTO> listBoard(String subjectNo, int offset, int size) {
		List<DebateDTO> list = new ArrayList<DebateDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT b.articleNo, b.Id, title, hitCount, TO_CHAR(reg_date, 'YYYY-MM-DD') reg_date, NVL(replyCount, 0) replyCount , a.name");
			sb.append(" FROM subject_bbs b");
			sb.append(" JOIN account a ON a.Id = b.Id");
			sb.append(" LEFT OUTER JOIN (SELECT articleNo, COUNT(*) replyCount FROM subject_bbs_Reply WHERE answer=0 GROUP BY articleNo) c ON b.articleNo = c.articleNo");
			sb.append(" WHERE b.subjectNo = ? AND b.bbscode='00002'");
			sb.append(" ORDER BY articleNo DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, subjectNo);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				DebateDTO dto = new DebateDTO();

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
				} catch (SQLException e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}

		return list;
	}

	// 검색에서 리스트
	public List<DebateDTO> listBoard(String subjectNo, int offset, int size, String condition, String keyword) {
		List<DebateDTO> list = new ArrayList<DebateDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT b.articleNo, b.Id, title, hitCount, TO_CHAR(reg_date, 'YYYY-MM-DD') reg_date, NVL(replyCount, 0) replyCount");
			sb.append("FROM subject_bbs b ");
			sb.append("JOIN account a ON a.Id = b.Id ");
			sb.append("LEFT OUTER JOIN ( SELECT  articleNo, COUNT(*) replyCount FROM subject_bbs_Reply WHERE answer=0 GROUP BY articleNo) c ON b.articleNo = c.articleNo");
			sb.append("WHERE b.subjectNo = ? AND b.bbscode='00002'");
			
			
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
				DebateDTO dto = new DebateDTO();

				dto.setArticleNo(rs.getString("articleNo"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setReg_date(rs.getString("reg_date"));
				
				dto.setReplyCount(rs.getInt("replyCount"));

				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}

		return list;
	}

	// 조회수 증가하기
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
				} catch (SQLException e2) {
				}
			}
		}

	}

	// 해당 게시물 보기
	public DebateDTO readBoard(String articleNo, String subjectNo) {
		DebateDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = " SELECT b.articleNo, b.Id, a.Name, title, content, reg_date, hitCount "
					+ " FROM subject_bbs b "
					+ " JOIN account a ON a.Id = b.Id "
					+ " WHERE b.articleNo = ? AND b.subjectNo =? AND b.bbscode='00002'";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, articleNo);
			pstmt.setString(2, subjectNo);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new DebateDTO();
				
				dto.setArticleNo(rs.getString("articleNo"));
				dto.setUserId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setHitCount(rs.getInt("hitCount"));
				dto.setReg_date(rs.getString("reg_date"));
				
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


	// 게시물 수정 
	public void updateBoard(DebateDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE subject_bbs SET title=?, content=? WHERE articleno=? AND Id=?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getTitle());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getArticleNo());
			pstmt.setString(4, dto.getUserId());
			
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

	// 게시물 삭제 
	public void deleteBoard(String articleNo, String userId) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			if (userId.equals("admin")) {
				sql = "DELETE FROM subject_bbs WHERE articleNo=?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, articleNo);
				
				pstmt.executeUpdate();
			} else {
				sql = "DELETE FROM subject_bbs WHERE articleNo=? AND Id=?";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, articleNo);
				pstmt.setString(2, userId);
				
				pstmt.executeUpdate();
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

	// 게시물의 댓글 및 답글 추가
	public void insertReply(ReplyDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO subject_bbs_Reply(replyNo, Id, content, reg_date, articleNo, answer, bbsCode, subjectNo) "
					+ " VALUES (subject_bbs_Reply_seq.NEXTVAL, ?, ?, SYSDATE, ?, ?, '00002', ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getUserId());
			pstmt.setString(2, dto.getContent());
			pstmt.setString(3, dto.getArticleNo());
			pstmt.setLong(4, dto.getAnswer());
			pstmt.setString(5, dto.getSubjectNo());
			
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

	// 게시물의 댓글 개수
	public int dataCountReply(String articleNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM subject_bbs_Reply WHERE articleNo=? AND answer=0";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, articleNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result = rs.getInt(1);
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
		
		return result;
	}

	// 게시물 댓글 리스트
	public List<ReplyDTO> listReply(String articleNo, int offset, int size) {
		List<ReplyDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT r.replyNo, r.Id, a.Name, articleNo, content, r.reg_date, NVL(answerCount, 0) answerCount, NVL(likeCount, 0) likeCount, NVL(disLikeCount, 0) disLikeCount  ");
			sb.append(" FROM subject_bbs_reply r  ");
			sb.append(" JOIN account a ON r.Id = a.Id ");
			sb.append(" LEFT OUTER  JOIN ( SELECT answer, COUNT(*) answerCount FROM subject_bbs_reply WHERE answer != 0 GROUP BY answer) b ON r.replyNo = b.answer  ");
			sb.append(" LEFT OUTER  JOIN ( SELECT replyNo, COUNT(DECODE(replyLike, 1, 1)) likeCount, COUNT(DECODE(replyLike, 0, 1)) disLikeCount FROM bbs_Reply_Like GROUP BY replyNo ) c ON r.replyNo = c.replyNo  "); 
			sb.append(" WHERE articleNo = ? AND r.answer=0  ");
			sb.append(" ORDER BY r.replyNo DESC  ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setString(1, articleNo);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReplyDTO dto = new ReplyDTO();
				
				dto.setReplyNo(rs.getString("replyNo"));
				dto.setArticleNo(rs.getString("articleNo"));
				dto.setUserId(rs.getString("Id"));
				dto.setName(rs.getString("Name"));
				dto.setContent(rs.getString("content"));
				dto.setReg_date(rs.getString("reg_date"));
				dto.setAnswerCount(rs.getInt("answerCount"));
				dto.setLikeCount(rs.getInt("likeCount"));
				dto.setDisLikeCount(rs.getInt("disLikeCount"));
				
				list.add(dto);
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
		
		return list;
	}

	public ReplyDTO readReply(String replyNo) {
		ReplyDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT replyNo, articleNo, r.Id, a.Name, content ,r.reg_date "
					+ " FROM subject_bbs_Reply r JOIN account a ON r.Id=a.Id  "
					+ " WHERE replyNo = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, replyNo);

			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new ReplyDTO();
				
				dto.setReplyNo(rs.getString("replyNo"));
				dto.setArticleNo(rs.getString("articleNo"));
				dto.setUserId(rs.getString("Id"));
				dto.setName(rs.getString("Name"));
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
	
	// 게시물의 댓글 삭제
	public void deleteReply(String replyNo, String userId) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		if(! userId.equals("admin")) {
			ReplyDTO dto = readReply(replyNo);
			if(dto == null || (! userId.equals(dto.getUserId()))) {
				return;
			}
		}
		
		try {
			sql = "DELETE FROM subject_bbs_Reply "
					+ " WHERE replyNo IN  "
					+ " (SELECT replyNo FROM subject_bbs_Reply START WITH replyNo = ?"
					+ "     CONNECT BY PRIOR replyNo = answer)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, replyNo);
			
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

	// 댓글의 답글 리스트
	public List<ReplyDTO> listReplyAnswer(long answer) {
		List<ReplyDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb=new StringBuilder();
		
		try {
			sb.append(" SELECT replyNo, articleNo, r.Id, a.Name, content, reg_date, answer ");
			sb.append(" FROM subject_bbs_Reply r ");
			sb.append(" JOIN account a ON r.userId=a.Id ");
			sb.append(" WHERE answer=? ");
			sb.append(" ORDER BY replyNo DESC ");
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setLong(1, answer);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReplyDTO dto=new ReplyDTO();
				
				dto.setReplyNo(rs.getString("replyNo"));
				dto.setArticleNo(rs.getString("articleNo"));
				dto.setUserId(rs.getString("Id"));
				dto.setName(rs.getString("Name"));
				dto.setContent(rs.getString("content"));
				dto.setReg_date(rs.getString("reg_date"));
				dto.setAnswer(rs.getLong("answer"));
				
				list.add(dto);
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
		return list;
	}
	
	// 댓글의 답글 개수
	public int dataCountReplyAnswer(long answer) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM subject_bbs_Reply WHERE answer=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, answer);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				result=rs.getInt(1);
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
		
		return result;
	}
	
	// 댓글의 좋아요 / 싫어요 추가
	public void insertReplyLike(ReplyDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO bbs_Reply_Like(replyNo, Id, replyLike) VALUES (?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getReplyNo());
			pstmt.setString(2, dto.getUserId());
			pstmt.setInt(3, dto.getReplyLike());
			
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
	
	// 댓글의 좋아요 / 싫어요 개수
	public Map<String, Integer> countReplyLike(String replyNo) {
		Map<String, Integer> map = new HashMap<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = " SELECT COUNT(DECODE(replyLike, 1, 1)) likeCount,  "
				+ "     COUNT(DECODE(replyLike, 0, 1)) disLikeCount  "
				+ " FROM bbs_Reply_Like WHERE replyNo = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, replyNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				map.put("likeCount", rs.getInt("likeCount"));
				map.put("disLikeCount", rs.getInt("disLikeCount"));
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
		
		return map;
	}	
}
