package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import utilities.CommonTypes;
import utilities.EmailSender;
import utilities.StringTools;
import admin.Admin;

import com.google.api.services.drive.model.User;

import connection.ConnectionManager;

public class UserAuthentication 
{
	private static Map<String, OtpManager> otpManager = new HashMap<String, OtpManager>();
	private EmailSender emailSender = new EmailSender();

	
	public static final int ACTION_INVALID_USER = 1;
	public static final int ACTION_VALID_NEW_USER = 2;
	public static final int ACTION_VALID_EXSISTING_USER = 3;

	
	public boolean generateOtpAndSendEmail(String email) 
	{
		if (!StringTools.isValidEmail(email))
			return false;

		String otp = utilities.StringTools.getRandomString(6);
		emailSender.sendEmail(email, "Hi there, here is your OTP: " + otp + ". Complete verification process.");
		OtpManager om = new OtpManager();
		om.email = email;
		om.otp = otp;
		om.time = new Date().getTime();
		otpManager.put(email, om);
		return true;
	}

	public boolean verifyOtp(String email, String otp) 
	{
		if (!StringTools.isValidEmail(email))
			return false;

		if (otpManager.containsKey(email)) 
		{
			OtpManager otpInCache = otpManager.get(email);
			if (otpInCache != null) 
			{
				boolean isOtpValid = (new Date().getTime()) - otpInCache.time > 5 * 60 * 1000 ? false : true;
				if (isOtpValid && otpInCache.otp.equals(otp)) 
				{
					otpManager.remove(email);
					return true;
				}
			}
		}

		return false;
	}

