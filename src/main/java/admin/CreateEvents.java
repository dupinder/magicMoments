package admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import user.Event;
import utilities.StringTools;

/**
 * Servlet implementation class CreateEvents
 */
public class CreateEvents extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	
		Event event = new Event();
		
		event.setName(request.getParameter("eventName"));
		event.setDiscription(request.getParameter("eventDiscription"));
		event.setStartDate(StringTools.convertStringToTimestamp(request.getParameter("eventStartDate")));
		event.setEndDate(StringTools.convertStringToTimestamp(request.getParameter("eventEndDate")));
		event.setDataDelete(StringTools.convertStringToTimestamp(request.getParameter("dateToDeleteData")));
		event.setCollageId(request.getParameter("collegeId") == null ? 0 : Integer.parseInt(request.getParameter("collegeId")));
		event.setBranchId(request.getParameter("branchId") == null ? 0 : Integer.parseInt(request.getParameter("branchId")));
		event.setFolderId(request.getParameter("FOLDER_ID"));
		if(StringTools.isValidString(event.getName()) || event.getCollageId() == 0 || event.getBranchId() == 0 || event.getStartDate() == null || event.getEndDate() == null || event.getDataDelete() == null || event.getFolderId() == null)
		{
			Map<String, String> EventStatus = new HashMap<String, String>();
			EventStatus.put("result", "false");
			response.getWriter().write(new Gson().toJson(EventStatus));
		}
		else
		{
			if(AdminUtilites.createEvent(event)){
				Map<String, String> EventStatus = new HashMap<String, String>();
				EventStatus.put("result", "true");
				response.getWriter().write(new Gson().toJson(EventStatus));
			}			
		}
		
 		
	
	}

}
