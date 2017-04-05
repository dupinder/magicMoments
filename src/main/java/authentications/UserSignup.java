package authentications;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.UserAuthentication;
import utilities.StringTools;

/**
 * Servlet implementation class UserSignup
 */
public class UserSignup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserAuthentication userAuthentication = new UserAuthentication();
	
    public UserSignup() {
      
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return;
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String otp = request.getParameter("otp");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		
		if(StringTools.isValidString(email) && StringTools.isValidString(otp) &&StringTools.isValidString(password) &&StringTools.isValidString(confirmPassword))
		{
			if(userAuthentication.verifyOtp(email, otp))
			{
				response.getWriter().write("true");
				return;
			}
		}
		
		response.getWriter().write("false");
	}

}