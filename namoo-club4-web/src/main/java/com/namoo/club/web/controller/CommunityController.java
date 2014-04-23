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

import com.namoo.club.service.facade.CommunityService;
import com.namoo.club.service.facade.UserService;
import com.namoo.club.web.controller.cmd.CommunityCommand;
import com.namoo.club.web.controller.pres.PresCommunity;
import com.namoo.club.web.session.LoginRequired;
import com.namoo.club.web.session.SessionManager;

import dom.entity.Community;
import dom.entity.CommunityManager;
import dom.entity.CommunityMember;
import dom.entity.SocialPerson;

@Controller
@RequestMapping(value="/community")
@LoginRequired
public class CommunityController {
	//
	@Autowired
	private CommunityService service;
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/comList", method=RequestMethod.GET)
	public ModelAndView communityList(HttpServletRequest req) {
		//
		SessionManager manager = new SessionManager(req);
		String email = manager.getLoginEmail();
		List<Community> joinCommunities = service.findBelongCommunities(email);
		List<Community> unjoinCommunities = service.findNotBelongCommunities(email);

		List<PresCommunity> presJoinedCommunities = convertAll(joinCommunities, email);
		List<PresCommunity> presUnjoinedCommunities = convertAll(unjoinCommunities, email);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("joinCommunities", presJoinedCommunities);
		map.put("unJoinCommunities", presUnjoinedCommunities);
		
		return new ModelAndView("/community/comList", map);
	}
	
	@RequestMapping(value="/comCreate", method=RequestMethod.GET)
	public String createCommunity() {
		//
		return "/community/comCreateInput";
	}
	
	@RequestMapping(value="/comCreateCheck", method=RequestMethod.POST)
	public ModelAndView createCheckCommunity(CommunityCommand command) {
		//
		Community community = new Community(command.getCommunityName(), command.getDescription());
		community.setCategories(command.getClubCategories());
		return new ModelAndView("/community/comCreateCheck", "community", community);
	}
	
	@RequestMapping(value="/comCreate", method=RequestMethod.POST)
	public String createCommunity(HttpServletRequest req, CommunityCommand command) {
		//
		SessionManager manager = new SessionManager(req);
		service.registCommunity(command.getCommunityName(), command.getDescription(), manager.getLoginEmail(), command.getClubCategories());
		
		return "redirect:/community/comList";
	}
	
	@RequestMapping(value="/comJoin/{communityNo}", method=RequestMethod.GET)
	public ModelAndView joinCommunity(@PathVariable("communityNo") int communityNo) {
		//
		Community community = service.findCommunity(communityNo);
		return new ModelAndView("/community/comJoinInput", "community", community);
	}
	
	@RequestMapping(value="/communityJoin/{comNo}", method=RequestMethod.GET)
	public String joinCommunity(HttpServletRequest req, @PathVariable("comNo") int communityNo) {
		//
		SessionManager manager = new SessionManager(req);
		service.joinAsMember(communityNo, manager.getLoginEmail());
		return "redirect:/community/comList";
	}
	
	@RequestMapping(value="/comRemove/{communityNo}", method=RequestMethod.GET)
	public ModelAndView removeCom(@PathVariable("communityNo") int communityNo) {
		//
		Community community = service.findCommunity(communityNo);
		return new ModelAndView("/inform/comRemoveCheck", "community", community);
	}
	
	@RequestMapping(value="/comRemove/{communityNo}", method=RequestMethod.POST)
	public String removeCommunity(@PathVariable("communityNo") int communityNo) {
		//
		service.removeCommunity(communityNo, true);
		return "redirect:/community/comList";
	}
	
	@RequestMapping(value="/comWithdrawl", method=RequestMethod.POST)
	public ModelAndView withdrawlCommunity(@RequestParam("communityNo") int communityNo) {
		//
		Community community = service.findCommunity(communityNo);
		return new ModelAndView("/inform/comWithdrawlCheck", "community", community);
	}
	
	@RequestMapping(value="/communityWithdrawl", method=RequestMethod.POST)
	public String withdrawlCommunity(HttpServletRequest req, @RequestParam("communityNo") int communityNo) {
		//
		SessionManager manager = new SessionManager(req);
		service.withdrawalCommunity(communityNo, manager.getLoginEmail());
		return "/inform/comWithdrawl";
	}
	
	@RequestMapping(value="/comSelectMem/{communityNo}", method=RequestMethod.GET)
	public ModelAndView comSelectMem(@PathVariable("communityNo") int communityNo) {
		//
		List<CommunityMember> members = service.findAllCommunityMember(communityNo);
		CommunityManager manager = service.findCommunityManager(communityNo);
		members = filter(members, manager);
		
		Community community = service.findCommunity(communityNo);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("members", members);
		map.put("community", community);
		return new ModelAndView("/commission/comSelectMem", map);
	}
	
	@RequestMapping(value="/comCommission/{communityNo}", method=RequestMethod.POST)
	public String commissionCommunity(HttpServletRequest req, @PathVariable("communityNo") int communityNo, @RequestParam("email") String email) {
		//
		SessionManager manager = new SessionManager(req);
		SocialPerson originPerson = userService.findTowner(manager.getLoginEmail());
		SocialPerson nwPerson = userService.findTowner(email);
		service.commissionManagerCommunity(communityNo, originPerson, nwPerson);
		return "redirect:/community/comList";
	}
	
	//-----------------------------------------------------------------------------------------
	//private method
	
	private List<PresCommunity> convertAll(List<Community> communities, String loginEmail) {
		// 
		List<PresCommunity> presCommunities = new ArrayList<PresCommunity>();
		for (Community community : communities) {
			PresCommunity presCommunity = new PresCommunity(service.findCommunity(community.getComNo()));
			presCommunity.setLoginEmail(loginEmail);
			presCommunities.add(presCommunity);
		}
		return presCommunities;
	}
	
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
