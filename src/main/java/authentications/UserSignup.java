package authentications;

import java.io.IOException;
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
 * Servlet implementation class UserSignup
 */
public class UserSignup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserAuthentication userAuthentication = new UserAuthentication();

	public UserSignup() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String otp = request.getParameter("otp");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");

		if (StringTools.isValidEmail(email) && StringTools.isValidString(otp) && StringTools.isValidString(password)
				&& StringTools.isValidString(confirmPassword)) {
			if (StringTools.isValidEmail(email)) {
				try {
					Map<String, String> userStatus = new HashMap<String, String>();
					switch (UserAuthentication.IsExistingUser(email)) {
					case UserAuthentication.ACTION_INVALID_USER:
						userStatus.put("result", "false");
						userStatus.put("cause", "You are not a valid user");
						response.getWriter().write(new Gson().toJson(userStatus));
						break;
					case UserAuthentication.ACTION_VALID_EXSISTING_USER:
						userStatus.put("result", "false");
						userStatus.put("cause", "You are already registered with us");
						response.getWriter().write(new Gson().toJson(userStatus));
						break;
					case UserAuthentication.ACTION_VALID_NEW_USER:
						if (userAuthentication.verifyOtp(email, otp)) 
						{
							if (password.equals(confirmPassword)) {
								if(UserAuthentication.savePassword(email,password)){
									userStatus.put("result", "true");
								}
								else{
									userStatus.put("result", "false");
									userStatus.put("cause", "something bad happened during saving");
								}
							} else {
								userStatus.put("result", "false");
								userStatus.put("cause", "Password and Confirm Password not matched");
							}

							response.getWriter().write(new Gson().toJson(userStatus));
							return;
						}
						else
						{
							userStatus.put("result", "false");
							userStatus.put("cause", "Invalid OTP..!!");
							response.getWriter().write(new Gson().toJson(userStatus));
						}
						break;
					default:
						break;
					}
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		else{
			Map<String, String> userStatus = new HashMap<String, String>();
			userStatus.put("result", "false");
			response.getWriter().write(new Gson().toJson(userStatus));

		}
	}

}