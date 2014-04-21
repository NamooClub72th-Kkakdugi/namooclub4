package com.namoo.club.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.namoo.club.service.facade.CommunityService;

import dom.entity.Community;

@Controller
@RequestMapping(value="/community")
public class CommunityController {
	//
	@Autowired
	private CommunityService service;
	
	@RequestMapping(value="/comList", method=RequestMethod.GET)
	public ModelAndView communityList() {
		//
		List<Community> communities = service.findAllCommunities();
		return new ModelAndView("/community/comList", "communities", communities);
	}
}
