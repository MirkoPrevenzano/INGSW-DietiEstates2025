
package com.dietiestates.backend.exception;

import com.dietiestates.backend.enums.ChartType;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class ChartServiceException extends RuntimeException 
{
    private final ChartType chartType;
    private final Long agentId;

    
    public ChartServiceException(String msg) 
    {
        this(msg, null, null, null);
    }
    
    public ChartServiceException(String msg, Throwable cause) 
    {
        this(msg, null, null, cause);
    }

    public ChartServiceException(ChartType chartType, Long agentId) 
    {
        this(null, chartType, agentId, null);
    }

    public ChartServiceException(ChartType chartType, Long agentId, Throwable cause) 
    {
        this(null, chartType, agentId, cause);
    }

    public ChartServiceException(String msg, ChartType chartType, Long agentId) 
    {
        this(msg, chartType, agentId, null);
    }

    public ChartServiceException(String msg, ChartType chartType, Long agentId, Throwable cause) 
    {
        super(msg, cause);
        this.chartType = chartType;
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
            fullMessage.append("Errore durante la creazione dei chart!");

        
        if (chartType != null) 
            fullMessage.append("\nChart type: ").append(chartType.getValue());
        
        if (agentId != null)
            fullMessage.append("\nAgent id: ").append(agentId);
    

        return fullMessage.toString();
    }
}