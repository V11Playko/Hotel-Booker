package com.playko.userservice.configuration;

import com.playko.userservice.service.exceptions.ClientNotFound;
import com.playko.userservice.service.exceptions.EmployeeNotFound;
import com.playko.userservice.service.exceptions.OwnerNotFound;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.playko.userservice.configuration.Constants.CLIENT_NOT_FOUND_EXCEPTION;
import static com.playko.userservice.configuration.Constants.EMPLOYEE_NOT_FOUND_EXCEPTION;
import static com.playko.userservice.configuration.Constants.OWNER_NOT_FOUND_EXCEPTION;
import static com.playko.userservice.configuration.Constants.RESPONSE_MESSAGE_KEY;

@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();

        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        }

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleBindExceptions(BindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(OwnerNotFound.class)
    public ResponseEntity<Map<String, String>> handleOwnerNotFoundException(
            OwnerNotFound ownerNotFound) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, OWNER_NOT_FOUND_EXCEPTION));
    }

    @ExceptionHandler(EmployeeNotFound.class)
    public ResponseEntity<Map<String, String>> handleEmployeeNotFoundException(
            EmployeeNotFound employeeNotFound) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, EMPLOYEE_NOT_FOUND_EXCEPTION));
    }

    @ExceptionHandler(ClientNotFound.class)
    public ResponseEntity<Map<String, String>> handleClientNotFoundException(
            ClientNotFound clientNotFound) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_MESSAGE_KEY, CLIENT_NOT_FOUND_EXCEPTION));
    }
}
