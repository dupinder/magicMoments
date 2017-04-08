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

/**
 * Servlet implementation class CreateCollages
 */
public class CreateCollages extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		CollageInfo collage = new CollageInfo();
		collage.setName(request.getParameter("collageName"));
		collage.setAbbrivation(request.getParameter("collageAbbrivation"));
		collage.setLocation(request.getParameter("location"));
		collage.setPincode(request.getParameter("pincode"));
		collage.setAddress(request.getParameter("address"));
		Map<String, String> collagesStatus = new HashMap<String, String>();
		
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
