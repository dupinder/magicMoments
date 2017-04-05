package user;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import utilities.EmailSender;

public class UserAuthentication
{
	private static Map<String, OtpManager> otpManager = new HashMap<String, OtpManager>();
	private EmailSender emailSender = new EmailSender();
	private String emailValidationPattern = "^[a-zA-Z]{1,30}[_A-Za-z0-9-]{0,30}(\\.[A-Za-z0-9-_]{1,30})*@[a-zA-Z0-9-]{1,30}(\\.[A-Za-z0-9-]{1,30}){1,2}$";
	
	public boolean generateOtpAndSendEmail(String email)
	{
		if(!email.matches(emailValidationPattern))
			return false;
		
		String otp = utilities.StringTools.getRandomString(6);
		emailSender.sendEmail(email, "Hi there, here is your OTP: " + otp + ". Complete verification process.");
		OtpManager om = new OtpManager();
		om.email = email;
		om.otp = otp;
		om.time = new Date().getTime();
		otpManager.put(email, om);
		return true;
	}
	
	public boolean verifyOtp(String email, String otp)
	{
		if(!email.matches(emailValidationPattern))
			return false;
		
		if(otpManager.containsKey(email))
		{
			OtpManager otpInCache = otpManager.get(email);
			if(otpInCache != null)
			{
				boolean isOtpValid = (new Date().getTime()) - otpInCache.time > 5 * 60 * 1000 ? false : true;   
				if(isOtpValid && otpInCache.otp.equals(otp))
				{
					otpManager.remove(email);
					return true;
				}
			}
		}
		
		return false;
	}
	
	private class OtpManager
	{
		String email;
		String otp;
		long time;
	}
}
