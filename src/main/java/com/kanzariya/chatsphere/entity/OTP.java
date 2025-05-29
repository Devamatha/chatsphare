package com.kanzariya.chatsphere.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "otp")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Better primary key strategy

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "\\d{6}", message = "OTP must be exactly 6 digits")
    @Column(nullable = false)
    private String otp;

    private LocalDateTime expiryTime; // Optional, handles OTP expiration logic
}


