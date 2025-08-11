package com.skylena.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.skylena.entity.User;
import com.skylena.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository; // Repository for accessing user data

    @Autowired
    private EmailService emailService; // Service for sending emails

    // In-memory store to keep OTP and its generation timestamp
    private Map<String, OtpData> otpStore = new HashMap<>();

    // Helper class to store OTP and its generation time
    private static class OtpData {
        private String otp; // The generated OTP
        private LocalDateTime generatedTime; // The time when the OTP was generated

        public OtpData(String otp, LocalDateTime generatedTime) {
            this.otp = otp;
            this.generatedTime = generatedTime;
        }

        public String getOtp() {
            return otp;
        }

        public LocalDateTime getGeneratedTime() {
            return generatedTime;
        }
    }

    // Validate the email and password
    public boolean validateUser(String email, String password) {
        User user = userRepository.findByEmail(email); // Fetch user by email
        return user != null && user.getPassword().equals(password); // Check if user exists and password matches
    }

    // Generate OTP and send it to the user's registered email
    public String generateOtp(String email) {
        // Generate a random 6-digit OTP
        String otp = String.format("%06d", new Random().nextInt(999999));

        // Store the OTP and the time it was generated
        otpStore.put(email, new OtpData(otp, LocalDateTime.now()));
        System.out.println("Generated OTP for " + email + ": " + otp); // Debugging statement

        // Fetch the user from the database to get their registered email
        User user = userRepository.findByEmail(email);
        if (user != null) {
            String registeredEmail = user.getEmail(); // Get email from the User entity

            if (registeredEmail != null && !registeredEmail.isEmpty()) {
                // Send the OTP to the registered email
                emailService.sendOtp(registeredEmail, otp);
                System.out.println("OTP sent to registered email: " + registeredEmail);
            } else {
                System.out.println("Email not found for user: " + email);
            }
        } else {
            System.out.println("User not found: " + email);
        }

        // Start a 2-minute countdown timer for OTP validity
        startOtpTimer(email);

        return otp; // Return the generated OTP (for logging or debugging purposes)
    }

    // Start a 2-minute countdown timer
    private void startOtpTimer(String email) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        final int[] countdown = { 120 }; // 2 minutes = 120 seconds

        Runnable task = new Runnable() {
            @Override
            public void run() {
                if (countdown[0] > 0) {
                    System.out.println("OTP for " + email + " will expire in: " + countdown[0] + " seconds");
                    countdown[0] -= 1; // Decrease the countdown
                } else {
                    scheduler.shutdown(); // Stop the scheduler
                    otpStore.remove(email); // Remove OTP from storage after expiration
                    System.out.println("OTP for " + email + " has expired.");
                }
            }
        };

        // Schedule the task to run every second for 2 minutes
        scheduler.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
    }

    // Validate OTP with expiration logic (valid for 2 minutes) and one-time use
    public boolean validateOtp(String email, String otp) {
        OtpData otpData = otpStore.get(email); // Retrieve the OTP data for the given email

        if (otpData == null) {
            System.out.println("No OTP found or OTP already used for user: " + email);
            return false; // No OTP was generated or OTP was already used
        }

        // Check if OTP has expired (valid for 2 minutes)
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryTime = otpData.getGeneratedTime().plusMinutes(2);

        if (now.isAfter(expiryTime)) {
            // OTP has expired, remove it from the store
            otpStore.remove(email);
            System.out.println("OTP has expired for user: " + email);
            return false;
        }

        // Check if the OTP matches
        boolean isOtpValid = otp.equals(otpData.getOtp());

        if (isOtpValid) {
            // OTP is valid; remove it immediately to prevent reuse
            otpStore.remove(email);
            System.out.println("OTP validated and removed for user: " + email);
        } else {
            System.out.println("Invalid OTP entered by user: " + email);
        }

        return isOtpValid; // Return the result of OTP validation
    }
}
