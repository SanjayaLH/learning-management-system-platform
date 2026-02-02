package com.sanjaya.lms_platform.dto;

import lombok.Data;

// Data Transfer Object for incoming registration data
@Data
public class RegistrationRequest {
    private String email;
    private String password;
    private String role; // "TEACHER" or "STUDENT"
    private String firstName;
    private String lastName;
}
