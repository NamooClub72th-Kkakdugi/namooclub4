package com.namoo.club.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.namoo.club.dao.UserDao;
import com.namoo.club.shared.exception.NamooClubExceptionFactory;

import dom.entity.SocialPerson;

@Repository
public class UserDaoJdbc implements UserDao {
	//
	@Autowired
	private DataSource dataSource;
	
	@Override
	public List<SocialPerson> readAllUsers() {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<SocialPerson> users = new ArrayList<SocialPerson>();

		try {
			conn = dataSource.getConnection();
			String sql = "SELECT email, name, password FROM user ORDER BY email";
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				String email = rset.getString("email");
				String name = rset.getString("name");
				String password = rset.getString("password");

				SocialPerson user = new SocialPerson(name, email, password);
				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw NamooClubExceptionFactory.createRuntime("사용자 목록을 조회하는 중 오류가 발생했습니다.");
		} finally {
			if (rset != null) try { rset.close(); } catch (SQLException e) { }
			if (pstmt != null) try {pstmt.close();} catch (SQLException e) { }
			if (conn != null) try { conn.close(); } catch (SQLException e) { }
		}
		return users;
	}

	@Override
	public SocialPerson readUser(String email) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		SocialPerson user = null;

		try {
			conn = dataSource.getConnection();
			String sql = "SELECT email, name, password FROM user WHERE email=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, email);
			
			rset = pstmt.executeQuery();
			

			if (rset.next()) {
				String name = rset.getString("name");
				String email2 = rset.getString("email");
				String password = rset.getString("password");
				user = new SocialPerson(name, email2, password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw NamooClubExceptionFactory.createRuntime("이메일을 받아 사용자를 조회하는 중 오류가 발생하였습니다.");
		} finally {
			if (rset != null) try { rset.close(); } catch (SQLException e) { }
			if (pstmt != null) try {pstmt.close();} catch (SQLException e) { }
			if (conn != null) try { conn.close(); } catch (SQLException e) { }
		}
		return user;
	}

	@Override
	public void createUser(SocialPerson user) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO user(email, name, password) VALUES(?, ?,  ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user.getEmail());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getPassword());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw NamooClubExceptionFactory.createRuntime("사용자를 생성하는 중 오류가 발생하였습니다.");
		} finally {
			if (pstmt != null) try {pstmt.close();} catch (SQLException e) { }
			if (conn != null) try { conn.close(); } catch (SQLException e) { }
		}
	}

	@Override
	public void updateUser(SocialPerson user) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "UPDATE user SET password=? WHERE email=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getEmail());
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw NamooClubExceptionFactory.createRuntime("사용자 정보를 업데이트하는 중 오류가 발생하였습니다.");
		} finally {
			if (pstmt != null) try {pstmt.close();} catch (SQLException e) { }
			if (conn != null) try { conn.close(); } catch (SQLException e) { }
		}
	}

	@Override
	public void deleteUser(String email) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			String sql = "DELETE FROM user WHERE email=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, email);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw NamooClubExceptionFactory.createRuntime("사용자를 삭제하는 중 오류가 발생하였습니다.");
		} finally {
			if (pstmt != null) try {pstmt.close();} catch (SQLException e) { }
			if (conn != null) try { conn.close(); } catch (SQLException e) { }
		}
	}

}
