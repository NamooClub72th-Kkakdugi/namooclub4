package com.namoo.club.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.namoo.club.service.facade.UserService;

/**
 * 로그인 세션을 관리하는 클래스
 * 
 * @author kosta-18
 *
 */
@Component
@Scope(value="request")
public class SessionManager {
	//
	@Autowired
	private UserService userService;
	
	private HttpSession session;
	
	//-----------------------------------------------------------------------------------------------------------------
	//constructor
	
	/**
	 * 생성자
	 * @param req
	 */
	public SessionManager(HttpServletRequest req) {
		//
		session = req.getSession();
	 }
	
	//------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 로그인하기
	 * 
	 * @param userId
	 * @param password
	 * @return
	 */
	public boolean login(String userId, String password) {
		//
		if (userService.loginAsTowner(userId, password)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 로그아웃하기
	 */
	public void logout() {
		//
		session.invalidate();
	}
	
	/**
	 * 로그인 여부 확인하기
	 * @return
	 */
	public boolean isLogin() {
		//
		return session.getAttribute("loginId") != null ? true : false;
	}
}
