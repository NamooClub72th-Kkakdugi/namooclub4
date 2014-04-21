package com.namoo.club.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.namoo.club.service.facade.ClubService;

import dom.entity.Club;
import dom.entity.ClubMember;
import dom.entity.SocialPerson;

/**
 * 클럽 컨트롤러
 * @author kosta-19
 */
public class ClubController {
	
	@Autowired
	private ClubService clubService;
	
	@RequestMapping(value="/club/clubList", method = RequestMethod.GET)
	public ModelAndView clubList(@PathVariable("comNo") int comNo, HttpServletRequest req) {
		//
		SocialPerson person = (SocialPerson) req.getSession().getAttribute("loginUser");
		String email = person.getEmail();
		List<Club> belongClubs = clubService.findBelongClubs(email, comNo);
		List<Club> notBelongClubs = clubService.findNotBelogClubs(email, comNo);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("joinClubs", belongClubs);
		map.put("unJoinClubs", notBelongClubs);
		
		return new ModelAndView("club", map);
	}
	
	@RequestMapping(value="/club/clubCreateInput", method = RequestMethod.GET)
	public String clubCreateInput() {
		//
		return "/club/clubCreateInput";
	}
	
	@RequestMapping(value="/club/clubCreateCheck", method = RequestMethod.POST)
	public ModelAndView clubCreateCheck(@PathVariable("categoryNo") int categoryNo,
								@PathVariable("communityNo") int communityNo,
								@RequestParam("clubName") String clubName,
								@RequestParam("clubDescription") String description,
								@PathVariable("email") String email) {
		//
		Club club = clubService.registClub(categoryNo, communityNo, clubName, description, email);
		
		return new ModelAndView("clubList", "clubList", club);
	}
	
	@RequestMapping(value="/club/clubList", method = RequestMethod.POST)
	public String clubCreate(@PathVariable("categoryNo") int categoryNo,
							@PathVariable("communityNo") int communityNo,
							@RequestParam("clubName") String clubName,
							@RequestParam("clubDescription") String description,
							@PathVariable("email") String email) {
		
		clubService.registClub(categoryNo, communityNo, clubName, description, email);
		
		return "/club/clubList";
	}
	
	@RequestMapping(value="/club/clubJoinInput", method=RequestMethod.GET)
	public String clubJoinInput() { 
		//
		return "/club/clubJoinInput";
	}
	
	@RequestMapping(value="/club/clubJoinInput", method=RequestMethod.POST)
	public String clubJoinInput(@RequestParam("clubNo") int clubNo, @PathVariable("email") String email) { 
		//
		clubService.joinAsMember(clubNo, email);
		
		return "/club/clubList";
	}
	
	@RequestMapping(value="/club/clubMemberList", method=RequestMethod.GET)
	public ModelAndView clubMemberList(@RequestParam("clubNo") int clubNo) {
		//
		List<ClubMember> clubMembers = clubService.findAllClubMember(clubNo);
		
		return new ModelAndView("clubMemberList", "clubMemberList", clubMembers);  
		
	}
	
	
	
	
	
	

}
