package com.skylena.entity;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class UserOtp {

    @Id
    private String email;
    private String otp;
    private LocalDateTime otpCreatedAt;

    public UserOtp() {}

    public UserOtp(String email, String otp, LocalDateTime otpCreatedAt) {
        this.email = email;
        this.otp = otp;
        this.otpCreatedAt = otpCreatedAt;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getOtpCreatedAt() {
        return otpCreatedAt;
    }

    public void setOtpCreatedAt(LocalDateTime otpCreatedAt) {
        this.otpCreatedAt = otpCreatedAt;
    }
}
