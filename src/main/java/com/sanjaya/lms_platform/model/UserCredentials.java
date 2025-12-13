package com.sanjaya.lms_platform.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_credentials")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentials {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary Key

    @Column(unique = true, nullable = false)
    private String email; // Login identifier

    @Column(nullable = false, length = 60) // BCrypt hash length is 60
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

}