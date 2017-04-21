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

import com.google.gson.Gson;

import drive.DriveCommunications;
import drive.Photos;
import user.UserAuthentication;
import user.UserDetails;
import userAccountManagment.AccountManagmentUtility;

/**
 * Servlet implementation class HomePage
 */
@WebServlet(urlPatterns = "/HomePage", name = "HomePage")

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
		DriveCommunications driveService = new DriveCommunications();

		HttpSession session = request.getSession();
		UserDetails userDetails = UserAuthentication.getCurrentUser(session);
		Map<String, String> userStatus = new HashMap<String, String>();

		if(userDetails.getIsLogedInUser() && userDetails.getFolderId() != null)
		{
			Map<String, List<Photos>> photos = driveService.fetchEventPhotos(userDetails.getFolderId());			
			userStatus.put("result", "true");
			userStatus.put("dataUser", new Gson().toJson(userDetails));
			userStatus.put("dataPhotoList", new Gson().toJson(photos));
			userStatus.put("dataItemCountsBasedOnType", new Gson().toJson(AccountManagmentUtility.getCountBasedOnType(AccountManagmentUtility.getPhotosInBag(request.getSession()))));
			response.getWriter().write(new Gson().toJson(userDetails));
		}
		else
		{
			userStatus.put("result", "false");
			userStatus.put("data", null);
			userStatus.put("cause", "No Loggedin user available for this session Or G-Drive folder not assigned");
			response.getWriter().write(new Gson().toJson(userStatus));
		}
		
	}

}
