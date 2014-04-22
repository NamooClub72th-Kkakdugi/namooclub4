package com.namoo.club.web.controller.pres;

import java.util.Date;
import java.util.List;

import dom.entity.Club;
import dom.entity.Community;
import dom.entity.CommunityManager;
import dom.entity.CommunityMember;


public class PresCommunity {
	//
	private Community community;
	private String loginEmail;
	//--------------------------------------------------------------------------
	public PresCommunity () {
		//
	}
	
	public PresCommunity(Community community) {
		//
		this.community = community;
	}
	
	//--------------------------------------------------------------------------

	public int getCommunityNo() {
		return community.getComNo();
	}

	public String getName() {
		return community.getName();
	}
	
	public Date getOpenDate() {
		return community.getOpenDate();
	}
	
	public List<Club> getClubs() {
		return community.getClubs();
	}
	
	public String getDescription() {
		return community.getDescription();
	}
	
	
	public List<CommunityMember> getMembers() {
		return community.getMembers();
	}


	public CommunityManager getManager() {
		return community.getManager();
	}
	
	//------------------------------------------------------------------------
	public String getLoginEmail() {
		return loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}
	
	public boolean isManager() {
		//
		CommunityManager manager = community.getManager();
		return (manager != null && manager.getEmail().equals(loginEmail));
	}
}
