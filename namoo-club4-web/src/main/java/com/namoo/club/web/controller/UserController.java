package com.namoo.club.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.namoo.club.service.facade.UserService;
import com.namoo.club.web.session.SessionManager;

import dom.entity.SocialPerson;

@Controller
public class UserController {
	//
	@Autowired
	private UserService service;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
		//
		return "/user/login";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView login(HttpServletRequest req, @RequestParam("loginId") String userId, @RequestParam("password") String password) {
		//
		SessionManager manager = new SessionManager(req);
		if (manager.login(userId, password)) {
			return new ModelAndView(new RedirectView("/community/comList", true));  //true로 주면 앞에 contextpath가 저절로 붙어서 나옴!
		} else {
			return new ModelAndView("/user/login");
		}
	}
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String joinAsTowner() {
		//
		return "/user/joinInput";
	}
	
	@RequestMapping(value="/joinCheck", method=RequestMethod.POST)
	public ModelAndView joinAsTowner(@RequestParam("email")String userId, @RequestParam("name")String name, @RequestParam("password")String password) {
		//
		SocialPerson user = new SocialPerson(name, userId, password);
		return new ModelAndView("/user/joinCheck", "user", user);
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(@RequestParam("email")String userId, @RequestParam("name")String name, @RequestParam("password")String password) {
		//
		service.registTowner(name, userId, password);
		return "/user/login";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public RedirectView logout(HttpServletRequest req) {
		//
		SessionManager manager = new SessionManager(req);
		manager.logout();
		return new RedirectView("/login", true);
	}
	
	@RequestMapping(value="/withdrawl", method=RequestMethod.GET)
	public String withdrawl() {
		//
		return "/user/withdrawlCheck";
	}
	
	@RequestMapping(value="/withdrawl", method=RequestMethod.POST)
	public String withdrawlTowner(HttpServletRequest req) {
		//
		SessionManager manager = new SessionManager(req);
		service.removeTowner(manager.getLoginEmail());
		return "/user/withdrawl";
	}
	

}
