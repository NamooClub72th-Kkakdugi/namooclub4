package com.namoo.club.service.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.namoo.club.dao.ClubDao;
import com.namoo.club.dao.CommunityDao;
import com.namoo.club.dao.MemberDao;
import com.namoo.club.dao.UserDao;
import com.namoo.club.service.facade.ClubService;
import com.namoo.club.shared.exception.NamooClubExceptionFactory;

import dom.entity.Club;
import dom.entity.ClubKingManager;
import dom.entity.ClubManager;
import dom.entity.ClubMember;
import dom.entity.Community;
import dom.entity.SocialPerson;

@Service
public class ClubServiceLogic implements ClubService {
	//
	@Autowired
	private ClubDao clubDao;
	@Autowired
	private CommunityDao comDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private UserDao userDao;

	// ------------------------------------------------------------------------

	@Override
	public Club registClub(int categoryNo, int communityNo, String clubName, String description, String email) {
		//
		if (isExistClubByName(communityNo, clubName)) {
			throw NamooClubExceptionFactory.createRuntime("이미 존재하는 클럽입니다.");
		}

		Club club = new Club(categoryNo, communityNo, clubName, description, new SocialPerson(email, "asdf"));
		int clubNo = clubDao.createClub(communityNo, club);

		SocialPerson person = userDao.readUser(email);
		
		memberDao.addKingManager(new ClubKingManager(clubNo, person));
		return club;
	}

	private boolean isExistClubByName(int communityNo, String clubName) {
		//
		Community community = comDao.readCommunity(communityNo);
		List<Club> clubs = community.getClubs();

		if (community != null && !clubs.isEmpty()) {
			for (Club club : clubs) {
				if (club.getName().equals(clubName)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Club findClub(int clubNo) {
		//
		Club club = clubDao.readClub(clubNo);
		club.setKingManager(memberDao.readClubKingManager(clubNo));
		club.setManager(memberDao.readAllClubManagers(clubNo));
		club.setMember(memberDao.readAllClubMembers(clubNo));
		return club;
	}

	@Override
	public void joinAsMember(int clubNo, String email) {
		//
		Club club = clubDao.readClub(clubNo);
		if (club == null) {
			throw NamooClubExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}

		memberDao.addClubMember(new ClubMember(clubNo, new SocialPerson(email, "qwer")));
	}

	@Override
	public List<Club> findAllClubs(int comNo) {
		//
		return clubDao.readAllClubs(comNo);
	}

	@Override
	public ClubMember findClubMember(int clubNo, String email) {
		//
		Club club = clubDao.readClub(clubNo);
		if (club == null) {
			throw NamooClubExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}

		return memberDao.readClubMember(clubNo, email);
	}

	@Override
	public List<ClubMember> findAllClubMember(int clubNo) {
		//
		Club club = clubDao.readClub(clubNo);
		if (club == null) {
			throw NamooClubExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}
		return memberDao.readAllClubMembers(clubNo);
	}

	@Override
	public int countMembers(int clubNo) {
		//
		Club club = clubDao.readClub(clubNo);
		if (club == null) {
			throw NamooClubExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}
		return memberDao.readAllClubMembers(clubNo).size();
	}

	@Override
	public void removeClub(int clubNo, int comNo, boolean forcingRemove) {
		//
		if (clubDao.readClub(clubNo) == null) {
			throw NamooClubExceptionFactory.createRuntime("존재하지 않는 클럽입니다.");
		}
		if (forcingRemove) {
			memberDao.deleteAllClubMember(clubNo);
			memberDao.deleteAllClubManager(clubNo);
			memberDao.deleteClubKingManger(clubNo);
			clubDao.deleteClub(clubNo);
		} else {
		throw NamooClubExceptionFactory.createRuntime("멤버부터 탈퇴시키세요.");
		}

	}

	@Override
	public List<Club> findBelongClubs(String email, int comNo) {
		//
		List<Club> clubs = clubDao.readAllClubs(comNo);
		if (clubs == null)
			return null;

		List<Club> belongs = new ArrayList<>();

		for (Club club : clubs) {
			if (memberDao.readClubMember(club.getClubNo(), email) != null) {
				belongs.add(club);
			}
		}
		return belongs;
	}

	@Override
	public List<Club> findManagedClubs(String email, int comNo) {
		//
		List<Club> clubs = clubDao.readAllClubs(comNo);
		if (clubs == null)
			return null;

		List<Club> managers = new ArrayList<>();
		for (Club club : clubs) {
			if (memberDao.readClubManager(club.getClubNo(), email) != null) {

				managers.add(club);
			}
		}
		return managers;

	}

	@Override
	public void withdrawalClub(int clubNo, String email) {
		//
		Club club = clubDao.readClub(clubNo);
		if (club == null) {
			throw NamooClubExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}
		memberDao.deleteClubMember(clubNo, email);
	}

	@Override
	public void commissionManagerClub(int clubNo, SocialPerson originPerson, SocialPerson nwPerson) {
		//
		memberDao.deleteClubManager(clubNo, originPerson.getEmail());
		memberDao.addClubMember(new ClubMember(clubNo, originPerson));
		memberDao.deleteClubMember(clubNo, nwPerson.getEmail());
		memberDao.addClubManager(new ClubManager(clubNo, nwPerson));
	}

	@Override
	public void commissionGoKingManagerClub(int clubNo, SocialPerson originPerson, SocialPerson nwPerson) {
		//
		memberDao.deleteClubKingManger(clubNo);
		memberDao.addClubManager(new ClubManager(clubNo, originPerson));
		memberDao.deleteClubManager(clubNo, nwPerson.getEmail());
		memberDao.addKingManager(new ClubKingManager(clubNo, nwPerson));
	}

	@Override
	public List<ClubManager> findAllClubManager(int clubNo) {
		//
		Club club = clubDao.readClub(clubNo);
		if (club == null) {
			throw NamooClubExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}
		return memberDao.readAllClubManagers(clubNo);
	}

	@Override
	public ClubManager findClubManager(int clubNo, String email) {
		//
		Club club = clubDao.readClub(clubNo);
		if (club == null) {
			throw NamooClubExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}

		return memberDao.readClubManager(clubNo, email);
	}

	@Override
	public ClubKingManager findClubKingManager(int clubNo) {
		//
		Club club = clubDao.readClub(clubNo);
		if (club == null) {
			throw NamooClubExceptionFactory.createRuntime("클럽이 존재하지 않습니다.");
		}

		return memberDao.readClubKingManager(clubNo);
	}

}
