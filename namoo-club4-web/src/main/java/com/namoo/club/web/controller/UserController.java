package com.namoo.club.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.namoo.club.service.facade.UserService;

@Controller
public class UserController {
	//
	@Autowired
	private UserService service;
	
	@RequestMapping(value="/main", method=RequestMethod.GET)
	public String login() {
		//
		return "/user/login";
	}
}
