package admin;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import user.Branches;
import user.CollageInfo;
import user.Event;
import user.UserAuthentication;
import user.UserDetails;
import utilities.StringTools;
import connection.ConnectionManager;
import drive.DriveCommunications;

public class AdminUtilites {

	public static boolean createCollage(CollageInfo collage) {
		
		try {
			Connection conn = ConnectionManager.getConnection();
			
			String SqlInsertCollages = "INSERT INTO MM_COLLAGE (COLLAGE_NAME, COLLAGE_LOCATION, COLLAGE_ADDRESS, COLLAGE_PINCODE, COLLAGE_ABBREVATION) VALUES(?,?,?,?,?)";
			
			 PreparedStatement pStmt = conn.prepareStatement(SqlInsertCollages);
			 
			 pStmt.setString(1, collage.getName());
			 pStmt.setString(2, collage.getLocation() == null ? "" : collage.getLocation());
			 pStmt.setString(3, collage.getAddress());
			 pStmt.setString(4, collage.getPincode());
			 pStmt.setString(5, collage.getAbbreviation());

			 pStmt.execute();
			 
			 return true;
			 
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}		
		
	}

	@SuppressWarnings("null")
	public static List<CollageInfo> getAllCollageList() {

		 Connection conn;
		 List<CollageInfo> collages = null;
		try {
			conn = ConnectionManager.getConnection();
			String SqlSelectAllCollages = "SELECT * FROM MM_COLLAGE";
			PreparedStatement pStmt = conn.prepareStatement(SqlSelectAllCollages);
			ResultSet res = pStmt.executeQuery();
			collages = new LinkedList<CollageInfo>();
			while (res.next()) {
				CollageInfo collage = new CollageInfo(res.getString("COLLAGE_NAME"), res.getString("COLLAGE_LOCATION"), res.getInt("ID"), res.getString("COLLAGE_PINCODE"), res.getString("COLLAGE_ABBREVATION"), res.getString("COLLAGE_ADDRESS"));
				collages.add(collage);				
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		return collages;
	}

	public static boolean createBranch(Branches branch) {

		try 
		{
			Connection conn = ConnectionManager.getConnection();
			String SqlInsertBranches = "INSERT INTO MM_BRANCH (COLLAGE_ID, BRANCH_NAME, BRANCH_ABBRIVATION) VALUES(?, ?, ?)";
			
			PreparedStatement pStmt = conn.prepareStatement(SqlInsertBranches);
			pStmt.setInt(1, branch.getCollageId());
			pStmt.setString(2, branch.getBranchName());
			pStmt.setString(3, branch.getBranchAbbrevation());
			
			pStmt.executeUpdate();

			return true;
			
		} 
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public static List<Branches> getAllBranches() {
		List<Branches> branches = new LinkedList<Branches>();
		
		try 
		{
			Connection conn = ConnectionManager.getConnection();
			String SqlSelectAllBranches = "SELECT * FROM MM_BRANCH"; 
			PreparedStatement pStmt = conn.prepareStatement(SqlSelectAllBranches);
			ResultSet res = pStmt.executeQuery();
			while (res.next()) 
			{
				Branches branch = new Branches(res.getInt("ID"), res.getInt("COLLAGE_ID"), res.getString("BRANCH_NAME"), res.getString("BRANCH_ABBRIVATION"));
				branches.add(branch);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return branches;
	}

	public static boolean createEvent(Event event) {
	
		try {
			Connection conn	= ConnectionManager.getConnection();
			
			String SqlCreateEvent = "INSERT INTO MM_EVENT (EVENT_NAME, EVENT_DISCRIPTION, EVENT_START, EVENT_END, EVENT_DATA_DELETE, COLLAGE_ID, BRANCH_ID, MODIFIED_BY, MODIFIED_ON, FOLDER_ID) VALUES (?, ?, ?, ?,?, ?, ?,?,?,?)";
			PreparedStatement pStmt = conn.prepareStatement(SqlCreateEvent);
			pStmt.setString(1, event.getName());
			pStmt.setString(2, event.getDiscription());
			pStmt.setTimestamp(3, event.getStartDate());
			pStmt.setTimestamp(4, event.getEndDate());
			pStmt.setTimestamp(5, event.getDataDelete());
			pStmt.setInt(6, event.getCollageId());
			pStmt.setInt(7, event.getBranchId());
			pStmt.setInt(8, 0);
			pStmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
			pStmt.setString(10, event.getFolderId());
			
			pStmt.execute();
			return true;
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public static List<UserDetails> getUsersFromExcel(FileInputStream inputStream, int collegeId, int branchId) 
	{
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();
		List<UserDetails> users = new LinkedList<UserDetails>(); 
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			if(nextRow.getRowNum() == 0)
				nextRow = iterator.next();
			
			Iterator<Cell> cellIterator = nextRow.cellIterator();

			UserDetails user = new UserDetails();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				cell.getColumnIndex();
				
				switch (cell.getColumnIndex()) {
				case 0:
					user.setName(cell.getStringCellValue());
				break;

				case 1:
					String email = cell.getStringCellValue();
					if(StringTools.isValidEmail(email))
						user.setEmailId(cell.getStringCellValue());
					else
						throw new InputMismatchException();
				break;
				case 2:
					user.setPhoneNumber(new String(""+new java.text.DecimalFormat("0").format(cell.getNumericCellValue())+""));
				break;
				default:
					break;
				}
			}
			
			user.setCollageId(collegeId);
			user.setBranchId(branchId);
			
			users.add(user);
		}
		try {
			workbook.close();
			inputStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return users;
	}

	public static boolean InsertUsers(List<UserDetails> users) 
	{
		boolean isAllOk = true;
		for (UserDetails user : users) {
			if(UserAuthentication.isUserExisting(user.getEmailId()))
				continue;
			
			String SqlInsertUsers = "INSERT INTO MM_USER(NAME,EMAIL,PHONE,PASSWORD,COLLAGE_ID,BRANCH_ID)VALUES(?,?,?,?,?,?)";
			Connection conn;
			try {
				conn = ConnectionManager.getConnection();
				PreparedStatement pStmt = conn.prepareStatement(SqlInsertUsers);
				pStmt.setString(1, user.getName());
				pStmt.setString(2, user.getEmailId());
				pStmt.setString(3, user.getPhoneNumber());
				pStmt.setString(4, null);
				pStmt.setInt(5, user.getCollageId());
				pStmt.setInt(6, user.getBranchId());
				pStmt.executeUpdate();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				isAllOk = false;
				System.out.println("User with email: " + user.getEmailId() + " failed to save");
			}
			
		}
		return isAllOk;
	}
	
	public static List<Branches> getAllBranches(int collegeId) {
		List<Branches> branches = getAllBranches();
		List<Branches> selectedBranches = new LinkedList<Branches>();
		
		for(Branches branch: branches)
			if(collegeId == branch.getCollageId())
				selectedBranches.add(branch);
		
		return selectedBranches;
	}
	
	public static boolean isCollegeExisting(String collegeName)
	{
		for(CollageInfo ci: getAllCollageList())
			if(ci.getName() != null && ci.getName().equals(collegeName))
				return true;
		
		return false;
	}
	
	public static boolean isBranchExisting(String branchName)
	{
		for(Branches branch: getAllBranches())
			if(branch.getBranchName() != null && branch.getBranchName().equals(branchName))
				return true;
		
		return false;
	}
	
	
	public static CollageInfo getCollegeDetail(int id)
	{		
		String selectCollageDetail = "SELECT * FROM MM_COLLAGE WHERE ID = ?";
		CollageInfo college = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pStmt = conn.prepareStatement(selectCollageDetail);
			pStmt.setInt(1, id);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) 
			{
				college = new CollageInfo(rs.getString("COLLAGE_NAME"), rs.getString("COLLAGE_LOCATION"), id , rs.getString("COLLAGE_PINCODE"), rs.getString("COLLAGE_ABBREVATION"), rs.getString("COLLAGE_ADDRESS"));
			}
			
			return college;			
		}
		catch (ClassNotFoundException | SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		

	}
	
	public static Branches getBranchDetail(int id)
	{		
		String selectBranchDetail = "SELECT * FROM MM_BRANCH WHERE ID = ?";
		
		Branches branch = null;
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pStmt = conn.prepareStatement(selectBranchDetail);
			pStmt.setInt(1, id);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) 
			{
				branch = new Branches(id, rs.getInt("COLLAGE_ID"), rs.getString("BRANCH_NAME"), rs.getString("BRANCH_ABBRIVATION "));
			}
			return branch;
		}
		catch (ClassNotFoundException | SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		

	}
	
	
	public static Event getEventDetail(int collegeId, int branchId)
	{		
		String selectEventDetail = "SELECT * FROM MM_BRANCH WHERE COLLAGE_ID = ? AND BRANCH_ID = ?";
		
		try 
		{
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pStmt = conn.prepareStatement(selectEventDetail);
			pStmt.setInt(1, collegeId);
			pStmt.setInt(2, branchId);
			
			ResultSet rs = pStmt.executeQuery();
	
			return new Event(rs.getString("EVENT_NAME"), rs.getString("EVENT_DISCRIPTION"), rs.getTimestamp("EVENT_START"), rs.getTimestamp("EVENT_END"), rs.getTimestamp("EVENT_DATA_DELETE"), collegeId, branchId, rs.getString("FOLDER_ID"), rs.getInt("ID"), DriveCommunications.getEventThumbnail(DriveCommunications.getDriveService(), rs.getString("FOLDER_ID")));
		
		}
		catch (ClassNotFoundException | SQLException | IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}		

	}

	public static List<Eventpresenter> getListOfAllEvents() {

		
		return null;
	}
	
	
	

}
