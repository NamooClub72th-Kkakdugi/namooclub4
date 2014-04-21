package com.namoo.club.service.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namoo.club.dao.CommunityDao;
import com.namoo.club.dao.MemberDao;
import com.namoo.club.dao.UserDao;
import com.namoo.club.service.facade.UserService;
import com.namoo.club.shared.exception.NamooClubException;

import dom.entity.Community;
import dom.entity.SocialPerson;

@Service
public class UserServiceLogic implements UserService {
	//
	@Autowired
	private UserDao userDao;
	@Autowired
	private CommunityDao comDao;
	@Autowired
	private MemberDao memberDao;
	
	// ------------------------------------------------------------------------
	@Override
	public boolean loginAsTowner(String email, String password) {
		//
		SocialPerson user = userDao.readUser(email);
		if (user != null && password.equals(user.getPassword())) {
			return true;
		}
		return false;
	}

	@Override
	public void registTowner(String name, String email, String password) {
		//
		if (userDao.readUser(email) != null) {
			throw new NamooClubException("이미 가입되어있는 사용자입니다.");
		}
		SocialPerson user = new SocialPerson(name, email, password);
		userDao.createUser(user);
	}

	@Override
	public void removeTowner(String email) {
		//
		List<Community> communities = comDao.readAllCommunities();
		if (communities != null) {
			for (Community community : communities) {
				if (email.equals(memberDao.readCommunityManager(community.getComNo()))) {
					throw new NamooClubException("게시판 관리자는 탈퇴할 수 없습니다.");
				}
			}
		}
		userDao.deleteUser(email);
	}

	@Override
	public SocialPerson findTowner(String email) {
		//
		return userDao.readUser(email);
	}

	@Override
	public List<SocialPerson> findAllTowner() {
		//
		return userDao.readAllUsers();
	}

}
