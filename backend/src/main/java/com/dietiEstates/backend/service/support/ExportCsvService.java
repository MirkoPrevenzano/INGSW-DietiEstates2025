
package com.dietiEstates.backend.service.support;

import org.springframework.stereotype.Service;

import com.dietiEstates.backend.repository.AgentRepository;
import com.dietiEstates.backend.service.AgentService;


@Service
public class ExportCsvService extends ExportTemplate 
{
     public ExportCsvService(AgentRepository agentRepository, AgentService agentService) 
    {
        super(agentRepository, agentService);
    }   
}