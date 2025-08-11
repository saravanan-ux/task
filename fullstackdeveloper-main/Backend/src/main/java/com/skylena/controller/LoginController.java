package com.skylena.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.skylena.entity.LoginRequest;
import com.skylena.entity.OtpRequest;
import com.skylena.service.AuthService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // Validate email and password
        boolean isValid = authService.validateUser(loginRequest.getemail(), loginRequest.getPassword());
        if (!isValid) {
            return ResponseEntity.status(401).body(Collections.singletonMap("message", "Invalid email or password"));
        }

        // Generate OTP and send to user's registered email
        authService.generateOtp(loginRequest.getemail());

        // Return a response indicating that OTP is required
        return ResponseEntity.ok(Collections.singletonMap("otpRequired", true));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpRequest otpRequest) {
        // Validate the OTP
        boolean isOtpValid = authService.validateOtp(otpRequest.getEmail(), otpRequest.getOtp());

        Map<String, String> response = new HashMap<>();
        if (isOtpValid) {
            response.put("status", "success");
            response.put("message", "OTP verified successfully");
            return ResponseEntity.ok(response); // Send a JSON response
        } else {
            response.put("status", "error");
            response.put("message", "Invalid OTP");
            return ResponseEntity.status(401).body(response); // Send a JSON response
        }
    }
}
