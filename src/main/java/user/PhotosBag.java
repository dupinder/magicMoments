package user;

public class PhotosBag {

	private String PhotoId;
	private int userId;
	private int type; 	
	private int quantity;
	private int price;
	private int daysToDeliver;
	
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
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getDaysToDeliver() {
		return daysToDeliver;
	}
	public void setDaysToDeliver(int daysToDeliver) {
		this.daysToDeliver = daysToDeliver;
	}
	
	
}
