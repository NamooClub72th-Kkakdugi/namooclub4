package com.namoo.club.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.namoo.club.service.facade.UserService;

import dom.entity.SocialPerson;

/**
 * 로그인 세션을 관리하는 클래스
 * 
 * @author kosta-18
 *
 */
public class SessionManager {
	//
	private static final String LOGIN_USER = "loginUser";
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
		
		WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(req.getSession().getServletContext());
		this.userService = ac.getBean(UserService.class); 
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
			session.setAttribute(LOGIN_USER, userService.findTowner(email));
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
		return session.getAttribute(LOGIN_USER) != null ? true : false;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String getLoginEmail() {
		if (isLogin()) {
			return ((SocialPerson)session.getAttribute(LOGIN_USER)).getEmail();
		} 
		return null;
	}

}
