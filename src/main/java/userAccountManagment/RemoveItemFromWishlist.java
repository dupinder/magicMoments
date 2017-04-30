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

import com.google.gson.Gson;

@WebServlet(urlPatterns = "/user/RemoveItemWishlist", name = "RemoveItemFromWishlist")
public class RemoveItemFromWishlist extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		UserDetails user = (UserDetails)session.getAttribute(CommonTypes.USER_DETAILS_SESSION_KEY);
		int userId = user.getId();
		int type = CommonTypes.BAG_TYPE_WISHLIST;
		String photoId = request.getParameter("bagId");

		PhotosBag photoBag = new PhotosBag();
		
		photoBag.setPhotoId(photoId);
		photoBag.setUserId(userId);
		photoBag.setType(type);
		Map<String, String> deleteItem = new HashMap<String, String>();
		
		if(AccountManagmentUtility.removeItem(photoBag))
		{
			deleteItem.put("result", "true");
		}
		else
		{
			deleteItem.put("result", "false");
		}
	
		response.getWriter().write(new Gson().toJson(deleteItem));	
	}
}
