package com.health.healthassistant.exception;

import com.health.healthassistant.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(404)
                .body(new ApiResponse(ex.getMessage(), false));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(400)
                .body(new ApiResponse(ex.getMessage(), false));
    }
}