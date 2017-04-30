package userAccountManagment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.PhotosBag;
import user.UserDetails;
import utilities.CommonTypes;
import utilities.StringTools;

import com.google.gson.Gson;

@WebServlet(urlPatterns = "/user/RemoveItemCart", name = "RemoveItemFromCart")
public class RemoveItemFromCart extends HttpServlet
{

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		try
		{
			HttpSession session = request.getSession();
			UserDetails user = (UserDetails)session.getAttribute(CommonTypes.USER_DETAILS_SESSION_KEY);
			
			String bagId = request.getParameter("bagId");
			
			if(!StringTools.isValidString(bagId))
			{
				response.getWriter().write("false");
				return;
			}
			
			PhotosBag photoBag = new PhotosBag();
			photoBag.setId(Integer.valueOf(bagId));

			if(AccountManagmentUtility.removeItem(photoBag))
			{
				response.getWriter().write("true");
			}
			else
			{
				response.getWriter().write("false");
			}
		}
		catch(Exception e)
		{
			response.getWriter().write("false");
		}
	}	
}
