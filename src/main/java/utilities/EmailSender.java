package utilities;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class EmailSender
{
	public void sendEmail(String recipient, String dataToSend)
	{
	      // Recipient's email ID needs to be mentioned.
	      String to = recipient;

	      // Sender's email ID needs to be mentioned
	      String from = "ujjisss@gmail.com";
	      String password = "ujju@1990#9909155792%";

	      // Assuming you are sending email from localhost
	      String host = "smtp.gmail.com";

	      // Get system properties
	      Properties properties = System.getProperties();

	      // Setup mail server
	      properties.setProperty("mail.smtp.host", host);
	      properties.setProperty("mail.smtp.auth", "true");
	      properties.setProperty("mail.smtp.starttls.enable", "true");
	      properties.setProperty("mail.smtp.port", "465");
	      
	      properties.setProperty("mail.smtp.socketFactory.port", "465");
	      properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	      properties.setProperty("mail.smtp.socketFactory.fallback", "false");

	      Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() 
	      {
	          protected PasswordAuthentication getPasswordAuthentication() 
	          {
	              return new PasswordAuthentication(from,password);
	          }
	     });

	      try {
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject("Magic moment email verification");

	         // Now set the actual message
	         message.setText(dataToSend);

	         // Send message
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      }
	      catch (MessagingException mex) 
	      {
	         mex.printStackTrace();
	         System.out.println("Sending email failed.... :(");
	      }
	   }
}
