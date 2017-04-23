package userAccountManagment;

import java.util.List;

public class Order {

	int userId;
	List<String> Photos;
	int price;
	int quantity;
	int totalBillingAmount;
	int deliveryCharges;
	int daysToDeliver;
	
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
	public List<String> getPhotos() {
		return Photos;
	}
	public void setPhotos(List<String> photos) {
		Photos = photos;
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
