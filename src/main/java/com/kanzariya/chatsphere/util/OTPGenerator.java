package com.kanzariya.chatsphere.util;

import java.security.SecureRandom;

public class OTPGenerator {
    private static final int OTP_LENGTH = 6;
    private static final SecureRandom random = new SecureRandom();

    public static String generateOTP() {
        int otp = 100000 + random.nextInt(900000); // Ensures 6-digit OTP
        return String.valueOf(otp);
    }
}

