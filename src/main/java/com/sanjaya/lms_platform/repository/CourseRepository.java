package com.sanjaya.lms_platform.repository;

import com.sanjaya.lms_platform.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query(value = "SELECT c.* FROM courses c " +
            "JOIN enrollments e ON c.id = e.course_id " +
            "JOIN user_credentials u ON u.id = e.student_id " +
            "WHERE u.email = :email", nativeQuery = true)
    List<Course> findCoursesByStudentEmail(@Param("email") String email);
}
