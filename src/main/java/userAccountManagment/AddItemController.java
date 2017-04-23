package userAccountManagment;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import user.PhotosBag;
import user.UserDetails;
import utilities.CommonTypes;

/**
 * Servlet implementation class AddToCartOrWishlist
 */
 
 @WebServlet(urlPatterns = "/user/AddItemController", name = "AddItemController")
 
 
public class AddItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddItemController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserDetails user = (UserDetails)session.getAttribute(CommonTypes.USER_DETAILS_SESSION_KEY);
		int userId = user.getId();
		int type = Integer.parseInt(request.getParameter("photoType"));
		String photoId = request.getParameter("photoId");

		PhotosBag photoBag = new PhotosBag();
		
		photoBag.setPhotoId(photoId);
		photoBag.setUserId(userId);
		photoBag.setType(type);
		
		Map<String, String> ItemAddStatus = new HashMap<String, String>();
		if(AccountManagmentUtility.SaveAddToCartPhotos(photoBag))
		{
			List<PhotosBag> photosBag = AccountManagmentUtility.getPhotosInBag(request.getSession());
			ItemAddStatus.put("result", "true");
			ItemAddStatus.put("dataItemsInBag", new Gson().toJson(photosBag));		
			ItemAddStatus.put("dataItemCountsBasedOnType", new Gson().toJson(AccountManagmentUtility.getCountBasedOnType(photosBag)));
		}
		else
		{
			ItemAddStatus.put("result", "false");
		}
		
		response.getWriter().write(new Gson().toJson(ItemAddStatus));	

		
	}


}
