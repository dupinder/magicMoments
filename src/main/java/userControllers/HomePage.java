package userControllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import user.UserAuthentication;
import user.UserDetails;

/**
 * Servlet implementation class HomePage
 */
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
		Map<String, String> userStatus = new HashMap<String, String>();

		if(userDetails.getIsLogedInUser())
		{
			userStatus.put("result", "true");
			userStatus.put("data", new Gson().toJson(userDetails));
			response.getWriter().write(new Gson().toJson(userDetails));
		}
		else
		{
			userStatus.put("result", "false");
			userStatus.put("data", null);
			userStatus.put("cause", "No Loggedin user available for this session");
			response.getWriter().write(new Gson().toJson(userStatus));
		}
		
	}

}