package com.namoo.club.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.namoo.club.dao.ClubDao;
import com.namoo.club.shared.exception.NamooClubExceptionFactory;

import dom.entity.Club;

@Repository
public class ClubDaoJdbc implements ClubDao {
	//
	@Autowired
	private DataSource dataSource;
	
	@Override
	public List<Club> readAllClubs(int comNo) {
		// 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		List<Club> clubs = new ArrayList<>();
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT club_no, category_no, club_nm, club_des, club_date FROM club WHERE com_no=? ORDER BY club_no";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comNo);
			
			resultSet = pstmt.executeQuery();
			while(resultSet.next()){
				//
				int clubNo = resultSet.getInt("club_no");
				int categoryNo = resultSet.getInt("category_no");
				String clubNm = resultSet.getString("club_nm");
				String clubDes = resultSet.getString("club_des");
				Date date = resultSet.getDate("club_date");

				Club club = new Club(categoryNo, comNo, clubNm, clubDes);
				club.setOpenDate(date);
				club.setClubNo(clubNo);
				clubs.add(club);
			}
		}
		 catch(SQLException e) {
			 e.printStackTrace();
			 NamooClubExceptionFactory.createRuntime("클럽목록조회중 오류발생");
		 } finally {
			 if ( resultSet != null) try { resultSet.close(); } catch (SQLException e) { }
			 if ( pstmt != null) try { pstmt.close(); } catch (SQLException e) { }
			 if ( conn != null) try { conn.close(); } catch (SQLException e) { }
		 }
		return clubs;
	}

	@Override
	public Club readClub(int clubNo) {
		// 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		Club club = null;
		try {
			conn = dataSource.getConnection();
			
			String sql = "SELECT club_no, com_no, category_no, club_nm, club_des, club_date FROM club WHERE club_no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, clubNo);
			
			resultSet= pstmt.executeQuery();
			
			if(resultSet.next()) {
				clubNo = resultSet.getInt("club_no");
				int comNo = resultSet.getInt("com_no");
				int categoryNo = resultSet.getInt("category_no");
				String clubName = resultSet.getString("club_nm");
				String clubDes = resultSet.getString("club_des");
				
				club = new Club(categoryNo, comNo, clubName, clubDes);
				club.setClubNo(clubNo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw NamooClubExceptionFactory.createRuntime("클럽을 조회하는 중 오류가 발생하였습니다.");
		} finally {
			 if ( resultSet != null) try { resultSet.close(); } catch (SQLException e) { }
			 if ( pstmt != null) try { pstmt.close(); } catch (SQLException e) { }
			 if ( conn != null) try { conn.close(); } catch (SQLException e) { }
		}

		return club;
	}

	@Override
	public int createClub(int comNo, Club club) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "INSERT INTO club(com_no, category_no, club_nm, club_des, club_date)"
					+ " VALUES(?, ?, ?, ?, sysdate())";
			pstmt = conn.prepareStatement(sql);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comNo);
			pstmt.setInt(2, club.getCategoryNo());
			pstmt.setString(3, club.getName());
			pstmt.setString(4, club.getDescription());
			
			pstmt.executeUpdate();
			
			resultSet = pstmt.getGeneratedKeys();
			if(resultSet.next()) {
				club.setClubNo(resultSet.getInt(1));
			} 
		} catch (SQLException e) {
				e.printStackTrace();
				throw NamooClubExceptionFactory.createRuntime("클럽을 생성하는 중 오류가 발생하였습니다.");
		} finally {
			 if ( resultSet != null) try { resultSet.close(); } catch (SQLException e) { }
			 if ( pstmt != null) try { pstmt.close(); } catch (SQLException e) { }
			 if ( conn != null) try { conn.close(); } catch (SQLException e) { }
		}
		return club.getClubNo();
	}

	@Override
	public void updateClub(Club club) {
		// 
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			
			String sql = "UPDATE club SET club_nm =?, club_des = ?, club_date = sysdate() WHERE club_no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, club.getName());
			pstmt.setString(2, club.getDescription());
			pstmt.setInt(3, club.getClubNo());
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw NamooClubExceptionFactory.createRuntime("클럽정보를 업데이트하는 중 오류가 발생하였습니다.");
		} finally {
			if ( pstmt != null) try { pstmt.close(); } catch (SQLException e) { }
			if ( conn != null) try { conn.close(); } catch (SQLException e) { }
		}
	}

	@Override
	public void deleteClub(int clubNo) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			
			String sql = "DELETE FROM club WHERE club_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, clubNo);
			
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw NamooClubExceptionFactory.createRuntime("클럽을 삭제하는 중 오류가 발생하였습니다.");
		} finally {
			if (conn != null) try { conn.close(); } catch (SQLException e) { }
			if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { }
		}
	}
	
	//----------------------------------------------
	
}
