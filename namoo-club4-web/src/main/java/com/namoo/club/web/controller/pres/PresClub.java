package com.namoo.club.web.controller.pres;

import java.util.Date;
import java.util.List;

import dom.entity.Club;
import dom.entity.ClubManager;
import dom.entity.ClubMember;


public class PresClub {
	//
	private Club club;
	private String loginEmail;
	private String categoryName;

	//--------------------------------------------------------------------------
	
	public PresClub(Club club){
		//
		this.club = club;
	}
	
	//--------------------------------------------------------------------------

	public Club getClub() {
		return club;
	}

	public String getName() {
		return club.getName();
	}

	public int getclubNo() {
		return club.getClubNo();
	}
	
	
	public Date getOpenDate() {
		return club.getOpenDate();
	}
	
	
	public String getDescription() {
		return club.getDescription();
	}
	
	
	public void setClub(Club club) {
		this.club = club;
	}
	
	public List<ClubMember> getMembers() {
		return club.getMembers();
	}
	
	public List<ClubManager> getManager() {
		
		return club.getManagers();
	}
	
	public ClubManager getKingManager() {
		return club.getKingManager();
	}

	public int getCategoryNo() {
		return club.getCategoryNo();
	}
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
		ClubManager manager = club.findManager(loginEmail);
		return manager != null ? true : false;
	}
	
	public boolean isKingManager() {
		//
		ClubManager manager = club.getKingManager();
		return (manager != null && manager.getEmail().equals(loginEmail)) ? true : false; 
	}
}
