package com.example.userapi.repository;

import com.example.userapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for performing database operations on the User entity.
 * Extends JpaRepository to provide CRUD methods.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their username.
     *
     * @param username The username to search for.
     * @return Optional containing the User if found, or empty if not.
     */
    Optional<User> findByUsername(String username);

    /**
     * Checks if a user with the given username exists.
     *
     * @param username The username to check.
     * @return True if a user with the username exists, false otherwise.
     */
    Boolean existsByUsername(String username);

    /**
     * Checks if a user with the given email exists.
     *
     * @param email The email to check.
     * @return True if a user with the email exists, false otherwise.
     */
    Boolean existsByEmail(String email);
}
