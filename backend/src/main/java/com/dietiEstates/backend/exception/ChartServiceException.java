
package com.dietiEstates.backend.exception;

import com.dietiEstates.backend.enums.ChartType;

public class ChartServiceException extends RuntimeException 
{
    private final ChartType;
    
    public ChartServiceException(String message) 
    {
        super(message);
    }
    
    public ChartServiceException(String message, Throwable cause) 
    {
        super(message, cause);
    }
}