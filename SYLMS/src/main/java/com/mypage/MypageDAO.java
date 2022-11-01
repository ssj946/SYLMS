package com.mypage;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.util.DBConn;

public class MypageDAO {
	private Connection conn = DBConn.getConnection();

	public MypageDTO readMember(String id) {
		MypageDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT distinct ");
			sb.append(
					" a.id, a.name, a.pwd, a.tel, a.email, TO_CHAR(a.birth, 'yyyy-mm-dd') birth, a.filename ,d.departmentName  ");
			sb.append(" FROM account a ");
			sb.append(" LEFT outer JOIN professor p ");
			sb.append(" ON a.id = p.id ");
			sb.append(" LEFT outer JOIN subject s ");
			sb.append(" ON p.id = s.id ");
			sb.append(" LEFT outer JOIN department d ");
			sb.append(" ON s.departmentNum = d.departmentNum ");
			sb.append(" WHERE a.id = ? ");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, id);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new MypageDTO();

				dto.setUserId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setPwd(rs.getString("pwd"));
				dto.setTel(rs.getString("tel"));
				dto.setEmail(rs.getString("email"));
				dto.setBirth(rs.getString("birth"));
				dto.setFileName(rs.getString("filename"));
				dto.setDepartmentName(rs.getString("departmentName"));
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

