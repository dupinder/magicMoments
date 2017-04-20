package admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import user.Event;
import user.UserDetails;
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
		boolean isUserImportChecked = false;
		event.setName(request.getParameter("eventName"));
		event.setDiscription(request.getParameter("eventDiscription"));
		event.setStartDate(StringTools.convertStringToTimestamp(request.getParameter("eventStartDate")));
		event.setEndDate(StringTools.convertStringToTimestamp(request.getParameter("eventEndDate")));
		event.setDataDelete(StringTools.convertStringToTimestamp(request.getParameter("dateToDeleteData")));
		event.setCollageId(request.getParameter("collegeId") == null ? 0 : Integer.parseInt(request.getParameter("collegeId")));
		event.setBranchId(request.getParameter("branchId") == null ? 0 : Integer.parseInt(request.getParameter("branchId")));
		event.setFolderId(request.getParameter("FOLDER_ID"));
		isUserImportChecked = Boolean.getBoolean(request.getParameter("isUserImportChecked"));
		
		Map<String, String> EventStatus = new HashMap<String, String>();
		if(StringTools.isValidString(event.getName()) || event.getCollageId() == 0 || event.getBranchId() == 0 || event.getStartDate() == null || event.getEndDate() == null || event.getDataDelete() == null || event.getFolderId() == null)
		{
			EventStatus.put("result", "false");
			response.getWriter().write(new Gson().toJson(EventStatus));
		}
		else
		{
			if(AdminUtilites.createEvent(event))
			{
				if(isUserImportChecked)
				{
					String excelFilePath = "MM_userDetails.xlsx";
					FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
					List<UserDetails> users = AdminUtilites.getUsersFromExcel(inputStream, event.getCollageId(), event.getBranchId());

					if(AdminUtilites.InsertUsers(users))
					{
						EventStatus.put("UserImported", "true");
					}
					else
					{
						EventStatus.put("UserImported", "false");
					}			
				}
				
				EventStatus.put("result", "true");
				EventStatus.put("EventCreated", "true");
			}
			else
			{
				EventStatus.put("result", "false");
			}
			
			response.getWriter().write(new Gson().toJson(EventStatus));
		}
	}

}
