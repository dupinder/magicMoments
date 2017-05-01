package userAccountManagment;


public class Order {

	int userId;
	String referenceId;
	String photoId;
	int price;
	int quantity;
	int totalBillingAmount;
	int deliveryCharges;
	int daysToDeliver;
	
	public String getReferenceId(){
		return referenceId;
	}
	public void setReferenceId(String orderReference){
		this.referenceId = orderReference;
	}
	public int getDaysToDeliver() {
		return daysToDeliver;
	}
	public void setDaysToDeliver(int daysToDeliver) {
		this.daysToDeliver = daysToDeliver;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getPhotoId() {
		return photoId;
	}
	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getTotalBillingAmount() {
		return totalBillingAmount;
	}
	public void setTotalBillingAmount(int totalBillingAmount) {
		this.totalBillingAmount = totalBillingAmount;
	}
	public int getDeliveryCharges() {
		return deliveryCharges;
	}
	public void setDeliveryCharges(int deliveryCharges) {
		this.deliveryCharges = deliveryCharges;
	} 
	
}
