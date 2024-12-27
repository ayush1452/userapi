package com.example.userapi.controller;

import com.example.userapi.dto.UserDTO;
import com.example.userapi.dto.UserRequestDTO;
import com.example.userapi.exception.ResourceNotFoundException;
import com.example.userapi.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing user-related operations.
 */
@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Provides a welcome message for the User Management API.
     *
     * @return A string containing the welcome message.
     */
    @GetMapping("/")
    public String home() {
        return "Welcome to the User Management API!";
    }

    /**
     * Retrieves all users from the system.
     *
     * @return A list of UserDTO objects representing all users in the system.
     */
    @GetMapping
    public List<UserDTO> getAllUsers() {
        log.info("GET /api/users called");
        return userService.getAllUsers();
    }

    /**
     * Retrieves a specific user by their ID.
     *
     * @param id The unique identifier of the user to retrieve.
     * @return A ResponseEntity containing the UserDTO of the requested user.
     * @throws ResourceNotFoundException If no user is found with the given ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) throws ResourceNotFoundException {
        log.info("GET /api/users/{} called", id);
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * Creates a new user in the system.
     *
     * @param userRequest The UserRequestDTO containing the details of the user to be created.
     * @return A ResponseEntity containing the created UserDTO if successful, or an error message if the username or email is already in use.
     */
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDTO userRequest) {
        log.info("POST /api/users called");
        if (userService.usernameExists(userRequest.getUsername())) {
            log.warn("Username {} is already taken", userRequest.getUsername());
            return ResponseEntity.badRequest().body("Username is already taken");
        }
        if (userService.emailExists(userRequest.getEmail())) {
            log.warn("Email {} is already in use", userRequest.getEmail());
            return ResponseEntity.badRequest().body("Email is already in use");
        }
        UserDTO createdUser = userService.createUser(userRequest);
        return ResponseEntity.ok(createdUser);
    }

    /**
     * Updates an existing user in the system.
     *
     * @param id The unique identifier of the user to be updated.
     * @param userRequest The UserRequestDTO containing the updated details of the user.
     * @return A ResponseEntity containing the updated UserDTO.
     * @throws ResourceNotFoundException If no user is found with the given ID.
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequest) throws ResourceNotFoundException {
        log.info("PUT /api/users/{} called", id);
        UserDTO updatedUser = userService.updateUser(id, userRequest);
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Deletes a user from the system based on the provided ID.
     *
     * @param id The unique identifier of the user to be deleted.
     * @return A ResponseEntity with no content, indicating successful deletion.
     * @throws ResourceNotFoundException If no user is found with the given ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws ResourceNotFoundException {
        log.info("DELETE /api/users/{} called", id);
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}