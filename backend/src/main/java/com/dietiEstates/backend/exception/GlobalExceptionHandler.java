
package com.dietiEstates.backend.exception;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.modelmapper.MappingException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dietiEstates.backend.dto.response.ApiErrorResponse;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ElementKind;
import jakarta.validation.Path;
import lombok.extern.slf4j.Slf4j;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
    // TODO: creare INTERNAL ERROR handler


    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) 
    {
        List<String> errors = new ArrayList<>();

        log.error("MethodArgumentNotValidException" + ex.getClass().getSimpleName());

        log.error("ex.getMessage:  " + ex.getMessage());
        log.error("ex.getTypeMessageCode:  " + ex.getTypeMessageCode());
        log.error("ex.getTitleMessageCode:  " + ex.getTitleMessageCode());
        log.error("ex.getDetailMessageCode:  " + ex.getDetailMessageCode());

        ex.getBindingResult()
          .getAllErrors()
          .forEach((error) -> { String fieldName;
                                try 
                                {
                                    fieldName = ((FieldError) error).getField();
                                } 
                                catch (ClassCastException exx) 
                                {
                                    fieldName = error.getObjectName();
                                }

                                String message = error.getDefaultMessage();
                                
                                errors.add(fieldName + ": " + message);});


        String errorDescription = "ERRORE!";
        String errorPath = request.getContextPath();

        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, errorDescription, errorPath, errors);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(MappingException.class)
    public ResponseEntity<?> handleMappingExceptions(MappingException e) 
    {
        return ResponseEntity.badRequest().header("Error", 
        "Problems while mapping! Probably the source object was different than the one expected!").body(null);
    }


    @Override
    //@ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) 
    {
        log.error("HttpMessageNotReadableException!");
        log.error(e.getMessage());
        
        if (e.getCause() instanceof ValueInstantiationException) 
        {
            String errorDescription = "Errore durante la deserializzazione JSON! ";
            String errorPath = request.getDescription(false);

            ValueInstantiationException vie = (ValueInstantiationException) e.getCause();
            errorDescription += vie.getCause().getMessage();

            //ApiErrorResponse errorResponse = new ApiErrorResponse(statusCode, errorReason, errorType, errorDescription, errorPath);
            ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, errorDescription, errorPath);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        else
            return super.handleHttpMessageNotReadable(e, headers, status, request);

    }



    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationExceptions(ConstraintViolationException e, HttpServletRequest request) 
    {
        log.error("ConstraintViolationException!");
        log.error(e.getMessage());

        if (isViolationFromEntity(e))
        {
            String errorDescription = "Errore durante il salvataggio dei dati!";
            String errorPath = request.getRequestURI();
    
            ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorDescription, errorPath);
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
        else
        {
            String errorDescription = "ERRORE";
            String errorPath = request.getRequestURI();
    
            ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, errorDescription, errorPath);
            
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