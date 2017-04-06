package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.print.attribute.standard.PresentationDirection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import connection.connectionManager;
import utilities.CommonTypes;
import utilities.EmailSender;

public class UserAuthentication {
	private static Map<String, OtpManager> otpManager = new HashMap<String, OtpManager>();
	private EmailSender emailSender = new EmailSender();
	private String emailValidationPattern = "^[a-zA-Z]{1,30}[_A-Za-z0-9-]{0,30}(\\.[A-Za-z0-9-_]{1,30})*@[a-zA-Z0-9-]{1,30}(\\.[A-Za-z0-9-]{1,30}){1,2}$";

	
	public static final int ACTION_INVALID_USER = 1;
	public static final int ACTION_VALID_NEW_USER = 2;
	public static final int ACTION_VALID_EXSISTING_USER = 3;

	public boolean generateOtpAndSendEmail(String email) {
		if (!email.matches(emailValidationPattern))
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

	public boolean verifyOtp(String email, String otp) {
		if (!email.matches(emailValidationPattern))
			return false;

		if (otpManager.containsKey(email)) {
			OtpManager otpInCache = otpManager.get(email);
			if (otpInCache != null) {
				boolean isOtpValid = (new Date().getTime()) - otpInCache.time > 5 * 60 * 1000 ? false : true;
				if (isOtpValid && otpInCache.otp.equals(otp)) {
					otpManager.remove(email);
					return true;
				}
			}
		}

		return false;
	}

	public static boolean SignupUser(String email, String password) {

		try {
			int actionToTake = IsExistingUser(email);

			switch (actionToTake) {
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

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public static boolean savePassword(String email, String password) throws ClassNotFoundException {
		boolean isPasswordUpdated = false;
		String SavePassword = "UPDATE MM_USER SET USER_PASSWORD = " + password + " WHERE USER_EMAIL = " + email + "";
		try {
			Connection conn = connectionManager.getConnection();
			PreparedStatement pStmt = conn.prepareStatement(SavePassword);
			if (pStmt.execute())
				isPasswordUpdated = true;
			else
				isPasswordUpdated = false;
			
			conn.commit();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return isPasswordUpdated;
	}

	public static int IsExistingUser(String email) throws ClassNotFoundException {

		int actionToTake = 0;

		String SqlIsExistingUser = "SELECT USER_EMAIL, USER_PASSWORD FROM MM_USER WHERE USER_EMAIL = '"+email+"'";

		Connection conn = connectionManager.getConnection();
		try {
			PreparedStatement pStmt = conn.prepareStatement(SqlIsExistingUser);
			ResultSet resultSet = pStmt.executeQuery();
			if (!resultSet.next()) {
				//System.out.println("User Not allowed to access Magic Moments");
				actionToTake = ACTION_INVALID_USER;
			} else {
				String password = resultSet.getString("USER_PASSWORD");
				if (password == null) {
					//System.out.println("new User");
					actionToTake = ACTION_VALID_NEW_USER;
				} else {
					//System.out.println("Exsisting User");
					actionToTake = ACTION_VALID_EXSISTING_USER;
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return actionToTake;
	}

	private class OtpManager {
		String email;
		String otp;
		long time;
	}


	public static boolean LoginCreateSession(String email, String password, HttpServletRequest request) throws ClassNotFoundException, SQLException {

		boolean authenticationStatus = false;
		Connection conn = connectionManager.getConnection();
		String SQLSelectUser = "SELECT USER_EMAIL, USER_PASSWORD, ID FROM MM_USER WHERE USER_EMAIL = '"+email+"' AND USER_PASSWORD = '"+password+"'";
		PreparedStatement pStmt = conn.prepareStatement(SQLSelectUser);
		
		ResultSet res = pStmt.executeQuery();
		
		while (res.next()) {
			if(res.getString("USER_PASSWORD").toString().equals(password)){
				HttpSession session = request.getSession();
				session.setAttribute(CommonTypes.TOKKEN_NAME, res.getInt("ID"));
				authenticationStatus = true;
			}
			else{
				authenticationStatus = false;
			}
		}
		return authenticationStatus;
	}
}
