
package com.dietiEstates.backend.dto.response;

import java.time.LocalDateTime;

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
    private String reason;    
    private String type;
    private String description;
    private String path;

    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd, HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();



    public ApiErrorResponse(HttpStatus httpStatus, String description, String path) 
    {
        this.status = httpStatus.value();
        this.reason = httpStatus.getReasonPhrase();
        this.type = httpStatus.series().name();
        this.description = description;
        this.path = path;
    }
}