package com.sanjaya.lms_platform.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String title;
    private  String description;
    private String category;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @JsonIgnoreProperties("enrolledCourses")
    private UserCredentials teacher;
    @ToString.Exclude
    @ManyToMany(mappedBy = "enrolledCourses")
    @Builder.Default
    @JsonIgnoreProperties("enrolledCourses")
    private Set<UserCredentials> students = new HashSet<>();

}
