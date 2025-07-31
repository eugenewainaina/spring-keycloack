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

    public void sendUserCreateCredentials(String to, String firstName, String lastName, String username, String tempPassword, String organization) {
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

    public void sendUserUpdateNotification(String to, String firstName, String lastName, String organization) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your account has been updated - " + organization);
        message.setText(String.format(
                "Hello %s %s,\n\n" +
                        "Your account details in '%s' have been updated.\n\n",
                firstName, lastName, organization
        ));
        mailSender.send(message);
    }

    public void sendUserDeletionNotification(String to, String firstName, String lastName, String organization) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Account Deleted - " + organization);
        message.setText(String.format(
                "Hello %s %s,\n\n" +
                        "Your account in '%s' has been deleted.\n\n",
                firstName, lastName, organization
        ));
        mailSender.send(message);
    }

    public void sendUserStatusChangeNotification(String to, String firstName, String lastName, String organization, boolean enabled) {
        String status = enabled ? "enabled" : "disabled";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Account " + status + " - " + organization);
        message.setText(String.format(
                "Hello %s %s,\n\n" +
                        "Your account in '%s' has been %s.\n\n",
                firstName, lastName, organization, status
        ));
        mailSender.send(message);
    }
}