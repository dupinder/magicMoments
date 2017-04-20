package userAccountManagment;

public class PhotoManagment {

	public static final boolean cart = true;
	public static final boolean wishlist = false;
	public PhotoManagment() {
		super();
	}

	int id;
	String photoId;
	boolean type;
	
	
	public PhotoManagment(int id, String photoId, boolean type) {
		super();
		this.id = id;
		this.photoId = photoId;
		this.type = type;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhotoId() {
		return photoId;
	}
	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}
	public boolean isType() {
		return type;
	}
	public void setType(boolean type) {
		this.type = type;
	}
	
	
	
	
}
