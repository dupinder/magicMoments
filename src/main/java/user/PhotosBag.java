package user;

public class PhotosBag {

	private String PhotoId;
	private int userId;
	private int type; 	
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPhotoId() {
		return PhotoId;
	}
	public void setPhotoId(String photoId) {
		PhotoId = photoId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
