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

import user.Branches;
import utilities.StringTools;

/**
 * Servlet implementation class CreateBranches
 */
public class CreateBranches extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Branches branch = new Branches();
		branch.setCollageId(request.getParameter("collageId") == null ? 0 : Integer.parseInt(request.getParameter("collageId")));
		branch.setBranchName(request.getParameter("branchName"));
		branch.setBranchAbbrivation(request.getParameter("branchAbbrevation"));
		Map<String, String> branchStatus = new HashMap<String, String>();

		if((branch.getCollageId() == 0)|| StringTools.isValidString(branch.getBranchName()))
		{
			branchStatus.put("result", "false");
			response.getWriter().write(new Gson().toJson(branchStatus));
		}
		else
		{
			if(AdminUtilites.createBranches(branch))
			{
				List<Branches> branches = AdminUtilites.getAllBranches();
				branchStatus.put("result", "true");
				branchStatus.put("dataBranchList", new Gson().toJson(branches));
				response.getWriter().write(new Gson().toJson(branchStatus));
			}
			else
			{
				branchStatus.put("result", "false");
				branchStatus.put("cause", "Due to some reasons branch not created");
				response.getWriter().write(new Gson().toJson(branchStatus));
			}
		}
	}

}
