package utilities;

import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

public class StringTools
{
	private static String sLegalCharsForIdentifier = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	private static Random randomGenerator = new Random(System.currentTimeMillis());
	private static String emailValidationPattern = "^[a-zA-Z]{1,30}[_A-Za-z0-9-]{0,30}(\\.[A-Za-z0-9-_]{1,30})*@[a-zA-Z0-9-]{1,30}(\\.[A-Za-z0-9-]{1,30}){1,2}$";
	private static String passwordValidationPattern = "^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,})$";
 
	/**
	* Returns a random generated string
	* The new string has a fixed length defined by the user
	*
	* @param iLength The fixed length of the new string
	* @return The new generated string
	*/
	public static String getRandomString(int iLength)
	{
		return getRandomString(iLength, sLegalCharsForIdentifier);
	}
	
	/**
	* Returns a random generated string
	* The new string has a fixed length defined by the user
	*
	* @param iLength The fixed length of the new string
	* @param legalCharacters the string consisting of legal characters allowed to be there in identifier
	* @return The new generated string
	*/
	public static String getRandomString(int iLength, String legalCharacters)
	{
		char chars[] = new char[iLength];
		int iSize = legalCharacters.length();

		for (int i = 0; i < iLength; i++)
			chars[i] = legalCharacters.charAt(randomGenerator.nextInt(iSize));	// Gets a legal character

		// Can not tolerate blank at the end of the string:

		while (chars[iLength - 1] == ' ')
			chars[iLength - 1] = legalCharacters.charAt(randomGenerator.nextInt(iSize));// Gets a legal character

		return new String(chars);
	}
	
	/**
	 * Checks if a string is valid or not
	 *
	 * It checks these properties of the string:
	 * - The string cannot be <code>null</code>
	 * - The string cannot equal "null" (with any letter case)
	 * - The length of the string AFTER .trim() must be larger than 0
	 *
	 * Returns a <code>boolean</code> that is true if this is a valid string and false if not.
	 *
	 *
	 * @param strToCheck DESCRIPTION
	 * @return true if string is valid, false if not
	 */
	public static boolean isValidString(String strToCheck)
	{

		boolean bolValid = false;
		// IT IS FORBIDDEN TO CHANGE THE IMPLEMENTATION BELOW! - Tormod
		// But I am in doubt whether or not it is correct to have the "null" check here, what if the user WANTS it to be "null"? - Tormod
		if ((strToCheck != null) && !(strToCheck.toUpperCase().equals("NULL")) && (strToCheck.trim().length() != 0))
		{
			bolValid = true;
		}

		return bolValid;
	}
	
	  public static Timestamp convertStringToTimestamp(String str_date) 
	  {	
		  return convertStringToTimestamp(str_date, "yyyy-MM-dd hh:mm:ss");
	  }
	  
	  public static Timestamp convertStringToTimestamp(String str_date, String dateFormat) 
	  {	
		  if(str_date != null){
		    try 
		    {
		      DateFormat formatter;
		      formatter = new SimpleDateFormat(dateFormat);
		       // you can change format of date
		      Date date = formatter.parse(str_date);
		      java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());

		      return timeStampDate;
		    } 
		    catch (Exception e) 
		    {
		      System.out.println("Exception :" + e);
		      return null;
		    }
		  }
		  else
		  {
			  return null;
		  }
	  }

	public static boolean isValidEmail(String email){
		if (StringTools.isValidString(email) && email.matches(emailValidationPattern))
			return true;
		
		return false;
	}
	
	public static boolean isValidPassword(String password)
	{
		if (StringTools.isValidString(password) && password.matches(passwordValidationPattern))
			return true;
		return false;
	}
}
