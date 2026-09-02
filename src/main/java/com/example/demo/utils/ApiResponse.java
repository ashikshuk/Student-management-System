package com.example.demo.utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) // Hides fields if they are null (e.g., skips data on errors)
public class ApiResponse<T> {

    private Integer status;        // e.g., 200, 400, 404, 500
    private String message;    // e.g., "Student added successfully", "Validation failed"
    private T data;            // Holds your actual payloads (List, Object, or null)

    // Quick helper for successful responses
    public static <T> ApiResponse<T> success(Integer status, String message, T data) {
        return ApiResponse.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }

    // Quick helper for error responses
    public static <T> ApiResponse<T> error(Integer status, String message) {
        return ApiResponse.<T>builder()
                .status(status)
                .message(message)
                .data(null)
                .build();
    }
}
