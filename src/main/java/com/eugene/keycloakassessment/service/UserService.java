package com.eugene.keycloakassessment.service;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    private final Keycloak keycloak;
    private final EmailService emailService;

    @Autowired
    public UserService(Keycloak keycloak, EmailService emailService) {
        this.keycloak = keycloak;
        this.emailService = emailService;
    }

    // Create a user in the specified realm
    public void createUser(String realm, String firstName, String lastName, String username, String email, String tempPassword) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(true);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(tempPassword);
        user.setCredentials(Collections.singletonList(credential));

        keycloak.realm(realm).users().create(user);
        System.out.println("User " + username + " created in realm: " + realm);

        emailService.sendUserCreateCredentials(email, firstName, lastName, username, tempPassword, realm);
        System.out.println("Creation email sent");
    }

    // List all users in the specified organisation
    public List<UserRepresentation> getUsers(String realm) {
        return keycloak.realm(realm).users().list();
    }

    // Get details of a specific user
    public UserRepresentation getUserById(String realm, String userId) {
        return keycloak.realm(realm).users().get(userId).toRepresentation();
    }

    // Update a user’s details
    public void updateUser(String realm, String userId, String firstName, String lastName, String email) {
        UserRepresentation user = keycloak.realm(realm).users().get(userId).toRepresentation();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);

        keycloak.realm(realm).users().get(userId).update(user);
        System.out.println("User" + userId + " updated in realm: " + realm);

        emailService.sendUserUpdateNotification(email, firstName, lastName, realm);
        System.out.println("Update email sent");
    }

    // Delete a user
    public void deleteUser(String realm, String userId) {
        UserRepresentation user = keycloak.realm(realm).users().get(userId).toRepresentation();

        keycloak.realm(realm).users().get(userId).remove();
        System.out.println("User " + userId + " deleted from realm: " + realm);

        emailService.sendUserDeletionNotification(user.getEmail(), user.getFirstName(), user.getLastName(), realm);
        System.out.println("Deletion email sent");
    }

    // Disable a user
    public void disableUser(String realm, String userId) {
        UserRepresentation user = keycloak.realm(realm).users().get(userId).toRepresentation();
        user.setEnabled(false);

        keycloak.realm(realm).users().get(userId).update(user);
        System.out.println("User " + userId + " disabled in realm: " + realm);

        emailService.sendUserStatusChangeNotification(user.getEmail(), user.getFirstName(), user.getLastName(), realm, false);
        System.out.println("Status change email sent");
    }

    // Enable a user
    public void enableUser(String realm, String userId) {
        UserRepresentation user = keycloak.realm(realm).users().get(userId).toRepresentation();
        user.setEnabled(true);

        keycloak.realm(realm).users().get(userId).update(user);
        System.out.println("User " + userId + " enabled in realm: " + realm);

        emailService.sendUserStatusChangeNotification(user.getEmail(), user.getFirstName(), user.getLastName(), realm, true);
        System.out.println("Status change email sent");
    }
}