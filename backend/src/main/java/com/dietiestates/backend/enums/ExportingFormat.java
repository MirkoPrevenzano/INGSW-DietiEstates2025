
package com.dietiestates.backend.enums;

import org.springframework.http.MediaType;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum ExportingFormat 
{
    PDF(MediaType.APPLICATION_PDF, ".pdf"),

    CSV(MediaType.valueOf("text/csv"), ".csv");
    

    private final MediaType mediaType;
    
    private final String fileExtension;
}