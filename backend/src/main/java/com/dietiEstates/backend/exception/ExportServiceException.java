
package com.dietiEstates.backend.exception;

import com.dietiEstates.backend.enums.ExportingFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class ExportServiceException extends RuntimeException
{
    private final ExportingFormat exportingFormat;
    private final Long agentId;


    public ExportServiceException(String msg)
    {
        this(msg, null, null, null);
    }
    
    public ExportServiceException(String msg, Throwable cause)
    {
        this(msg, null, null, cause);
    }

    public ExportServiceException(ExportingFormat exportingFormat, Long agentId) 
    {
        this(null, exportingFormat, agentId, null);
    }

    public ExportServiceException(ExportingFormat exportingFormat, Long agentId, Throwable cause) 
    {
        this(null, exportingFormat, agentId, cause);
    }

    public ExportServiceException(String msg, ExportingFormat exportingFormat, Long agentId) 
    {
        this(msg, exportingFormat, agentId, null);
    }

    public ExportServiceException(String msg, ExportingFormat exportingFormat, Long agentId, Throwable cause) 
    {
        super(msg, cause);
        this.exportingFormat = exportingFormat;
        this.agentId = agentId;
    }


    @Override
    public String getMessage() 
    {
        String baseMessage = super.getMessage();
        StringBuilder fullMessage = new StringBuilder();
        
        if (baseMessage != null && !baseMessage.isEmpty()) 
            fullMessage.append(baseMessage);
        else 
            fullMessage.append("Errore durante l'esportazione del report!");

        
        if (exportingFormat != null) 
            fullMessage.append("\nExporting format: ").append(exportingFormat.getMimeType());
        
        if (agentId != null)
            fullMessage.append("\nAgent id: ").append(agentId);
    

        return fullMessage.toString();
    }
}