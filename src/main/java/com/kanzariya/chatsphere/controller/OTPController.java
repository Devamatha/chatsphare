package com.kanzariya.chatsphere.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.kanzariya.chatsphere.entity.OTP;
import com.kanzariya.chatsphere.service.EmailService;
import com.kanzariya.chatsphere.service.OTPService;

@RestController
@RequestMapping("/otp")
public class OTPController {

    @Autowired
    private OTPService otpService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendotp")
    public String sendOTP(@RequestParam String email ) {
        String generatedOtp = otpService.generateAndSaveOTP(email);
        emailService.sendOTPEmail(email, generatedOtp);
        return "OTP sent successfully!";
    }


    @PostMapping("/validateotp")
    public ResponseEntity<String> validateOTP(@RequestParam String email, String Otp) {
        boolean isValid = otpService.validateOTP(email, Otp);
        return isValid 
            ? ResponseEntity.ok("OTP is valid!") 
            : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired OTP.");
    }


}

