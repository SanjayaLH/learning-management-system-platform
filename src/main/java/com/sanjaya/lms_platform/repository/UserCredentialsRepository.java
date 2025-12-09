package com.sanjaya.lms_platform.repository;

import com.sanjaya.lms_platform.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
    // Custom query method for registration and login
    Optional<UserCredentials> findByEmail(String email);
}
