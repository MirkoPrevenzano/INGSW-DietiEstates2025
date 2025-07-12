
package com.dietiEstates.backend.service.support;

import org.springframework.stereotype.Service;

import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.repository.AgentRepository;
import com.dietiEstates.backend.service.AgentService;

import jakarta.servlet.http.HttpServletResponse;


@Service
public class PdfExportService extends ExportServiceTemplate 
{
    public PdfExportService(AgentRepository agentRepository, AgentService agentService) 
    {
        super(agentRepository, agentService);
    }



    @Override
    protected Object initializeWriter(HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void setupResponseHeaders(String username, HttpServletResponse response) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void writeAgentInfo(Agent agent, Object writer) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void writeAgentStats(Agent agent, Object writer) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void writeRealEstatePerMonthStats(Agent agent, Object writer) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void writeRealEstateStats(Agent agent, Object writer) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void writeSectionSeparator(Object writer) throws Exception {
        // TODO Auto-generated method stub
        
    }

    
    @Override
    protected void finalizeWriter(Object writer, HttpServletResponse response) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void handleExportError(Exception e, HttpServletResponse response) {
        // TODO Auto-generated method stub
        
    }
}