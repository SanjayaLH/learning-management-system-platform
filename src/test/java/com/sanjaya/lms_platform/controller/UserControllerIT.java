package com.sanjaya.lms_platform.controller;

import com.sanjaya.lms_platform.model.UserCredentials;
import com.sanjaya.lms_platform.repository.UserCredentialsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Uses your application-test.yml (H2 database)
@Transactional // Adds automatic rollback after each test
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserCredentialsRepository userRepository; // Match your repo name

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();

        // Seed the H2 database so the service finds a user
        UserCredentials user = new UserCredentials();
        user.setEmail("test@example.com");
        user.setFirstName("Sanjaya");
        user.setLastName("Developer");
        user.setRole(com.sanjaya.lms_platform.model.Role.STUDENT);
        user.setPasswordHash("password");
        userRepository.save(user);
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = {"STUDENT"})
    void getMyProfile_ShouldReturnUser_WhenAuthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/users/myprofile"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.firstName").value("Sanjaya"));
    }

    @Test
    void getMyProfile_ShouldReturnUnauthorized_WhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/users/myprofile"))
                .andExpect(status().isForbidden()); // or isUnauthorized() depending on your config
    }

}
