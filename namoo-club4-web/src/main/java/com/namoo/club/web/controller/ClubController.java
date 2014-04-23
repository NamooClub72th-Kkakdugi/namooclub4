package com.namoo.club.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.namoo.club.service.facade.ClubService;
import com.namoo.club.service.facade.CommunityService;
import com.namoo.club.service.facade.UserService;
import com.namoo.club.web.controller.cmd.ClubCommand;
import com.namoo.club.web.controller.pres.PresClub;
import com.namoo.club.web.session.SessionManager;

import dom.entity.Club;
import dom.entity.ClubCategory;
import dom.entity.ClubManager;
import dom.entity.ClubMember;
import dom.entity.Community;
import dom.entity.SocialPerson;

/**
 * 클럽 컨트롤러
 * 
 * @author kosta-19
 */
@Controller
@RequestMapping(value = "/club")
public class ClubController {

	@Autowired
	private ClubService clubService;
	@Autowired
	private CommunityService comService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/clubList/{comNo}", method = RequestMethod.GET)
	public ModelAndView clubList(@PathVariable("comNo") int comNo, HttpServletRequest req) {
		//
		Community community = comService.findCommunity(comNo);
		SessionManager manager = new SessionManager(req);
		String email = manager.getLoginEmail();
		List<Club> belongClubs = clubService.findBelongClubs(email, comNo);
		List<Club> notBelongClubs = clubService.findNotBelogClubs(email, comNo);

		List<PresClub> presJoinClubs = convertAll(belongClubs, email);
		List<PresClub> presUnJoinClubs = convertAll(notBelongClubs, email);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("joinClubs", presJoinClubs);
		map.put("unJoinClubs", presUnJoinClubs);
		map.put("community", community);

		return new ModelAndView("/club/clubList", map);
	}

	@RequestMapping(value = "/clubCreateInput", method = RequestMethod.GET)
	public ModelAndView clubCreateInput(@RequestParam("comNo") int comNo) {

		Community community = comService.findCommunity(comNo);
		return new ModelAndView("/club/clubCreateInput", "community", community);
	}

	@RequestMapping(value = "/clubCreateCheck", method = RequestMethod.POST)
	public ModelAndView clubCreateCheck(ClubCommand command, HttpServletRequest req) {
		//
		Map<String, Object> map = new HashMap<String, Object>();
		Community community = comService.findCommunity(command.getCommunityNo());
		Club club = new Club(command.getCategoryNo(), command.getCommunityNo(), command.getClubName(), command.getClubDescription());
		PresClub presClub = new PresClub(club);
		presClub.setCategoryName(getCategoryNameBy(command.getCommunityNo(), command.getCategoryNo()));

		map.put("club", presClub);
		map.put("community", community);
		return new ModelAndView("/club/clubCreateCheck", map);
	}

	private String getCategoryNameBy(int communityNo, int categoryNo) {
		//
		List<ClubCategory> categories = comService.findAllCategories(communityNo);
		for (ClubCategory category : categories) {
			if (category.getCategoryNo() == categoryNo) {
				return category.getCategoryName();
			}
		}
		return null;
	}

	@RequestMapping(value = "/clubCreate", method = RequestMethod.POST)
	public RedirectView clubCreate(ClubCommand command, HttpServletRequest req) {
		//
		SessionManager manager = new SessionManager(req);
		clubService.registClub(command.getCategoryNo(), command.getCommunityNo(), command.getClubName(), command.getClubDescription(), manager.getLoginEmail());
		int comNo = command.getCommunityNo();

		return new RedirectView("/club/clubList/" + comNo, true);
	}

	@RequestMapping(value = "/clubJoinInput/{clubNo}", method = RequestMethod.GET)
	public ModelAndView clubJoinInput(@PathVariable("clubNo") int clubNo) {
		//
		Club club = clubService.findClub(clubNo);
		return new ModelAndView("/club/clubJoinInput", "club", club);
	}

