package com.messege;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.member.MemberDTO;
import com.util.DBConn;

public class MessegeDAO {
	private Connection conn = DBConn.getConnection();

	public void sendMessege(MessegeDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "INSERT INTO messege(messegeCode, content, sendDate, sendId, receiveId) "
					+ " VALUES (messege_seq.NEXTVAL, ?, SYSDATE, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getContent());
			pstmt.setString(2, dto.getSendId());
			pstmt.setString(3, dto.getReceiveId());

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
	public int dataCount(String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM messege m "
					+ " JOIN account a ON m.receiveId = a.id "
					+ " WHERE receiveId = ? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1,userId);
			
		
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
	public int dataCount(String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM messege m " 
					+ " JOIN account a ON m.sendId = a.id ";
			if (condition.equals("all")) {
				sql += "  WHERE INSTR(content, ?) >= 1 ";
			} else if (condition.equals("sendDate")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += "  WHERE TO_CHAR(sendDate, 'YYYYMMDD') = ? ";
			} else {
				sql += "  WHERE INSTR(" + condition + ", ?) >= 1 ";
			}

			pstmt = conn.prepareStatement(sql);
			
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

	public List<MessegeDTO> listBoard(int offset, int size, String userId) {
		List<MessegeDTO> list = new ArrayList<MessegeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = " SELECT sendId, receiveId, name, content, TO_CHAR(sendDate, 'YYYY-MM-DD') sendDate " 
					+ " FROM messege m "
					+ " JOIN account a ON m.receiveId = a.id "
					+ " WHERE receiveId = ? "
					+ " ORDER BY sendDate DESC "
					+ " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1,userId);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MessegeDTO dto = new MessegeDTO();

				dto.setSendId(rs.getString("sendId"));
				dto.setSendName(rs.getString("name"));
				dto.setContent(rs.getString("content"));
				dto.setSendDate(rs.getString("sendDate"));
				dto.setReceiveId(rs.getString("receiveId"));

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

	public List<MessegeDTO> listBoard(int offset, int size, String condition, String keyword) {
		List<MessegeDTO> list = new ArrayList<MessegeDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = " SELECT content, name, sendId, TO_CHAR(sendDate, 'YYYY-MM-DD') sendDate, receiveId "
					+ " FROM messege m "
					+ " JOIN account a ON m.sendId = a.id ";
			if (condition.equals("all")) {
				sql += " WHERE INSTR(content, ?) >= 1 ";
			} else if (condition.equals("sendDate")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += " WHERE TO_CHAR(sendDate, 'YYYYMMDD') = ? ";
			} else {
				sql += " WHERE INSTR(" + condition + ", ?) >= 1 ";
			}
			sql += " ORDER BY sendDate DESC ";
			sql += " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";

			pstmt = conn.prepareStatement(sql);

			if (condition.equals("all")) {
				
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
				pstmt.setInt(3, offset);
				pstmt.setInt(4, size);
			} else {
				pstmt.setString(1, keyword);
				pstmt.setInt(2, offset);
				pstmt.setInt(3, size);
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MessegeDTO dto = new MessegeDTO();

				dto.setSendId(rs.getString("sendId"));
				dto.setSendName(rs.getString("sendName"));
				dto.setContent(rs.getString("content"));
				dto.setSendDate(rs.getString("sendDate"));
				dto.setReceiveId(rs.getString("receiveId"));

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
	
	public MessegeDTO readMessege(String sendId) {
		MessegeDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = " SELECT m.sendId, name, content, sendDate "
					+ " FROM messege m "
					+ " JOIN account a ON m.sendId = a.id "
					+ " WHERE sendId = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sendId);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new MessegeDTO();
				
				dto.setSendId(rs.getString("sendId"));
				dto.setSendName(rs.getString("name"));
				dto.setContent(rs.getString("content"));
				dto.setSendDate(rs.getString("sendDate"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return dto;
	}
	
	public List<MemberDTO> idList (String id){
		List<MemberDTO> list = new ArrayList<>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = " SELECT id, name "
					+ " FROM account "
					+ " ORDER BY name DESC ";

			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MemberDTO dto = new MemberDTO();

				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				
				list.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
		
	}
	
	//메세지 읽으면 원래 해당 메세지에 읽은 날짜 대입
	public void readDate(MessegeDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE messege SET readDate = SYSDATE WHERE messegeCode = ?";
			pstmt = conn.prepareStatement(sql);
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

	// 읽지 않은 개수
	public int messegeCount(String userId) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM messege"
					+ " WHERE readDate = NULL";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, userId);
			
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
