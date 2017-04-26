package drive;

public class Photos {
	
	private String photoId;
	private String photoName;
	private String photoUrl;
	private String photoThumbnailUrl;
	private String photoDiscription;
	private String createdDate;
	private String deletedDate;
	private String photoPrice;
	
	public Photos(String photoId, String photoName, String photoUrl, String photoDiscription, String createdDate, String deletedDate, String photoPrice, String photoThumbnailUrl){
		
		this.photoId = photoId;
		this.photoName = photoName;
		this.photoUrl = photoUrl;
		this.photoDiscription = photoDiscription;
		this.createdDate = createdDate;
		this.deletedDate = deletedDate;
		this.photoPrice = photoPrice;
		this.photoThumbnailUrl = photoThumbnailUrl;
	}
	
	public String getPhotoId() {
		return photoId;
	}
	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}
	public String getPhotoName() {
		return photoName;
	}
	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getPhotoDiscription() {
		return photoDiscription;
	}
	public void setPhotoDiscription(String photoDiscription) {
		this.photoDiscription = photoDiscription;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getDeletedDate() {
		return deletedDate;
	}
	public void setDeletedDate(String deletedDate) {
		this.deletedDate = deletedDate;
	}

	public String getPhotoPrice() {
		return photoPrice;
	}

	public void setPhotoPrice(String photoPrice) {
		this.photoPrice = photoPrice;
	}

	public String getPhotoThumbnailUrl() {
		return photoThumbnailUrl;
	}

	public void setPhotoThumbnailUrl(String photoThumbnailUrl) {
		this.photoThumbnailUrl = photoThumbnailUrl;
	}





}