	@RequestMapping(value = "/clubJoin/{clubNo}", method = RequestMethod.GET)
	public RedirectView clubJoin(HttpServletRequest req, @PathVariable("clubNo") int clubNo) {
		//
		Club club = clubService.findClub(clubNo);
		int comNo = club.getComNo();
		SessionManager manager = new SessionManager(req);
		clubService.joinAsMember(clubNo, manager.getLoginEmail());
		return new RedirectView("/club/clubList/" + comNo, true);
	}

	@RequestMapping(value = "/clubMemberList", method = RequestMethod.GET)
	public ModelAndView clubMemberList(@RequestParam("clubNo") int clubNo) {
		//
		List<ClubMember> clubMembers = clubService.findAllClubMember(clubNo);

		return new ModelAndView("/club/clubMemberList", "clubMemberList", clubMembers);

	}

	@RequestMapping(value = "/clubRemove/{clubNo}", method = RequestMethod.GET)
	public ModelAndView removeCom(@PathVariable("clubNo") int clubNo) {
		//
		Club club = clubService.findClub(clubNo);
		return new ModelAndView("/inform/clubRemoveCheck", "club", club);
	}

	@RequestMapping(value = "/clubRemove/{clubNo}", method = RequestMethod.POST)
	public RedirectView removeCommunity(@PathVariable("clubNo") int clubNo, @RequestParam("communityNo") int communityNo) {
		//
		clubService.removeClub(clubNo, true);
		return new RedirectView("/club/clubList/" + communityNo, true);
	}

	@RequestMapping(value = "/clubWithdrawlCheck/{clubNo}", method = RequestMethod.GET)
	public ModelAndView withdrawlCheckClub(@PathVariable("clubNo") int clubNo) {
		//
		Map<String, Object> map = new HashMap<String, Object>();
		Club club = clubService.findClub(clubNo);
		String communityName = comService.findCommunity(club.getComNo()).getName();

		map.put("club", club);
		map.put("communityName", communityName);
		return new ModelAndView("/inform/clubWithdrawlCheck", map);
	}

	@RequestMapping(value = "/clubWithdrawl/{clubNo}", method = RequestMethod.POST)
	public ModelAndView withdrawlClub(@PathVariable("clubNo") int clubNo, HttpServletRequest req) {
		//
		int comNo = clubService.findClub(clubNo).getComNo();
		SessionManager manager = new SessionManager(req);
		clubService.withdrawalClub(clubNo, manager.getLoginEmail());
		return new ModelAndView("/inform/clubWithdrawl", "communityNo", comNo);
	}

	@RequestMapping(value="/clubSelectMem/{clubNo}", method=RequestMethod.GET)
	public ModelAndView assignManagerClub(@PathVariable("clubNo") int clubNo) {
		//
		Map<String, Object> map = new HashMap<String, Object>();
		List<ClubMember> members = clubService.findAllClubMember(clubNo);
		List<ClubManager> managers = clubService.findAllClubManager(clubNo);
		members = filter(members, managers);
		
		Club club = clubService.findClub(clubNo);
		
		map.put("members", members);
		map.put("club", club);
		return new ModelAndView("/commission/clubSelectMem", map);
	}
	
	@RequestMapping(value="/clubCommission/{clubNo}", method = RequestMethod.GET)
	public ModelAndView commissionManagerClub(@PathVariable("clubNo") int clubNo) {
		//
		Map<String, Object> map = new HashMap<String, Object>();
		List<ClubMember> members = clubService.findAllClubMember(clubNo);
		List<ClubManager> managers = clubService.findAllClubManager(clubNo);
		members = filter(members, managers);
		
		Club club = clubService.findClub(clubNo);
		
		map.put("members", members);
		map.put("club", club);
		return new ModelAndView("/commission/clubSelectMemToManager", map);
	}
	
