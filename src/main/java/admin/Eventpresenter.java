package admin;

import java.sql.Timestamp;

import user.Event;

public class Eventpresenter extends Event
{
	 String collegeName;
	 String branchName;
	 String collegeAbbr;
	 String branchAbbr;
	 
		public Eventpresenter(String name, String discription, Timestamp startDate, Timestamp endDate, Timestamp dataDelete,
				int collageId, int branchId, String folderId, int id, String eventThumbnailURL, String collegeName, String branchName, String collegeAbbr, String branchAbbr) 
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
			this.collegeName = collegeName;
			this.collegeAbbr = collegeAbbr;
			this.branchName = branchName;
			this.branchAbbr = branchAbbr;
		}

		public String getCollegeName() {
			return collegeName;
		}

		public void setCollegeName(String collegeName) {
			this.collegeName = collegeName;
		}

		public String getBranchName() {
			return branchName;
		}

		public void setBranchName(String branchName) {
			this.branchName = branchName;
		}

		public String getCollegeAbbr() {
			return collegeAbbr;
		}

		public void setCollegeAbbr(String collegeAbbr) {
			this.collegeAbbr = collegeAbbr;
		}

		public String getBranchAbbr() {
			return branchAbbr;
		}

		public void setBranchAbbr(String branchAbbr) {
			this.branchAbbr = branchAbbr;
		}
		
		
	 
}