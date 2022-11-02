package com.syllabus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class SyllabusDAO {
	private Connection conn = DBConn.getConnection();


	// 교수용
	public int dataCountProfessor(String id) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT COUNT(*) FROM curriculum c " + " JOIN subject s ON c.subjectNo = s.subjectNo "
					+ " JOIN account a ON s.id = a.id " + " WHERE s.id = ? ";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
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

		return result;
	}

	// 학생용-교수계획서
	public SyllabusDTO read(String subjectNo) {
		SyllabusDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT b.subjectNo,subjectName,b.lecturePlace, b.semester, b.assignmentRate, b.middleRate,b.finalRate, "
					+ " TO_CHAR(b.openDate, 'YYYY-MM-DD') openDate, credit, s.id, a.name "
					+ " FROM curriculum b"
					+ " JOIN subject s ON b.subjectNo = s.subjectNo"
					+ " JOIN account a ON s.id = a.id "
				 	+ " WHERE b.subjectNo = ? ";


			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subjectNo);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new SyllabusDTO();
				dto.setSubjectNo(rs.getString("subjectNo"));
				dto.setSubjectName(rs.getString("subjectName"));
				dto.setLecturePlace(rs.getString("lecturePlace"));
				dto.setOpenDate(rs.getString("openDate"));
				dto.setSemester(rs.getInt("semester"));
				dto.setAssignmentRate(rs.getInt("assignmentRate"));
				dto.setMiddleRate(rs.getInt("middleRate"));
				dto.setFinalRate(rs.getInt("finalRate"));
				dto.setCredit(rs.getInt("credit")); // 학점

				dto.setId(rs.getString("id")); // 교수아이디
				dto.setName(rs.getString("name")); // 교수이름

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
	
	
	// 학생별 수강신청과목
	public List<SyllabusDTO> listRegisterSubject(String id) {
		List<SyllabusDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql= "SELECT s.subjectNo, subjectName, credit, TO_CHAR(sYear,'YYYY') sYear, semester, studentcode, name "
					+ " FROM REGISTERSUBJECT r"
					+ " JOIN subject s ON s.subjectNo=r.subjectNo "
					+ " JOIN account a ON a.id=studentcode "
					+ " WHERE STUDENTcode = ?"
					+ " AND TO_CHAR(sYear,'YYYY')=TO_CHAR(SYSDATE,'YYYY') "
					+ " AND SYSDATE<sYear+(interval '4' month)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				SyllabusDTO dto = new SyllabusDTO();
				dto.setSubjectNo(rs.getString("subjectNo"));
				dto.setSubjectName(rs.getString("subjectName"));
				
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
	

	
	// 교수용
	public List<SyllabusDTO> listProfessor(String id, int offset, int size) {
		List<SyllabusDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT b.subjectNo,subjectName,b.lecturePlace, b.semester, b.assignmentRate, b.middleRate,b.finalRate, "
					+ " TO_CHAR(b.openDate, 'YYYY-MM-DD') openDate, credit, s.id, a.name " + " FROM curriculum b"
					+ " JOIN subject s ON b.subjectNo = s.subjectNo" + " JOIN account a ON s.id = a.id "
					+ " WHERE s.id = ? " + " ORDER BY b.subjectNo DESC " + " OFFSET ? ROWS FETCH FIRST ? ROWS ONLY";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setInt(2, offset);
			pstmt.setInt(3, size);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				SyllabusDTO dto = new SyllabusDTO();
				dto.setSubjectNo(rs.getString("subjectNo"));
				dto.setSubjectName(rs.getString("subjectName"));
				dto.setLecturePlace(rs.getString("lecturePlace"));
				dto.setOpenDate(rs.getString("openDate"));
				dto.setSemester(rs.getInt("semester"));
				dto.setAssignmentRate(rs.getInt("assignmentRate"));
				dto.setMiddleRate(rs.getInt("middleRate"));
				dto.setFinalRate(rs.getInt("finalRate"));
				dto.setCredit(rs.getInt("credit")); // 학점

				dto.setId(rs.getString("id")); // 교수아이디
				dto.setName(rs.getString("name")); // 교수이름

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

//세부사항
	public SyllabusDTO readProfessor(String subjectNo) {
		SyllabusDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT b.subjectNo,subjectName,b.lecturePlace, b.semester, b.assignmentRate, b.middleRate,b.finalRate, "
					+ " TO_CHAR(b.openDate, 'YYYY-MM-DD') openDate, credit, s.id, a.name " + " FROM curriculum b"
					+ " JOIN subject s ON b.subjectNo = s.subjectNo" + " JOIN account a ON s.id = a.id "
					+ " WHERE b.subjectNo = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subjectNo);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new SyllabusDTO();

				dto.setSubjectNo(rs.getString("SubjectNo"));
				dto.setSubjectName(rs.getString("subjectName"));
				dto.setOpenDate(rs.getString("openDate"));
				dto.setAssignmentRate(rs.getInt("assignmentRate"));
				dto.setMiddleRate(rs.getInt("middleRate"));
				dto.setFinalRate(rs.getInt("finalRate"));
				dto.setCredit(rs.getInt("credit")); // 학점

				dto.setId(rs.getString("id")); // 교수아이디
				dto.setName(rs.getString("name")); // 교수이름

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
	
	
	
	
}
