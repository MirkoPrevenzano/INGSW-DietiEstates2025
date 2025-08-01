
package com.dietiEstates.backend.exception;


public class EmailServiceException extends RuntimeException
{
    public EmailServiceException(String msg)
    {
        super(msg);
    }
    
    public EmailServiceException(String msg, Throwable throwable)
    {
        super(msg, throwable);
    }
}