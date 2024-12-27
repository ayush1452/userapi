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
1. **Fork the Project**:
   - Click on the "**Fork**" button at the top-right corner of the repository page on GitHub to create a copy of the project under your own GitHub account.

2. **Clone the Repository**:
   - After forking, clone the repository to your local machine:
   ```bash
   git clone https://github.com/your-username/your-forked-repo.git
   cd your-forked-repo
   ```

3. **Build the Project:**
   ```bash
   mvn clean install
   ```
4. **Run the Application:**
   ```bash
   mvn spring-boot:run
   ```
   Access the app at: http://localhost:8080


### H2 Database Console (Optional)

- URL: http://localhost:8080/h2-console
- JDBC URL: jdbc:h2:mem:usersdb
- Username: sa (no password needed)

Use this console to inspect database tables and data.

---

## API Documentation & Testing

### Swagger UI

Access the Swagger UI at:
- http://localhost:8080/swagger-ui/index.html

You can:

- View available endpoints
- See request/response models
- Test API calls interactively


### Sample API Calls

**Create a New User** (POST `/api/users`):

```json
{
  "username": "johndoe",
  "password": "password123",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe"
}
```
**Expected Response (200 OK)** :

```json
{
   "id": 1,
   "username": "johndoe",
   "email": "john.doe@example.com",
   "firstName": "John",
   "lastName": "Doe"
}
```
**Get All Users** (GET `/api/users`): **Expected Response (200 OK)**:

```json
[
  {
    "id": 1,
    "username": "johndoe",
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe"
  }
]
```
**Using cURL**:

```bash
# Get all users
curl -X GET http://localhost:8080/api/users

# Create a user
curl -X POST http://localhost:8080/api/users \
  -H 'Content-Type: application/json' \
  -d '{
        "username":"janedoe",
        "password":"securePass123",
        "email":"jane.doe@example.com",
        "firstName":"Jane",
        "lastName":"Doe"
      }'
```

#### Update a User

**Endpoint:**
```http
PUT /api/users/{id}
```

**Request Body:**
```json
{
  "username": "johndoe",
  "password": "newpassword123",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe Updated"
}
```

**cURL Command:**
```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H 'Content-Type: application/json' \
  -d '{
        "username": "johndoe",
        "password": "newpassword123",
        "email": "john.doe@example.com",
        "firstName": "John",
        "lastName": "Doe Updated"
      }'
```

**Response Example:**
```json
{
  "id": 1,
  "username": "johndoe",
  "email": "john.doe@example.com",
  "firstName": "John",
  "lastName": "Doe Updated"
}
```

---

## Contributing

Contributions are welcome!

To contribute:

1. Fork the repository.
2. Create a new branch:
   ```bash
   git checkout -b feature/your-feature
   ```
3. Make your changes and test them.
4. Commit and push:
   ```bash
   git push origin feature/your-feature
   ```
5. Open a pull request describing your changes.

---
## License

This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).

---

## Contact

For questions or issues, please open an issue in the repository.

Thank you for **checking out this project!** Feel free to reach out if you have any questions or suggestions.