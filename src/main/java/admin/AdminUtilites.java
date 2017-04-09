package admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import connection.ConnectionManager;
import user.Branches;
import user.CollageInfo;

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
			 pStmt.setString(5, collage.getAbbrivation());

			 pStmt.executeUpdate();
			 
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
			while (res.next()) {
				CollageInfo collage = new CollageInfo(res.getString("COLLAGE_NAME"), res.getString("COLLAGE_LOCATION"), res.getInt("ID"), res.getString("COLLAGE_PINCODE"), res.getString("COLLAGE_ABBRIVATION"), res.getString("COLLAGE_ADDRESS"));
				collages.add(collage);				
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		return collages;
	}

	public static boolean createBranches(Branches branch) {

		try 
		{
			List<Branches> branches = new LinkedList<Branches>();
			Connection conn = ConnectionManager.getConnection();
			String SqlInsertBranches = "INSERT INTO MM_BRANCH (COLLAGE_ID, BRANCH_NAME, BRANCH_ABBRIVATION) VALUES(?, ?, ?)";
			
			PreparedStatement pStmt = conn.prepareStatement(SqlInsertBranches);
			pStmt.setInt(1, branch.getCollageId());
			pStmt.setString(2, branch.getBranchName());
			pStmt.setString(3, branch.getBranchAbbrivation());
			
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
	
	
	
	

}
