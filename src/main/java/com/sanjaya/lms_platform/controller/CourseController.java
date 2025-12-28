package com.sanjaya.lms_platform.controller;

import com.sanjaya.lms_platform.dto.CourseRequest;
import com.sanjaya.lms_platform.model.Course;
import com.sanjaya.lms_platform.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody CourseRequest request) {
        Course newCourse = courseService.createCourse(request);
        return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
    }
}