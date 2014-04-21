package com.namoo.club.dao;

import java.util.List;

import dom.entity.Club;
public interface ClubDao {
	//
	List<Club> readAllClubs(int comNo);
	Club readClub(int clubNo);
	int createClub(int comNo, Club club);
	void updateClub(Club club);
	void deleteClub(int clubNo);
}
