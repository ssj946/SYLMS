package com.freebbs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.util.DBConn;

public class FreebbsDAO {
	private Connection conn = DBConn.getConnection();

	// 과목정보 불러오기
	public FreebbsDTO readSubject(String subjectNo) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;
		ResultSet rs = null;
		FreebbsDTO dto = new FreebbsDTO();

		try {
			sql = " SELECT s.id, name, subjectname, credit, TO_CHAR(syear,'YYYY')syear, semester FROM subject s "
					+ "  JOIN account a ON a.id=s.id " + "  WHERE subjectNo= ? ;";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, subjectNo);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto.setProfessorname(rs.getString("name"));
				dto.setSubjectName(rs.getString("subjectname"));
				dto.setCredit(Integer.parseInt(rs.getString("credit")));
				dto.setSemester(Integer.parseInt(rs.getString("semester")));
				dto.setSyear(Integer.parseInt(rs.getString("syear")));
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

		return dto;

	}

	// 데이터 추가
	public void insertFreebbs(FreebbsDTO dto) throws SQLException {
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

			sql = " INSERT INTO subject_bbs(articleNo, bbsCode, subjectNo,  "
					+ " ID, title, content, reg_date, hitCount) " + " VALUES ( ?,'00004', ?, ?, ?, ?, SYSDATE, 0)";
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
						+ " VALUES (subject_bbs_file_seq.NEXTVAL, ?, ? , ?, '00004', ? )";
				pstmt = conn.prepareStatement(sql);

				for (int i = 0; i < dto.getSaveFiles().length; i++) {
					pstmt.setString(1, dto.getSaveFiles()[i]);
					pstmt.setString(2, dto.getOriginalFiles()[i]);
					pstmt.setString(3, dto.getArticleNo());
					pstmt.setString(4, dto.getSubjectNo());

					pstmt.executeUpdate();
				}
			}

		} catch (Exception e) {
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

	// 데이터개수
   
	
	
	
	
	
	
	// 데이터개수 - 검색

	// list

	// list 검색

}
