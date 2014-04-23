package dom.entity;

import java.util.Date;
import java.util.List;

public class Club {
	//
	private int categoryNo;
	private int comNo;
	private int clubNo;
	private String name;
	private String description;
	private Date openDate;

	private List<ClubManager> managers;
	private List<ClubMember> members;

	// ---------------------------------------------------------------------------------------------
	public Club(int categoryNo, int comNo, String name, String description) {
		//
		this.categoryNo = categoryNo;
		this.comNo = comNo;
		this.name = name;
		this.description = description;
	}

	public Club(int categoryNo, int comNo, String name, String description, SocialPerson user) {
		//
		this.categoryNo = categoryNo;
		this.comNo = comNo;
		this.name = name;
		this.description = description;
	}

	// ----------------------------------------------------------------------------------------------
	public int getComNo() {
		return comNo;
	}

	public void setComNo(int comNo) {
		this.comNo = comNo;
	}

	public int getClubNo() {
		return clubNo;
	}

	public void setClubNo(int clubNo) {
		this.clubNo = clubNo;
	}

	public int getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(int categoryNo) {
		this.categoryNo = categoryNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public List<ClubManager> getManagers() {
		return managers;
	}

	public void setManagers(List<ClubManager> manager) {
		this.managers = manager;
	}

	public List<ClubMember> getMembers() {
		return members;
	}

	public void setMembers(List<ClubMember> member) {
		this.members = member;
	}

	// --------------------------------------------------------------------------

	public ClubManager findManager(String loginEmail) {
		//
		List<ClubManager> managers = this.getManagers();
		for (ClubManager manager : managers) {
			if (!manager.isKingManager() && manager.getEmail().equals(loginEmail)) {
				return manager;
			}
		}
		return null;
	}

	public ClubManager getKingManager() {
		//
		if (managers != null) {
			for (ClubManager manager : managers) {
				if (manager.isKingManager()) {
					return manager;
				}
			}
		}
		return null;
	}

}
