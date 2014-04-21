package com.namoo.club.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.namoo.club.service.facade.UserService;

/**
 * 로그인 세션을 관리하는 클래스
 * 
 * @author kosta-18
 *
 */
public class SessionManager {
	//
	private UserService userService;
	
	private HttpSession session;
	
	//-----------------------------------------------------------------------------------------------------------------
	//constructor
	
	/**
	 * 생성자
	 * @param req
	 */
	public SessionManager(HttpServletRequest req, UserService userService) {
		//
		session = req.getSession();
		this.userService = userService; 
	 }
	
	//------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 로그인하기
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public boolean login(String email, String password) {
		//
		if (userService.loginAsTowner(email, password)) {
			session.setAttribute("loginUser", userService.findTowner(email));
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
