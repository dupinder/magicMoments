package userAccountManagment;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.UserDetails;
import utilities.CommonTypes;

import com.google.gson.Gson;

@WebServlet(urlPatterns = "/user/addToWishlist", name = "AddToWishlistController")
public class AddToWishlistController extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException
	{
		try
		{
			String photoId = request.getParameter("photoId");
			int eventId = Integer.parseInt(request.getParameter("eventId"));
			
			if(utilities.StringTools.isValidString(photoId))
			{
				HttpSession session = request.getSession();
				UserDetails user = (UserDetails)session.getAttribute(CommonTypes.USER_DETAILS_SESSION_KEY);
				int userId = user.getId();

				ManageUserItems manageUserItem = new ManageUserItems();
				
				Integer itemExists = manageUserItem.itemExists(photoId, CommonTypes.BAG_TYPE_WISHLIST, userId, eventId);
				Map<String, String> status = null;
				
				if(itemExists == null)
				{
					status = manageUserItem.addToBagage(photoId, CommonTypes.BAG_TYPE_WISHLIST, userId, eventId);
					status.put("itemAdded", "true");
				}
				else
				{
					if(manageUserItem.removeItem(itemExists))
					{
						status = new HashMap<String, String>(1);
						status.put("itemRemoved", "true");
					}
				}
				response.getWriter().write(new Gson().toJson(status));	
			}
			else
				response.getWriter().write(String.valueOf(false));
		}
		catch(Exception e)
		{
			
		}
	}
}
