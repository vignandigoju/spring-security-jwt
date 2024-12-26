package com.example.spring_security_jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    @Autowired
    private EmailService emailService;

    @JmsListener(destination = "emailQueue")
    public void receiveMessage(String message) {
        // Split the message to get the email and OTP
        String[] parts = message.split(":");
        String email = parts[0];
        String otp = parts[1];

        // Send the OTP to the user's email
        String subject = "Your OTP for Password Reset";
        String text = "Dear User,\n\nYour OTP is: " + otp + "\n\nPlease use this OTP to reset your password.\n\nThank you.";
        emailService.sendEmail(email, subject, text);
    }
}