	public static boolean SignupUser(String email, String password) {

		try 
		{
			int actionToTake = getExistingUser(email);

			switch (actionToTake) 
			{
				case ACTION_INVALID_USER:
	
					break;
				case ACTION_VALID_NEW_USER:
					savePassword(email, password);
					break;
	
				case ACTION_VALID_EXSISTING_USER:
	
					break;
				default:
					break;
			}

		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public static boolean savePassword(String email, String password) throws ClassNotFoundException 
	{
		String SavePassword = "UPDATE MM_USER SET PASSWORD = ? WHERE EMAIL = ?";
		try
		{
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pStmt = conn.prepareStatement(SavePassword);
			pStmt.setString(1, password);
			pStmt.setString(2, email);
			pStmt.executeUpdate();
			conn.commit();
			conn.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static Boolean isUserExisting(String email)
	{
		UserDetails userDetail = isExistingUser(email);
		if(userDetail == null)
			return false;
		return true;
	}
	
	public static UserDetails isExistingUser(String email)
	{
		if(!StringTools.isValidEmail(email))
			return null;

		String SqlIsExistingUser = "SELECT EMAIL, PASSWORD FROM MM_USER WHERE EMAIL = ?";

		ResultSet resultSet = null;
		Connection conn = null;
		try {
			conn = ConnectionManager.getConnection();
			PreparedStatement pStmt = conn.prepareStatement(SqlIsExistingUser);
			pStmt.setString(1, email);
			resultSet = pStmt.executeQuery();
			if(!resultSet.next())
				return null;
			
			UserDetails ud = new UserDetails();
			ud.setEmailId(resultSet.getString("EMAIL"));
			ud.setPassword(resultSet.getString("PASSWORD"));
			
			return ud;
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if(conn != null)
				try
				{
					conn.close();
				} catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		return null;
		
	}
	
	public static int getExistingUser(String email) throws ClassNotFoundException {

		int actionToTake = 0;
		if(!StringTools.isValidEmail(email))
			return actionToTake;

		UserDetails userDetail = isExistingUser(email);
		if (userDetail == null) {
			actionToTake = ACTION_INVALID_USER;
		} 
		else 
		{
			String password = userDetail.getPassword();
			if (!StringTools.isValidString(password)) 
			{
				actionToTake = ACTION_VALID_NEW_USER;
			} 
			else
			{
				actionToTake = ACTION_VALID_EXSISTING_USER;
			}
		}

		return actionToTake;
	}
	
	public static boolean LoginCreateSession(String email, String password, String classType, HttpServletRequest request) throws ClassNotFoundException 
	{
		if(!StringTools.isValidEmail(email) || !StringTools.isValidPassword(password))
			return false;
		
		String tableName = null;
		if(Admin.class.getName().equals(classType))
		{
			tableName = "MM_ADMINS";
		}
		else if(User.class.getName().equals(classType))
		{
			tableName = "MM_USER";
		}
		
		return LoginCreateSession(email, password, request, tableName);
	}

private class OtpManager {

		String email;
		String otp;
		long time;
	}


	private static boolean LoginCreateSession(String email, String password, HttpServletRequest request, String tableName) 
	{
		boolean authenticationStatus = false;
		try
		{
			Connection conn = ConnectionManager.getConnection();
			String SQLSelectUser = "SELECT EMAIL, PASSWORD, ID FROM "+ tableName +" WHERE EMAIL = ? AND PASSWORD = ?";
			PreparedStatement pStmt = conn.prepareStatement(SQLSelectUser);
			pStmt.setString(1, email);
			pStmt.setString(2, password);
			ResultSet res = pStmt.executeQuery();
			
			if(!res.isBeforeFirst())
				return false;
			
			while (res.next()) {
		
				HttpSession session = request.getSession();
				authenticationStatus = true;
				
				if("MM_USER".equals(tableName))
				{
					UserDetails ud = getUserDetails(res.getInt("ID"));
					ud.setLogedInUser(true);
					session.setAttribute(CommonTypes.USER_DETAILS_SESSION_KEY, ud);
				}
				else if("MM_ADMINS".equals(tableName))
				{
					Admin admin = new Admin(email, password);
					session.setAttribute(CommonTypes.AMDIN_SESSION_KEY, admin);
				}
				
				break;
			}
			
			return authenticationStatus;
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public static UserDetails getCurrentUser(HttpSession session)
	{
		UserDetails userDetails = new UserDetails();
		if(session.getAttribute(CommonTypes.USER_DETAILS_SESSION_KEY) != null)
		{
			try 
			{				
				userDetails = (UserDetails) session.getAttribute(CommonTypes.USER_DETAILS_SESSION_KEY); 
			} 
			catch (NumberFormatException e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			userDetails.setIsLogedInUser(false);
		}
		
		return userDetails;
	}

	public static UserDetails getUserDetails(int userId) throws ClassNotFoundException, SQLException 
	{
		UserDetails userDetails = null;
		Connection conn = ConnectionManager.getConnection();
		String SQLSelectUser = "SELECT * FROM MM_USER WHERE ID = ?";
		PreparedStatement pStmt = conn.prepareStatement(SQLSelectUser);
		pStmt.setInt(1, userId);
		ResultSet res = pStmt.executeQuery();
		if (!res.isBeforeFirst()) 
		{    
			return null;
		}
		else
		{
			while(res.next()) 
			{
				Set<String> folderIds = getFolderIds(res.getInt("COLLAGE_ID"), res.getInt("BRANCH_ID"));
				userDetails = new UserDetails(res.getInt("ID"), res.getString("NAME"), res.getString("EMAIL"), folderIds, res.getInt("COLLAGE_ID"), res.getInt("BRANCH_ID"));
				userDetails.setIsLogedInUser(true);
				break;
			}
		}
		return userDetails;
	}
	
	private static Admin getAdminDetails(String adminEmail)
	{
		Admin admin = null;
		try
		{
			Connection conn = ConnectionManager.getConnection();
			String query = "SELECT * FROM MM_ADMIN WHERE EMAIL = ?";
			PreparedStatement pStmt = conn.prepareStatement(query);
			pStmt.setString(1, adminEmail);
			ResultSet res = pStmt.executeQuery();
			if (!res.isBeforeFirst()) 
			{    
				return null;
			}
			else
			{
				while (res.next()) 
				{
					admin = new Admin(res.getString("NAME"), res.getString("PASSWORD"));
					break;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return admin;
	}

	private static Set<String> getFolderIds(int collegeId, int branchId) {
		
		String SqlSelectUserFolders = "SELECT FOLDER_ID FROM MM_EVENT WHERE COLLAGE_ID = ? AND BRANCH_ID = ?";
		Set<String> userFolderIds = new HashSet<String>();
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pStmt = conn.prepareStatement(SqlSelectUserFolders);
			pStmt.setInt(1, collegeId);
			pStmt.setInt(2, branchId);
			ResultSet res = pStmt.executeQuery();
			while (res.next()) {
				userFolderIds.add(res.getString("FOLDER_ID"));
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userFolderIds;
	}
	
}




