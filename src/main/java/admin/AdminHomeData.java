package admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class AdminHomeData
 */
@javax.servlet.annotation.WebServlet(urlPatterns = "/admin/adminHomeData", name = "AdminHomeData")
public class AdminHomeData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminHomeData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			Admin loggedInAdmin = (Admin) request.getSession().getAttribute(utilities.CommonTypes.AMDIN_SESSION_KEY);
			AdminData adminData = new AdminData(loggedInAdmin.getEmail(), 0, 0, 0);
			response.getWriter().write(new Gson().toJson(adminData));
		}
		catch(Exception e)
		{
			response.sendRedirect("AdminLogin.html");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	private class AdminData
	{
		String email;
		int totalOrders;
		int totalOderAmount;
		int totalEvents;
		
		public AdminData(String email, int orders, int amount, int events)
		{
			this.email = email;
			this.totalEvents = events;
			this.totalOderAmount = orders;
			this.totalOrders = orders;
		}
	}
}
