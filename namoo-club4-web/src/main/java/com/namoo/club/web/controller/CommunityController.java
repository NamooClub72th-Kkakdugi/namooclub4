package com.namoo.club.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.namoo.club.service.facade.CommunityService;
import com.namoo.club.web.controller.pres.PresCommunity;

import dom.entity.Community;
import dom.entity.SocialPerson;

@Controller
@RequestMapping(value="/community")
public class CommunityController {
	//
	@Autowired
	private CommunityService service;
	
	@RequestMapping(value="/comList", method=RequestMethod.GET)
	public ModelAndView communityList(HttpServletRequest req) {
		//
		SocialPerson person = (SocialPerson) req.getSession().getAttribute("loginUser");
		String email = person.getEmail();
		List<Community> joinCommunities = service.findBelongCommunities(email);
		List<Community> unjoinCommunities = service.findNotBelongCommunities(email);

		List<PresCommunity> presJoinedCommunities = convertAll(joinCommunities, email);
		List<PresCommunity> presUnjoinedCommunities = convertAll(unjoinCommunities, email);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("joinCommunities", presJoinedCommunities);
		map.put("unJoinCommunities", presUnjoinedCommunities);
		
		return new ModelAndView("/community/comList", map);
	}
	
	@RequestMapping
	public String createCommunity() {
		//
		return "/community/comCreateInput";
	}
	
	//-----------------------------------------------------------------------------------------
	//private method
	
	private List<PresCommunity> convertAll(List<Community> communities, String loginEmail) {
		// 
		List<PresCommunity> presCommunities = new ArrayList<PresCommunity>();
		for (Community community : communities) {
			PresCommunity presCommunity = new PresCommunity(community);
			presCommunity.setLoginEmail(loginEmail);
			presCommunities.add(presCommunity);
		}
		return presCommunities;
	}
}
