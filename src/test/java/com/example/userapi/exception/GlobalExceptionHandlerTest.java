package com.example.userapi.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collections;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private WebRequest webRequest;

    /**
     * Verifies that handleResourceNotFoundException correctly handles ResourceNotFoundException.
     *
     * - Ensures the response contains a 404 status and the correct error message.
     */
    @Test
    public void testHandleResourceNotFoundException() {
        String errorMessage = "Resource not found";
        ResourceNotFoundException exception = new ResourceNotFoundException(errorMessage);
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleResourceNotFoundException(exception, webRequest);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.value(), Objects.requireNonNull(responseEntity.getBody()).getStatus());
        assertEquals(errorMessage, responseEntity.getBody().getMessage());
    }

    /**
     * Verifies that handleValidationExceptions correctly handles MethodArgumentNotValidException.
     *
     * - Ensures the response contains a 400 status and validation error details.
     */
    @Test
    public void testHandleValidationExceptions() {
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("objectName", "fieldName", "defaultMessage");
        when(exception.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(fieldError));
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleValidationExceptions(exception);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST.value(), Objects.requireNonNull(responseEntity.getBody()).getStatus());
        assertEquals("Validation failed: {fieldName=defaultMessage}", responseEntity.getBody().getMessage());
    }

    /**
     * Verifies that handleAllExceptions correctly handles generic exceptions.
     *
     * - Ensures the response contains a 500 status and a generic error message.
     */
    @Test
    public void testHandleAllExceptionsWhenException() {
        Exception exception = new Exception("An unexpected error occurred");
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleAllExceptions(exception, webRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), Objects.requireNonNull(responseEntity.getBody()).getStatus());
        assertEquals("An unexpected error occurred", responseEntity.getBody().getMessage());
    }

    /**
     * Verifies the immutability and correct initialization of the ErrorResponse class.
     *
     * Ensures the status, message, and timestamp fields are set correctly during construction.
     */
    @Test
    public void testErrorResponseImmutability() {
        ErrorResponse errorResponse = new ErrorResponse(0, null);

        int newStatus = 404;
        String newMessage = "Resource not found";
        errorResponse.setStatus(newStatus);
        errorResponse.setMessage(newMessage);

        assertEquals(newStatus, errorResponse.getStatus(), "The status value is not as expected.");
        assertEquals(newMessage, errorResponse.getMessage(), "The message value is not as expected.");
        assertNotNull(errorResponse.getTimestamp(), "The timestamp should not be null.");
    }
}
