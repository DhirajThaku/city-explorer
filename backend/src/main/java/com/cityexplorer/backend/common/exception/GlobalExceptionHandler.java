
package com.cityexplorer.backend.common.exception;

import java.util.Map;

import jakarta.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {
        private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

        @ExceptionHandler(AuthenticationException.class)
        public ResponseEntity<Map<String, Object>> handleAuthenticationException(
                        AuthenticationException exception) {

                return ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .body(Map.of(
                                                "status", 401,
                                                "error", "Unauthorized",
                                                "message", "Invalid email or password"));
        }

        @ExceptionHandler(ConstraintViolationException.class)
        public ResponseEntity<Map<String, Object>> handleConstraintViolation(
                        ConstraintViolationException exception) {

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(Map.of(
                                                "status", 400,
                                                "error", "Bad Request",
                                                "message", exception.getMessage()));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<Map<String, Object>> handleValidationErrors(
                        MethodArgumentNotValidException exception) {

                String message = exception.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(error -> error.getDefaultMessage())
                                .findFirst()
                                .orElse("Invalid request");

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(Map.of(
                                                "status", 400,
                                                "error", "Bad Request",
                                                "message", message));
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<Map<String, Object>> handleIllegalArgument(
                        IllegalArgumentException exception) {

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(Map.of(
                                                "status", 400,
                                                "error", "Bad Request",
                                                "message", exception.getMessage()));
        }

        @ExceptionHandler(IllegalStateException.class)
        public ResponseEntity<Map<String, Object>> handleIllegalState(
                        IllegalStateException exception) {

                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body(Map.of(
                                                "status", 409,
                                                "error", "Conflict",
                                                "message", exception.getMessage()));
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(
                        DataIntegrityViolationException exception) {

                return ResponseEntity
                                .status(HttpStatus.CONFLICT)
                                .body(Map.of(
                                                "status", 409,
                                                "error", "Conflict",
                                                "message", "A record with the supplied unique value already exists"));
        }

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<Map<String, Object>> handleResourceNotFound(
                        ResourceNotFoundException exception) {

                return ResponseEntity
                                .status(HttpStatus.NOT_FOUND)
                                .body(Map.of(
                                                "status", 404,
                                                "error", "Not Found",
                                                "message", exception.getMessage()));
        }

        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<Map<String, Object>> handleInvalidJson(
                        HttpMessageNotReadableException exception) {

                return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(Map.of(
                                                "status", 400,
                                                "error", "Bad Request",
                                                "message",
                                                "Request body is missing, malformed, or contains an invalid value"));
        }

        // generic exception
        @ExceptionHandler(Exception.class)
        public ResponseEntity<Map<String, Object>> handleGeneralException(
                        Exception exception) {
                logger.error("Unexpected server error occurred", exception);
                return ResponseEntity
                                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(Map.of(
                                                "status", 500,
                                                "error", "Internal Server Error",
                                                "message", "An unexpected error occurred"));
        }

}
