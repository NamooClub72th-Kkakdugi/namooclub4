package com.namoo.club.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import dom.entity.SocialPerson;


public class UserDaoTest extends DbCommonTest{
	//
	private static final String DATASET_XML="UserDaoTest_dataset.xml";
	
	@Autowired
	private UserDao dao;
	
	//-------------------------------------------------------------------------
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testReadAllUsers() {
		//
		assertEquals(3, dao.readAllUsers().size());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testCreateUser() {
		//
		SocialPerson user = new SocialPerson("abcd", "abcd@a.a", "abcd");
		dao.createUser(user);
		
		//검증
		user = dao.readUser("abcd@a.a");
		assertEquals("abcd", user.getPassword());
		assertEquals("abcd", user.getName());
		assertEquals("abcd@a.a", user.getEmail());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testUpdateUser() {
		//
		SocialPerson user = dao.readUser("ekdgml");
		user.setPassword("asdfasdf");
		dao.updateUser(user);
		//검증
		user = dao.readUser("ekdgml");
		assertEquals("asdfasdf", user.getPassword());
	}

}
