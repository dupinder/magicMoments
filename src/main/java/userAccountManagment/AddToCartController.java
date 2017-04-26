package userAccountManagment;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import user.UserDetails;
import utilities.CommonTypes;

import com.google.gson.Gson;

/**
 * Servlet implementation class AddToCartOrWishlist
 */
 
@WebServlet(urlPatterns = "/user/addToCart", name = "AddToCartController")
public class AddToCartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddToCartController() {
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
		String photoId = request.getParameter("photoId");
		
		if(utilities.StringTools.isValidString(photoId))
		{
			ManageUserItems manageUserItem = new ManageUserItems();
			Map<String, String> status = manageUserItem.addToBagage(photoId, CommonTypes.BAG_TYPE_CART, userId);
			response.getWriter().write(new Gson().toJson(status));	
		}
		
		response.getWriter().write(String.valueOf(false));	
	}


}
