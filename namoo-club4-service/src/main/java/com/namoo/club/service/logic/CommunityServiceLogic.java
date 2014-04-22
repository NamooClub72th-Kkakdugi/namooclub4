package com.namoo.club.service.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namoo.club.dao.ClubDao;
import com.namoo.club.dao.CommunityDao;
import com.namoo.club.dao.MemberDao;
import com.namoo.club.dao.UserDao;
import com.namoo.club.service.facade.CommunityService;
import com.namoo.club.shared.exception.NamooClubExceptionFactory;

import dom.entity.Club;
import dom.entity.ClubCategory;
import dom.entity.Community;
import dom.entity.CommunityManager;
import dom.entity.CommunityMember;
import dom.entity.SocialPerson;

@Service
public class CommunityServiceLogic implements CommunityService {
	//
	@Autowired
	private CommunityDao dao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private ClubDao clubDao;

	// ------------------------------------------------------------------------
	@Override
	public Community registCommunity(String communityName, String description, String email, List<ClubCategory> categories) {
		//
		if (isExistCommunityByName(communityName)) {
			throw NamooClubExceptionFactory.createRuntime("이미 존재하는 게시판입니다.");
		}
		SocialPerson person = userDao.readUser(email);
		Community community = new Community(communityName, description, person);
		int communityNo = dao.createCommunity(community);
		CommunityManager comManager = new CommunityManager(communityNo, person);
		memberDao.addCommunityManager(communityNo, comManager);

		// 카테고리 추가
		registCategory(communityNo, categories);

		return community;
	}

	public void registCategory(int communityNo, List<ClubCategory> categories) {
		//
		for (ClubCategory category : categories) {
			dao.createClubCategory(communityNo, category);
		}
	}

