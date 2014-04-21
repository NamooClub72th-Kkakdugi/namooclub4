package com.namoo.club.service.factory;

import com.namoo.club.service.facade.ClubService;
import com.namoo.club.service.facade.CommunityService;
import com.namoo.club.service.facade.UserService;
import com.namoo.club.service.logic.ClubServiceLogic;
import com.namoo.club.service.logic.CommunityServiceLogic;
import com.namoo.club.service.logic.UserServiceLogic;


public class NamooClubServiceFactory {
	
	private static NamooClubServiceFactory instance = new NamooClubServiceFactory();
	
	private NamooClubServiceFactory() {
		//
	}
	
	public static NamooClubServiceFactory getInstance() {
		//
		return instance;
	} 
	
	public UserService getUserService() {
		//
		return new UserServiceLogic();
	}
	
	public CommunityService getCommunityService() {
		//
		return new CommunityServiceLogic();
	}
	
	public ClubService getClubService() {
		//
		return new ClubServiceLogic();
	}
	

}
