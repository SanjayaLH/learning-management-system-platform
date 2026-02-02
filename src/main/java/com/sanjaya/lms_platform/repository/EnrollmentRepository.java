package com.sanjaya.lms_platform.repository;

import com.sanjaya.lms_platform.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Course, Long> {
    // Add custom queries here later to find courses by student ID
}