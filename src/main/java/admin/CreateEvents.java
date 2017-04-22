package admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import user.Event;
import user.UserDetails;
import utilities.SimpleExcelReaderExample;
import utilities.StringTools;

import com.google.gson.Gson;

/**
 * Servlet implementation class CreateEvents
 */
@WebServlet(urlPatterns = "/CreateEvents", name = "CreateEvents")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
maxFileSize=1024*1024*10,      // 10MB
maxRequestSize=1024*1024*50)
public class CreateEvents extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Map<String, String> eventStatus = new HashMap<String, String>();
		if(!ServletFileUpload.isMultipartContent(request))
		{
			eventStatus.put("result", "false");
			response.getWriter().write(new Gson().toJson(eventStatus));
			return;
		}
		 // configures upload settings
        DiskFileItemFactory factory = new DiskFileItemFactory();
        
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		Map<String, String> requestParams = new HashMap<String, String>();
		
		try
		{
			List<FileItem> fileItems = upload.parseRequest(request);
			 Iterator i = fileItems.iterator();
			 File file = null;
			 String filePath = "D:\\files";
			 while (i.hasNext()) 
			 {
				 FileItem fi = (FileItem)i.next();
				 if (!fi.isFormField())	
				 {
					 // Get the uploaded file parameters
					 String fileName = fi.getName();
					 if(fileName.lastIndexOf("\\") >= 0)
					 {
						 file = new File( filePath + fileName.substring( fileName.lastIndexOf("\\"))) ;
					 }		
					 else
					 {
						 file = new File( filePath + File.separator + fileName.substring(fileName.lastIndexOf("\\")+1)) ;
						 int index = 1;
						 while(file.exists())
						 {
							 fileName += "("+index+")";
							 file = new File( filePath + File.separator + fileName.substring(fileName.lastIndexOf("\\")+1));
							 index++;
						 }
					 }
					 
					 fi.write(file);	
				 }
				 else
				 {
					 requestParams.put(fi.getFieldName(), fi.getString());
				 }
			 }
			 
			 if(file != null)
			 {
				 SimpleExcelReaderExample exre = new SimpleExcelReaderExample();
				 List<UserDetails> users = exre.importStudents(file);
				 
				 fetchEventDataAndSave(requestParams, eventStatus, users);
				 response.getWriter().write(new Gson().toJson(eventStatus));
			 }
		} 
		catch(Exception e)
		{
			eventStatus.put("result", "false");
			e.printStackTrace();
		}
		
		response.getWriter().write(new Gson().toJson(eventStatus));
		response.sendRedirect("AdminHome.html");
	}
	
	private Map<String, String> fetchEventDataAndSave(Map<String, String> requestParams, Map<String, String> eventStatus, List<UserDetails> users) throws FileNotFoundException
	{
		Event event = new Event();
		event.setName(requestParams.get("eventName"));
		event.setDiscription(requestParams.get("eventDiscription"));
		
		String dateFormat = "dd/MM/yyyy";
		event.setStartDate(StringTools.convertStringToTimestamp(requestParams.get("eventStartDate"), dateFormat));
		event.setEndDate(StringTools.convertStringToTimestamp(requestParams.get("eventEndDate"), dateFormat));
		event.setDataDelete(StringTools.convertStringToTimestamp(requestParams.get("validityPeriod"), dateFormat));
		
		String collegeId = requestParams.get("selectedCollege");
		event.setCollageId(collegeId == null ? 0 : Integer.parseInt(collegeId));
		
		String branchId = requestParams.get("selectedBranch");
		event.setBranchId(branchId == null ? 0 : Integer.parseInt(branchId));
		
		event.setFolderId(requestParams.get("folderId"));
		
		boolean eventSaved = false;
		if(!StringTools.isValidString(event.getName()) || event.getCollageId() == 0 || event.getBranchId() == 0 || event.getStartDate() == null || event.getEndDate() == null || event.getDataDelete() == null || event.getFolderId() == null)
		{
			eventStatus.put("result", String.valueOf(eventSaved));
			return eventStatus;
		}
		else
		{
			if(AdminUtilites.createEvent(event))
			{
				eventSaved = true;
				for(UserDetails user: users)
				{
					user.setBranchId(event.getBranchId());
					user.setCollageId(event.getCollageId());
				}
				
				boolean userSavedSuccessfully = false;
				
				if(AdminUtilites.InsertUsers(users))
				{
					userSavedSuccessfully = true;
				}
				
				eventStatus.put("userImported", String.valueOf(userSavedSuccessfully));
			}
			
			eventStatus.put("eventCreated", String.valueOf(eventSaved));
			eventStatus.put("result", String.valueOf(eventSaved));
			
			return eventStatus;
		}
	}
}
