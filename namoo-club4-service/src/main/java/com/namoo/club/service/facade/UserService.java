package com.namoo.club.service.facade;

import java.util.List;



import dom.entity.SocialPerson;

public interface UserService {
	//
	boolean loginAsTowner(String email, String password);
	void registTowner(String name, String email, String password);
	void removeTowner(String email);
	SocialPerson findTowner(String email);
	List<SocialPerson> findAllTowner();
}
