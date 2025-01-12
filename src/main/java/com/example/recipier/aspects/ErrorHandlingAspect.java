package com.example.recipier.aspects;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class ErrorHandlingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandlingAspect.class);

    // Obsługa walidacji (kontrolery)
    @AfterThrowing(pointcut = "execution(* com.example.recipier.controller..*(..))", throwing = "ex")
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        logger.error("Validation error: {}", ex.getMessage());

        // Budowanie mapy błędów walidacji
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        // Odpowiedź dla klienta
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", "Validation failed");
        response.put("errors", errors);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Logowanie innych wyjątków w kontrolerach
    @AfterThrowing(pointcut = "execution(* com.example.recipier.controller..*(..))", throwing = "ex")
    public void handleControllerException(Exception ex) {
        logger.error("Exception in controller: {}", ex.getMessage(), ex);
    }

    // Logowanie wyjątków w serwisach
    @AfterThrowing(pointcut = "execution(* com.example.recipier.service..*(..))", throwing = "ex")
    public void handleServiceException(Exception ex) {
        logger.error("Exception in service: {}", ex.getMessage(), ex);
    }

    // Obsługa wyjątków biznesowych
    @AfterThrowing(pointcut = "execution(* com.example.recipier.service..*(..))", throwing = "ex")
    public ResponseEntity<Map<String, Object>> handleBusinessException(RuntimeException ex) {
        logger.error("Business exception: {}", ex.getMessage());

        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
