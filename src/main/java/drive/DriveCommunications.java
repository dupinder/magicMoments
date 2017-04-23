package drive;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

public class DriveCommunications {
	/**
	 * Static Values;
	 */
	public static Drive SERVICE = null;
	
	
	public DriveCommunications() throws IOException {
		SERVICE = getDriveService();
	}
	
	/** Application name. */
    private static final String APPLICATION_NAME =
        "Drive API Java Quickstart";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File("../credentials/drive-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/drive-java-quickstart
     */
    private static final List<String> SCOPES =
        Arrays.asList(DriveScopes.DRIVE_METADATA_READONLY);

   // private static Drive service = getGoogleDriveService();
    
    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
            DriveCommunications.class.getResourceAsStream("client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

/*    private static Drive getGoogleDriveService() {
    	Drive service = null; 
		try {
			service = getDriveService();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return service;
	}*/

    
    
	/**
     * Build and return an authorized Drive client service.
     * @return an authorized Drive client service
     * @throws IOException
     */
    public static Drive getDriveService() throws IOException {
        Credential credential = authorize();
        return new Drive.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }


    /****
     * 
     * @param fileId
     * @param service
     * @return
     * @throws IOException
     */
    public String getThumbnailLink(String fileId, Drive service) throws IOException{

    	 File file = service.files().get(fileId).execute();
    	 System.out.println(file.getThumbnailLink());
    	 return file.getThumbnailLink();
    }
    
    /***
     * 
     * @return
     * @throws IOException
     */
    public List<Event> fetchEventFolder() throws IOException{

    	List<Event> events = new ArrayList<Event>();
    	getEvents(SERVICE, events);
    		
    		return events;
    }
    
    
    /*****
     * 
     * @throws IOException
     */
    @Deprecated
    public void fetchPhotosFromDrive() throws IOException{
        // Build a new authorized API client service.
    	
    	
    	List<Event> events = new LinkedList<Event>();
    	
    	// Get List of folders as Events
    		getEvents(SERVICE, events);

    		
    		System.out.println(events);
        // Print the names and IDs for up to 10 files.
/*        FileList result = service.files().list()
             .setPageSize(10)
             .setQ("mimeType='application/vnd.google-apps.folder'")
   			 .setSpaces("drive")
             .setFields("nextPageToken, files(id, name, originalFilename, description, mimeType)")
             .execute();
        List<File> files = result.getFiles();
        if (files == null || files.size() == 0) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
            	System.out.println(file.getName() +" : "+ file.getId() + ":" + file.getOriginalFilename() +  " : " + file.getDescription() + " : " + getThumbnailLink(file.getId(), service) + ":"+ file.getMimeType());
            }
        }*/
    }
    
    /***
     * 
     * @param folderId
     * @throws IOException
     */
    public Map<String, List<Photos>> fetchEventPhotosMap(Set<String> folderIds) throws IOException{
    	
    	Map<String, List<Photos>> ListOfFolder = new HashMap<String, List<Photos>>();
    	
    	for (String folderId : folderIds) {
        	
        	List<Photos> photos = new ArrayList<Photos>();
    		FileList result = SERVICE.files().list()
    		          .setQ("'"+folderId+"' in parents")
    		          .setSpaces("drive")
    		          .setFields("nextPageToken, files(id, name, originalFilename, description, mimeType, webContentLink, createdTime, webViewLink)")
    		          .execute();
    		     List<File> files = result.getFiles();
    		     if (files == null || files.size() == 0) {
    		         //System.out.println("No files found.");
    		     } else {
    		    	 
    		         for (File file : files) {
    		        	 System.out.println(file.getWebViewLink());
    		        	 photos.add(new Photos(file.getId(), file.getName(), getWebContentLink(file), file.getDescription(), file.getCreatedTime().toString(), file.getCreatedTime().toString(), "50", getImageThumbnail(file.getId()))); 
    		         }
    		         
    		     }
    		    	ListOfFolder.put(folderId, photos);
		}
    	
		return ListOfFolder;
    }
   
 public List<Photos> fetchEventPhotos(Set<String> folderIds) throws IOException{
    	
    	List<Photos> ListOfPhotos = new LinkedList<Photos>();
    	for (String folderId : folderIds) {
    		FileList result = SERVICE.files().list()
    		          .setQ("'"+folderId+"' in parents")
    		          .setSpaces("drive")
    		          .setFields("nextPageToken, files(id, name, originalFilename, description, mimeType, webContentLink, createdTime, webViewLink)")
    		          .execute();
    		     List<File> files = result.getFiles();
    		     if (files == null || files.size() == 0) {
    		         //System.out.println("No files found.");
    		     } else {
    		    	 
    		         for (File file : files) {
    		        	 System.out.println(file.getWebViewLink());
    		        	 ListOfPhotos.add(new Photos(file.getId(), file.getName(), getWebContentLink(file), file.getDescription(), file.getCreatedTime().toString(), file.getCreatedTime().toString(), "50", getImageThumbnail(file.getId()))); 
    		         }
    		     }
		}
    	
		return ListOfPhotos;
    }
    
    private String getWebContentLink(File file) {
    	String[] wenContentLink = file.getWebContentLink().split("&");
			return wenContentLink[0];
	}

	/****
     * 
     * @param service
     * @param events
     * @throws IOException
     */
    private void getEvents(Drive service, List<Event> events) throws IOException {
        FileList result = service.files().list()
                .setPageSize(10)
                .setQ("mimeType='application/vnd.google-apps.folder' and  trashed != true")
      			.setSpaces("drive")
                .setFields("nextPageToken, files(id, name, originalFilename, description, mimeType, createdTime)")
                .execute();
           List<File> files = result.getFiles();
           if (files == null || files.size() == 0) {
               //System.out.println("No files found.");
           } else {
               //System.out.println("Files:");
               for (File file : files) {
            	   Event event = new Event(file.getId(), file.getName(), getEventThumbnail(service, file.getId()), file.getDescription(), file.getCreatedTime().toString(), file.getCreatedTime().toString());
            	   events.add(event);
               }
           }
		
	}

/**
 * 
 * @param service
 * @param folderId
 * @return
 * @throws IOException
 */
	public static String getEventThumbnail(Drive service, String folderId) throws IOException {
		String EventThumbnail = "";
		
		FileList result = service.files().list()
          .setPageSize(1)
          .setQ("'"+folderId+"' in parents")
          .setSpaces("drive")
          .setFields("nextPageToken, files(id, name, originalFilename, description, mimeType)")
          .execute();
     List<File> files = result.getFiles();
     if (files == null || files.size() == 0) {
         //System.out.println("No files found.");
     } else {
    	 
         for (File file : files) {
        	 EventThumbnail = getImageThumbnail(file.getId());
         }
         
     }
		return EventThumbnail;
	}

	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static String getImageThumbnail(String id) {
		return "https://drive.google.com/thumbnail?sz=w300-h400&id="+id;
	}

	
	/***
	 * 
	 * @throws IOException
	 */
	public void fetchAllFolders() throws IOException
    {
    	Drive service = getDriveService();

        // Print the names and IDs for up to 10 files.
        FileList result = service.files().list()
	            .setQ("mimeType='application/vnd.google-apps.folder'")
	            .setSpaces("drive")
             	.setPageSize(10)
             	.setFields("nextPageToken, files(id, name, originalFilename, description)")
             	.execute();
        List<File> files = result.getFiles();
        if (files == null || files.size() == 0) {
            System.out.println("No files found.");
        } else {
            System.out.println("Files:");
            for (File file : files) {
            	System.out.println(file.getName() +" : "+ file.getId() + ":" + file.getOriginalFilename() +  " : " + file.getDescription() + " : " + getThumbnailLink(file.getId(), service));
            }
        }
    	
    }
    

}