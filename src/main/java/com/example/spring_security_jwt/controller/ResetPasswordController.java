package com.example.spring_security_jwt.controller;

import com.example.spring_security_jwt.service.OtpService;
import com.example.spring_security_jwt.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class ResetPasswordController {

    @Autowired
    private OtpService otpService;

    @Autowired
    private Userservice userService;

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam String email,
            @RequestParam String otp,
            @RequestParam String newPassword) {

        if (!otpService.validateOtp(email, otp)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired OTP.");
        }

        userService.updatePassword(email, newPassword);

        return ResponseEntity.ok("Password updated successfully.");
    }
}