package com.kanzariya.chatsphere.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kanzariya.chatsphere.entity.OTP;
import com.kanzariya.chatsphere.repository.OTPRepository;
import com.kanzariya.chatsphere.repository.UsersRepository;
import com.kanzariya.chatsphere.service.EmailService;
import com.kanzariya.chatsphere.service.OTPService;
import com.kanzariya.chatsphere.util.OTPGenerator;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OTPServiceImpl implements OTPService {
	@Autowired
    private EmailService emailService;

    @Autowired
    private OTPRepository otpRepository;

    public String generateAndSaveOTP(String email) {
        String otp = OTPGenerator.generateOTP();
        OTP otpEntity = new OTP();
        otpEntity.setEmail(email);
        otpEntity.setOtp(otp);
        otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(5)); // OTP valid for 5 mins
        otpRepository.save(otpEntity);
        return otp;
    }

    public boolean validateOTP(String email, String enteredOtp) {
        OTP otpEntity = otpRepository.findByEmail(email);
        if (otpEntity != null && otpEntity.getOtp().equals(enteredOtp) &&
            otpEntity.getExpiryTime().isAfter(LocalDateTime.now())) {
            otpRepository.delete(otpEntity); // Clear OTP after successful validation
            return true;
        }
        return false;
    }
    
    public void saveOTP(String email, String otp) {
        OTP otpEntity = new OTP();
        otpEntity.setEmail(email);
        otpEntity.setOtp(otp);
        otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(5)); // Set expiry time
        otpRepository.save(otpEntity);
    }
    
    public void generateAndSendOTP(String email) {
        String otp = OTPGenerator.generateOTP();
        saveOTP(email, otp); // Store OTP in database
        emailService.sendOTPEmail(email, otp); // Send OTP via email
    }

}

