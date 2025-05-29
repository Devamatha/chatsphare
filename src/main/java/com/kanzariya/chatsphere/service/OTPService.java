package com.kanzariya.chatsphere.service;

public interface OTPService {
	
	 public String generateAndSaveOTP(String email);
	 public boolean validateOTP(String email, String enteredOtp);
	 void saveOTP(String email, String otp);

}
