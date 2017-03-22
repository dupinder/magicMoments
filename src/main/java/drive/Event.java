package drive;

public class Event {

	private String eventId;
	private String eventName;
	private String eventThumnail;
	private String eventDiscription;
	private String createdDate;
	private String updatedDate;
	
	public String getEventId() {
		return eventId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getEventThumnail() {
		return eventThumnail;
	}
	public void setEventThumnail(String eventThumnail) {
		this.eventThumnail = eventThumnail;
	}
	public String getEventDiscription() {
		return eventDiscription;
	}
	public void setEventDiscription(String eventDiscription) {
		this.eventDiscription = eventDiscription;
	}
	
	
	
}
