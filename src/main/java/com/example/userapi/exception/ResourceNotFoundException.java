package com.example.userapi.exception;

/**
 * Exception thrown when a requested resource is not found.
 */
public class ResourceNotFoundException extends Exception {
    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method)
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
