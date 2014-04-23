package dom.entity;

public class ClubManager  {

	private int clubNo;
	private SocialPerson rolePerson;
	private boolean kingManager;
	
	//----------------------------------------------------------------
	
	public ClubManager(int clubNo) {
		//
		this.clubNo = clubNo;
	}
	
	public ClubManager(int clubNo, SocialPerson rolePerson) {
		//
		this.rolePerson = rolePerson;
		this.clubNo = clubNo;
	}
	
	public ClubManager(int clubNo, SocialPerson rolePerson, boolean king) {
		//
		this(clubNo, rolePerson);
		this.kingManager = king; 
	}
	//--------------------------------------------------
	public String getEmail() {
		return rolePerson.getEmail();
	}
	
	public String getName() {
		return rolePerson.getName();
	}

	public int getClubNo() {
		return clubNo;
	}

	public void setClubNo(int clubNo) {
		this.clubNo = clubNo;
	}

	public boolean isKingManager() {
		return kingManager;
	}
	public void setKingManager(boolean kingManager) {
		this.kingManager = kingManager;
	}
	//-------------------------------------------------------------------------
}
