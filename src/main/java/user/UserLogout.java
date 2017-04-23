package user;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utilities.CommonTypes;

@WebServlet(urlPatterns = "/user/UserLogout", name = "UserLogout")

public class UserLogout extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		Map<String, String> userLogoutStatus = new HashMap<String, String>();
		HttpSession session = req.getSession();
		session.removeAttribute(CommonTypes.USER_DETAILS_SESSION_KEY);
		
		if(session.getAttribute(CommonTypes.USER_DETAILS_SESSION_KEY) == null)
		{
			userLogoutStatus.put("result", "true");
		}
		else
		{
			userLogoutStatus.put("result", "false");
		}
		
	}
	
}
