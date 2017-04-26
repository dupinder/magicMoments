package userAccountManagment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import user.PhotosBag;

import com.google.gson.Gson;

public class ManageUserItems
{

	public Map<String, String> addToBagage(String photoId, int bagType, int userId)
	{
		PhotosBag photoBag = new PhotosBag();
		photoBag.setPhotoId(photoId);
		photoBag.setUserId(userId);
		photoBag.setType(bagType);
		photoBag.setQuantity(0);
		
		Map<String, String> itemAddStatus = new HashMap<String, String>();
		if(AccountManagmentUtility.saveAddToCartPhotos(photoBag))
		{
			List<PhotosBag> photosBag = AccountManagmentUtility.getPhotosInBag(userId);
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
}
