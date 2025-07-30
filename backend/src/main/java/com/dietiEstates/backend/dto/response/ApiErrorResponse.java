
package com.dietiEstates.backend.dto.response;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ApiErrorResponse 
{
    private int status;
    private String title;    
    private String type;
    private String detail;
    private List<String> errors;
    private String path;

    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd, HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();


    public ApiErrorResponse(HttpStatus httpStatus, String detail, String path) 
    {
        this.status = httpStatus.value();
        this.title = httpStatus.getReasonPhrase();
        this.type = httpStatus.series().name();
        this.detail = detail;
        this.path = path;
    }

    public ApiErrorResponse(HttpStatus httpStatus, String detail, String path, List<String> errors) 
    {
        this(httpStatus, detail, path);
        this.errors = errors;
    }

    public ApiErrorResponse(HttpStatus httpStatus, String detail, String path, String error) 
    {
        this(httpStatus, detail, path);
        this.errors = Arrays.asList(error);
    }
}