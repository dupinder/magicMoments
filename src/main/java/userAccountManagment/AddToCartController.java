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

import user.UserDetails;
import utilities.CommonTypes;

import com.google.gson.Gson;

/**
 * Servlet implementation class AddToCartOrWishlist
 */
 
@WebServlet(urlPatterns = "/user/addToCart", name = "AddToCartController")
public class AddToCartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToCartController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				
				Integer itemExists = manageUserItem.itemExists(photoId, CommonTypes.BAG_TYPE_CART, userId, eventId);
				Map<String, String> status = null;
				
				if(itemExists == null)
				{
					status = manageUserItem.addToBagage(photoId, CommonTypes.BAG_TYPE_CART, userId, eventId);
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
				return;
			}
		}
		catch(Exception e)
		{
		}
		
		response.getWriter().write(String.valueOf(false));
	}


}
