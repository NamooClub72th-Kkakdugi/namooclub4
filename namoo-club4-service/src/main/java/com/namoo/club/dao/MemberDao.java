package com.namoo.club.dao;

import java.util.List;

import dom.entity.ClubKingManager;
import dom.entity.ClubManager;
import dom.entity.ClubMember;
import dom.entity.CommunityManager;
import dom.entity.CommunityMember;

public interface MemberDao {
	//
	//community
	CommunityManager addCommunityManager(int comNo, CommunityManager comManager);
	CommunityMember addCommunityMember(int comNo, CommunityMember comMember);
	CommunityMember readCommunityMember(int comNo, String email);
	CommunityManager readCommunityManager(int comNo);
	List<CommunityMember> readAllCommunityMember(int comNo);
	void deleteAllComMember(int comNo);
	void deleteCommuninyMember(int comNo, String email);
	void deleteCommunityManager(int comNo);
	
	//club
	ClubMember addClubMember(ClubMember clubMember);
	ClubManager addClubManager(ClubManager clubManager);
	ClubKingManager addKingManager(ClubKingManager clubKingManager);
	void deleteAllClubMember(int clubNo);
	void deleteAllClubManager(int clubNo);
	void deleteClubMember(int clubNo, String email);
	void deleteClubManager(int clubNo, String email);
	void deleteClubKingManger(int clubNo);
	List<ClubMember> readAllClubMembers(int clubNo);
	List<ClubManager> readAllClubManagers(int clubNo);
	ClubMember readClubMember(int clubNo, String email); 
	ClubManager readClubManager(int clubNo, String email);
	ClubKingManager readClubKingManager(int clubNo);
	

}
