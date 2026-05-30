package com.booking.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.booking.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleNotFound(
            ResourceNotFoundException ex) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder()
                                .message(ex.getMessage())
                                .status(404)
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @ExceptionHandler(
            ConflictException.class)
    public ResponseEntity<ErrorResponse>
    handleConflict(
            ConflictException ex) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse.builder()
                                .message(ex.getMessage())
                                .status(409)
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @ExceptionHandler(
            MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>
    handleValidation(
            MethodArgumentNotValidException ex) {

        String error =
                ex.getBindingResult()
                        .getFieldError()
                        .getDefaultMessage();

        return ResponseEntity
                .badRequest()
                .body(
                        ErrorResponse.builder()
                                .message(error)
                                .status(400)
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }

    @ExceptionHandler(
            Exception.class)
    public ResponseEntity<ErrorResponse>
    handleGeneric(
            Exception ex) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.builder()
                                .message(ex.getMessage())
                                .status(500)
                                .timestamp(LocalDateTime.now())
                                .build()
                );
    }
}
