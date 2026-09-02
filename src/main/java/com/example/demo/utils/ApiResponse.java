package com.example.demo.utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL) 
public class ApiResponse<T> {

    private Integer status;     
    private String message;    
    private T data;           

  
    public static <T> ApiResponse<T> success(Integer status, String message, T data) {
        return ApiResponse.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }

   
    public static <T> ApiResponse<T> error(Integer status, String message) {
        return ApiResponse.<T>builder()
                .status(status)
                .message(message)
                .data(null)
                .build();
    }
}
