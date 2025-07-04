
package com.dietiEstates.backend.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ApiErrorResponse 
{
    @NonNull
    private Integer status;

    @NonNull
    private String reason;    
    
    @NonNull
    private String type;

    @NonNull
    private String description;

    @NonNull
    private String path;

    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd', 'HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();
}