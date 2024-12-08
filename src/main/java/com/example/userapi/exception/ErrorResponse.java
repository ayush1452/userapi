package com.example.userapi.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
public class ErrorResponse {
    @Setter
    private int status;
    @Setter
    private String message;
    // timestamp
    private LocalDateTime timestamp;

    // Constructors
    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(int status, String message) {
        this();
        this.status = status;
        this.message = message;
    }

    // No setter for timestamp since it's set in the constructor
}
