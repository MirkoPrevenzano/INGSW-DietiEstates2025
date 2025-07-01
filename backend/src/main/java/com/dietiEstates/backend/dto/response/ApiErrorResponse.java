
package com.dietiEstates.backend.dto.response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Data
@NoArgsConstructor
public class ApiErrorResponse 
{
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd', 'HH:mm:ss");

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

    @NonNull
    private String timestamp;


    
    public ApiErrorResponse(@NonNull Integer status, @NonNull String reason, @NonNull String type, @NonNull String description, @NonNull String path) 
    {
        this.status = status;
        this.reason = reason;
        this.type = type;
        this.description = description;
        this.path = path;
        this.timestamp = LocalDateTime.now().format(FORMATTER);
    }
}