package authentications;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import connection.connectionManager;
import drive.DriveCommunications;
import user.UserDetails;
import utilities.CommonTypes;

public class HandleAjaxCall extends HttpServlet {
	
	public static final int ACTION_CREATE_USER = 1;
	public static final int ACTION_AUTHENTICATE_USER = 2;
	
	private static final long serialVersionUID = 1L;
	private static final int ACTION_LOGOUT_USER = 3;
	private static final int ACTION_LODE_DRIVE_PHOTOS = 4;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int action = Integer.parseInt(request.getParameter("action"));

		UserDetails mmUser = new UserDetails();
		mmUser.setName(request.getParameter("userName"));
		mmUser.setPhoneNumber(request.getParameter("phoneNumber"));
		mmUser.setEmailId(request.getParameter("email"));
		mmUser.setAddress(request.getParameter("address"));
		mmUser.setPassword(request.getParameter("password"));
		System.out.println(request.getSession().getAttribute("UserPrimaryKey"));
		try {
			Connection conn = connectionManager.getConnection();
			
			try {
				switch (action)
				{
				case ACTION_CREATE_USER:
					insert_MM_UserDetails(conn, mmUser);
				break;
				
				case ACTION_AUTHENTICATE_USER:
					authenticate_MM_User(conn, mmUser, request);
				break;
				
				case ACTION_LOGOUT_USER:
					logouts_User(conn, mmUser, request);
				break;
				case ACTION_LODE_DRIVE_PHOTOS:
					DriveCommunications obj = new DriveCommunications();
					obj.fetchPhotosFromDrive();
					break;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
	
	private void logouts_User(Connection conn, UserDetails mmUser, HttpServletRequest request) {

		request.getSession().removeAttribute(CommonTypes.TOKKEN_NAME);
		request.getSession().invalidate();
	}

	private void authenticate_MM_User(Connection conn, UserDetails mmUser, HttpServletRequest request) throws SQLException {
		
		String SelectUser = "SELECT * FROM MM_USERDATA WHERE USER_EMAILID = ?";
		PreparedStatement pStmt = conn.prepareStatement(SelectUser);
		pStmt.setString(1, mmUser.getEmailId());
		
		ResultSet res = pStmt.executeQuery();
		
		while (res.next()) {
			if(res.getString("USER_PASSWORD").toString().equals(mmUser.getPassword()))
			{
				HttpSession session = request.getSession();
				session.setAttribute(CommonTypes.TOKKEN_NAME, res.getInt("USER_ID"));
				System.out.println(session.getAttribute(CommonTypes.TOKKEN_NAME));
			}
			else
			{
				System.out.println("invalid password");
			}
		}
		
		
	}

	private void insert_MM_UserDetails(Connection conn, UserDetails mmUser) throws SQLException {
		
		String InserQuery = "INSERT INTO MM_USERDATA (USER_NAME, USER_EMAILID, USER_PASSWORD, USER_ADDRESS, USER_PHONENUMBER) VALUES (?, ?, ?, ?, ?)";
		
		PreparedStatement pStmt = conn.prepareStatement(InserQuery);
		pStmt.setString(1, mmUser.getName());
		pStmt.setString(2, mmUser.getEmailId());
		pStmt.setString(3, mmUser.getPassword());
		pStmt.setString(4, mmUser.getAddress());
		pStmt.setString(5, mmUser.getPhoneNumber());
		pStmt.execute();
		
		System.out.println("User Inserted");		
		
	}

}
