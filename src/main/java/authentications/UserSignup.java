package authentications;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserSignup
 */
public class UserSignup extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UserSignup() {
      
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		System.out.println(request.getParameter("userName"));
		System.out.println(request.getParameter("password"));
		System.out.println(request.getParameter("phoneNumber"));
		response.sendRedirect("authentications/UserSignup.html");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}