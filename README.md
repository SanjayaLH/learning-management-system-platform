# Learning Management System Platform

A comprehensive Learning Management System built with Spring Boot, Java, and Maven. This platform supports user authentication (students, teachers(admins) and course management.

## Features:
   - Role-Based Authentication: Secure sign-up and login for Students, Instructors, and Administrators.
   - Course Catalog: Browse available courses, view details, and manage enrollment.

## Database ER Diagram:

![ER Diagram](docs/ER-diagram-v1.png)

## Tech Stack:
  - Framework: Spring Boot - Application Framework
  - Language: Java - Core programming language
  - Database: MySQL - Primary data storage (configurable)
  - Build Tool: Maven - Build automation and dependency management
  - Frontend: Thymeleaf/React/Angular(Specify your frontend technology)
  - Security: Spring Security Authentication and authorization

## Sequence Diagram:

![Sequence Diagram](docs/user-registration.png)
![Sequence Diagram](docs/user-login.png)
![Sequence Diagram](docs/teacher-create-course.png)
![Sequence Diagram](docs/teacher-views-courseList-then-details.png)
![Sequence Diagram](docs/student-views-courseList-then-details.png)