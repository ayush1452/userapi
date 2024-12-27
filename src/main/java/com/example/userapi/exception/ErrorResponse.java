package com.example.userapi.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


/**
 * Represents an error response containing status, message, and timestamp information.
 * This class is used to encapsulate error details for API responses.
 */
@Setter
@Getter
public class ErrorResponse {
    /** The HTTP status code of the error. */
    private int status;

    /** A descriptive message providing details about the error. */
    private String message;

    /** The timestamp when the error occurred. */
    private final LocalDateTime timestamp;

    /**
     * Constructs a new ErrorResponse with the specified status, message, and current timestamp.
     *
     * @param status  the HTTP status code of the error
     * @param message a descriptive message providing details about the error
     */
    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
