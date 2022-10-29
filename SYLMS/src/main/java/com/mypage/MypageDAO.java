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
		sb.append(" a.id, a.name, a.pwd, a.tel, a.email, TO_CHAR(a.birth, 'yyyy-mm-dd') birth, a.filename ,d.departmentName  ");
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
		
		if(rs.next()) {
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
	}finally {
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

			
				// 데이터 개수 구하기
				public int dataCount() {
					int result = 0;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					String sql;

					try {
						sql = " SELECT NVL (COUNT(*), 0) FROM subject WHERE  sYear >= sysdate";
						pstmt = conn.prepareStatement(sql);
	
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
			
				// 검색에서의 개수 구하기
				public int dataCount(String condition, String keyword) {
					int result = 0;
					PreparedStatement pstmt = null;
					ResultSet rs = null;
					String sql;

					try {
						sql = " SELECT NVL (COUNT(*), 0) FROM subject WHERE  sYear >= SYSDATE ";

						if (condition.equals("subjectName")) {
							sql += " AND INSTR(s.subjectName, ?) >= 1 ";
				
						}

						pstmt = conn.prepareStatement(sql);

						pstmt.setString(1, keyword);

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
			List<MypageDTO> listSubject( int offset, int size){
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
					sb.append(" WHERE  sYear >= SYSDATE ");
					sb.append(" ORDER BY d.departmentName DESC ");
					sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
					
					
					pstmt = conn.prepareStatement(sb.toString());
					
					
					pstmt.setInt(1, offset);
					pstmt.setInt(2, size);
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						 MypageDTO	dto = new MypageDTO();
						
						dto.setYear(rs.getString("sYear"));
						dto.setSemester(rs.getString("semester"));
						dto.setDepartment(rs.getString("departmentName"));
						dto.setSubject(rs.getString("subjectName"));
						dto.setProfessor(rs.getString("name"));
						dto.setSubjectNo(rs.getString("subjectno"));
						
						list.add(dto);
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
				
				
				return list;
			}
				
				

			
		
				
				
			//검색에서 리스트 
			List<MypageDTO> listSubmit(int offset, int size, String condition, String keyword){
				List<MypageDTO> list = new ArrayList<>();
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				StringBuilder sb = new StringBuilder();
				
				
				try {
					
					sb.append(" SELECT distinct ");
					sb.append(" TO_CHAR(s.sYear, 'YYYY') sYear, s.semester, s.subjectno, d.departmentName, s.subjectName, a.name ");
					sb.append(" FROM subject s");
					sb.append(" LEFT outer join department  d");
					sb.append(" ON s.departmentNum = d.departmentNum ");
					sb.append(" LEFT outer join account a ");
					sb.append(" ON s.id = a.id ");
					sb.append(" WHERE  sYear >= SYSDATE ");
					
					
					if (condition.equals("subjectName")) {
						sb.append(" AND INSTR(s.subjectName, ?) >= 1 ");
					}
					sb.append(" ORDER BY d.departmentName DESC ");
					sb.append(" OFFSET ? ROWS FETCH FIRST ? ROWS ONLY ");
					
					
					
					pstmt = conn.prepareStatement(sb.toString());
					
				
						pstmt.setString(1, keyword);
						pstmt.setInt(2, offset);
						pstmt.setInt(3, size);
				
						
						rs = pstmt.executeQuery();
						
						while(rs.next()) {
							MypageDTO dto = new MypageDTO();
							
							
							dto.setYear(rs.getString("sYear"));
							dto.setSemester(rs.getString("semester"));
							dto.setDepartment(rs.getString("departmentName"));
							dto.setSubject(rs.getString("subjectName"));
							dto.setProfessor(rs.getString("name"));
							dto.setSubjectNo(rs.getString("subjectno"));
							
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
			public void insertassiantance( String subjectNo, String id) throws SQLException {
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
					if(pstmt != null) {
						try {
							pstmt.close();
						} catch (SQLException e) {
						}
					}
				}

			}
	
			
			
			// 조교 신청 및 승인 내용 출력
			List<MypageDTO>  readAssitance(String studentCode) {
				List<MypageDTO> alist = new ArrayList<>();
			       PreparedStatement pstmt = null;
			       ResultSet rs = null;
			       String  sql;
			       
			       try {
			    	   
			    	sql = " SELECT distinct  "
			    			+ " TO_CHAR(s.sYear, 'YYYY') sYear , s.subjectno, s.semester, d.departmentName, s.subjectName, a.name, ENABLE "
			    			+ " FROM subject s "
			    			+ " LEFT outer join department  d "
			    			+ " ON s.departmentNum = d.departmentNum "
			    			+ " LEFT outer join account a "
			    			+ " ON s.id = a.id "
			    			+ " LEFT outer join applicationAssistant ap "
			    			+ " ON s.subjectNo = ap.subjectNo "
			    			+ " WHERE  sYear >= SYSDATE  AND  studentCode =  ? "
			    			+ " ORDER BY d.departmentName, ENABLE DESC ";
					
					pstmt = conn.prepareStatement(sql);
					
					pstmt.setString(1, studentCode);
					
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						MypageDTO dto = new MypageDTO();
						
						
						dto.setYear(rs.getString("sYear"));
						dto.setSubjectNo(rs.getString("subjectno"));
						dto.setSemester(rs.getString("semester"));
						dto.setDepartment(rs.getString("departmentName"));
						dto.setSubject(rs.getString("subjectName"));
						dto.setProfessor(rs.getString("name"));
						dto.setENABLE(rs.getString("ENABLE"));
						
						
						alist.add(dto);
						
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
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
			       
			       return alist;
				}
		
			//조교승인내역
			
			
			
			//조교 신청 내역
	
}
