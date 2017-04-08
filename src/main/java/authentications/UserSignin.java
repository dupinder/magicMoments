package authentications;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import user.UserAuthentication;
import utilities.StringTools;

/**
 * Servlet implementation class UserSignin
 */
public class UserSignin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String email = request.getParameter("email");
		String Password = request.getParameter("password");
		try {
			if(StringTools.isValidString(email) && StringTools.isValidString(Password) && (UserAuthentication.IsExistingUser(email) == 1 ? false : true)){
				if(UserAuthentication.LoginCreateSession(email, Password, request)){
					Map<String, String> userStatus = new HashMap<String, String>();
					userStatus.put("result", "true");
					response.getWriter().write(new Gson().toJson(userStatus));

				}else{
					Map<String, String> userStatus = new HashMap<String, String>();
					userStatus.put("result", "false");
					userStatus.put("cause", "password or email are not valid");
					response.getWriter().write(new Gson().toJson(userStatus));
				}
			}else{
				Map<String, String> userStatus = new HashMap<String, String>();
				userStatus.put("result", "false");
				userStatus.put("cause", "password or email are not valid");
				response.getWriter().write(new Gson().toJson(userStatus));
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}