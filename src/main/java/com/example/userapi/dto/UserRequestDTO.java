package com.example.userapi.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestDTO {
    // Getters and Setters
    // username
    @NotBlank(message = "Username is mandatory")
    @Size(max = 50)
    private String username;

    // password
    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password should be at least 6 characters")
    private String password;

    // email
    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    // firstName
    private String firstName;
    // lastName
    private String lastName;

}
