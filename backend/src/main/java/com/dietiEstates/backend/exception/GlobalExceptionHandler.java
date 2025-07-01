
package com.dietiEstates.backend.exception;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.dietiEstates.backend.dto.response.ApiErrorResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler 
{
    @ExceptionHandler(MappingException.class)
    public ResponseEntity<?> handleMappingExceptions(MappingException e) 
    {
        return ResponseEntity.badRequest().header("Error", 
        "Problems while mapping! Probably the source object was different than the one expected!").body(null);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationExceptions(ConstraintViolationException e, HttpServletRequest request) 
    {
        
        //log.info("constraintviolationexc to string : {}", e);
        log.info("constraintviolationexc  mess : {}", e.getMessage());
        log.info("constraintviolationexc  cause : {}", e.getCause());

        int statusCode = HttpStatus.BAD_REQUEST.value();
        String errorReason = HttpStatus.BAD_REQUEST.getReasonPhrase();
        String errorType = HttpStatus.BAD_REQUEST.series().toString();
        String errorDescription = "ERRORE!";
        String errorPath = request.getRequestURI();

        ApiErrorResponse errorResponse = new ApiErrorResponse(statusCode, errorReason, errorType, errorDescription, errorPath);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException e, HttpServletRequest request) 
    {
        StringBuilder strBuilder = new StringBuilder();

        
        e.getBindingResult().getAllErrors().forEach((error) -> {
        String fieldName;
        try {
            fieldName = ((FieldError) error).getField();

        } catch (ClassCastException ex) {
            fieldName = error.getObjectName();
        }
        String message = error.getDefaultMessage();
        strBuilder.append(String.format("%s: %s,  ", fieldName, message));
        });

        log.info("STR BUILDER : {}", strBuilder);
        log.info("\n\ne.message : {}", e.getMessage());

        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage()));
        
        log.info("MAP : {}", errors);

        int statusCode = HttpStatus.BAD_REQUEST.value();
        String errorReason = HttpStatus.BAD_REQUEST.getReasonPhrase();
        String errorType = HttpStatus.BAD_REQUEST.series().name();
        String errorDescription = "ERRORE!";
        String errorPath = request.getRequestURI();

        ApiErrorResponse errorResponse = new ApiErrorResponse(statusCode, errorReason, errorType, errorDescription, errorPath);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}