package com.exam;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;






public class ExamDAO {
	private Connection conn = DBConn.getConnection();

	// 과목정보 불러오기
	public ExamDTO readSubject(String subjectNo) throws SQLException{
		
		PreparedStatement pstmt= null;
		String sql;
		ResultSet rs=null;
		ExamDTO dto = new ExamDTO();
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
	
	
	
	
	
	// 해당 과목의 시험 기본폼 입력
	public void codeList(ExamDTO dto) {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = " INSERT INTO exam (examCode, score, examtype, gradecode) "
					+ " SELECT LPAD(EXAM_SEQ.NEXTVAL,8,'0'), 0, '미정', gradecode FROM grades g "
					+ " WHERE g.subjectNo = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getSubjectNo());
			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}

	}

	// 시험 성적 입력폼에 채울 기본컬럼(과목번호, 학생학번, 성적번호) 가져오기
	public List<ExamDTO> listBoard(String subjectNo) {
		List<ExamDTO> list = new ArrayList<ExamDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = " SELECT subjectNo, studentCode, gradeCode FROM grades g WHERE g.subjectNo = ? ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, subjectNo);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				ExamDTO dto = new ExamDTO();

				dto.setSubjectNo(rs.getString("subjectNo"));
				dto.setStudentCode(rs.getString("studentCode"));
				dto.setGradeCode(rs.getString("gradeCode"));

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

	// 시험 성적입력
	public void examInsert(ExamDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE exam SET score = ?, examtype = ? WHERE gradecode = ? AND examType = '미정' ";

			pstmt = conn.prepareStatement(sql);
			
			for(int i=0; i<dto.getGradeCodes().length; i++) {
				pstmt.setInt(1, dto.getScores()[i]);
				pstmt.setString(2, dto.getExamTypes()[i]);
				pstmt.setString(3, dto.getGradeCodes()[i]);
				
				pstmt.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
	}

	// 본인 시험성적 확인하기
	public List<ExamDTO> readExam(String userId) {
		List<ExamDTO> list = new ArrayList<ExamDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = " SELECT score, studentCode, examType, e.gradeCode " 
					+ " FROM exam e "
					+ " JOIN grades g ON g.gradeCode = e.gradeCode " 
					+ " WHERE studentCode = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				ExamDTO dto = new ExamDTO();

				dto.setScore(Integer.parseInt(rs.getString("score")));
				dto.setStudentCode(rs.getString("studentCode"));
				dto.setExamType(rs.getString("examType"));
				dto.setGradeCode(rs.getString("gradeCode"));

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
	
	//선택한 학생의 점수 불러오기
	public List<ExamDTO> stdExam(String examType, String gradeCode) {
		List<ExamDTO> list = new ArrayList<ExamDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = " SELECT score, examType, gradeCode "
					+ "from exam "
					+ "WHERE examType = ? AND gradeCode = ? ";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, examType);
			pstmt.setString(2, gradeCode);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				ExamDTO dto = new ExamDTO();

				dto.setScore(Integer.parseInt(rs.getString("score")));
				dto.setExamType(rs.getString("examType"));
				dto.setGradeCode(rs.getString("gradeCode"));

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
	
	// 시험 성적수정
		public void examUpdate(ExamDTO dto) throws SQLException {
			PreparedStatement pstmt = null;
			String sql;

			try {
				sql = " update exam SET score = ? WHERE examType = ? AND gradeCode = ? ";
				
				pstmt = conn.prepareStatement(sql);
				
				for(int i=0; i<dto.getGradeCodes().length; i++) {
					pstmt.setInt(1, dto.getScores()[i]);
					pstmt.setString(2, dto.getExamTypes()[i]);
					pstmt.setString(3, dto.getGradeCodes()[i]);
					
					pstmt.executeUpdate();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
			}
		}
		
}
