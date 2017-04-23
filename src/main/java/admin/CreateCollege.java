package admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import user.CollageInfo;

/**
 * Servlet implementation class CreateCollages
 */
@WebServlet(urlPatterns = "/admin/CreateCollage", name = "CreateCollage")
public class CreateCollege extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		CollageInfo college = new CollageInfo();
		college.setName(request.getParameter("collegeName"));
		college.setAbbreviation(request.getParameter("abbreviation"));
		college.setLocation(request.getParameter("location"));
		college.setPincode(request.getParameter("pincode"));
		college.setAddress(request.getParameter("address"));
		Map<String, String> collagesStatus = new HashMap<String, String>();
		
		if(AdminUtilites.isCollegeExisting(college.getName()))
		{
			collagesStatus.put("result", "false");
			collagesStatus.put("cause", "College already exists..!!");
		}
		else if(AdminUtilites.createCollage(college))
		{
			List<CollageInfo> collages = AdminUtilites.getAllCollageList();
			collagesStatus.put("result", "true");
			collagesStatus.put("dataCollageList", new Gson().toJson(collages));
		}
		else
		{
			collagesStatus.put("result", "false");
			collagesStatus.put("cause", "Error while saving college");
		}
		
		response.getWriter().write(new Gson().toJson(collagesStatus));
	}

}
