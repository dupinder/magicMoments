package userAccountManagment;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.CartPresenter;
import user.PhotosBag;
import user.UserDetails;
import utilities.CommonTypes;

import com.google.gson.Gson;

import drive.DriveCommunications;
import drive.Photos;

@WebServlet(urlPatterns = "/user/showCartView", name = "ReviewCart")
public class ReviewCart extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher rd=request.getRequestDispatcher("/CartView.html");  
		rd.forward(request, response); 
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/**
		 * find items in user cart...
		 */
		HttpSession session = request.getSession();				
		UserDetails userDetails = (UserDetails) session.getAttribute(CommonTypes.USER_DETAILS_SESSION_KEY);
		List<PhotosBag> photosBag = AccountManagmentUtility.getPhotosInBag(userDetails.getId());
		Map<String, Integer> photosConfig = AccountManagmentUtility.getPhotoDetailsFromConfiguration();
		
		for (PhotosBag photo : photosBag) 
		{
			for (String  photoConfig : photosConfig.keySet()) {
				
				if (CommonTypes.CONFIGURATION_PHOTO_PRICE_KEY.equals(photoConfig)) 
				{
					photo.setPrice(photosConfig.get(photoConfig));
				}
				else if(CommonTypes.CONFIGURATION_PHOTO_DAYS_TO_DELIVER.equals(photoConfig))
				{
					photo.setDaysToDeliver(photosConfig.get(photoConfig));
				}
			}
		}
		
		/*
		 * mapping photo price and quantity object with drive photo object
		 */
		DriveCommunications driveService = new DriveCommunications();
		List<Photos> photos = driveService.fetchEventPhotos(userDetails.getFolderId());	
		List<CartPresenter> cartPresenters = new LinkedList<CartPresenter>();
		
		for (Photos photo : photos) {
			for (PhotosBag photoInBag : photosBag) {
				if(photo.getPhotoId().equals(photoInBag.getPhotoId())){
					cartPresenters.add(new CartPresenter(photoInBag, photo));
				}
			}
		}
		
		Map<String, String> reviewCart = new HashMap<String, String>();
		reviewCart.put("result", "true");
		reviewCart.put("dataPhotosWithPrices", new Gson().toJson(cartPresenters));
		response.getWriter().write(new Gson().toJson(reviewCart));
	}
	
}
