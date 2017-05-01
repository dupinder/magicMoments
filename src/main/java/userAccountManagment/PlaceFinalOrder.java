package userAccountManagment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

@WebServlet(urlPatterns = "/user/placeOrder", name = "PlaceFinalOrder")

public class PlaceFinalOrder extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
		
			Map<String, String> orderInfo = new Gson().fromJson(request.getParameter("orderInfo"), HashMap.class);
			if(orderInfo == null || orderInfo.isEmpty())
			{
				response.getWriter().write("false");
				return;
			}
			
			HttpSession session = request.getSession();
			UserDetails userDetails = (UserDetails) session.getAttribute(utilities.CommonTypes.USER_DETAILS_SESSION_KEY);
			
			List<PhotosBag> photosBag = AccountManagmentUtility.getPhotosInBagById(new ArrayList<String>(orderInfo.keySet()));
			Map<String, Integer> photosConfig = AccountManagmentUtility.getPhotoDetailsFromConfiguration();
			
			int price = 0;
			int deliveryDays = 0;
			
			for (String  photoConfig : photosConfig.keySet()) {
				
				if (CommonTypes.CONFIGURATION_PHOTO_PRICE_KEY.equals(photoConfig)) 
				{
					price = photosConfig.get(photoConfig);
				}
				else if(CommonTypes.CONFIGURATION_PHOTO_DAYS_TO_DELIVER.equals(photoConfig))
				{
					deliveryDays = photosConfig.get(photoConfig);
				}
			}
			
			int userId = userDetails.getId();
			List<Order> orders = new ArrayList<Order>();
			String orderReference = StringTools.getRandomString(10);
			for (PhotosBag photo : photosBag) 
			{
				Order order = new Order();
				if(photo.getUserId() == userId)
				{
					order.setReferenceId(orderReference);
					order.setUserId(userId);
					order.setPrice(price);
					order.setPhotoId(photo.getPhotoId());
					order.setQuantity(Integer.valueOf(orderInfo.get(photo.getId()+"")));
					order.setTotalBillingAmount(price * order.getQuantity());
					order.setDaysToDeliver(deliveryDays);
					orders.add(order);
				}
			}
			
			if (AccountManagmentUtility.savePlacedOrder(orders)) 
			{
				for(PhotosBag photo : photosBag)
				{
					AccountManagmentUtility.removeItem(photo);
				}
				
				AccountManagmentUtility.sendEmailInvoiceOfOrder(orders);
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
