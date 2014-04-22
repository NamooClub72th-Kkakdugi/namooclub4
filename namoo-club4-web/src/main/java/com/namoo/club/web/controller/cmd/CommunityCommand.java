package com.namoo.club.web.controller.cmd;

import java.util.ArrayList;
import java.util.List;

import com.namoo.club.web.shared.util.StringUtil;

import dom.entity.ClubCategory;

public class CommunityCommand {
	//
	private String communityName;
	private String description;
	private String[] ctgr;

	//--------------------------------------------------------------------------------------
	public String getCommunityName() {
		return communityName;
	}
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String[] getCtgr() {
		return ctgr;
	}
	public void setCtgr(String[] ctgr) {
		this.ctgr = ctgr;
	}
	
	public List<ClubCategory> getClubCategories() {
		//
		List<ClubCategory> categories = new ArrayList<ClubCategory>();
		
		int categoryNo = 1;
		for (String categoryName : ctgr) {
			if (!StringUtil.isEmpty(categoryName)) {
				categories.add(new ClubCategory(categoryNo, categoryName));
				categoryNo++;
			}
		}
		return categories;
	}
	
}
