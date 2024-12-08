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

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    // Get all users
    @GetMapping
    public List<UserDTO> getAllUsers() {
        log.info("GET /api/users called");
        return userService.getAllUsers();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) throws ResourceNotFoundException {
        log.info("GET /api/users/{} called", id);
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRequestDTO userRequest) {
        log.info("POST /api/users called");
        if (userService.userRepository.existsByUsername(userRequest.getUsername())) {
            log.warn("Username {} is already taken", userRequest.getUsername());
            return ResponseEntity.badRequest().body("Username is already taken");
        }
        if (userService.userRepository.existsByEmail(userRequest.getEmail())) {
            log.warn("Email {} is already in use", userRequest.getEmail());
            return ResponseEntity.badRequest().body("Email is already in use");
        }
        UserDTO createdUser = userService.createUser(userRequest);
        return ResponseEntity.ok(createdUser);
    }

    // Update a user
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequest) throws ResourceNotFoundException {
        log.info("PUT /api/users/{} called", id);
        UserDTO updatedUser = userService.updateUser(id, userRequest);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) throws ResourceNotFoundException {
        log.info("DELETE /api/users/{} called", id);
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}