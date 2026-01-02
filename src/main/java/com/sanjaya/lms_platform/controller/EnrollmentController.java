package com.sanjaya.lms_platform.controller;

import com.sanjaya.lms_platform.model.Course;
import com.sanjaya.lms_platform.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping("/{courseId}")
    public ResponseEntity<String> enroll(@PathVariable Long courseId) {
        enrollmentService.enroll(courseId);
        return ResponseEntity.ok("Enrolled successfully" + courseId);
    }

    @GetMapping("/my-courses")
    public ResponseEntity<Set<Course>> getMyCourses() {
        return ResponseEntity.ok(enrollmentService.getStudentCourses());
    }
}
