package dom.entity;

public class ClubCategory {
	//
	private int categoryNo;
	private int communityNo;
	private String categoryName;
	
	//-----------------
	public ClubCategory(int categoryNo, int communityNo, String categoryName) {
		//
		this.communityNo = communityNo;
		this.categoryNo = categoryNo;
		this.categoryName = categoryName;
	}
	//------------------------------
	public int getCategoryNo() {
		return categoryNo;
	}

	public void setCategoryNo(int categoryNo) {
		this.categoryNo = categoryNo;
	}

	public int getCommunityNo() {
		return communityNo;
	}

	public void setCommunityNo(int communityNo) {
		this.communityNo = communityNo;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
