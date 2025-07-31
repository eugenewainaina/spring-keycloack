package com.eugene.keycloakassessment.service;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class KeycloakService {

    private final Keycloak keycloak;

    public KeycloakService() {
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl("http://keycloak:8080")
                .realm("master")
                .clientId("admin-cli")
                .username("admin1")
                .password("admin1_password")
                .build();
    }

    public void createRealm(String realmName) {
        boolean exists = keycloak.realms().findAll().stream()
                .anyMatch(r -> r.getRealm().equalsIgnoreCase(realmName));

        if (exists) {
            System.out.println("Existing Realm found. Name: " + realmName);
            return;
        }

        RealmRepresentation realm = new RealmRepresentation();
        realm.setRealm(realmName);
        realm.setEnabled(true);
        realm.setUsers(Collections.emptyList());


        keycloak.realms().create(realm);

        System.out.println("Realm created: " + realmName);

        // The default client for the realm
        createClient(realmName, "spring-boot-backend");
    }

    private void createClient(String realmName, String clientID) {
        var client = new ClientRepresentation();

        client.setClientId(clientID);
        client.setProtocol("openid-connect");
        client.setPublicClient(true);
        client.setDirectAccessGrantsEnabled(true);
        client.setStandardFlowEnabled(true);
        client.setRedirectUris(Collections.singletonList("*"));

        keycloak.realm(realmName).clients().create(client);

        System.out.println("Client " + clientID + " created in Realm: " + realmName);
    }
}