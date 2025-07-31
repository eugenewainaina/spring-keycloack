package com.eugene.keycloakassessment.service;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private final Keycloak keycloak;
    private final EmailService emailService;


    public UserService(EmailService emailService) {
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl("http://keycloak:8080")
                .realm("master")
                .clientId("admin-cli")
                .username("admin1")
                .password("admin1_password")
                .build();
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

        System.out.println("User created: " + username + " in realm: " + realm);

        emailService.sendUserCredentials(email, firstName, lastName, username, tempPassword, realm);
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
    }

    // Delete a user
    public void deleteUser(String realm, String userId) {
        keycloak.realm(realm).users().get(userId).remove();
    }

}