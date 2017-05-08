package authentications;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import user.UserAuthentication;
import utilities.StringTools;

/**
 * Servlet implementation class VerifyEmail
 */
@WebServlet(urlPatterns = "/VerifyEmail", name = "VerifyEmail")
public class VerifyEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserAuthentication userAuthentication = new UserAuthentication();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VerifyEmail() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return;
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		if (StringTools.isValidString(email)) {
			try {

				Map<String, String> userStatus = new HashMap<String, String>();
				switch (UserAuthentication.getExistingUser(email)) {
				case UserAuthentication.ACTION_INVALID_USER:					
					userStatus.put("isValidUser", "false");
					response.getWriter().write(new Gson().toJson(userStatus));
					break;
				case UserAuthentication.ACTION_VALID_EXSISTING_USER:
					userStatus.put("isValidUser", "true");
					userStatus.put("isNewUser", "false");
					response.getWriter().write(new Gson().toJson(userStatus));
					break;
				case UserAuthentication.ACTION_VALID_NEW_USER:
					if (userAuthentication.generateOtpAndSendEmail(email)) {
						userStatus.put("isValidUser", "true");
						userStatus.put("isNewUser", "true");
						response.getWriter().write(new Gson().toJson(userStatus));						
					}
					break;
				default:
					break;
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				response.getWriter().write(String.valueOf(false));
			}
		}
		else
			response.getWriter().write(String.valueOf(false));
	}

}
