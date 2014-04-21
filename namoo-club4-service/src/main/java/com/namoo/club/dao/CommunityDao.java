package com.namoo.club.dao;

import java.util.List;

import dom.entity.ClubCategory;
import dom.entity.Community;

public interface CommunityDao {
	//
	//Community
	List<Community> readAllCommunities();
	Community readCommunity(int comNo);
	int createCommunity(Community community);
	void updateCommunity(Community community);
	void deleteCommunity(int comNo);
	
	// ClubCategory
	List<ClubCategory> readAllCategories(int comNo);
	void createClubCategory(int comNo, ClubCategory category);
	void deleteAllClubCategory(int comNo);

}
