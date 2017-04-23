package user;

import java.sql.Timestamp;

public class Event {

	private String name;
	private String discription;
	private Timestamp startDate;
	private Timestamp endDate;
	private Timestamp dataDelete;
	private int collageId;
	private int branchId;
	private String folderId;
	private String eventThumbnailURL;
	private int id;
	
	
	public Event(String name, String discription, Timestamp startDate, Timestamp endDate, Timestamp dataDelete,
			int collageId, int branchId, String folderId, int id, String eventThumbnailURL) 
	{
		this.name = name;
		this.discription = discription;
		this.startDate = startDate;
		this.endDate = endDate;
		this.dataDelete = dataDelete;
		this.collageId = collageId;
		this.branchId = branchId;
		this.folderId = folderId;
		this.id = id;
		this.eventThumbnailURL = eventThumbnailURL;
	}
	
	public Event()
	{
		super();
	}

	public int getId() 
	{
		return id;
	}
	public void setId(int id) 
	{
		this.id = id;
	}
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public String getDiscription() 
	{
		return discription;
	}
	public void setDiscription(String discription) 
	{
		this.discription = discription;
	}
	public Timestamp getStartDate() 
	{
		return startDate;
	}
	public void setStartDate(Timestamp startDate) 
	{
		this.startDate = startDate;
	}
	public Timestamp getEndDate() 
	{
		return endDate;
	}
	public void setEndDate(Timestamp endDate) 
	{
		this.endDate = endDate;
	}
	public Timestamp getDataDelete() 
	{
		return dataDelete;
	}
	public void setDataDelete(Timestamp dataDelete) 
	{
		this.dataDelete = dataDelete;
	}
	public int getCollageId() 
	{
		return collageId;
	}
	public void setCollageId(int collageId) 
	{
		this.collageId = collageId;
	}
	public int getBranchId() 
	{
		return branchId;
	}
	public void setBranchId(int branchId) 
	{
		this.branchId = branchId;
	}
	public String getFolderId() {
		return folderId;
	}
	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	public String getEventThumbnailURL() {
		return eventThumbnailURL;
	}
	public void setEventThumbnailURL(String eventThumbnailURL) {
		this.eventThumbnailURL = eventThumbnailURL;
	}	
}
