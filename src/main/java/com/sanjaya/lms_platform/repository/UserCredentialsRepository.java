package com.sanjaya.lms_platform.repository;

import com.sanjaya.lms_platform.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
    // Custom query method for registration and login
    Optional<UserCredentials> findByEmail(String email);
    /*
    This is the fix for getting empty enrollment Set,
    It fetches the user AND their courses in one query.
    */
    @Query("SELECT u FROM UserCredentials u LEFT JOIN FETCH u.enrolledCourses WHERE u.email = :email")
    Optional<UserCredentials> findByEmailWithCourses(@Param("email") String email);
}
