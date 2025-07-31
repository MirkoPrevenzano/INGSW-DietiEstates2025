
package com.dietiEstates.backend.exception;


public class ExportReportException extends RuntimeException
{
    public ExportReportException(String msg)
    {
        super(msg);
    }
    
    public ExportReportException(String msg, Throwable throwable)
    {
        super(msg, throwable);
    }
}