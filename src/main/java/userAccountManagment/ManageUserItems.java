package userAccountManagment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import user.PhotosBag;

import com.google.gson.Gson;

public class ManageUserItems
{

	public Map<String, String> addToBagage(String photoId, int bagType, int userId, int eventId)
	{
		PhotosBag photoBag = new PhotosBag();
		photoBag.setPhotoId(photoId);
		photoBag.setUserId(userId);
		photoBag.setType(bagType);
		photoBag.setEventId(eventId);
		photoBag.setQuantity(1);
		
		Map<String, String> itemAddStatus = new HashMap<String, String>();
		if(AccountManagmentUtility.saveAddToCartPhotos(photoBag))
		{
			List<PhotosBag> photosBag = AccountManagmentUtility.getPhotosInBag(userId, bagType);
			itemAddStatus.put("result", "true");
			itemAddStatus.put("dataItemsInBag", new Gson().toJson(photosBag));		
			itemAddStatus.put("dataItemCountsBasedOnType", new Gson().toJson(AccountManagmentUtility.getCountBasedOnType(photosBag)));
		}
		else
		{
			itemAddStatus.put("result", "false");
		}
		
		return itemAddStatus;
	}
	
	public Integer itemExists(String photoId, int bagType, int userId, int eventId)
	{
		return AccountManagmentUtility.itemExits(photoId, bagType, userId, eventId);
	}
	
	public boolean removeItem(int bagId)
	{
		return AccountManagmentUtility.removeItem(bagId);
	}
}
