package com.sanjaya.lms_platform.service;

import com.sanjaya.lms_platform.model.Course;
import com.sanjaya.lms_platform.model.UserCredentials;
import com.sanjaya.lms_platform.repository.CourseRepository;
import com.sanjaya.lms_platform.repository.UserCredentialsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

        //Role Validation (Safety check)
        if (!student.getRole().name().equals("STUDENT")) {
            throw new RuntimeException("Only students can enroll in courses.");
        }

        //Identify the course
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        //Enrollment duplicate Check
        if (student.getEnrolledCourses().contains(course)) {
            throw new RuntimeException("You are already enrolled in this course: " + course.getTitle());
        }

        //Perform the enrollment (adds to the Join Table)
        student.getEnrolledCourses().add(course);
        course.getStudents().add(student);
        // Use saveAndFlush to forces to write to the 'enrollments' table NOW
        userRepository.saveAndFlush(student);
    }

    @Transactional(readOnly = true)
    public List<Course> getStudentCourses() {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println("Fetching courses for: " + email);

        // Using the Native Query from CourseRepository to bypass Hibernate's proxy issues
        List<Course> courses = courseRepository.findCoursesByStudentEmail(email);

        System.out.println("Courses found in DB: " + courses.size());
        return courses;
    }
}
