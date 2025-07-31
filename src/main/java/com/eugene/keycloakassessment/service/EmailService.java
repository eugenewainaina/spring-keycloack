package com.eugene.keycloakassessment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendUserCredentials(String to, String firstName, String lastName, String username, String tempPassword, String organization) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Welcome to " + organization);
        message.setText(String.format(
                "Hello %s %s,\n\n" +
                        "Your account for organization '%s' has been created.\n\n" +
                        "Username: %s\nTemporary Password: %s\n\n" +
                        "Please log in and change your password.",
                firstName, lastName, organization, username, tempPassword
        ));
        mailSender.send(message);
    }
}