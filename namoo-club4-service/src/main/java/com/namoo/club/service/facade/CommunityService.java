package com.namoo.club.service.facade;

import java.util.List;

import dom.entity.ClubCategory;
import dom.entity.Community;
import dom.entity.CommunityManager;
import dom.entity.CommunityMember;
import dom.entity.SocialPerson;

public interface CommunityService {

	/**
	 * [주민으로 등록된 경우] 커뮤니티 개설
	 * 
	 * 이미 주민으로 가입되어 있는 경우 이메일만 필요하다.
	 * 존재하지 않는 주민인 경우 예외가 발생한다. 
	 * 
	 * @param communityName
	 * @param description
	 * @param email
	 * @param categories
	 * 
	 * @return
	 * @throws NamooRuntimeException
	 */
	public Community registCommunity(String communityName, String description, String email, List<ClubCategory> categories);

	/**
	 * 
	 * @param communityName
	 */
	public Community findCommunity(int communityNo);

	/**
	 * [주민으로 등록되지 않은 경우] 커뮤니티 가입
	 * 
	 * 주민 가입을 처리하고 나서 커뮤니티에 가입한다.
	 * 이미 존재하는 주민인 경우 예외가 발생한다.
	 * 
	 * @param communityName
	 * @param name
	 * @param email
	 * @param password
	 * 
	 * @throws NamooRuntimeException
	 */
	public void joinAsMember(int communityNo, String name, String email, String password);
	
	/**
	 * [주민으로 등록된 경우] 커뮤니티 가입
	 * 
	 * 이미 주민으로 가입되어 있는 경우 이메일만 필요하다.
	 * 존재하지 않는 주민인 경우 예외가 발생한다. 
	 * 
	 * @param communityName
	 * @param email
	 * 
	 * @throws NamooRuntimeException
	 */
	public void joinAsMember(int communityNo, String email);

	/**
	 * @return
	 */
	public List<Community> findAllCommunities();
	
	
	/**
	 * 이메일로 커뮤니티 회원 찾기
	 * 
	 * @param communityName
	 * @param email
	 * @return
	 */
	public CommunityMember findCommunityMember(int communityNo, String email);
	
	/**
	 * 커뮤니티 관리자 찾기
	 * 
	 * @param communityNㅐ
	 * @return
	 */
	public CommunityManager findCommunityManager(int communityNo);
	
	/**
	 * 커뮤니티 회원목록 조회
	 * 
	 * @param communityName
	 * @return
	 */
	public List<CommunityMember> findAllCommunityMember(int communityNo);
	
	/**
	 * 
	 * @param communityName
	 */
	public int countMembers(int communityNo);
	
	/**
	 * @param communityName
	 */
	public void removeCommunity(int communityNo, boolean forcingRemove);
	
	/**
	 * 자신이 회원으로 있는 커뮤니티 목록조회
	 * 
	 * @param email
	 * @return
	 */
	public List<Community> findBelongCommunities(String email);
	
	/**
	 * 자신이 관리하는 커뮤니티 목록조회
	 * 
	 * @param email
	 * @return
	 */
	public List<Community> findManagedCommunities(String email);

	/**
	 * 커뮤니티에서 탈퇴하기
	 * 
	 * @param communityName
	 * @param email
	 */
	public void withdrawalCommunity(int communityNo, String email);
	
	public void commissionManagerCommunity(int communityNo, SocialPerson originPerson, SocialPerson nwPerson);
	
	
	//카테고리
	public List<ClubCategory> findAllCategories(int communityNo);
}