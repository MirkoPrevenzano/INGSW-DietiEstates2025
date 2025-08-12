
package com.dietiEstates.backend.enums;


public enum ExportingFormat 
{
    PDF("application/pdf"),
    CSV("text/csv");


    private final String mimeType;


    private ExportingFormat(String mimeType)
    {
        this.mimeType = mimeType;
    }


    public String getMimeType()
    {
        return this.mimeType;
    }
}