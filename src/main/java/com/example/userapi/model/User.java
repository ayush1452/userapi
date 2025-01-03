package com.example.userapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * Entity class representing the User table in the database.
 * Includes validation constraints for data integrity.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    /**
     * The unique identifier for the user.
     * Auto-generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE) // Exclude id from having a setter
    private Long id;

    /**
     * The username of the user.
     * Must be unique and non-blank.
     */
    @NotBlank(message = "Username is mandatory")
    @Size(max = 50)
    @Column(unique = true)
    private String username;

    /**
     * The password of the user.
     * Must be non-blank and at least 6 characters long.
     */
    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;

    /**
     * The email address of the user.
     * Must be unique, non-blank, and a valid email format.
     */
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;

    /**
     * The first name of the user.
     */
    private String firstName;

    /**
     * The last name of the user.
     */
    private String lastName;
}