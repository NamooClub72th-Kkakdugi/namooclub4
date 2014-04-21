package dom.entity;

public class ClubKingManager {

	private int clubNo;
	private SocialPerson rolePerson;
	//----------------------------------------------------------------
	public ClubKingManager(int clubNo) {
		//
		this.clubNo = clubNo;
	}
	
	public ClubKingManager(int clubNo, SocialPerson rolePerson) {
		//
		this.rolePerson = rolePerson;
		this.clubNo = clubNo;
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
	//-------------------------------------------------------------------------
}
