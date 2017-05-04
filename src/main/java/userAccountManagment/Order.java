package userAccountManagment;


public class Order {
	private int userId;
	private String referenceId;
	private String photoId;
	private String photoUrl;
	private int price;
	private int quantity;
	private int totalBillingAmount;
	private int deliveryCharges;
	private int daysToDeliver;
	private int eventId;
	private String eventName;
	
	
	public int getEventId(){
		return eventId;
	}
	public void setEventId(int eventId){
		this.eventId = eventId;
	}
	public String getEventName(){
		return eventName;
	}
	public void setEventName(String eventName){
		this.eventName = eventName;
	}
	public String getPhotoUrl(){
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl){
		this.photoUrl = photoUrl;
	}
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
