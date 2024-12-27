package com.example.userapi.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for user registration requests.
 * This class encapsulates the data required for creating or updating a user account.
 */
@Setter
@Getter
public class UserRequestDTO {
    /**
     * The username of the user.
     */
    @NotBlank(message = "Username is mandatory")
    @Size(max = 50)
    private String username;

    /**
     * The password for the user account.
     * */
    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;

    /**
     * The email address of the user.
     */
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
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
