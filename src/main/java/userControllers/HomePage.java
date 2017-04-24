package userControllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.Event;
import user.UserAuthentication;
import user.UserDetails;
import userAccountManagment.AccountManagmentUtility;
import admin.AdminUtilites;

import com.google.gson.Gson;

/**
 * Servlet implementation class HomePage
 */
@WebServlet(urlPatterns = "/user/HomePage", name = "HomePage")

public class HomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{

		doPost(request, response);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		UserDetails userDetails = UserAuthentication.getCurrentUser(session);
		
		String branchName = AdminUtilites.getBranchDetail(userDetails.getBranchId()).getBranchName();
		String collegeName = AdminUtilites.getCollegeDetail(userDetails.getCollageId()).getName();
		
		UserDataPresenter userData = new UserDataPresenter(userDetails.getId(), userDetails.getName(), userDetails.getEmailId(), collegeName, branchName);
		Map<String, String> userStatus = new HashMap<String, String>();

		List<Event> events = AdminUtilites.getEventDetail(userDetails.getCollageId(), userDetails.getBranchId());
		Gson gson = new Gson();
		if(userDetails.getIsLogedInUser() && userDetails.getFolderId() != null)
		{
			userStatus.put("result", "true");
			userStatus.put("dataUser", gson.toJson(userData));
			userStatus.put("events", gson.toJson(events));
			userStatus.put("dataItemCountsBasedOnType", new Gson().toJson(AccountManagmentUtility.getCountBasedOnType(AccountManagmentUtility.getPhotosInBag(request.getSession()))));
			response.getWriter().write(gson.toJson(userStatus));
		}
		else
		{
			userStatus.put("result", "false");
			userStatus.put("data", null);
			userStatus.put("cause", "No Loggedin user available for this session Or G-Drive folder not assigned");
			response.getWriter().write(gson.toJson(userStatus));
		}
	}

	private class UserDataPresenter
	{
		int id;
		String name;
		String email;
		String collegeName;
		String branchName;
		
		public UserDataPresenter(int id, String name, String email, String collegeName, String branchName)
		{
			this.id = id;
			this.name = name;
			this.email = email;
			this.collegeName = collegeName;
			this.branchName = branchName;
		}
	}
}
