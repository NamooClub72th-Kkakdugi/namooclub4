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

import com.namoo.club.service.facade.ClubService;
import com.namoo.club.service.facade.CommunityService;
import com.namoo.club.service.facade.UserService;
import com.namoo.club.web.controller.pres.PresClub;
import com.namoo.club.web.controller.pres.PresCommunity;
import com.namoo.club.web.session.LoginRequired;
import com.namoo.club.web.session.SessionManager;

import dom.entity.Club;
import dom.entity.Community;
import dom.entity.CommunityManager;
import dom.entity.CommunityMember;
import dom.entity.SocialPerson;

@Controller
@RequestMapping(value = "/management")
@LoginRequired
public class ManagementController {
	//
	@Autowired
	private ClubService clubService;
	@Autowired
	private CommunityService comService;
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/userManagement", method = RequestMethod.GET)
	public String userManagement() {
		//
		return "/management/userManagement";
	}

	@RequestMapping(value = "/comManagement", method = RequestMethod.GET)
	public ModelAndView comManagement(HttpServletRequest req) {
		//
		SessionManager manager = new SessionManager(req);
		List<Community> communities = comService.findManagedCommunities(manager.getLoginEmail());
		for (Community community : communities) {
			community.setMembers(comService.findAllCommunityMember(community.getComNo()));
			community.setManager(comService.findCommunityManager(community.getComNo()));
		}
		return new ModelAndView("/management/comManagement", "communities", communities);
	}

	@RequestMapping(value = "/belongComMem/{communityNo}", method = RequestMethod.GET)
	public ModelAndView belongCommunityMembers(@PathVariable("communityNo") int communityNo) {
		//
		List<CommunityMember> members = comService.findAllCommunityMember(communityNo);
		CommunityManager manager = comService.findCommunityManager(communityNo);
		members = filter(members, manager);

		Community community = comService.findCommunity(communityNo);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("members", members);
		map.put("community", community);
		return new ModelAndView("/management/comMemManagement", map);
	}

	@RequestMapping(value = "/redCard", method = RequestMethod.GET)
	public ModelAndView redCardCommunityMember(@RequestParam("email") String email, @RequestParam("communityNo") int communityNo) {
		//
		Community community = comService.findCommunity(communityNo);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("community", community);
		map.put("email", email);
		return new ModelAndView("/management/redCardInform", map);
	}

	@RequestMapping(value = "/redCard", method = RequestMethod.POST)
	public ModelAndView redCard(@RequestParam("email") String email, @RequestParam("communityNo") int communityNo) {
		//
		Community community = comService.findCommunity(communityNo);
		comService.withdrawalCommunity(communityNo, email);
		return new ModelAndView("/management/redCard", "community", community);
	}

	@RequestMapping(value = "/{communityNo}/showUserInfo/{email}", method = RequestMethod.GET)
	public ModelAndView userInfo(@PathVariable("email") String email, @PathVariable("communityNo") int communityNo, HttpServletRequest req) {
		//
		SessionManager manager = new SessionManager(req);
		Map<String, Object> map = new HashMap<String, Object>();
		SocialPerson person = userService.findTowner(email);
		List<Club> clubs = clubService.findBelongClubs(email, communityNo);
		List<PresClub> presClubs = new ArrayList<PresClub>();
		for (Club club : clubs) {
			PresClub presClub = new PresClub(clubService.findClub(club.getClubNo()));
			presClub.setLoginEmail(manager.getLoginEmail());
			presClubs.add(presClub);
		}
		map.put("user", person);
		map.put("clubs", presClubs);
		return new ModelAndView("/management/userInfo", map);
	}

	@RequestMapping(value = "/myInfo", method = RequestMethod.GET)
	public ModelAndView showMyInfo(HttpServletRequest req) {
		//
		Map<String, Object> map = new HashMap<String, Object>();
		SessionManager manager = new SessionManager(req);
		String email = manager.getLoginEmail();
		SocialPerson person = userService.findTowner(email);
		
		List<Community> communities = comService.findBelongCommunities(email);
		List<PresClub> presClubs = new ArrayList<PresClub>();
		List<PresCommunity> presCommunities = new ArrayList<PresCommunity>();
		
		for (Community community : communities) {
			PresCommunity presCommunity = new PresCommunity(community);
			presCommunities.add(presCommunity);
			List<Club> clubs = clubService.findBelongClubs(email, community.getComNo());
			
			for (Club club : clubs) {
				PresClub presClub = new PresClub(clubService.findClub(club.getClubNo()));
				presClub.setLoginEmail(manager.getLoginEmail());
				presClubs.add(presClub);
			}
		}
		map.put("user", person);
		map.put("clubs", presClubs);
		map.put("communities", presCommunities);
		return new ModelAndView("/management/myInfo", "user", person);
	}

	// -------------------------------------------------------------------------------------------------------
	// private method

	private List<CommunityMember> filter(List<CommunityMember> members, CommunityManager manager) {
		//
		CommunityMember found = null;
		for (CommunityMember member : members) {
			if (member.getEmail().equals(manager.getEmail())) {
				found = member;
				break;
			}
		}

		if (found != null) {
			members.remove(found);
		}

		return members;
	}
}
