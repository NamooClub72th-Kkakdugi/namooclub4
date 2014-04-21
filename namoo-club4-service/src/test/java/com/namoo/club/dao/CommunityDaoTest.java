package com.namoo.club.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import dom.entity.ClubCategory;
import dom.entity.Community;
import dom.entity.SocialPerson;

public class CommunityDaoTest extends DbCommonTest {
	//
	private static final String DATASET_XML="CommunityDaoTest_dataset.xml";
	
	@Autowired
	private CommunityDao dao;
	
	//-------------------------------------------
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadAllCommunities() {
		//
		assertEquals(2, dao.readAllCommunities().size());
	}
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testCreateCommunity() {
		//
		Community community = new Community("com3", "com3_des", new SocialPerson("wntjd", "이주성"));
		int comNo = dao.createCommunity(community);
		//검증
		community = dao.readCommunity(comNo);
		assertEquals("com3", community.getName());
		assertEquals("com3_des", community.getDescription());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testUpdateCommunity() {
		//
		Community community = dao.readCommunity(1);
		community.setName("com1_test");
		community.setDescription("com1_des_test");
		dao.updateCommunity(community);
		
		//검증
		community = dao.readCommunity(1);
		assertEquals("com1_test", community.getName());
		assertEquals("com1_des_test", community.getDescription());
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testDeleteCommunity() {
		//
		dao.deleteCommunity(2);
		//검증
		assertEquals(1, dao.readAllCommunities().size());
	}
	
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadAllCategories() {
		//
		assertEquals(2, dao.readAllCategories(1).size());
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testCreateClubCategory() {
		//
		ClubCategory category = new ClubCategory(2, 2, "category2");
		dao.createClubCategory(2, category);
		//검증
		assertEquals(2, dao.readAllCategories(2).size());
	}
	
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testDeleteAllClubCategory() {
		//
		dao.deleteAllClubCategory(2);
		assertEquals(0, dao.readAllCategories(2).size());
	}
}
