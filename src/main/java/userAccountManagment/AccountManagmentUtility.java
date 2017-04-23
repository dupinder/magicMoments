package userAccountManagment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.google.gson.JsonElement;

import connection.ConnectionManager;
import user.PhotosBag;
import user.UserAuthentication;
import user.UserDetails;
import utilities.CommonTypes;
import utilities.EmailSender;

public class AccountManagmentUtility {

	public static boolean SaveAddToCartPhotos(PhotosBag photoBag) {
		
		String InsertPhotoToBag = "INSERT INTO MM_PHOTO_BAG (USER_ID, PHOTO_ID, TYPE, QUANTITY) VALUES (?, ?, ?, ?)";
		
		Connection conn;
		try {
			conn = ConnectionManager.getConnection();
			PreparedStatement pStmt = conn.prepareStatement(InsertPhotoToBag);
			pStmt.setInt(1, photoBag.getUserId());
			pStmt.setString(2, photoBag.getPhotoId());
			pStmt.setInt(3, photoBag.getType());
			pStmt.setInt(4, photoBag.getQuantity());
			
			pStmt.execute();
			
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}


	public static List<PhotosBag> getPhotosInBag(HttpSession session) 
	{
		UserDetails user = (UserDetails) session.getAttribute(CommonTypes.USER_DETAILS_SESSION_KEY);		
		return getPhotosInUsersBag(user.getId());
	}


	private static List<PhotosBag> getPhotosInUsersBag(int id) {
	
		String selectPhotosInUserBag = "SELECT * FROM MM_PHOTO_BAG WHERE USER_ID = ?";
		List<PhotosBag> photosDetails = new LinkedList<PhotosBag>();

		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pStmt  = conn.prepareStatement(selectPhotosInUserBag);
			
			pStmt.setInt(1, id);
			
			ResultSet resultSet = pStmt.executeQuery();
			while (resultSet.next()) {
				PhotosBag photo = new PhotosBag();
				photo.setPhotoId(resultSet.getString("PHOTO_ID"));
				photo.setUserId(id);
				photo.setQuantity(Integer.parseInt(resultSet.getString("QUANTITY")));
				switch (resultSet.getInt("TYPE")) 
				{
					case 0:
						photo.setType(CommonTypes.BAG_TYPE_WISHLIST);
					break;
	
					case 1:
						photo.setType(CommonTypes.BAG_TYPE_FAVOURIT);
					break;
	
					case 2:
						photo.setType(CommonTypes.BAG_TYPE_CART);
					break;
	
					default:
					break;
					
				}
				photosDetails.add(photo);
			}
			
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return photosDetails;
	}


	public static Map<Integer, Integer> getCountBasedOnType(List<PhotosBag> photosBag) 
	{
	
		Map<Integer, Integer> countsBasedOnType = new HashMap<Integer, Integer>();
		
		Integer whishList = 0, favourit = 0, cart = 0;
		
		for (PhotosBag photo : photosBag) {
			
			switch (photo.getType()) {
			case CommonTypes.BAG_TYPE_WISHLIST:
				whishList++;
				break;
			case CommonTypes.BAG_TYPE_FAVOURIT:
				favourit++;
				break;
			case CommonTypes.BAG_TYPE_CART:
				cart++;
				break;
				
			default:
				break;
			}
			
		}
		countsBasedOnType.put(CommonTypes.BAG_TYPE_WISHLIST, whishList);
		countsBasedOnType.put(CommonTypes.BAG_TYPE_FAVOURIT, favourit);
		countsBasedOnType.put(CommonTypes.BAG_TYPE_CART, cart);
		
		return countsBasedOnType;
		
	}


	public static boolean removeItem(PhotosBag photoBag) {
		String DeleteItem = "DELETE * FROM MM_PHOTO_BAG WHERE USER_ID = ?, PHOTO_ID = ?, TYPE = ?";
		
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pStmt = conn.prepareStatement(DeleteItem);
			pStmt.setInt(1, photoBag.getUserId());
			pStmt.setString(2, photoBag.getPhotoId());
			pStmt.setInt(3, photoBag.getType());
			
			pStmt.execute();
			
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
	}


	public static Map<String, Integer> getPhotoDetailsFromConfiguration() {
		
		Map<String, Integer> photoConfigurations = new HashMap<String, Integer>();
		String selectPhotoPrice = "SELECT CONFIG_KEY, CONFIG_VALUE FROM MM_CONFIGURATION";
		
		try 
		{
			Connection conn  = ConnectionManager.getConnection();
			PreparedStatement pStmt = conn.prepareStatement(selectPhotoPrice);
			ResultSet rs = pStmt.executeQuery();
			while (rs.next()) 
			{
				if(rs.getString("CONFIG_KEY").equals(CommonTypes.CONFIGURATION_PHOTO_PRICE_KEY))
				{
					photoConfigurations.put(CommonTypes.CONFIGURATION_PHOTO_PRICE_KEY, Integer.parseInt(rs.getString("CONFIG_VALUE")));
				}
				
				else if(rs.getString("CONFIG_KEY").equals(CommonTypes.CONFIGURATION_PHOTO_DAYS_TO_DELIVER))
				{
					photoConfigurations.put(CommonTypes.CONFIGURATION_PHOTO_DAYS_TO_DELIVER, Integer.parseInt(rs.getString("CONFIG_VALUE")));
				}
			}
			return photoConfigurations;
		}
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();	
			return null;
		}
		
	}


	public static boolean savePlacedOrder(Order order) 
	{
		List<String> photoIds = order.getPhotos();
		String insertPlacedOrder = "INSERT INTO MM_PLACE_OREDR (USER_ID, PHOTO_ID, PRICE, QUANTITY, TOTAL_BILLING_AMOUNT, DELIVERY_CHARGES) VALUES (?, ?, ?, ?, ?, ?)";
		try 
		{
			Connection conn = ConnectionManager.getConnection();
			
			PreparedStatement pStmt = conn.prepareStatement(insertPlacedOrder);
			pStmt.setInt(1, order.getUserId());
			pStmt.setInt(3, order.getPrice());
			pStmt.setInt(4, order.getQuantity());
			pStmt.setInt(5, order.getTotalBillingAmount());
			pStmt.setInt(6, order.getDeliveryCharges());
			
			for (String photoId : photoIds) 
			{
				pStmt.setString(2, photoId);
				pStmt.execute();
			}
		return true;
			
		}
		catch (ClassNotFoundException | SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}


	public static void sendEmailInvoiceOfOrder(Order order) 
	{
		EmailSender emailSender = new EmailSender();
		try {
			UserDetails userDetail = UserAuthentication.getUserDetails(order.getUserId());
			emailSender.sendEmail(userDetail.getEmailId(), "Hi "+ userDetail.getName() +" Your order has been place of "+order.getQuantity()+" photos. Your order will be deliverd between "+order.getDaysToDeliver()+" from order date to your collage location, worth rupees " + order.getTotalBillingAmount() + "<br/> Thank you");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}


	
	
	
}
