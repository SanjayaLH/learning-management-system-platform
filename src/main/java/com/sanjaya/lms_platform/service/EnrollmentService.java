package com.sanjaya.lms_platform.service;

import com.sanjaya.lms_platform.model.Course;
import com.sanjaya.lms_platform.model.UserCredentials;
import com.sanjaya.lms_platform.repository.CourseRepository;
import com.sanjaya.lms_platform.repository.UserCredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final UserCredentialsRepository userRepository;
    private final CourseRepository courseRepository;

    @Transactional // Ensures the DB transaction is committed correctly
    public void enroll(Long courseId) {
        //Identify the student from the JWT token
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserCredentials student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        //Identify the course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        //Perform the enrollment (adds to the Join Table)
        student.getEnrolledCourses().add(course);
        userRepository.save(student);
    }

    @Transactional(readOnly = true)
    public Set<Course> getStudentCourses() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserCredentials student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        //forces Hibernate to initialize the collection
        student.getEnrolledCourses().size();
        return student.getEnrolledCourses();
    }
}
