package com.namoo.club.service.facade;

import java.util.List;

import dom.entity.Club;
import dom.entity.ClubKingManager;
import dom.entity.ClubManager;
import dom.entity.ClubMember;
import dom.entity.SocialPerson;

public interface ClubService {
	//
	/**
	 * [주민으로 등록된 경우] 클럽 개설
	 * 
	 * 이미 주민으로 가입되어 있는 경우 이메일만 필요하다.
	 * 존재하지 않는 주민인 경우 예외가 발생한다. 
	 * 
	 * @param clubName
	 * @param description
	 * @param email
	 * 
	 * @throws NamooRuntimeException
	 */
	public Club registClub(int categoryNo, int communityNo, String clubName, String description, String email);

	/**
	 * 
	 * @param clubName
	 */
	public Club findClub(int clubNo);
	
	/**
	 * [주민으로 등록된 경우] 클럽 가입
	 * 
	 * 이미 주민으로 가입되어 있는 경우 이메일만 필요하다.
	 * 존재하지 않는 주민인 경우 예외가 발생한다. 
	 * 
	 * @param clubName
	 * @param email
	 * 
	 * @throws NamooRuntimeException
	 */
	public void joinAsMember(int clubNo, String email);

	/**
	 * @return
	 */
	public List<Club> findAllClubs(int comNo);
	
	/**
	 * 이메일로 클럽 회원 찾기
	 * 
	 * @param clubName
	 * @param email
	 * @return
	 */
	public ClubMember findClubMember(int clubNo, String email);
	
	public ClubManager findClubManager(int clubNo, String email);
	
	public ClubKingManager findClubKingManager(int clubNo);
	
	/**
	 * 클럽 회원목록 조회
	 * 
	 * @param clubName
	 * @return
	 */
	public List<ClubMember> findAllClubMember(int clubNo);
	
	public List<ClubManager> findAllClubManager(int clubNo);
	
	/**
	 * 
	 * @param clubName
	 */
	public int countMembers(int clubNo);
	
	/**
	 * @param clubName
	 */
	public void removeClub(int clubNo, int comNo,boolean forcingRemove);
	
	/**
	 * 해당 커뮤니티에
	 * 자신이 회원으로 있는 클럽 목록조회
	 * 
	 * @param email
	 * @return
	 */
	public List<Club> findBelongClubs(String email, int comNo);
	
	/**
	 * 해당 커뮤니티에
	 * 자신이 관리하는 클럽 목록조회
	 * 
	 * @param email
	 * @return
	 */
	public List<Club> findManagedClubs(String email, int comNo);

	/**
	 * 클럽에서 탈퇴하기
	 * 
	 * @param clubName
	 * @param email
	 */
	public void withdrawalClub(int clubNo, String email);
	
	public void commissionManagerClub(int clubNo, SocialPerson originPerson, SocialPerson nwPerson);
	
	public void commissionGoKingManagerClub(int clubNo, SocialPerson originPerson, SocialPerson nwPerson);



}
