package user;
public class CollageInfo {

	private String Name;
	private String Location;
	private int CollageId;
	private String Pincode;
	private String Abbrevation;
	private String Address;
	

	public CollageInfo() {
		super();
	}
	public CollageInfo(String name, String location, int collageId, String pincode, String abbrevation, String Address) {
		this.Name = name;
		this.Location = location;
		this.CollageId = collageId;
		this.Pincode = pincode;
		this.Abbrevation = abbrevation;
		this.Address = Address;
	}
	
	
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getLocation() {
		return Location;
	}
	public void setLocation(String location) {
		Location = location;
	}
	public int getCollageId() {
		return CollageId;
	}
	public void setCollageId(int collageId) {
		CollageId = collageId;
	}
	public String getPincode() {
		return Pincode;
	}
	public void setPincode(String pincode) {
		Pincode = pincode;
	}
	public String getAbbrevation() {
		return Abbrevation;
	}
	public void setAbbrevation(String abbrevation) {
		Abbrevation = abbrevation;
	}
	
	
}
