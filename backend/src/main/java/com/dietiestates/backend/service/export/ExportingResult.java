
package com.dietiestates.backend.service.export;

import org.springframework.http.MediaType;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ExportingResult
{
    private final byte[] exportingBytes;
    private final String filename;
    private final MediaType contentType;
}