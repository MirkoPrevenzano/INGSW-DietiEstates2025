
package com.dietiEstates.backend.dto.response;

import java.time.LocalDateTime;

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
    private String error;

    @NonNull
    private String message;

    @NonNull
    private String path;

    @NonNull
    private LocalDateTime timestamp;
}