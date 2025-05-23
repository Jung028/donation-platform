package com.charityplatform.accountservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * Controller for handling authentication-related endpoints.
 * Provides endpoints for user registration, login, and retrieving current user information.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    /**
     * Registers a new user in the system.
     * 
     * @param request The registration request containing user details
     * @return ResponseEntity with success message or error details
     * @throws Exception if registration fails
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // TODO: Implement user registration
        return ResponseEntity.ok().build();
    }

    /**
     * Authenticates a user and returns a JWT token.
     * 
     * @param request The login request containing credentials
     * @return ResponseEntity with JWT token or error details
     * @throws Exception if authentication fails
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        // TODO: Implement user login
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves the currently authenticated user's information.
     * 
     * @return ResponseEntity with user details
     * @throws Exception if user is not authenticated
     */
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getCurrentUser() {
        // TODO: Implement get current user
        return ResponseEntity.ok().build();
    }

    /**
     * Record representing a user registration request.
     * Contains all necessary information for creating a new user account.
     */
    public record RegisterRequest(
            String username,
            String password,
            String email,
            String firstName,
            String lastName,
            String phoneNumber,
            String userType
    ) {}

    /**
     * Record representing a user login request.
     * Contains the credentials needed for authentication.
     */
    public record LoginRequest(
            String username,
            String password
    ) {}
}
