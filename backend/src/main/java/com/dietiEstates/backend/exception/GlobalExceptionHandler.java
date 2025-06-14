
package com.dietiEstates.backend.exception;

import org.modelmapper.MappingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler 
{
    @ExceptionHandler(exception = MappingException.class)
    public ResponseEntity<?> handleError(MappingException e) 
    {
        return ResponseEntity.badRequest().header("Error", 
        "Problems while mapping! Probably the source object was different than the one expected!").body(null);
    }
}
