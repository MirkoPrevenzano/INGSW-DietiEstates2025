
package com.dietiEstates.backend.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ApiErrorResponse 
{
    private Integer status;
    private String reason;    
    private String type;
    private String description;
    private String path;

    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd', 'HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();



    public ApiErrorResponse(Integer status, String reason, String type, String description, String path) 
    {
        this.status = status;
        this.reason = reason;
        this.type = type;
        this.description = description;
        this.path = path;
    }
}