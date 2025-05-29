package com.kanzariya.chatsphere.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanzariya.chatsphere.entity.OTP;

public interface OTPRepository extends JpaRepository<OTP, String> {
    OTP findByEmail(String email);
}

