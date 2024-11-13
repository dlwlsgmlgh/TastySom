package Dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import Dao.JDBCUtil;
import Dto.Student;

public class StudentDAO {
	
	private JDBCUtil jdbcUtil = null;
	//기본 생성자 
	public StudentDAO() {
		jdbcUtil = new JDBCUtil();
	}
	
	//새로운 학생 삽입
	public int insert(Student newStud) {

		String sql = "INSERT INTO StudentUser (student_id, name, password) VALUES (?, ?, ?)";
	    Object[] param = new Object[] { newStud.getStudentId(), newStud.getName(), newStud.getPassword() };	
	
		jdbcUtil.setSqlAndParameters(sql, param);
		try {    
			jdbcUtil.executeUpdate();  // insert 문 실행
		 
			
		} catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {		
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 반환
		}
		return 0;
	}
	
	//비밀번호 변경(아이디, 이름 변경 불가하니까 비밀번호만)  
	public int updatePassword(String id, String pwd) {
		
		String sql = "UPDATE StudentUser SET password = ? WHERE student_Id = ?";
		Object[] param = new Object[] {id, pwd};		
	
		jdbcUtil.setSqlAndParameters(sql, param);
		
		try {    
			jdbcUtil.executeUpdate();  // update 문 실행
		 } catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {		
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 반환
		}		
		return 0;
	}
	
	//id로 회원 삭제 
	public int delete(String id) {
		String sql = "DELETE FROM StudentUser WHERE student_Id = ?";
		Object[] param = new Object[] {id};		
	
		jdbcUtil.setSqlAndParameters(sql, param);
		
		try {    
			jdbcUtil.executeUpdate();  // delete 문 실행
		 } catch (Exception ex) {
			jdbcUtil.rollback();
			ex.printStackTrace();
		} finally {		
			jdbcUtil.commit();
			jdbcUtil.close();	// resource 반환
		}		
		return 0;
	}
	
	//id 중복 검사 
	public boolean existingId(String id) throws SQLException {
		String sql = "SELECT student_id FROM StudentUser WHRE student_id=?";      
		jdbcUtil.setSqlAndParameters(sql, new Object[] {id});	// JDBCUtil에 query문과 매개 변수 설정

		try {
			ResultSet rs = jdbcUtil.executeQuery();		// query 실행
			if (rs.next()) {
				int count = rs.getInt(1);
				return (count == 1 ? true : false);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			jdbcUtil.close();		// resource 반환
		}
		return false;
	}
}
