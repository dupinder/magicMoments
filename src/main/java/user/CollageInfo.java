package user;
public class CollageInfo {

	private String name;
	private String location;
	private int id;
	private String pincode;
	private String abbreviation;
	private String address;
	

	public CollageInfo() {
		super();
	}
	public CollageInfo(String name, String location, int collageId, String pincode, String abbreviation, String Address) {
		this.name = name;
		this.location = location;
		this.id = collageId;
		this.pincode = pincode;
		this.abbreviation = abbreviation;
		this.address = Address;
	}
	
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getCollageId() {
		return id;
	}
	public void setCollageId(int collageId) {
		id = collageId;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbrivation) {
		this.abbreviation = abbrivation;
	}
	
	
}
