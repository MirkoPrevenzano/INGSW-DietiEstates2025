
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
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
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
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) 
    {
        log.error("Exception occurred: " + ex.getClass().getSimpleName());
        log.error("Message: " + ex.getMessage());


        List<String> errors = new ArrayList<>();

        ex.getBindingResult()
          .getAllErrors()
          .forEach((error) -> { String fieldName;
                                if (error instanceof FieldError)
                                {
                                    fieldName = ((FieldError) error).getField();

                                    int lastDot = fieldName.lastIndexOf('.');
                                    if (lastDot != -1) 
                                        fieldName = fieldName.substring(lastDot + 1);
                                }
                                else 
                                    fieldName = error.getObjectName();

                                String message = error.getDefaultMessage();
                                
                                errors.add(fieldName + ": " + message);});


        String errorMessage = "Errore durante la validazione dei dati!";
        String errorPath = request.getDescription(false);

        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, errorMessage, errorPath, errors);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(MappingException.class)
    public ResponseEntity<?> handleMappingExceptions(MappingException e) 
    {
        return ResponseEntity.badRequest().header("Error", 
        "Problems while mapping! Probably the source object was different than the one expected!").body(null);
    }




    @Override
    public ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) 
    {
        log.error("Exception occurred: " + ex.getClass().getSimpleName());
        log.error("Message: " + ex.getMessage());

        
        String errorMessage = "Errore nella richiesta! La variabile URI obbligatoria '" + ex.getVariableName()  + "' di tipo '" + ex.getParameter().getNestedParameterType().getSimpleName() + "' non è presente.";
        String errorPath = request.getDescription(false);

        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.valueOf(status.value()), errorMessage, errorPath);
        
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);

        //return super.handleMissingPathVariable(ex, headers, status, request);
    }


    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) 
    {
        log.error("Exception occurred: " + ex.getClass().getSimpleName());
        log.error("Message: " + ex.getMessage());

        
        String errorMessage = "Errore nella richiesta! Il parametro obbligatorio '" + ex.getParameterName() + "' di tipo '" + ex.getParameterType() + "' non è presente.";
        String errorPath = request.getDescription(false);

        ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.valueOf(status.value()), errorMessage, errorPath);
        
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    
        //return super.handleMissingServletRequestParameter(ex, headers, status, request);
    }


    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) 
    {
        return super.handleMissingServletRequestPart(ex, headers, status, request);
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

/*     @ExceptionHandler({ ConstraintViolationException.class })
public ResponseEntity<Object> handleConstraintViolation(
  ConstraintViolationException ex, WebRequest request) {
    List<String> errors = new ArrayList<String>();
    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
        errors.add(violation.getRootBeanClass().getName() + " " + 
          violation.getPropertyPath() + ": " + violation.getMessage());
    }

    ApiError apiError = 
      new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
    return new ResponseEntity<Object>(
      apiError, new HttpHeaders(), apiError.getStatus());
} */


    private boolean isViolationFromEntity(ConstraintViolationException e) 
    {
        //e.getConstraintViolations().iterator().next().getPropertyPath().iterator().next().getKind().equals(ElementKind.PROPERTY)
        ConstraintViolation<?> firstViolation = e.getConstraintViolations().iterator().next();
        Path.Node firstNode = firstViolation.getPropertyPath().iterator().next();
        ElementKind kind = firstNode.getKind();
           
        return kind == ElementKind.PROPERTY ? true : false;
    }
}