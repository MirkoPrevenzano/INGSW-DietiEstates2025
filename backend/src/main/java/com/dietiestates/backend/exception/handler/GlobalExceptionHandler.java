
package com.dietiestates.backend.exception.handler;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ElementKind;
import jakarta.validation.Path;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dietiestates.backend.exception.ExportServiceException;
import com.dietiestates.backend.exception.FileStorageServiceException;
import com.fasterxml.jackson.databind.exc.ValueInstantiationException;

import lombok.extern.slf4j.Slf4j;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
{
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) 
    {
        logExceptionInfo(ex);

        //String errorPath = request.getDescription(false);
        String errorDetail = "Errore nella richiesta! " +
                             "Il metodo HTTP '" +  ex.getMethod() + "' non è supportato. " +  
                             "I metodi supportati sono: {" + ex.getSupportedHttpMethods() + "}";

        //ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.valueOf(status.value()), errorDetail, errorPath);
        
/*         return ResponseEntity.status(errorResponse.getStatus())
                             .body(errorResponse);   */    
                             
        return buildResponseEntity(HttpStatus.valueOf(status.value()), errorDetail, null, headers, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) 
    {
        logExceptionInfo(ex);

        // String errorPath = request.getDescription(false);
        String errorDetail = "Errore nella richiesta! " + 
                             "Il content media type '" +  ex.getContentType() + "' non è supportato. " + 
                             "I content supportati sono: {" + ex.getSupportedMediaTypes() + "}";

        //ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.valueOf(status.value()), errorDetail, errorPath);
        
        return buildResponseEntity(HttpStatus.valueOf(status.value()), errorDetail, null, headers, request);

/*         return ResponseEntity.status(errorResponse.getStatus())
                             .body(errorResponse);  */       
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) 
    {
        logExceptionInfo(ex);

        //String errorPath = request.getDescription(false);
        String errorDetail = "Errore nella richiesta! " + 
                             "Il content media type non è in una rappresentazione accettabile. " + 
                             "I content supportati sono: {" + ex.getSupportedMediaTypes() + "}";

        //ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.valueOf(status.value()), errorDetail, errorPath);
        
        //HttpHeaders responseHeaders = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        return buildResponseEntity(HttpStatus.valueOf(status.value()), errorDetail, null, headers, request);

/*         return ResponseEntity.status(errorResponse.getStatus())
                             .headers(responseHeaders)
                             .body(errorResponse); */
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) 
    {
        logExceptionInfo(ex);

        List<String> subErrors = new ArrayList<>();

        ex.getBindingResult()
          .getAllErrors()
          .forEach(error -> { String fieldName;
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
                                
                                subErrors.add(fieldName + ": " + message);
                            });

        //String errorPath = request.getDescription(false);
        String errorDetail = "Errore durante la validazione dei dati!";

        //ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.valueOf(status.value()), errorDetail, errorPath, subErrors);
        
        return buildResponseEntity(HttpStatus.valueOf(status.value()), errorDetail, subErrors, headers, request);

/*         return ResponseEntity.status(errorResponse.getStatus())
                             .body(errorResponse); */
    }

    @Override
    public ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) 
    {
        logExceptionInfo(ex);

        //String errorPath = request.getDescription(false);
        String errorDetail = "Errore nella richiesta! " + 
                             "La variabile URI obbligatoria '" + ex.getVariableName()  + "' " + 
                             "di tipo '" + ex.getParameter().getNestedParameterType().getSimpleName() + "' non è presente.";

        //ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.valueOf(status.value()), errorDetail, errorPath);
       
        return buildResponseEntity(HttpStatus.valueOf(status.value()), errorDetail, null, headers, request);

/*         return ResponseEntity.status(errorResponse.getStatus())
                             .body(errorResponse); */
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) 
    {
        logExceptionInfo(ex);
        
        //String errorPath = request.getDescription(false);
        String errorDetail = "Errore nella richiesta! " +
                             "Il parametro URI obbligatorio '" + ex.getParameterName() + "' " + 
                             "di tipo '" + ex.getParameterType() + "' non è presente.";

        //ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.valueOf(status.value()), errorDetail, errorPath);
        
        return buildResponseEntity(HttpStatus.valueOf(status.value()), errorDetail, null, headers, request);

/*         return ResponseEntity.status(errorResponse.getStatus())
                             .body(errorResponse);   */  
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) 
    {
        logExceptionInfo(ex);

        //String errorPath = request.getDescription(false);        
        String errorDetail = "Errore nella richiesta! " +  
                             "La parte obbligatoria '" + ex.getRequestPartName() + "' non è presente.";

        //ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.valueOf(status.value()), errorDetail, errorPath);
        
        return buildResponseEntity(HttpStatus.valueOf(status.value()), errorDetail, null, headers, request);

/*         return ResponseEntity.status(errorResponse.getStatus())
                             .body(errorResponse); */
    }

    @Override
    protected ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) 
    {
        logExceptionInfo(ex);

        //String errorPath = request.getDescription(false);
        String errorDetail = "Errore nella richiesta! Superata la soglia massima di dimensione di upload.";

        //ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.valueOf(status.value()), errorDetail, errorPath);
        
        return buildResponseEntity(HttpStatus.valueOf(status.value()), errorDetail, null, headers, request);

/*         return ResponseEntity.status(errorResponse.getStatus())
                             .body(errorResponse);    */             
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) 
    {
        logExceptionInfo(ex);

        //String errorPath = request.getDescription(false);
        String errorDetail = "Errore nella richiesta! " + 
                                  "Fallimento nel convertire il valore '" + ex.getValue() + "' " + 
                                  "del parametro '" + ex.getPropertyName() + "' " + 
                                  "nel tipo richiesto '" + ex.getRequiredType().getSimpleName() +  "'";

        //ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.valueOf(status.value()), errorDescription, errorPath);

        return buildResponseEntity(HttpStatus.valueOf(status.value()), errorDetail, null, headers, request);

/*         return ResponseEntity.status(errorResponse.getStatus())
                             .body(errorResponse); */
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) 
    {
        logExceptionInfo(ex);
        
        if (ex.getCause() instanceof ValueInstantiationException) 
        {
            //String errorPath = request.getDescription(false);
            String errorDetail = "Errore durante la deserializzazione dei dati JSON! ";

            ValueInstantiationException vie = (ValueInstantiationException) ex.getCause();
            errorDetail += vie.getCause().getMessage();

            return buildResponseEntity(HttpStatus.BAD_REQUEST, errorDetail, null, headers, request);

            /*             ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, errorDescription, errorPath);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(errorResponse); */
        }
        else
            return super.handleHttpMessageNotReadable(ex, headers, status, request);

    }


    @ExceptionHandler(ExportServiceException.class)
    public ResponseEntity<Object> handleExportServiceException(ExportServiceException ex, WebRequest request) 
    {
        logExceptionInfo(ex);
        
        //String errorPath = request.getDescription(false);
        String errorDetail = ex.getMessage();

        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, errorDetail, null, null, request);

