package admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.Branches;
import user.CollageInfo;

import com.google.gson.Gson;

/**
 * Servlet implementation class GetAllRequiredDataForEvents
 */
@WebServlet(urlPatterns = "/admin/GetAllRequiredDataForEvents", name = "GetAllRequiredDataForEvents")
public class GetAllRequiredDataForEvents extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAllRequiredDataForEvents() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<CollageInfo> allColleges = AdminUtilites.getAllCollageList();
		List<Branches> branches =  AdminUtilites.getAllBranches();
		
		ResponseData rd = new ResponseData();
		rd.setAllColleges(allColleges);
		rd.setBranches(branches);
		response.getWriter().write(new Gson().toJson(rd));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	class ResponseData 
	{
		List<CollageInfo> allColleges;
		List<Branches> allBranches;
		
		public void setAllColleges(List<CollageInfo> allColleges)
		{
			this.allColleges = allColleges;
		}
		public void setBranches(List<Branches> branches)
		{
			this.allBranches = branches;
		}
	}

}