	@RequestMapping(value="/clubCommission/{clubNo}", method = RequestMethod.POST)
	public RedirectView commissionManagerClub(HttpServletRequest req, @PathVariable("clubNo") int clubNo, @RequestParam("email") String email) {
		//
		SessionManager manager = new SessionManager(req);
		SocialPerson originPerson = userService.findTowner(manager.getLoginEmail());
		SocialPerson nwPerson = userService.findTowner(email);
		clubService.commissionManagerClub(clubNo, originPerson, nwPerson);
		int communityNo = clubService.findClub(clubNo).getComNo();
		
		return new RedirectView("/club/clubList/"+ communityNo, true);
	}

	@RequestMapping(value="/clubAssignManager/{clubNo}", method = RequestMethod.POST)
	public RedirectView assignManager(@PathVariable("clubNo") int clubNo, @RequestParam("email") String email) {
		//
		SocialPerson person = userService.findTowner(email);
		clubService.assignManagerClub(clubNo, person);
		int communityNo = clubService.findClub(clubNo).getComNo();
		return new RedirectView("/club/clubList/" + communityNo, true);
	}
	
	@RequestMapping(value="/clubSelectMng/{clubNo}", method=RequestMethod.GET)
	public ModelAndView commissionKingManager(@PathVariable("clubNo") int clubNo) {
		//
		Map<String, Object> map = new HashMap<String, Object>();
		List<ClubManager> managers = clubService.findAllClubManager(clubNo);
		ClubManager kingManager = clubService.findClubKingManager(clubNo);
		managers = filterManager(managers, kingManager);
		Club club = clubService.findClub(clubNo);
		
		map.put("managers", managers);
		map.put("club", club);
		
		return new ModelAndView("/commission/clubSelectMng", map);
	}
	
	@RequestMapping(value="/clubSelectMng/{clubNo}", method=RequestMethod.POST)
	public RedirectView commissionKingManagerClub(HttpServletRequest req, @PathVariable("clubNo") int clubNo, @RequestParam("email") String email) {
		//
		SessionManager manager = new SessionManager(req);
		SocialPerson originPerson = userService.findTowner(manager.getLoginEmail());
		SocialPerson nwPerson = userService.findTowner(email);
		clubService.commissionKingManagerClub(clubNo, originPerson, nwPerson);
		
		int communityNo = clubService.findClub(clubNo).getComNo();
		return new RedirectView("/club/clubList/"+communityNo, true);
	}

	// -----------------------------------------------------------------------------------------
	// private method

	/**
	 * view에 표현하기위한 Club객체
	 * 
	 * @param clubs
	 * @param loginEmail
	 * @return
	 */
	private List<PresClub> convertAll(List<Club> clubs, String loginEmail) {
		//
		List<PresClub> presClubs = new ArrayList<PresClub>();
		for (Club club : clubs) {
			PresClub presClub = new PresClub(clubService.findClub(club.getClubNo()));
			presClub.setLoginEmail(loginEmail);
			presClubs.add(presClub);
		}
		return presClubs;

	}

	/**
	 * 멤버들 중에서 매니저를 제외한 멤버들 목록조회
	 * 
	 * @param members
	 * @param managers
	 * @return
	 */
	private List<ClubMember> filter(List<ClubMember> members, List<ClubManager> managers) {
		//
		List<ClubMember> found = new ArrayList<ClubMember>();
		for (ClubMember member : members) {
			for (ClubManager manager : managers) {
				if (member.getEmail().equals(manager.getEmail())) {
					found.add(member);
				}
			}
		}
		if (found != null) {
			members.removeAll(found);
		}
		return members;
	}
	
	/**
	 * kingManager를 제외한 manager목록조회
	 * 
	 * @param managers
	 * @param kingManager
	 * @return
	 */
	private List<ClubManager> filterManager(List<ClubManager> managers, ClubManager kingManager) {
		//
		ClubManager found = null;
		for (ClubManager manager : managers) {
			if (manager.getEmail().equals(kingManager.getEmail())) {
				found = manager;
				break;
			}
		}
		if (found != null) {
			managers.remove(found);
	}
		return managers;
	}

}
