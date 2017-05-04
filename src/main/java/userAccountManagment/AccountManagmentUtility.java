package userAccountManagment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import user.OrderPresenter;
import user.PhotosBag;
import user.UserAuthentication;
import user.UserDetails;
import utilities.CommonTypes;
import utilities.EmailSender;
import connection.ConnectionManager;

public class AccountManagmentUtility {

	public static boolean saveAddToCartPhotos(PhotosBag photoBag) {
		
		String insertPhotoToBag = "INSERT INTO MM_PHOTO_BAG (USER_ID, PHOTO_ID, TYPE, QUANTITY, EVENT_ID) VALUES (?, ?, ?, ?, ?)";
		
		Connection conn;
		try {
			conn = ConnectionManager.getConnection();
			PreparedStatement pStmt = conn.prepareStatement(insertPhotoToBag);
			pStmt.setInt(1, photoBag.getUserId());
			pStmt.setString(2, photoBag.getPhotoId());
			pStmt.setInt(3, photoBag.getType());
			pStmt.setInt(4, photoBag.getQuantity());
			pStmt.setInt(5, photoBag.getEventId());
			
			pStmt.execute();
			
			return true;
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public static List<PhotosBag> getPhotosInBag(int userId) 
	{
		try
		{
			PreparedStatement pStmt = null;
			String selectPhotosInUserBag = "SELECT * FROM MM_PHOTO_BAG WHERE USER_ID = ?";
			Connection conn = ConnectionManager.getConnection();
			pStmt  = conn.prepareStatement(selectPhotosInUserBag);
			pStmt.setInt(1, userId);	
			
			return getPhotosInUsersBag(pStmt);			
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public static List<PhotosBag> getPhotosInBag(int userId, int bagType) 
	{
		try
		{
			PreparedStatement pStmt = null;
			String selectPhotosInUserBag = "SELECT * FROM MM_PHOTO_BAG WHERE USER_ID = ? AND TYPE = ?";
			Connection conn = ConnectionManager.getConnection();
			pStmt  = conn.prepareStatement(selectPhotosInUserBag);
			pStmt.setInt(1, userId);	
			pStmt.setInt(2, bagType);
			return getPhotosInUsersBag(pStmt);	
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public static List<PhotosBag> getPhotosInBagById(List<String> photoBagIds) 
	{
		try
		{
			PreparedStatement pStmt = null;
			String selectPhotosInUserBag = "SELECT * FROM MM_PHOTO_BAG WHERE ID in (";
			
			for(int index = 0; index < photoBagIds.size(); index++)
			{
				selectPhotosInUserBag += "?";
				if(index != photoBagIds.size() -1)
					selectPhotosInUserBag += ",";
			}
			
			selectPhotosInUserBag+= ")";
			
			Connection conn = ConnectionManager.getConnection();
			
			pStmt  = conn.prepareStatement(selectPhotosInUserBag);
			
			for(int index = 0; index < photoBagIds.size(); index++)
			{
				pStmt.setInt(index + 1, Integer.valueOf(photoBagIds.get(index)));
			}
			
			return getPhotosInUsersBag(pStmt);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}


	private static List<PhotosBag> getPhotosInUsersBag(PreparedStatement pStmt) throws SQLException {
		List<PhotosBag> photosDetails = new ArrayList<PhotosBag>();
		ResultSet resultSet = pStmt.executeQuery();
		while (resultSet.next()) {
			PhotosBag photo = new PhotosBag();
			photo.setId(Integer.valueOf(resultSet.getString("ID")));
			photo.setPhotoId(resultSet.getString("PHOTO_ID"));
			photo.setUserId(Integer.valueOf(resultSet.getString("USER_ID")));
			photo.setQuantity(Integer.parseInt(resultSet.getString("QUANTITY")));
			photo.setEventId(resultSet.getInt("EVENT_ID"));
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
		String deleteItem = "DELETE FROM MM_PHOTO_BAG WHERE ID = ?";
		
		try {
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pStmt = conn.prepareStatement(deleteItem);
			pStmt.setInt(1, photoBag.getId());
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


	public static boolean savePlacedOrder(List<Order> orders) 
	{
		String insertPlacedOrder = "INSERT INTO MM_PLACE_OREDR (USER_ID, PHOTO_ID, PRICE, QUANTITY, TOTAL_BILLING_AMOUNT, DELIVERY_CHARGES, REFERENCE_ID, EVENT_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try 
		{
			Connection conn = ConnectionManager.getConnection();
			for (Order order : orders) 
			{
				PreparedStatement pStmt = conn.prepareStatement(insertPlacedOrder);
				pStmt.setInt(1, order.getUserId());
				pStmt.setString(2, order.getPhotoId());
				pStmt.setInt(3, order.getPrice());
				pStmt.setInt(4, order.getQuantity());
				pStmt.setInt(5, order.getTotalBillingAmount());
				pStmt.setInt(6, order.getDeliveryCharges());
				pStmt.setString(7, order.getReferenceId());
				pStmt.setInt(8, order.getEventId());
				pStmt.execute();
			}

			return true;
		}
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	public static OrderPresenter getMyOrders(int userId)
	{
		String insertPlacedOrder = "SELECT MM_PLACE_OREDR.PHOTO_ID, MM_PLACE_OREDR.PRICE, MM_PLACE_OREDR.QUANTITY, MM_PLACE_OREDR.TOTAL_BILLING_AMOUNT,"
									+"MM_PLACE_OREDR.REFERENCE_ID, MM_PLACE_OREDR.DELIVERY_CHARGES, MM_EVENT.EVENT_NAME, " 
									+"MM_COLLAGE.COLLAGE_NAME, MM_BRANCH.BRANCH_NAME "
									+"FROM MM_BRANCH INNER JOIN "
									+"MM_COLLAGE ON MM_BRANCH.COLLAGE_ID = MM_COLLAGE.ID INNER JOIN "
									+"MM_EVENT ON MM_BRANCH.ID = MM_EVENT.BRANCH_ID AND MM_COLLAGE.ID = MM_EVENT.COLLAGE_ID INNER JOIN "
									+"MM_PLACE_OREDR ON MM_EVENT.ID = MM_PLACE_OREDR.EVENT_ID "
									+"WHERE MM_PLACE_OREDR.USER_ID = ? ";
		try 
		{
			Connection conn = ConnectionManager.getConnection();
			PreparedStatement pStmt = conn.prepareStatement(insertPlacedOrder);
			pStmt.setInt(1, userId);
			ResultSet resultSet = pStmt.executeQuery();
			List<Order> orders = new ArrayList<Order>();
			
			String orderReference = null;
			String branchName = null;
			String collegeName = null;
			int amountPayable = 0;
			
			while(resultSet.next())
			{
				Order order = new Order();
				order.setReferenceId(resultSet.getString("REFERENCE_ID"));
				
				if(!Objects.nonNull(orderReference))
				{
					orderReference = order.getReferenceId();
				}
				
				if(!Objects.nonNull(branchName))
				{
					branchName = resultSet.getString("BRANCH_NAME");
				}
				
				if(!Objects.nonNull(collegeName))
				{
					collegeName = resultSet.getString("COLLAGE_NAME");
				}
				
				order.setUserId(userId);
				order.setPhotoId(resultSet.getString("PHOTO_ID"));
				order.setPhotoUrl(drive.DriveCommunications.getPhotoUrlById(order.getPhotoId()));
				order.setPrice(Integer.valueOf(resultSet.getString("PRICE")));
				order.setQuantity(resultSet.getInt("QUANTITY"));
				order.setTotalBillingAmount(resultSet.getInt("TOTAL_BILLING_AMOUNT"));
				amountPayable += order.getTotalBillingAmount();
				order.setDeliveryCharges(Integer.valueOf(resultSet.getInt("DELIVERY_CHARGES")));
				order.setEventName(resultSet.getString("EVENT_NAME"));
				orders.add(order);
			}
			
			OrderPresenter op = new OrderPresenter();
			op.setReferenceId(orderReference);
			op.setBranchName(branchName);
			op.setCollegeName(collegeName);
			op.setOrders(orders);
			op.setAmountPayable(amountPayable);
			return op;
		}
		catch (ClassNotFoundException | SQLException e) 
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void sendEmailInvoiceOfOrder(List<Order> orders) 
	{
		EmailSender emailSender = new EmailSender();
		try {
			UserDetails userDetail = UserAuthentication.getUserDetails(orders.get(0).getUserId());
			
			String emailContent = "Hi " + userDetail.getName() + ",\n\n";
			emailContent+= "Your order on Magic moments has been placed successfully. Following is the summary:\n\n";
			emailContent += "\t Order reference: " + orders.get(0).getReferenceId() + "\n";
			emailContent += "\t Your order will be delivered at your college address.";
			emailSender.sendEmail(userDetail.getEmailId(), emailContent);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean cancelOrder(int userId, String orderReference)
	{
		try
		{
			Connection conn = ConnectionManager.getConnection();
			String query = "DELETE FROM MM_PLACE_OREDR WHERE USER_ID = ? AND REFERENCE_ID = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, userId);
			preparedStatement.setString(2, orderReference);
			preparedStatement.execute();
			return true;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
		
	}
}
