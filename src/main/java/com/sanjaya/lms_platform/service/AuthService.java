package com.sanjaya.lms_platform.service;

import com.sanjaya.lms_platform.dto.LoginRequest;
import com.sanjaya.lms_platform.dto.LoginResponse;
import com.sanjaya.lms_platform.dto.RegistrationRequest;
import com.sanjaya.lms_platform.model.Role;
import com.sanjaya.lms_platform.model.UserCredentials;
import com.sanjaya.lms_platform.repository.UserCredentialsRepository;
import com.sanjaya.lms_platform.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserCredentialsRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public LoginResponse loginUser(LoginRequest request) {

        // Fetch Credentials by Email (Query DB)
        UserCredentials credentials = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        // Verify Password (Action: Compare Hash)
        // passwordEncoder.matches(rawPassword, hashedPassword)
        boolean isMatch = passwordEncoder.matches(
                request.getPassword(),
                credentials.getPasswordHash()
        );

        if (!isMatch) {
            // Use the same generic message for security purposes (prevents enumeration attacks)
            throw new IllegalArgumentException("Invalid email or password.");
        }
        // Success: Generate a token
        try {
            String token = jwtProvider.generateToken(
                    credentials.getEmail(),
                    credentials.getRole().name()
            );

            // Return the comprehensive LoginResponse
            return new LoginResponse(
                    token,
                    credentials.getEmail(),
                    credentials.getRole().name()
            );
        } catch (Exception e) {
            // Log the error and throw a runtime exception if token generation fails
            throw new RuntimeException("Error generating security token", e);
        }
    }

    public UserCredentials registerUser(RegistrationRequest request) {

        // Check for User Existence
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered: " + request.getEmail());
        }

        // Hash the Password
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        // Create and Save the Credentials Entity
        UserCredentials credentials = UserCredentials.builder()
                .email(request.getEmail())
                .passwordHash(hashedPassword)
                .role(Role.valueOf(request.getRole().toUpperCase()))
                .build();

        return repository.save(credentials);
    }
}