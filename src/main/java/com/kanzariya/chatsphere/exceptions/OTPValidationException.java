package com.kanzariya.chatsphere.exceptions;

public class OTPValidationException extends RuntimeException {
    public OTPValidationException(String message) {
        super(message);
    }
}

