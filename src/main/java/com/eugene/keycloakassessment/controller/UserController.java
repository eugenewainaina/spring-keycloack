package com.eugene.keycloakassessment.controller;

import com.eugene.keycloakassessment.service.UserService;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create a user in the specified realm
    @PostMapping
    public ResponseEntity<String> createUser(@RequestParam String realm, @RequestBody Map<String, String> body) {
        String username = body.get("username");
        String email = body.get("email");
        String firstName = body.get("firstName");
        String lastName = body.get("lastName");
        String tempPassword = body.get("password");

        userService.createUser(realm, firstName, lastName, username, email, tempPassword);
        return ResponseEntity.ok("User created successfully in realm: " + realm);
    }

    // List all users in the specified organisation
    @GetMapping
    public List<UserRepresentation> listUsers(@RequestParam String realm) {
        return userService.getUsers(realm);
    }

    // Get details of a specific user.
    @GetMapping("/{userId}")
    public UserRepresentation getUser(@RequestParam String realm, @PathVariable String userId) {
        return userService.getUserById(realm, userId);
    }
}