package user;

import java.util.Set;

public class UserDetails {

	private int Id;
	private String Name;
	private String EmailId;
	private String Password;
	private String Address;
	private String PhoneNumber;
	private Set<String> FolderId;
	private int CollageId;
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
		this.Id = id;
		this.Name = name;
		this.EmailId = email;
		this.FolderId = folderId;
		this.CollageId	= collageId;
		this.branchId = branchId;
	}
	
	public UserDetails(){
		super();
	}
	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public Set<String> getFolderId() {
		return FolderId;
	}

	public void setFolderId(Set<String> folderId) {
		this.FolderId = folderId;
	}

	public int getCollageId() {
		return CollageId;
	}

	/**
	 * @param collageId the collageId to set
	 */
	public void setCollageId(int collageId) {
		this.CollageId = collageId;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getEmailId() {
		return EmailId;
	}

	public void setEmailId(String emailId) {
		EmailId = emailId;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}

	public boolean getIsLogedInUser() {
		return isLogedInUser;
	}

	public void setIsLogedInUser(boolean isLogedInUser) {
		this.isLogedInUser = isLogedInUser;
	}

}
