package authentications;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.UserAuthentication;
import utilities.StringTools;

/**
 * Servlet implementation class VerifyEmail
 */
public class VerifyEmail extends HttpServlet 
{
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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return;
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		if(StringTools.isValidString(email))
		{
			if(userAuthentication.generateOtpAndSendEmail(email))
			{
				response.getWriter().write("true");
				return;
			}
		}
		
		response.getWriter().write("false");
	}

}
