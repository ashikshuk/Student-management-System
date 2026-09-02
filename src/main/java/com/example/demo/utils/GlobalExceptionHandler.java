package com.example.demo.utils;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 1. Handles Request Validation Errors (e.g., @NotBlank, @Min, @Positive)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiResponse<Map<String, String>> response = ApiResponse.success(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                errors // Nested validation map inside the data block
        );

        return ResponseEntity.badRequest().body(response);
    }

    /**
     * 2. Handles Database Unique Constraint Violations
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String rootMsg = ex.getRootCause() != null ? ex.getRootCause().getMessage() : ex.getMessage();
        String message = "A record with this unique value already exists.";

        // Optional: Parse the database message to make it friendlier
        if (rootMsg != null && rootMsg.toLowerCase().contains("email")) {
            message = "This email address is already registered.";
        } else if (rootMsg != null && rootMsg.toLowerCase().contains("username")) {
            message = "This username is already taken.";
        }

        ApiResponse<Void> response = ApiResponse.error(
                HttpStatus.CONFLICT.value(),
                message
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    /**
     * 3. Fallback for any other unexpected Runtime Exceptions (prevents leaking raw stack traces)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
        ApiResponse<Void> response = ApiResponse.error(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected server error occurred: " + ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