		return dto;
	}

	public void updateMember(MypageDTO dto) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE account SET pwd = ?, tel = ?, email = ?,  filename=?  WHERE id = ?";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getPwd());
			pstmt.setString(2, dto.getTel());
			pstmt.setString(3, dto.getEmail());
			pstmt.setString(4, dto.getFileName());
			pstmt.setString(5, dto.getUserId());

			pstmt.executeUpdate();

		} catch (Exception e) {
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

	
	// 데이터 
		public int dataCount(int tyear, int tsemester ) {
			int result = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;

			try {  
				sql = " SELECT NVL (COUNT(*), 0) FROM subject s LEFT JOIN APPLICATIONASSISTANT ap "
						+ " ON s.subjectNo = ap.subjectNo  WHERE  to_char(sYear,'YYYY') >= ? AND semester = ? AND enable is NULL ";

				pstmt = conn.prepareStatement(sql);

				pstmt.setInt(1, tyear);
				pstmt.setInt(2, tsemester);
				

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

	
	
	// 데이터 검색 기본 
	public int dataCount(int tyear, int tsemester,  String keyword ) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {  
			sql = " SELECT NVL (COUNT(*), 0) FROM subject s LEFT JOIN APPLICATIONASSISTANT ap "
					+ " ON s.subjectNo = ap.subjectNo  WHERE  to_char(sYear,'YYYY') >= ? AND semester = ? AND enable is NULL ";

			if ( keyword != null ) {
				sql += " AND INSTR(subjectName, ?) >= 1 ";

			} 
			

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, tyear);
			pstmt.setInt(2, tsemester);
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

	// 기본 리스트  
		List<MypageDTO> listSubject(int offset, int size,  int tyear, int tsemester) {
			List<MypageDTO> list = new ArrayList<>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuilder sb = new StringBuilder();

			try {

				sb.append(" SELECT distinct ");
				sb.append(" TO_CHAR(s.sYear, 'YYYY') sYear , s.subjectno, s.semester, d.departmentName, s.subjectName, a.name ");
				sb.append(" FROM subject s");
				sb.append(" LEFT outer join department  d");
				sb.append(" ON s.departmentNum = d.departmentNum ");
				sb.append(" LEFT outer join account a ");
				sb.append(" ON s.id = a.id ");
				sb.append(" LEFT outer join APPLICATIONASSISTANT ap ");
				sb.append(" ON s.subjectNo = ap.subjectNo  ");
				sb.append(" where to_char(sYear,'YYYY') >= ? AND semester = ?  AND  enable is null ");
				sb.append(" ORDER BY d.departmentName DESC ");
				sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

				pstmt = conn.prepareStatement(sb.toString());

				pstmt.setInt(1, tyear);
				pstmt.setInt(2, tsemester);
			
				pstmt.setInt(3, offset);
				pstmt.setInt(4, size);

				rs = pstmt.executeQuery();

				while (rs.next()) {
					MypageDTO dto = new MypageDTO();

					dto.setYear(rs.getString("sYear"));
					dto.setSubjectNo(rs.getString("subjectno"));
					dto.setSemester(rs.getString("semester"));
					dto.setDepartment(rs.getString("departmentName"));
					dto.setSubject(rs.getString("subjectName"));
					dto.setProfessor(rs.getString("name"));
			

					list.add(dto);
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

			return list;
		}
	

	// 검색에서 리스트 검색 2개 페이징 처리 
	List<MypageDTO> listSubmit(int offset, int size, String keyword, int tyear, int tsemester) {
		List<MypageDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {

			sb.append(" SELECT distinct ");
			sb.append(" TO_CHAR(s.sYear, 'YYYY') sYear , s.subjectno, s.semester, d.departmentName, s.subjectName, a.name ");
			sb.append(" FROM subject s");
			sb.append(" LEFT outer join department  d");
			sb.append(" ON s.departmentNum = d.departmentNum ");
			sb.append(" LEFT outer join account a ");
			sb.append(" ON s.id = a.id ");
			sb.append(" LEFT outer join APPLICATIONASSISTANT ap ");
			sb.append(" ON s.subjectNo = ap.subjectNo  ");
			sb.append(" where to_char(sYear,'YYYY') >= ? AND semester = ?  AND  enable is null ");

			if ( keyword != null ) {
				sb.append(" AND INSTR(subjectName, ?) >= 1 ");
			}
			sb.append(" ORDER BY d.departmentName DESC ");
			sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");

			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setInt(1, tyear);
			pstmt.setInt(2, tsemester);
			pstmt.setString(3, keyword);
			pstmt.setInt(4, offset);
			pstmt.setInt(5, size);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MypageDTO dto = new MypageDTO();

				dto.setYear(rs.getString("sYear"));
				dto.setSubjectNo(rs.getString("subjectno"));
				dto.setSemester(rs.getString("semester"));
				dto.setDepartment(rs.getString("departmentName"));
				dto.setSubject(rs.getString("subjectName"));
				dto.setProfessor(rs.getString("name"));
		

				list.add(dto);
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

		return list;
	}

	// 조교 신청 등록
	public void insertassiantance(String subjectNo, String id) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "INSERT INTO applicationAssistant (APPLICATIONNUM, STUDENTCODE, ENABLE, SUBJECTNO ) "
					+ " VALUES (APPLICATIONASSISTANT_SEQ.NEXTVAL, ?, 0, ?  ) ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, id);
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

	// 조교 신청 및 승인 내용 출력 - 학생 신청내역, 승인내역
	List<MypageDTO> readAssitance(String studentCode) {
		List<MypageDTO> alist = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {

			sql = " SELECT distinct  "
					+ " TO_CHAR(s.sYear, 'YYYY') sYear , s.subjectno, s.semester, d.departmentName, s.subjectName, a.name, ENABLE "
					+ " FROM subject s " + " LEFT outer join department  d " + " ON s.departmentNum = d.departmentNum "
					+ " LEFT outer join account a " + " ON s.id = a.id " + " LEFT outer join applicationAssistant ap "
					+ " ON s.subjectNo = ap.subjectNo " + " WHERE  sYear >= SYSDATE  AND  studentCode =  ? "
					+ " ORDER BY d.departmentName, ENABLE DESC ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, studentCode);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MypageDTO dto = new MypageDTO();

				dto.setYear(rs.getString("sYear"));
				dto.setSubjectNo(rs.getString("subjectno"));
				dto.setSemester(rs.getString("semester"));
				dto.setDepartment(rs.getString("departmentName"));
				dto.setSubject(rs.getString("subjectName"));
				dto.setProfessor(rs.getString("name"));
				dto.setENABLE(rs.getInt("ENABLE"));

				alist.add(dto);

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

		return alist;
	}
	
	//데이터 카운터 
	
	
	
	//검색 키워드 없음 
	List<MypageDTO> approvelist(int offset, int size, int ENABLE, String id, int tyear, int tsemester) {
		List<MypageDTO> aplist = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {

			sql = " select distinct  TO_CHAR(s.sYear, 'YYYY') sYear , s.semester, departmentName, subjectName, TO_CHAR(REG_DATE, 'YYYY-MM-DD') REG_DATE,  name, enable, APPLICATIONNUM  "
					+ " FROM APPLICATIONASSISTANT ap "
					+ " join account a "
					+ " on ap.studentCode = a.id "
					+ " join subject s " + " on s.subjectNo = ap.subjectNo " 
					+ " join department d "
					+ " on s.departmentNum = d.departmentNum " 
					+ " where enable= ? and s.id = ? and to_char(sYear,'YYYY') >= ? AND semester = ? "
					+ " ORDER BY departmentName DESC  OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, ENABLE);
			pstmt.setString(2, id);
			pstmt.setInt(3, tyear);
			pstmt.setInt(4, tsemester);
			pstmt.setInt(5, offset);
			pstmt.setInt(6, size);
		

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MypageDTO dto = new MypageDTO();

				dto.setYear(rs.getString("sYear"));
				dto.setSemester(rs.getString("semester"));
				dto.setDepartment(rs.getString("departmentName"));
				dto.setSubject(rs.getString("subjectName"));
				dto.setReg_date(rs.getString("reg_date"));
				dto.setName(rs.getString("name"));
				dto.setENABLE(rs.getInt("ENABLE"));
				dto.setApplicationNum(rs.getString("applicationNum"));

				aplist.add(dto);

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

		return aplist;
	}

	
	
	
 //학번, 이름, 과목명 
	// 조교 내역 - 관리자 -페이징 처리 필요 승인, 출력 신청자 0 , 승인 1, 반려 2
	List<MypageDTO> approvelist(int offset, int size, int ENABLE, String id, int tyear, int tsemester, String keyword) {
		List<MypageDTO> aplist = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {

			sql = " select distinct  TO_CHAR(s.sYear, 'YYYY') sYear , s.semester, departmentName, subjectName, TO_CHAR(REG_DATE, 'YYYY-MM-DD') REG_DATE,  name, enable, APPLICATIONNUM  "
					+ " FROM APPLICATIONASSISTANT ap "
					+ " join account a "
					+ " on ap.studentCode = a.id "
					+ " join subject s " + " on s.subjectNo = ap.subjectNo " 
					+ " join department d "
					+ " on s.departmentNum = d.departmentNum " 
					+ " where enable= ? and s.id = ? and to_char(sYear,'YYYY') >= ? AND semester = ? ";
					
					if ( keyword != null ) {
						sql += " AND INSTR(subjectName, ?) >= 1 ";
					}
					sql +=  " ORDER BY departmentName DESC  OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ";

			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, ENABLE);
			pstmt.setString(2, id);
			pstmt.setInt(3, tyear);
			pstmt.setInt(4, tsemester);
			pstmt.setString(5, keyword);
			pstmt.setInt(6, offset);
			pstmt.setInt(7, size);
		

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MypageDTO dto = new MypageDTO();

				dto.setYear(rs.getString("sYear"));
				dto.setSemester(rs.getString("semester"));
				dto.setDepartment(rs.getString("departmentName"));
				dto.setSubject(rs.getString("subjectName"));
				dto.setReg_date(rs.getString("reg_date"));
				dto.setName(rs.getString("name"));
				dto.setENABLE(rs.getInt("ENABLE"));
				dto.setApplicationNum(rs.getString("applicationNum"));

				aplist.add(dto);

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

		return aplist;
	}

	// 조교 승인해주기

	public void approve(String applicationNum) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = " UPDATE APPLICATIONASSISTANT SET ENABLE = 1 WHERE APPLICATIONNUM = ?   ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, applicationNum);

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

	// 조교 취소하기

	public void cancel(String applicationNum) throws SQLException {
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = " UPDATE APPLICATIONASSISTANT SET ENABLE = 2 WHERE APPLICATIONNUM = ?   ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, applicationNum);

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

}
