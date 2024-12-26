package com.example.spring_security_jwt.controller;

import com.example.spring_security_jwt.entity.User;
import com.example.spring_security_jwt.service.OtpService;
import com.example.spring_security_jwt.service.Userservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class ForgotPasswordController {

    @Autowired
    private Userservice userService; // Service to interact with the user repository

    @Autowired
    private OtpService otpService; // Service to generate and validate OTPs

    @Autowired
    private JmsTemplate jmsTemplate; // To send messages to ActiveMQ

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        if (!userService.existsByEmail(email)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }

        // Generate OTP
        String otp = otpService.generateOtp(email);

        // Send the OTP message to ActiveMQ queue
        String message = email + ":" + otp; // Include email and OTP in the message
        jmsTemplate.convertAndSend("emailQueue", message);

        return ResponseEntity.ok("OTP sent to your email.");
    }
}