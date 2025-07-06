
package com.dietiEstates.backend.exception;

import java.lang.annotation.ElementType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.modelmapper.MappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dietiEstates.backend.dto.response.ApiErrorResponse;
import com.dietiEstates.backend.enums.EnergyClass;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ElementKind;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler 
{
    // TODO: creare INTERNAL ERROR handler

    
    @ExceptionHandler(MappingException.class)
    public ResponseEntity<?> handleMappingExceptions(MappingException e) 
    {
        return ResponseEntity.badRequest().header("Error", 
        "Problems while mapping! Probably the source object was different than the one expected!").body(null);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpServletRequest request) 
    {
        log.error("HttpMessageNotReadableException!");
        log.error(e.getMessage());


        int statusCode = HttpStatus.BAD_REQUEST.value();
        String errorReason = HttpStatus.BAD_REQUEST.getReasonPhrase();
        String errorType = HttpStatus.BAD_REQUEST.series().name();
        String errorDescription = "Errore durante la deserializzazione JSON! ";
        String errorPath = request.getRequestURI();
        
        if (e.getCause() instanceof ValueInstantiationException) {
            ValueInstantiationException vie = (ValueInstantiationException) e.getCause();
            errorDescription += "Valore '" + "' non valido per il campo che si aspetta un '" + ie.getTargetType().getSimpleName() + "'";
        }

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



    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationExceptions(ConstraintViolationException e, HttpServletRequest request) 
    {
        log.error("ConstraintViolationException!");
        log.error(e.getMessage());

        if (isViolationFromEntity(e))
        {
            int statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            String errorReason = HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
            String errorType = HttpStatus.INTERNAL_SERVER_ERROR.series().toString();
            String errorDescription = "Errore durante il salvataggio dei dati!";
            String errorPath = request.getRequestURI();
    
            ApiErrorResponse errorResponse = new ApiErrorResponse(statusCode, errorReason, errorType, errorDescription, errorPath);
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
        else
        {
            int statusCode = HttpStatus.BAD_REQUEST.value();
            String errorReason = HttpStatus.BAD_REQUEST.getReasonPhrase();
            String errorType = HttpStatus.BAD_REQUEST.series().toString();
            String errorDescription = "ERRORE";
            String errorPath = request.getRequestURI();
    
            ApiErrorResponse errorResponse = new ApiErrorResponse(statusCode, errorReason, errorType, errorDescription, errorPath);
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }


    private boolean isViolationFromEntity(ConstraintViolationException e) 
    {
        //e.getConstraintViolations().iterator().next().getPropertyPath().iterator().next().getKind().equals(ElementKind.PROPERTY)
        ConstraintViolation<?> firstViolation = e.getConstraintViolations().iterator().next();
        Path.Node firstNode = firstViolation.getPropertyPath().iterator().next();
        ElementKind kind = firstNode.getKind();
           
        return kind == ElementKind.PROPERTY ? true : false;
    }
}