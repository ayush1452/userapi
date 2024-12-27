package com.example.userapi.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a user in the system.
 * This class is used to transfer user data between different layers of the application.
 */
@Setter
@Getter
public class UserDTO {
    /**
     * The unique identifier for the user.
     */
    private Long id;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The email address of the user.
     */
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
