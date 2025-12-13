package com.sanjaya.lms_platform.controller;

import com.sanjaya.lms_platform.dto.LoginRequest;
import com.sanjaya.lms_platform.dto.RegistrationRequest;
import com.sanjaya.lms_platform.model.UserCredentials;
import com.sanjaya.lms_platform.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            // Call the service layer to verify credentials
            String token = authService.loginUser(request);

            // Return success response (200 OK) with the token
            // Note: In a real app, this would return a JSON object like {"token": "JWT_STRING"}
            return ResponseEntity.ok(token);

        } catch (IllegalArgumentException e) {
            // Handle Unauthorized/Invalid Credentials (401)
            // This handles the "Invalid email or password." error thrown by the service
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (Exception e) {
            // Handle generic server errors (500)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed due to server error.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        try {
            // Basic validation (can be replaced by @Valid annotation)
            if (request.getEmail() == null || request.getPassword() == null || request.getRole() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email, password, and role are required.");
            }

            UserCredentials newUser = authService.registerUser(request);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("User successfully registered with email: " + newUser.getEmail());

        } catch (IllegalArgumentException e) {
            // Email already exists
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            // Log the error and return a generic 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed due to server error.");
        }
    }
}
