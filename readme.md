# User Management API

A RESTful API for managing user accounts, built with **Spring Boot**, **Spring Data JPA**, **H2 in-memory database**, **Spring Security** (for password encoding), and **Springdoc OpenAPI** for interactive API documentation.

This application demonstrates best practices in software development, including:

- Layered Architecture (Controller → Service → Repository)
- Data Validation & DTOs for secure and flexible data transfer
- Global Exception Handling for consistent error responses
- Secure Password Handling with BCrypt
- Comprehensive Logging and Clear Documentation
- Interactive API Documentation via Swagger UI

---

## Key Features

1. **CRUD Operations**: Create, read, update, and delete user accounts.
2. **Validation & Error Handling**:
    - User input is validated using annotations (`@NotBlank`, `@Email`, `@Size`, etc.).
    - A global exception handler returns well-structured error responses.
3. **Data Transfer Objects (DTOs)**:
    - `UserRequestDTO` for input, ensuring clients cannot set the `id`.
    - `UserDTO` for output, hiding sensitive information like passwords.
4. **Password Security**:
    - Uses `BCryptPasswordEncoder` to hash passwords before persisting.
5. **In-memory Database**:
    - H2 is used for simplicity, enabling quick setup and testing.
    - Database schema auto-updates on application start.
6. **Swagger/OpenAPI Integration**:
    - Self-documented endpoints accessible via a web UI.
    - Easily test endpoints and view request/response models.
7. **Logging**:
    - Uses SLF4J with Logback for debugging and monitoring.
    - Configurable log levels for different environments.

---

# User Management API

A RESTful API for managing users, built with **Spring Boot**, **Spring Data JPA**, an **H2 in-memory database**, **Spring Security** (for password encoding), and **Springdoc OpenAPI** for interactive API documentation.

This project follows best practices, including:

- **Layered Architecture** (Controller → Service → Repository)
- **DTOs & Validation** for secure, flexible data handling
- **Global Exception Handling** for consistent error responses
- **Secure Password Hashing** with BCrypt
- **Comprehensive Logging** using SLF4J/Logback
- **Interactive API Docs** via Swagger UI

---

## Key Features

1. **CRUD Operations**: Create, read, update, and delete user accounts.
2. **Validation & Error Handling**:
    - Uses annotations (`@NotBlank`, `@Size`, `@Email`) to validate input.
    - Global exception handler provides consistent error responses.
3. **Data Transfer Objects (DTOs)**:
    - `UserRequestDTO` (input) prevents clients from setting the `id`.
    - `UserDTO` (output) hides sensitive data (e.g., password).
4. **Password Security**:
    - BCryptPasswordEncoder hashes passwords before saving.
5. **In-memory H2 Database**:
    - Quick setup, schema auto-updates.
    - Ideal for demos and testing.
6. **Swagger/OpenAPI Integration**:
    - View endpoints and models at `/swagger-ui/index.html`.
    - Test API calls interactively.
7. **Logging**:
    - SLF4J with Logback for debugging and monitoring.
    - Adjustable log levels via `application.properties` or `logback-spring.xml`.

---

## Project Structure

```text
src
└── main
    └── java
        └── com.example.usermanagement
            ├── UserManagementApplication.java        // Main entry point
            ├── config
            │   ├── OpenAPIConfig.java               // Swagger/OpenAPI configuration
            │   └── WebSecurityConfig.java           // Security & password encoder
            ├── controller
            │   └── UserController.java              // REST endpoints for user management
            ├── dto
            │   ├── UserDTO.java                     // DTO for user output
            │   └── UserRequestDTO.java              // DTO for user input
            ├── exception
            │   ├── ErrorResponse.java               // Standard error response format
            │   ├── GlobalExceptionHandler.java      // Centralized exception handling
            │   └── ResourceNotFoundException.java   // Custom exception for missing resources
            ├── model
            │   └── User.java                        // JPA entity representing a user
            ├── repository
            │   └── UserRepository.java              // Data access layer for User entity
            └── service
                └── UserService.java                 // Business logic for user operations
```

## Getting Started

### Prerequisites
- **Java 17+**
- **Maven**

---

## Installation & Run

### Clone the repository:
1. Clone the project to your local machine:
   ```bash
   git clone https://github.com/ayush1452/userapi.git
   cd userapi
   ```
