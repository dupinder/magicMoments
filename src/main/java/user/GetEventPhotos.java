package user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import userAccountManagment.AccountManagmentUtility;
import utilities.CommonTypes;
import utilities.StringTools;

import com.google.gson.Gson;

import drive.DriveCommunications;
import drive.Photos;

@javax.servlet.annotation.WebServlet(urlPatterns = "/user/getEventPhotos", name = "GetEventPhotos")
public class GetEventPhotos extends HttpServlet
{
	private static final long serialVersionUID = 3389774742443020802L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String eventFolderId = request.getParameter("folderId");
		if(StringTools.isValidString(eventFolderId))
		{
			List<Photos> photos = new DriveCommunications().fetchEventPhotosMap(eventFolderId);
			HttpSession session = request.getSession();
			UserDetails userDetails = (UserDetails) session.getAttribute(utilities.CommonTypes.USER_DETAILS_SESSION_KEY);
			List<PhotosBag> photosBag = AccountManagmentUtility.getPhotosInBag(userDetails.getId(), 0);

			List<String> photosAddedToCart = new ArrayList<String>();
			List<String> photosAddedToWishlist = new ArrayList<String>();
			for(PhotosBag photoBag: photosBag)
			{
				if(photoBag.getType() == CommonTypes.BAG_TYPE_CART)
				{
					photosAddedToCart.add(photoBag.getPhotoId());
				}
				
				if(photoBag.getType() == CommonTypes.BAG_TYPE_WISHLIST)
				{
					photosAddedToWishlist.add(photoBag.getPhotoId());
				}
			}
			
			EventPhotosPresenter responseData = new EventPhotosPresenter(photos, photosAddedToCart, photosAddedToWishlist);
			response.getWriter().write(new Gson().toJson(responseData));
		}
	}
	
	private class EventPhotosPresenter
	{
		List<Photos> photos = null;
		List<String> photosAddedToCart = null;
		List<String> photosAddedToWishlist = null;
		
		private EventPhotosPresenter(List<Photos> photos, List<String> photosAddedToCart, List<String> photosAddedToWishlist)
		{
			this.photos = photos;
			this.photosAddedToCart = photosAddedToCart;
			this.photosAddedToWishlist = photosAddedToWishlist;
		}
	}
}
