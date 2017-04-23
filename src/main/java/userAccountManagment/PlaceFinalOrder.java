package userAccountManagment;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet(urlPatterns = "/user/PlaceFinalOrder", name = "PlaceFinalOrder")

public class PlaceFinalOrder extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
			Order order = new Order();
			Map<String, String> placeOrderStatus = new HashMap<String, String>();
			if (AccountManagmentUtility.savePlacedOrder(order)) 
			{
				
				AccountManagmentUtility.sendEmailInvoiceOfOrder(order);
				
				placeOrderStatus.put("result", "true");
			}
			else 
			{
				placeOrderStatus.put("result", "false");
			}

			resp.getWriter().write(new Gson().toJson(placeOrderStatus));

		}
	
}
