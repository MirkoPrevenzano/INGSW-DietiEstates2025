
package com.dietiEstates.backend.service.support;

import org.springframework.stereotype.Service;

import com.dietiEstates.backend.repository.AgentRepository;
import com.dietiEstates.backend.service.AgentService;


@Service
public class ExportPdfService extends ExportTemplate 
{
    public ExportPdfService(AgentRepository agentRepository, AgentService agentService) 
    {
        super(agentRepository, agentService);
    }
}
