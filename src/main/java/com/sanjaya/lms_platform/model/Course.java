package com.sanjaya.lms_platform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private  String description;
    private String category;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private UserCredentials teacher;
    @ManyToMany(mappedBy = "enrolledCourses")
    private Set<UserCredentials> students = new HashSet<>();

}
