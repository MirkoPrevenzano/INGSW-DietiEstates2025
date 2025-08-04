
package com.dietiEstates.backend.exception;


public class ChartServiceException extends RuntimeException 
{
    
    public ChartServiceException(String message) 
    {
        super(message);
    }
    
    public ChartServiceException(String message, Throwable cause) 
    {
        super(message, cause);
    }
}