package com.namoo.club.service.facade;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import dom.entity.SocialPerson;

public class UserServiceTest extends DbCommonTest{
	//
	private static final String DATASET_XML="UserServiceTest_dataset.xml";
	
	@Autowired
	private UserService service;
	
	//-------------------------------------------------------------------------
	@Test
	@DatabaseSetup(DATASET_XML)
	public void testLoginAsTowner() {
		boolean login = service.loginAsTowner("ekdgml", "asdf");
//		boolean login2 = service.loginAsTowner("wntjd", "qwer");
		
		assertEquals(true, login);
//		assertEquals(true, login2);
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testRegistTowner() {
		//
		service.registTowner("박상희2", "ekdgml2", "asdf2");
		
		SocialPerson user = service.findTowner("ekdgml2");
		assertEquals("asdf2", user.getPassword());
		assertEquals("박상희2", user.getName());
		assertEquals("ekdgml2", user.getEmail());
	}

	@Test
	@DatabaseSetup(DATASET_XML)
	public void testFindAllTowner() {
		//
		List<SocialPerson> users = service.findAllTowner();
		
		assertEquals(3, users.size());
	}
}
