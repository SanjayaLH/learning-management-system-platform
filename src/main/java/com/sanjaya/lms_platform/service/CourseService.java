package com.sanjaya.lms_platform.service;

import com.sanjaya.lms_platform.dto.CourseRequest;
import com.sanjaya.lms_platform.model.Course;
import com.sanjaya.lms_platform.model.UserCredentials;
import com.sanjaya.lms_platform.repository.CourseRepository;
import com.sanjaya.lms_platform.repository.UserCredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserCredentialsRepository userRepository;

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course createCourse(CourseRequest request) {
        // Get the email of the logged-in teacher from the Security Context
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Find the Teacher entity in the DB
        UserCredentials teacher = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        // Map DTO to Entity
        Course course = Course.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .teacher(teacher) // Link the course to the teacher
                .build();

        // Save and return
        return courseRepository.save(course);
    }
}