/*         ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorDescription, errorPath);

        return ResponseEntity.status(errorResponse.getStatus())
                             .body(errorResponse); */
    }

    @ExceptionHandler(FileStorageServiceException.class)
    public ResponseEntity<Object> handleFileStorageServiceException(FileStorageServiceException ex, WebRequest request) 
    {
        logExceptionInfo(ex);
        
        //String errorPath = request.getDescription(false);
        String errorDetail = ex.getMessage();

        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, errorDetail, null, null, request);

/*         ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorDescription, errorPath);

        return ResponseEntity.status(errorResponse.getStatus())
                             .body(errorResponse); */
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) 
    {
        logExceptionInfo(ex);
        
        //String errorPath = request.getDescription(false);
        String errorDetail = ex.getMessage();

        return buildResponseEntity(HttpStatus.BAD_REQUEST, errorDetail, null, null, request);

/*         ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorDescription, errorPath);

        return ResponseEntity.status(errorResponse.getStatus())
                             .body(errorResponse); */
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception ex, WebRequest request) 
    {
        logExceptionInfo(ex);
        
        //String errorPath = request.getDescription(false);
        String errorDetail = "Errore interno non gestito.";

        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, errorDetail, null, null, request);

/*         ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorDescription, errorPath);

        return ResponseEntity.status(errorResponse.getStatus())
                             .body(errorResponse); */
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) 
    {
        logExceptionInfo(ex);

        if (isViolationFromEntity(ex))
        {
            //String errorPath = request.getDescription(false);
            String errorDetail = "Errore durante il salvataggio dei dati!";

            return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, errorDetail, null, null, request);

/*             ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorDescription, errorPath);
            
            return ResponseEntity.status(errorResponse.getStatus())
                                 .body(errorResponse); */
        }
        else
        {
            List<String> subErrors = new ArrayList<>();

            ex.getConstraintViolations()
             .forEach(cv -> { String parameterName;
                              String parameterPath = cv.getPropertyPath().toString(); 

                              int lastDot = parameterPath.lastIndexOf('.');
                              if (lastDot != -1) 
                                parameterName = parameterPath.substring(lastDot + 1); 
                              else
                                parameterName = parameterPath;

                              String message = cv.getMessage();
                
                              subErrors.add(parameterName + ": " + message);
                            });


            //String errorPath = request.getDescription(false);           
            String errorDetail = "Errore durante la validazione dei dati!";
            return buildResponseEntity(HttpStatus.BAD_REQUEST, errorDetail, subErrors, null, request);

            
/*             ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST, errorDetail, errorPath, subErrors);
            
            return ResponseEntity.status(errorResponse.getStatus())
                                 .body(errorResponse); */
        }
    }


    private boolean isViolationFromEntity(ConstraintViolationException e) 
    {
        ConstraintViolation<?> firstViolation = e.getConstraintViolations().iterator().next();
        Path.Node firstNode = firstViolation.getPropertyPath().iterator().next();
        ElementKind kind = firstNode.getKind();
           
        return kind == ElementKind.PROPERTY ? true : false;
    }

    private void logExceptionInfo(Exception ex)
    {
        log.error("Exception occurred: " + ex.getClass().getSimpleName());
        log.error("Exception message: " + ex.getMessage());

        if (ex.getCause() != null)
            log.error("Exception cause: " + ex.getCause().getMessage());
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus httpStatus, String errorDetail, List<String> subErrors, HttpHeaders headers, WebRequest request) 
    {
        String errorPath = request.getDescription(false);

        ApiErrorResponse apiErrorResponse;

        if (subErrors != null && subErrors.size() > 1)
            apiErrorResponse = new ApiErrorResponse(httpStatus, errorDetail, errorPath, subErrors);
        else if (subErrors != null && subErrors.size() == 1)
            apiErrorResponse = new ApiErrorResponse(httpStatus, errorDetail, errorPath, subErrors.get(0));
        else 
            apiErrorResponse = new ApiErrorResponse(httpStatus, errorDetail, errorPath);


        if (headers != null)
        {
            return ResponseEntity.status(apiErrorResponse.getStatus())
                                 .headers(headers)
                                 .body(apiErrorResponse);
        }
        
        return ResponseEntity.status(apiErrorResponse.getStatus())
                             .body(apiErrorResponse);
    }
}