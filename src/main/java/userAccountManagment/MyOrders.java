package userAccountManagment;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.UserDetails;

import com.google.gson.Gson;

@WebServlet(urlPatterns = "/user/showMyOrders", name = "MyOrders")
public class MyOrders extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			RequestDispatcher rd=request.getRequestDispatcher("/MyOrders.html");  
			rd.forward(request, response); 
		}
		catch(Exception e)
		{
			
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			UserDetails user = (UserDetails) request.getSession().getAttribute(utilities.CommonTypes.USER_DETAILS_SESSION_KEY);
			Map<String, List<Order>> myOrders = AccountManagmentUtility.getMyOrders(user.getId());
			response.getWriter().write(new Gson().toJson(myOrders));
		}
		catch(Exception e)
		{
			response.getWriter().write("false");
		}
	}
	
	private class OrderPresenter
	{
		String referenceId;
		String photoUrl;
		String eventName;
		String Quantity;
		String price;
		String amountPayable;
	}
}
