package com.sanjaya.lms_platform.dto;

import lombok.Data;
// Data Transfer Object for incoming login credentials
@Data
public class LoginRequest {
    private String email;
    private String password;
}
