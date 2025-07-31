package com.eugene.keycloakassessment.controller;

import com.eugene.keycloakassessment.service.UserService;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    public ResponseEntity<String> createUser(@RequestParam String organization, @RequestBody Map<String, String> body) {
        try {
            String username = body.get("username");
            String email = body.get("email");
            String firstName = body.get("firstName");
            String lastName = body.get("lastName");
            String tempPassword = body.get("password");

            userService.createUser(organization, firstName, lastName, username, email, tempPassword);
            return ResponseEntity.ok("User created successfully in realm: " + organization);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating user: " + e.getMessage());
        }
    }

    // List all users in the specified organization
    @GetMapping
    public ResponseEntity<List<UserRepresentation>> listUsers(@RequestParam String organization) {
        try {
            return ResponseEntity.ok(userService.getUsers(organization));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyList());
        }
    }

    // Get details of a specific user
    @GetMapping("/{userId}")
    public ResponseEntity<UserRepresentation> getUser(@RequestParam String organization, @PathVariable String userId) {
        try {
            return ResponseEntity.ok(userService.getUserById(organization, userId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Update user info
    @PutMapping("/{userId}")
    public ResponseEntity<String> updateUser(
            @RequestParam String organization,
            @PathVariable String userId,
            @RequestBody Map<String, String> body) {
        try {
            String firstName = body.get("firstName");
            String lastName = body.get("lastName");
            String email = body.get("email");

            userService.updateUser(organization, userId, firstName, lastName, email);
            return ResponseEntity.ok("User updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating user: " + e.getMessage());
        }
    }

    // Delete a user
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@RequestParam String organization, @PathVariable String userId) {
        try {
            userService.deleteUser(organization, userId);
            return ResponseEntity.ok("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting user: " + e.getMessage());
        }
    }

    // Disable a user
    @PutMapping("/{userId}/disable")
    public ResponseEntity<String> disableUser(@RequestParam String organization, @PathVariable String userId) {
        try {
            userService.disableUser(organization, userId);
            return ResponseEntity.ok("User disabled successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error disabling user: " + e.getMessage());
        }
    }

    // Enable a user
    @PutMapping("/{userId}/enable")
    public ResponseEntity<String> enableUser(@RequestParam String organization, @PathVariable String userId) {
        try {
            userService.enableUser(organization, userId);
            return ResponseEntity.ok("User enabled successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error enabling user: " + e.getMessage());
        }
    }
}