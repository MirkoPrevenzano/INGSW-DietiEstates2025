
package com.dietiEstates.backend.exception;


public class ExportServiceException extends RuntimeException
{
    public ExportServiceException(String msg)
    {
        super(msg);
    }
    
    public ExportServiceException(String msg, Throwable throwable)
    {
        super(msg, throwable);
    }
}