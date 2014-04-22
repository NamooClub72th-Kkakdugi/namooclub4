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
import com.namoo.club.web.controller.pres.PresClub;
import com.namoo.club.web.session.SessionManager;

import dom.entity.Club;
import dom.entity.ClubMember;
import dom.entity.Community;

/**
 * 클럽 컨트롤러
 * @author kosta-19
 */
@Controller
@RequestMapping(value="/club")
public class ClubController {
	
	@Autowired
	private ClubService clubService;
	@Autowired
	private CommunityService comService;
	
	@RequestMapping(value="/clubList/{comNo}", method = RequestMethod.GET)
	public ModelAndView clubList(@PathVariable("comNo") int comNo, HttpServletRequest req) {
		//
		SessionManager manager = new SessionManager(req);
		String email = manager.getLoginEmail();
		List<Club> belongClubs = clubService.findBelongClubs(email, comNo);
		List<Club> notBelongClubs = clubService.findNotBelogClubs(email, comNo);
		
		List<PresClub> presJoinClubs = convertAll(belongClubs, email);
		List<PresClub> presUnJoinClubs = convertAll(notBelongClubs, email);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("joinClubs", presJoinClubs);
		map.put("unJoinClubs", presUnJoinClubs);
		
		return new ModelAndView("/club/clubList", map);
	}
	
	@RequestMapping(value="/clubCreateInput", method = RequestMethod.GET)
	public ModelAndView clubCreateInput(@RequestParam("comNo") int communityNo) {
		//
		Community community = comService.findCommunity(communityNo);
		return new ModelAndView("/club/clubCreateInput", "community", community);
	}
	
	@RequestMapping(value="/clubCreateCheck", method = RequestMethod.POST)
	public String clubCreateCheck(@PathVariable("categoryNo") int categoryNo,
								@PathVariable("communityNo") int communityNo,
								@RequestParam("clubName") String clubName,
								@RequestParam("clubDescription") String description,
								HttpServletRequest req) {
		//
//		SessionManager manager = new SessionManager(req);
//		String email = manager.getLoginEmail();
//		Club club = clubService.registClub(categoryNo, communityNo, clubName, description, email);
		
		return "/club/clubCreateCheck";
	}
	
	@RequestMapping(value="/clubList", method = RequestMethod.POST)
	public String clubCreate(@PathVariable("categoryNo") int categoryNo,
							@PathVariable("communityNo") int communityNo,
							@RequestParam("clubName") String clubName,
							@RequestParam("clubDescription") String description,
							@PathVariable("email") String email) {
		
		clubService.registClub(categoryNo, communityNo, clubName, description, email);
		
		return "/club/clubList";
	}
	
	@RequestMapping(value="/clubJoinInput", method=RequestMethod.GET)
	public String clubJoinInput() { 
		//
		return "/club/clubJoinInput";
	}
	
	@RequestMapping(value="/clubJoinInput", method=RequestMethod.POST)
	public String clubJoinInput(@RequestParam("clubNo") int clubNo, @PathVariable("email") String email) { 
		//
		clubService.joinAsMember(clubNo, email);
		
		return "/club/clubList";
	}
	
	@RequestMapping(value="/clubMemberList", method=RequestMethod.GET)
	public ModelAndView clubMemberList(@RequestParam("clubNo") int clubNo) {
		//
		List<ClubMember> clubMembers = clubService.findAllClubMember(clubNo);
		
		return new ModelAndView("/club/clubMemberList", "clubMemberList", clubMembers);  
		
	}
	
	
	
	//-----------------------------------------------------------------------------------------
	//private method
	
	private List<PresClub> convertAll(List<Club> clubs, String loginEmail) {
		//
		List<PresClub> presClubs = new ArrayList<PresClub>();
		for (Club club : clubs) {
			PresClub presClub = new PresClub(club);
			presClub.setLoginEmail(loginEmail);
			presClubs.add(presClub);
		}
		return presClubs;
		
	}
	
	

}
