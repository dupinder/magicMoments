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
import user.UserDetails;
import utilities.CommonTypes;

public class AccountManagmentUtility {

	public static boolean SaveAddToCartPhotos(PhotosBag photoBag) {
		
		String InsertPhotoToBag = "INSERT INTO MM_PHOTO_BAG (USER_ID, PHOTO_ID, TYPE) VALUES (?, ?, ?)";
		
		Connection conn;
		try {
			conn = ConnectionManager.getConnection();
			PreparedStatement pStmt = conn.prepareStatement(InsertPhotoToBag);
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


	
	
	
}
