
package com.dietiEstates.backend.exception;


public class FileStorageServiceException extends RuntimeException
{
    public FileStorageServiceException(String msg)
    {
        super(msg);
    }
    
    public FileStorageServiceException(String msg, Throwable throwable)
    {
        super(msg, throwable);
    }
}