package admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import user.Branches;
import user.CollageInfo;

import com.google.gson.Gson;

/**
 * Servlet implementation class CreateBranch
 */
public class CreateBranch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateBranch() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Branches branch = new Branches();
		
		branch.setBranchName(request.getParameter("name"));
		branch.setBranchAbbrivation(request.getParameter("abbreviation"));
		branch.setCollageId(Integer.valueOf(request.getParameter("collegeId")));
		
		Map<String, String> status = new HashMap<String, String>();
		
		if(AdminUtilites.isCollegeExisting(branch.getBranchName()))
		{
			status.put("result", "false");
			status.put("cause", "College already exists..!!");
		}
		else if(AdminUtilites.createBranch(branch))
		{
			List<Branches> branches = AdminUtilites.getAllBranches(branch.getCollageId());
			status.put("result", "true");
			status.put("branchList", new Gson().toJson(branches));
		}
		else
		{
			status.put("result", "false");
			status.put("cause", "Error while saving branch");
		}
		
		response.getWriter().write(new Gson().toJson(status));
	}

}
