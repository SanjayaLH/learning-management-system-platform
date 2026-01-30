package com.sanjaya.lms_platform.controller;

import com.sanjaya.lms_platform.model.Role;
import com.sanjaya.lms_platform.model.UserCredentials;
import com.sanjaya.lms_platform.repository.CourseRepository;
import com.sanjaya.lms_platform.repository.UserCredentialsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class CourseControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserCredentialsRepository userRepository; // Match the repo name

    @BeforeEach
    void setUp() {
        courseRepository.deleteAll();
        userRepository.deleteAll(); // Need to inject UserRepository here too

        // Create the teacher so the CourseService can find them
        UserCredentials teacher = new UserCredentials();
        teacher.setEmail("teacher@lms.com");
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
        teacher.setRole(Role.TEACHER);
        teacher.setPasswordHash("password");
        userRepository.save(teacher);
    }

    @Test
    @WithMockUser(username = "teacher@lms.com", roles = {"TEACHER"})
    void createCourse_AsTeacher_ShouldReturnCreated() throws Exception {
        // Prepare JSON matching the CourseRequest DTO
        String courseJson = """
            {
                "title": "Java Masterclass",
                "description": "Learn Java from scratch",
                "category": "Programming"
            }
            """;

        // Perform the request
        mockMvc.perform(post("/api/v1/courses")
                        .with(csrf()) // Standard requirement for POST tests
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson))
                .andDo(print())

                // Assertions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists()) // The Entity usually has an ID after saving
                .andExpect(jsonPath("$.title").value("Java Masterclass"))
                .andExpect(jsonPath("$.category").value("Programming"));
    }

    @Test
    @WithMockUser(username = "student@lms.com", roles = {"STUDENT"})
    void createCourse_AsStudent_ShouldReturnForbidden() throws Exception {
        String courseJson = "{\"title\": \"Hacker Course\"}";

        mockMvc.perform(post("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson))
                .andExpect(status().isForbidden());
    }
}