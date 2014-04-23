package com.namoo.club.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import dom.entity.ClubManager;
import dom.entity.ClubMember;
import dom.entity.CommunityManager;
import dom.entity.CommunityMember;
import dom.entity.SocialPerson;

public class MemberDaoTest extends DbCommonTest{
	//
	private static final String DATASET_XML="MemberDaoTest_dataset.xml";
	
	@Autowired
	private MemberDao dao;

	//-------------------------------------------------------------------------
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testAddCommunityManager() {
		//
		CommunityManager comManager = new CommunityManager(3, new SocialPerson("wntjd", "이주성"));
		dao.addCommunityManager(3, comManager);
		//검증
		assertEquals("wntjd", dao.readCommunityManager(3).getEmail());
		assertEquals("이주성", dao.readCommunityManager(3).getName());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testAddCommunityMember() {
		//
		CommunityMember comMember = new CommunityMember(1, new SocialPerson("wntjd", "이주성"));
		dao.addCommunityMember(1, comMember);
		//검증
		assertEquals("wntjd", dao.readCommunityMember(1, "wntjd").getEmail());
		assertEquals("이주성", dao.readCommunityMember(1, "wntjd").getName());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadCommunityMember() {
		//
		CommunityMember comMember = dao.readCommunityMember(1, "ekdgml");
		//검증
		assertEquals("박상희", comMember.getName());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadCommunityManager() {
		//
		CommunityManager comManager = dao.readCommunityManager(1);
		//검증
		assertEquals("박상희", comManager.getName());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadAllCommunityMember() {
		//
		assertEquals(2, dao.readAllCommunityMember(1).size());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testDeleteAllComMember() {
		//
		dao.deleteAllComMember(1);
		assertEquals(1, dao.readAllCommunityMember(1).size());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testDeleteCommuninyMember() {
		//
		dao.deleteCommuninyMember(1, "hong");
		assertEquals(1, dao.readAllCommunityMember(1).size());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testDeleteCommunityManager() {
		//
		dao.deleteCommunityManager(1);
		assertNull(dao.readCommunityManager(1));
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testAddClubMember() {
		//
		ClubMember clubMember = new ClubMember(2, new SocialPerson("wntjd", "이주성"));
		dao.addClubMember(clubMember);
		//검증
		clubMember = dao.readClubMember(2, "wntjd");
		assertEquals("이주성", clubMember.getName());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testAddClubManager() {
		//
		ClubManager clubManager = new ClubManager(2, new SocialPerson("wntjd", "이주성"), false);
		dao.addClubManager(clubManager);
		//검증
		assertEquals("이주성", dao.readClubManager(2, "wntjd", "b").getName());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testAddClubManager_ForKing() {
		//
		ClubManager clubKingManager = new ClubManager(2, new SocialPerson("wntjd", "이주성"), true);
		dao.addClubManager(clubKingManager);
		//검증
		assertEquals("이주성", dao.readClubManager(2, "wnttjd", "a").getName());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testDeleteAllClubMember() {
		//
		dao.deleteAllClubMember(1);
		//검증
		assertEquals(2, dao.readAllClubMembers(1).size());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testDeleteAllClubManager() {
		//
		dao.deleteAllClubManager(1);
		//검증
		assertEquals(2, dao.readAllClubMembers(1).size());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testDeleteClubMember() {
		//
		dao.deleteClubMember(1, "wntjd");
		//검증
		assertEquals(2, dao.readAllClubMembers(1).size());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testDeleteClubManager() {
		//
		dao.deleteClubManager(1, "hong");
		//검증
		assertEquals(2, dao.readAllClubMembers(1).size());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testDeleteClubKingManger() {
		//
		dao.deleteClubKingManger(1);
		//검증
		assertEquals(2, dao.readAllClubMembers(1).size());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadAllClubMembers() {
		//
		assertEquals(3, dao.readAllClubMembers(1).size());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadAllClubManagers() {
		//
		assertEquals(2, dao.readAllClubManagers(1).size());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadClubMember() {
		//
		ClubMember clubMember = dao.readClubMember(1, "ekdgml");
		assertEquals("박상희", clubMember.getName());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadClubManager() {
		//
		ClubManager clubManager = dao.readClubManager(1, "hong", "b");
		assertEquals("홍길동", clubManager.getName());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadClubKingManager() {
		//
		ClubManager clubKingManager = dao.readClubManager(1, "ekdgml", "a");
		assertEquals("박상희", clubKingManager.getName());
	}

}
