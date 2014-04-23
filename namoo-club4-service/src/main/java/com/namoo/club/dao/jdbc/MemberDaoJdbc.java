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

import com.namoo.club.dao.MemberDao;
import com.namoo.club.shared.exception.NamooClubExceptionFactory;

import dom.entity.ClubManager;
import dom.entity.ClubMember;
import dom.entity.CommunityManager;
import dom.entity.CommunityMember;
import dom.entity.SocialPerson;

@Repository
public class MemberDaoJdbc implements MemberDao {
	//
	@Autowired
	private DataSource dataSource;
	
	@Override
	public void addCommunityManager(int comNo, CommunityManager comManager) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO communitymember(com_no, email, is_manager) VALUES (?, ?, '1')";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, comNo);
			pstmt.setString(2, comManager.getEmail());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw NamooClubExceptionFactory.createRuntime("커뮤니티 매니저로 등록하는 중 오류가 발생하였습니다.");
		} finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {}
			if (conn != null)try {conn.close();} catch (SQLException e) {}
		}
	}

	@Override
	public void addCommunityMember(int comNo, CommunityMember comMember) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			conn = dataSource.getConnection();
			String sql = "INSERT INTO communitymember(com_no, email, is_manager) VALUES (?, ?, '2')";
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, comNo);
			pstmt.setString(2, comMember.getEmail());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw NamooClubExceptionFactory.createRuntime("커뮤니티 멤버로 등록하는 중 오류가 발생하였습니다.");
		} finally {
			if (rset != null)try {rset.close();} catch (SQLException e) {}
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {}
			if (conn != null)try {conn.close();} catch (SQLException e) {}
		}
	}
	
	@Override
	public CommunityMember readCommunityMember(int comNo, String email) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
	    CommunityMember comMember = null;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT a.com_no, a.email, b.name FROM communitymember A JOIN user b ON a.email = b.email WHERE a.com_no = ? AND a.email=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comNo);
			pstmt.setString(2, email);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				int _comNo = rset.getInt("com_no");
				String _email = rset.getString("email");
				String name = rset.getString("name");
				comMember = new CommunityMember(_comNo, new SocialPerson(_email, name));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw NamooClubExceptionFactory.createRuntime("커뮤니티 멤버를 조회하는 중 오류가 발생하였습니다.");
		} finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	}
			if (conn != null)try {conn.close();} catch (SQLException e) {}
		}
		return comMember;
	}

	@Override
	public CommunityManager readCommunityManager(int comNo) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		CommunityManager comManager = null;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT a.com_no, a.email, a.is_manager, b.name FROM communitymember A JOIN user b ON a.email = b.email WHERE com_no = ? AND is_manager ='1'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comNo);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				int comNo2 = rset.getInt("com_no");
				String email = rset.getString("email");
				String name = rset.getString("name");
				comManager = new CommunityManager(comNo2, new SocialPerson(email, name));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw NamooClubExceptionFactory.createRuntime("커뮤니티 매니저를 조회하는 중 오류가 발생하였습니다.");
		} finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	}
			if (conn != null)try {conn.close();} catch (SQLException e) {}
		}
		return comManager;
	}

	@Override
	public List<CommunityMember> readAllCommunityMember(int comNo) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
	    List<CommunityMember> members = new ArrayList<CommunityMember>();
	    
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT a.com_no, a.email, a.is_manager, b.name " +
						"FROM communitymember A JOIN user b ON a.email = b.email WHERE com_no = ? ORDER BY com_no";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comNo);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				String name = rset.getString("name");
				String email = rset.getString("email");
				
				CommunityMember comMember = new CommunityMember(comNo, new SocialPerson(email, name));
				members.add(comMember);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw NamooClubExceptionFactory.createRuntime("모든 커뮤니티 멤버를 조회하는 중 오류가 발생하였습니다.");
		} finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	}
			if (conn != null)try {conn.close();} catch (SQLException e) {}
		}
		return members;
	}
	
	@Override
	public void deleteAllComMember(int comNo) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			String sql = "DELETE FROM communitymember WHERE com_no = ? AND is_manager = '2'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comNo);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw NamooClubExceptionFactory.createRuntime("모든 커뮤니티 멤버쉽을 삭제하는 중 오류가 발생하였습니다.");
		} finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	}
			if (conn != null)try {conn.close();} catch (SQLException e) {}
		}
	}

	@Override
	public void deleteCommuninyMember(int comNo, String email) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			String sql = "DELETE FROM communitymember WHERE com_no = ? AND is_manager = '2' AND email=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comNo);
			pstmt.setString(2, email);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw NamooClubExceptionFactory.createRuntime("커뮤니티 멤버를 삭제하는 중 오류가 발생하였습니다.");
		} finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	}
			if (conn != null)try {conn.close();} catch (SQLException e) {}
		}
	}

	@Override
	public void deleteCommunityManager(int comNo) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			String sql = "DELETE FROM communitymember WHERE com_no = ? AND is_manager = '1'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, comNo);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw NamooClubExceptionFactory.createRuntime("커뮤니티 멤버쉽을 삭제하는 중 오류가 발생하였습니다.");
		} finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	}
			if (conn != null)try {conn.close();} catch (SQLException e) {}
		}
	}
	
	
	//------------------------------------------------------------------------------------------------------------------
	@Override
	public void addClubMember(ClubMember clubMember) {
		// 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		try {
			conn = dataSource.getConnection();
			
			String sql = "INSERT INTO clubmember(club_no, email, type) VALUES(?, ?, 'c')";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, clubMember.getClubNo());
			pstmt.setString(2, clubMember.getEmail());
			
			pstmt.executeUpdate();
			
			resultSet = pstmt.getGeneratedKeys();
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
			 if ( resultSet != null) try { resultSet.close(); } catch (SQLException e) { }
			 if ( pstmt != null) try { pstmt.close(); } catch (SQLException e) { }
			 if ( conn != null) try { conn.close(); } catch (SQLException e) { }
		}
	}

	@Override
	public void addClubManager(ClubManager clubManager) {
		// 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		
		try {
			conn = dataSource.getConnection();
			
			String sql = "INSERT INTO clubmember(club_no, email, type)"
					+ " VALUES(?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, clubManager.getClubNo());
			pstmt.setString(2, clubManager.getEmail());
			pstmt.setString(3, clubManager.isKingManager() ? "a" : "b");
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
				e.printStackTrace();
		} finally {
			 if ( resultSet != null) try { resultSet.close(); } catch (SQLException e) { }
			 if ( pstmt != null) try { pstmt.close(); } catch (SQLException e) { }
			 if ( conn != null) try { conn.close(); } catch (SQLException e) { }
		}
	}
	
	@Override
	public void deleteAllClubMember(int clubNo) {
		// 
		deleteAllClubMembership(clubNo, "c");
	}

	@Override
	public void deleteAllClubManager(int clubNo) {
		// 
		deleteAllClubMembership(clubNo, "b");
	}
	
	private void deleteAllClubMembership(int clubNo, String type) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			
			String sql = "DELETE FROM clubmember WHERE club_no = ? AND type = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, clubNo);
			pstmt.setString(2, type);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if ( pstmt != null) try { pstmt.close(); } catch (SQLException e) { }
			if ( conn != null) try { conn.close(); } catch (SQLException e) { }
		}
	}
	
	@Override
	public void deleteClubManager(int clubNo, String email) {
		//
		deleteClubMembership(clubNo, email, "b");
		
	}
	
	@Override
	public void deleteClubMember(int clubNo, String email) {
		// 
		deleteClubMembership(clubNo, email, "c");
		
	}

	@Override
	public void deleteClubKingManger(int clubNo) {
		// 
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			
			String sql = "DELETE FROM clubmember WHERE club_no = ? AND type = 'a'";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, clubNo);
		
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw NamooClubExceptionFactory.createRuntime("클럽 대표관리자를 삭제하는 중 오류가 발생하였습니다.");
		} finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	}
			if (conn != null)try {conn.close();} catch (SQLException e) {}
		}
		
	}

	private void deleteClubMembership(int clubNo, String email, String type) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = dataSource.getConnection();
			
			String sql = "DELETE FROM clubmember WHERE club_no = ? AND email=? AND type = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, clubNo);
			pstmt.setString(2, email);
			pstmt.setString(3, type);
		
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw NamooClubExceptionFactory.createRuntime("커뮤니티 멤버쉽을 삭제하는 중 오류가 발생하였습니다.");
		} finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	}
			if (conn != null)try {conn.close();} catch (SQLException e) {}
		}
	}

	@Override
	public List<ClubMember> readAllClubMembers(int clubNo) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
	    List<ClubMember> members = new ArrayList<ClubMember>();
	    
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT a.club_no, a.email, b.name FROM clubmember A JOIN user b ON a.email = b.email WHERE club_no = ? ORDER BY email";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, clubNo);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				String email = rset.getString("email");
				String name = rset.getString("name");
				
				ClubMember clubMember = new ClubMember(clubNo, new SocialPerson(email, name));
				members.add(clubMember);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw NamooClubExceptionFactory.createRuntime("모든 클럽 멤버를 조회하는 중 오류가 발생하였습니다.");
		} finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	}
			if (conn != null)try {conn.close();} catch (SQLException e) {}
		}
		return members;
	}


	@Override
	public ClubMember readClubMember(int clubNo, String email) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
	    ClubMember clubMember = null;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT a.club_no, a.email, b.name FROM clubmember A JOIN user b ON a.email=b.email WHERE a.club_no = ? AND a.email=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, clubNo);
			pstmt.setString(2, email);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				int _clubNo = rset.getInt("club_no");
				String _email = rset.getString("email");
				String name = rset.getString("name");
				clubMember = new ClubMember(_clubNo, new SocialPerson(_email, name));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw NamooClubExceptionFactory.createRuntime("클럽 멤버를 조회하는 중 오류가 발생하였습니다.");
		} finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	}
			if (conn != null)try {conn.close();} catch (SQLException e) {}
		}
		return clubMember;
	}

	@Override
	public List<ClubManager> readAllClubManagers(int clubNo) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<ClubManager> managers = new ArrayList<>();
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT a.club_no, a.email, b.name, a.type FROM clubmember a JOIN user b ON a.email = b.email WHERE club_no = ? AND type IN ('a','b') ORDER BY club_no";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, clubNo);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				int clubNo2 = rset.getInt("club_no");
				String email = rset.getString("email");
				String name = rset.getString("name");
				String type = rset.getString("type");
				ClubManager clubManager = new ClubManager(clubNo2, new SocialPerson(email, name));
				if ("a".equals(type)) {
					clubManager.setKingManager(true);
				}
				managers.add(clubManager);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw NamooClubExceptionFactory.createRuntime("커뮤니티 멤버를 조회하는 중 오류가 발생하였습니다.");
		} finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	}
			if (conn != null)try {conn.close();} catch (SQLException e) {}
		}
		return managers;
	}

	@Override
	public ClubManager readClubManager(int clubNo, String email, String type) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ClubManager clubManager = null;
		try {
			conn = dataSource.getConnection();
			String sql = "SELECT a.club_no, a.email, b.name, a.type FROM clubmember A JOIN user b ON a.email=b.email WHERE a.club_no = ? AND a.type=? AND a.email=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, clubNo);
			pstmt.setString(2, type);
			pstmt.setString(3, email);
			
			rset = pstmt.executeQuery();
			
			while (rset.next()) {
				int _clubNo = rset.getInt("club_no");
				String _email = rset.getString("email");
				String name = rset.getString("name");
				clubManager = new ClubManager(_clubNo, new SocialPerson(_email, name));
				if ("a".equals(type)) {
					clubManager.setKingManager(true);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw NamooClubExceptionFactory.createRuntime("커뮤니티 멤버를 조회하는 중 오류가 발생하였습니다.");
		} finally {
			if (pstmt != null)try {pstmt.close();} catch (SQLException e) {	}
			if (conn != null)try {conn.close();} catch (SQLException e) {}
		}
		return clubManager;
	}
}
