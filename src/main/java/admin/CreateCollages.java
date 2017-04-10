package admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import user.CollageInfo;
import utilities.StringTools;

/**
 * Servlet implementation class CreateCollages
 */
public class CreateCollages extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		CollageInfo collage = new CollageInfo();
		collage.setName(request.getParameter("collageName"));
		collage.setAbbrevation(request.getParameter("collageAbbrevation"));
		collage.setLocation(request.getParameter("location"));
		collage.setPincode(request.getParameter("pincode"));
		collage.setAddress(request.getParameter("address"));
		Map<String, String> collagesStatus = new HashMap<String, String>();

		if(StringTools.isValidString(collage.getName()) || StringTools.isValidString(collage.getPincode()) || StringTools.isValidString(collage.getAddress()))
		{
			collagesStatus.put("result", "false");
			response.getWriter().write(new Gson().toJson(collagesStatus));
		}
		else
		{
			if(AdminUtilites.createCollage(collage))
			{
				List<CollageInfo> collages = AdminUtilites.getAllCollageList();
				collagesStatus.put("result", "true");
				collagesStatus.put("dataCollageList", new Gson().toJson(collages));
				response.getWriter().write(new Gson().toJson(collagesStatus));
			}
			else
			{
				collagesStatus.put("result", "false");
				response.getWriter().write(new Gson().toJson(collagesStatus));
			}
		}

	}

}
