package com.namoo.club.web.controller.cmd;

public class ClubCommand {
	//
	private int categoryNo;
	private String clubName;
	private String clubDescription;
	private int communityNo;


	//--------------------------------------------------------------------------
	
	public String getClubName() {
		return clubName;
	}
	public int getCategoryNo() {
		return categoryNo;
	}
	public void setCategoryNo(int categoryNo) {
		this.categoryNo = categoryNo;
	}
	public void setClubName(String clubName) {
		this.clubName = clubName;
	}
	public String getClubDescription() {
		return clubDescription;
	}
	public void setClubDescription(String clubDescription) {
		this.clubDescription = clubDescription;
	}
	public int getCommunityNo() {
		return communityNo;
	}
	public void setCommunityNo(int communityNo) {
		this.communityNo = communityNo;
	}
	
}
