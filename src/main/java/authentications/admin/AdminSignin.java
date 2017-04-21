package authentications.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.UserAuthentication;

/**
 * Servlet implementation class AdminLogin
 */

@WebServlet(urlPatterns = "/AdminSignin", name = "AdminSignin")
public class AdminSignin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminSignin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		try
		{
			if(UserAuthentication.LoginCreateSession(email, password, admin.Admin.class.getName(), request))
			{
				response.getWriter().write("true");
				return;
			}
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		
		response.getWriter().write("false");
	}

}