	private boolean isExistCommunityByName(String communityName) {
		//
		List<Community> communities = dao.readAllCommunities();

		if (communities != null && !communities.isEmpty()) {
			for (Community community : communities) {
				if (community.getName().equals(communityName)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Community findCommunity(int communityNo) {
		//
		Community community = dao.readCommunity(communityNo);
		community.setCategories(dao.readAllCategories(communityNo));
		community.setManager(memberDao.readCommunityManager(communityNo));
		community.setMembers(memberDao.readAllCommunityMember(communityNo));
		community.setClubs(clubDao.readAllClubs(communityNo));
		return community;
	}

	@Override
	public void joinAsMember(int communityNo, String name, String email, String password) {
		//
		Community community = dao.readCommunity(communityNo);

		if (community == null) {
			throw NamooClubExceptionFactory.createRuntime("커뮤니티가 존재하지 않습니다.");
		}

		if (memberDao.readCommunityMember(communityNo, email) != null) {
			throw NamooClubExceptionFactory.createRuntime("이미 커뮤니티 회원입니다.");
		}

		SocialPerson user = new SocialPerson(name, email, password);
		userDao.createUser(user);
		memberDao.addCommunityMember(communityNo, new CommunityMember(communityNo, user));
	}

	@Override
	public void joinAsMember(int communityNo, String email) {
		//
		Community community = dao.readCommunity(communityNo);
		SocialPerson person = userDao.readUser(email);
		if (community == null) {
			throw NamooClubExceptionFactory.createRuntime("커뮤니티가 존재하지 않습니다.");
		}
		memberDao.addCommunityMember(communityNo, new CommunityMember(communityNo, person));
	}

	@Override
	public List<Community> findAllCommunities() {
		//
		return dao.readAllCommunities();
	}

	@Override
	public CommunityMember findCommunityMember(int communityNo, String email) {
		//
		Community community = dao.readCommunity(communityNo);

		if (community == null) {
			throw NamooClubExceptionFactory.createRuntime("커뮤니티가 존재하지 않습니다.");
		}

		for (CommunityMember member : memberDao.readAllCommunityMember(communityNo)) {
			if (member.getEmail().equals(email)) {
				return member;
			}
		}

		return null;
	}

	@Override
	public CommunityManager findCommunityManager(int communityNo) {
		//
		Community community = dao.readCommunity(communityNo);

		if (community == null) {
			throw NamooClubExceptionFactory.createRuntime("커뮤니티가 존재하지 않습니다.");
		}
		return memberDao.readCommunityManager(communityNo);
	}

	@Override
	public List<CommunityMember> findAllCommunityMember(int communityNo) {
		//
		Community community = dao.readCommunity(communityNo);
		if (community == null) {
			throw NamooClubExceptionFactory.createRuntime("커뮤니티가 존재하지 않습니다.");
		}
		return memberDao.readAllCommunityMember(communityNo);
	}

	@Override
	public int countMembers(int communityNo) {
		//
		Community community = dao.readCommunity(communityNo);
		if (community != null) {
			return memberDao.readAllCommunityMember(communityNo).size();
		}
		return 0;
	}

	@Override
	public void removeCommunity(int communityNo, boolean forcingRemove) {
		//
		List<Club> clubs = clubDao.readAllClubs(communityNo);
		if (forcingRemove) {
			// TODO deleteAllClubs(int comNo)
			for (Club club : clubs) {
				memberDao.deleteClubKingManger(club.getClubNo());
				memberDao.deleteAllClubManager(club.getClubNo());
				memberDao.deleteAllClubMember(club.getClubNo());
				clubDao.deleteClub(club.getClubNo());
			}
			dao.deleteAllClubCategory(communityNo);
			memberDao.deleteAllComMember(communityNo);
			memberDao.deleteCommunityManager(communityNo);
			dao.deleteCommunity(communityNo);
		} else {
			throw NamooClubExceptionFactory.createRuntime("하위 클럽부터 삭제하세요.");
		}
	}

	@Override
	public List<Community> findBelongCommunities(String email) {
		//
		List<Community> commnities = dao.readAllCommunities();
		if (commnities == null)
			return null;

		List<Community> belongs = new ArrayList<>();
		for (Community community : commnities) {
			if (memberDao.readCommunityMember(community.getComNo(), email) != null) {
				belongs.add(community);
			}
		}
		return belongs;
	}

	@Override
	public List<Community> findNotBelongCommunities(String email) {
		//
		List<Community> communities = dao.readAllCommunities();
		List<Community> belongs = new ArrayList<>();
		for (Community community : communities) {
			if (memberDao.readCommunityMember(community.getComNo(), email) != null) {
				belongs.add(community);
			}
		}
		List<Community> unjoinCommunities = new ArrayList<Community>(communities);
		List<Community> remove = new ArrayList<Community>();

		for (Community joinCommunity : belongs) {
			for (Community community : communities) {
				if (community.getComNo() == (joinCommunity.getComNo())) {
					remove.add(community);
					break;
				}
			}
		}
		if (!remove.isEmpty()) {
			unjoinCommunities.removeAll(remove);
		}
		return unjoinCommunities;
	}

	@Override
	public List<Community> findManagedCommunities(String email) {
		//
		List<Community> commnities = dao.readAllCommunities();
		if (commnities == null)
			return null;

		List<Community> managers = new ArrayList<>();
		for (Community community : commnities) {
			if (memberDao.readCommunityManager(community.getComNo()) != null) {
				managers.add(community);
			}
		}
		return managers;
	}

	@Override
	public void withdrawalCommunity(int communityNo, String email) {
		//
		Community community = dao.readCommunity(communityNo);
		if (community == null) {
			throw NamooClubExceptionFactory.createRuntime("커뮤니티가 존재하지 않습니다.");
		}
		memberDao.deleteCommuninyMember(communityNo, email);
	}

	@Override
	public void commissionManagerCommunity(int communityNo, SocialPerson originPerson, SocialPerson nwPerson) {
		//
		memberDao.deleteCommunityManager(communityNo);
		memberDao.addCommunityMember(communityNo, new CommunityMember(communityNo, originPerson));
		memberDao.deleteCommuninyMember(communityNo, nwPerson.getEmail());
		memberDao.addCommunityManager(communityNo, new CommunityManager(communityNo, nwPerson));
	}

	@Override
	public List<ClubCategory> findAllCategories(int communityNo) {
		//
		return dao.readAllCategories(communityNo);
	}

}
