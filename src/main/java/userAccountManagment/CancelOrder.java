package userAccountManagment;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.UserDetails;
import utilities.StringTools;

@WebServlet(urlPatterns = "/user/cancelOrder", name = "CancelOrder")
public class CancelOrder extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			String orderReference = request.getParameter("orderReference").trim();
			UserDetails user = (UserDetails) request.getSession().getAttribute(utilities.CommonTypes.USER_DETAILS_SESSION_KEY);
			
			boolean isOrderCancelled = false;
			if(StringTools.isValidString(orderReference))
			{
				isOrderCancelled = AccountManagmentUtility.cancelOrder(user.getId(), orderReference);
			}
			
			response.getWriter().write(String.valueOf(isOrderCancelled));
		}
		catch(Exception e)
		{
			response.getWriter().write(String.valueOf(false));
		}
	}
}
