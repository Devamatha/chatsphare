package com.kanzariya.chatsphere.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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


//    @PostMapping("/validateotp")
//    public ResponseEntity<String> validateOTP(@RequestParam String email,@RequestParam String Otp) {
//        boolean isValid = otpService.validateOTP(email, Otp);
//        return isValid 
//            ? ResponseEntity.ok("OTP is valid!") 
//            : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired OTP.");
//    }
    
    @PostMapping("/validateotp")
    public ResponseEntity<Map<String, String>> validateOTP(@RequestBody Map<String, String> requestBody) {
        boolean isValid = otpService.validateOTP(requestBody.get("email"), requestBody.get("otp"));

        Map<String, String> response = new HashMap<>();
        if (isValid) {
            response.put("status", "success");
            response.put("message", "OTP is valid!");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Invalid or expired OTP.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }



}

