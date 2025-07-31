
package com.dietiEstates.backend.service.export;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class ExportingResult
{
    private final byte[] data;
    private final String filename;
    private final String contentType;
}