package userAccountManagment;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import drive.DriveCommunications;
import drive.Photos;
import user.CartPresentor;
import user.PhotosBag;
import user.UserDetails;
import utilities.CommonTypes;

public class ReviewCart extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	
	@Override
	protected void doPost(HttpServletRequest requset, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * find items in user cart...
		 * 
		 */
		HttpSession session = requset.getSession();				
		UserDetails userDetails = (UserDetails) session.getAttribute(CommonTypes.USER_DETAILS_SESSION_KEY);
		List<PhotosBag> photosBag = AccountManagmentUtility.getPhotosInBag(session);
		Map<String, Integer> getPhotosConfig = AccountManagmentUtility.getPhotoDetailsFromConfiguration();
		
		for (PhotosBag photo : photosBag) 
		{
			for (String  photosConfig : getPhotosConfig.keySet()) {
				
				if (CommonTypes.CONFIGURATION_PHOTO_PRICE_KEY.equals(photosConfig)) 
				{
					photo.setPrice(getPhotosConfig.get(photosConfig));
				}
				else if(CommonTypes.CONFIGURATION_PHOTO_DAYS_TO_DELIVER.equals(photosConfig))
				{
					photo.setDaysToDeliver(getPhotosConfig.get(photosConfig));
				}
				
			}
		}
		
		
		/***
		 * mapping photo price and quantity object with drive photo object
		 * 
		 */
		DriveCommunications driveService = new DriveCommunications();
		List<Photos> photos = driveService.fetchEventPhotos(userDetails.getFolderId());	
		
		List<CartPresentor> cartPresentors = new LinkedList<CartPresentor>();
		
		for (Photos photo : photos) {
			for (PhotosBag photoInBag : photosBag) {
				if(photo.getPhotoId().equals(photoInBag.getPhotoId())){
					cartPresentors.add(new CartPresentor(photoInBag, photo));
				}
			}
		}
		
		Map<String, String> reviewCart = new HashMap<String, String>();
		reviewCart.put("result", "true");
		reviewCart.put("dataPhotosWithPrices", new Gson().toJson(cartPresentors));
		response.getWriter().write(new Gson().toJson(reviewCart));
	}
	
}
