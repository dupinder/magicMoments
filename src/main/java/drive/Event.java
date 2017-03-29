package drive;

public class Event {

	private String eventId;
	private String eventName;
	private String eventThumbnailURL;
	private String eventDiscription;
	private String createdDate;
	private String updatedDate;
	
	public Event(String eventId, String eventName, String eventThumbnailURL, String eventDiscription, String createdDate, String updatedDate) {
		
		this.eventId = eventId;
		this.eventName = eventName;
		this.eventThumbnailURL = eventThumbnailURL;
		this.eventDiscription = eventDiscription;
		this.createdDate = createdDate;
		this.updatedDate = updatedDate;
	}
	
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
	public String getEventThumnailURL() {
		return eventThumbnailURL;
	}
	public void setEventThumnailURL(String eventThumnailURL) {
		this.eventThumbnailURL = eventThumnailURL;
	}
	public String getEventDiscription() {
		return eventDiscription;
	}
	public void setEventDiscription(String eventDiscription) {
		this.eventDiscription = eventDiscription;
	}
	
	
	
}
