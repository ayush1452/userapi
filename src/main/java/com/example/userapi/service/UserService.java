package com.example.userapi.service;

import com.example.userapi.dto.UserDTO;
import com.example.userapi.dto.UserRequestDTO;
import com.example.userapi.exception.ResourceNotFoundException;
import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing user-related business logic.
 * Provides functionality for creating, reading, updating, and deleting users.
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Retrieves a list of all users.
     *
     * @return List of UserDTO representing all users.
     */
    public List<UserDTO> getAllUsers() {
        log.debug("Fetching all users");
        return userRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a user by their unique ID.
     *
     * @param id The ID of the user to retrieve.
     * @return UserDTO representing the user.
     * @throws ResourceNotFoundException if no user is found with the given ID.
     */
    public UserDTO getUserById(Long id) throws ResourceNotFoundException {
        log.debug("Fetching user with id {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with id {}", id);
                    return new ResourceNotFoundException("User not found with id " + id);
                });
        return convertToDTO(user);
    }

    /**
     * Creates a new user in the system.
     *
     * @param userRequest Data for the user to be created.
     * @return UserDTO representing the created user.
     */
    public UserDTO createUser(UserRequestDTO userRequest) {
        User user = new User();

        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());

        log.debug("Before saving user: id = {}", user.getId());

        User savedUser = userRepository.save(user);

        log.debug("After saving user: id = {}", savedUser.getId());

        return convertToDTO(savedUser);
    }

    /**
     * Updates an existing user's information.
     *
     * @param id          The ID of the user to update.
     * @param userRequest Updated user data.
     * @return UserDTO representing the updated user.
     * @throws ResourceNotFoundException if no user is found with the given ID.
     */
    public UserDTO updateUser(Long id, UserRequestDTO userRequest) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with id {}", id);
                    return new ResourceNotFoundException("User not found with id " + id);
                });

        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());

        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        User updatedUser = userRepository.save(user);
        return convertToDTO(updatedUser);
    }

    /**
     * Deletes a user by their unique ID.
     *
     * @param id The ID of the user to delete.
     * @throws ResourceNotFoundException if no user is found with the given ID.
     */
    public void deleteUser(Long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("User not found with id {}", id);
                    return new ResourceNotFoundException("User not found with id " + id);
                });
        userRepository.delete(user);
        log.debug("Deleted user with id {}", id);
    }

    /**
     * Converts a User entity to a UserDTO.
     *
     * @param user The User entity to convert.
     * @return UserDTO representation of the user.
     */
    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());

        return userDTO;
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

}