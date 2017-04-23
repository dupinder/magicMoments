package authentications;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.services.drive.model.User;
import com.google.gson.Gson;

import user.UserAuthentication;
import utilities.StringTools;

/**
 * Servlet implementation class UserSignin
 */
@WebServlet(urlPatterns = "/UserSignin", name = "UserSignin")

public class UserSignin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String email = request.getParameter("email");
		String Password = request.getParameter("password");
		try 
		{
			if(StringTools.isValidString(email) && StringTools.isValidString(Password) && (UserAuthentication.getExistingUser(email) == 1 ? false : true))
			{
				if(UserAuthentication.LoginCreateSession(email, Password, User.class.getName(), request)){
					Map<String, String> userStatus = new HashMap<String, String>();
					userStatus.put("result", "true");
					response.getWriter().write(new Gson().toJson(userStatus));
				}
				else
				{
					Map<String, String> userStatus = new HashMap<String, String>();
					userStatus.put("result", "false");
					userStatus.put("cause", "password or email are not valid");
					response.getWriter().write(new Gson().toJson(userStatus));
				}
			}
			else
			{
				Map<String, String> userStatus = new HashMap<String, String>();
				userStatus.put("result", "false");
				userStatus.put("cause", "password or email are not valid");
				response.getWriter().write(new Gson().toJson(userStatus));
			}
		} 
		catch (ClassNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
