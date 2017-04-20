package user;

import java.util.Set;

public class UserDetails {

	private int id;
	private String name;
	private String emailId;
	private String password;
	private String address;
	private String phoneNumber;
	private Set<String> folderId;
	private int collageId;
	private int branchId;
	
	
	public int getBranchId() {
		return branchId;
	}

	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}

	public void setLogedInUser(boolean isLogedInUser) {
		this.isLogedInUser = isLogedInUser;
	}

	private boolean isLogedInUser;

	
	public UserDetails(int id, String name, String email, Set<String> folderId, int collageId, int branchId)
	{
		this.id = id;
		this.name = name;
		this.emailId = email;
		this.folderId = folderId;
		this.collageId	= collageId;
		this.branchId = branchId;
	}
	
	public UserDetails(){
		super();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<String> getFolderId() {
		return folderId;
	}

	public void setFolderId(Set<String> folderId) {
		this.folderId = folderId;
	}

	public int getCollageId() {
		return collageId;
	}

	/**
	 * @param collageId the collageId to set
	 */
	public void setCollageId(int collageId) {
		this.collageId = collageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean getIsLogedInUser() {
		return isLogedInUser;
	}

	public void setIsLogedInUser(boolean isLogedInUser) {
		this.isLogedInUser = isLogedInUser;
	}

}
