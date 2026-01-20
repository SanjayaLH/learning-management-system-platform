package com.sanjaya.lms_platform.service;

import com.sanjaya.lms_platform.dto.ProfileResponse;
import com.sanjaya.lms_platform.model.UserCredentials;
import com.sanjaya.lms_platform.repository.UserCredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserCredentialsRepository userRepository;

    public ProfileResponse getMyProfile() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserCredentials user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        //Map Entity to DTO
        return ProfileResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().name())
                .build();
    }
}