package user;

public class Branches {
	
	private int id;
	private int collageId;
	private String branchName;
	private String branchAbbrevation;
	
	


	public Branches(int id, int collageId, String branchName, String branchAbbrevation) 
	{
		this.id = id;
		this.collageId = collageId;
		this.branchName = branchName;
		this.branchAbbrevation = branchAbbrevation;
	}
	
	public Branches()
	{
		super();
	}
	
	
	
	
	public String getBranchAbbrevation() {
		return branchAbbrevation;
	}

	public void setBranchAbbrivation(String branchAbbrivation) {
		this.branchAbbrevation = branchAbbrivation;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCollageId() {
		return collageId;
	}
	public void setCollageId(int collageId) {
		this.collageId = collageId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	

}