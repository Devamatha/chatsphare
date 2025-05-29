package com.kanzariya.chatsphere.service;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true); // HTML format

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email. Please check SMTP settings.", e);
        }
    }
    public void sendOTPEmail(String to, String otp) {
        String subject = "Your OTP Code";
        String body = "<p>Your OTP is: <b>" + otp + "</b></p>" +
                      "<p>This OTP is valid for 5 minutes. Please do not share it with anyone.</p>";
        sendEmail(to, subject, body);
    }


}

