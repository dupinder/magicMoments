package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
			int actionToTake = IsExistingUser(email);

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
		String SavePassword = "UPDATE MM_USER SET USER_PASSWORD = ? WHERE USER_EMAIL = ?";
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

	public static int IsExistingUser(String email) throws ClassNotFoundException {

		int actionToTake = 0;
		if(!StringTools.isValidEmail(email))
			return actionToTake;

		String SqlIsExistingUser = "SELECT USER_EMAIL, USER_PASSWORD FROM MM_USER WHERE USER_EMAIL = ?";

		Connection conn = ConnectionManager.getConnection();
		try {
			PreparedStatement pStmt = conn.prepareStatement(SqlIsExistingUser);
			pStmt.setString(1, email);
			ResultSet resultSet = pStmt.executeQuery();
			if (!resultSet.next()) {
				actionToTake = ACTION_INVALID_USER;
			} 
			else 
			{
				String password = resultSet.getString("USER_PASSWORD");
				if (!StringTools.isValidString(password)) 
				{
					actionToTake = ACTION_VALID_NEW_USER;
				} 
				else
				{
					actionToTake = ACTION_VALID_EXSISTING_USER;
				}
			}

		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			
			while (res.next()) {
				if(res.getString("PASSWORD").toString().equals(password)){
		
					HttpSession session = request.getSession();
					
				session.setAttribute(CommonTypes.USER_DETAILS_SESSION_KEY, res.getInt("ID"));
					authenticationStatus = true;
				}
				else{
					authenticationStatus = false;
				}
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
				userDetails = getUserDetails(Integer.parseInt((String)session.getAttribute(CommonTypes.USER_DETAILS_SESSION_KEY)));
			} 
			catch (NumberFormatException | ClassNotFoundException | SQLException e) 
			{
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("not valid user");
		}
		
		return userDetails;
	}

	private static UserDetails getUserDetails(int userId) throws ClassNotFoundException, SQLException 
	{

		UserDetails userDetails = null;
		Connection conn = ConnectionManager.getConnection();
		String SQLSelectUser = "SELECT * FROM MM_USER WHERE ID = ?";
		PreparedStatement pStmt = conn.prepareStatement(SQLSelectUser);
		pStmt.setInt(1, userId);
		ResultSet res = pStmt.executeQuery();
		if (!res.isBeforeFirst()) 
		{    
		    userDetails = new UserDetails();
		    userDetails.setIsLogedInUser(false);
		}
		else
		{
			while (res.next()) 
			{
				userDetails = new UserDetails(res.getInt("ID"), res.getString("USER_NAME"), res.getString("USER_EMAIL"), res.getString("FOLDER_ID"), res.getInt("COLLAGE_ID"));
				userDetails.setIsLogedInUser(true);
			}
		}
		return userDetails;
	}
	
}




