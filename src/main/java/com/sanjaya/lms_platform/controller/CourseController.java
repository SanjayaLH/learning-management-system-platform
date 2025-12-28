package com.sanjaya.lms_platform.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {

    @GetMapping("/test")
    public String testSecurity() {
        return "Congratulations! The Bouncer recognized you as a TEACHER.";
    }
}