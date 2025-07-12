
package com.dietiEstates.backend.service.support;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.repository.AgentRepository;
import com.dietiEstates.backend.service.AgentService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
public abstract class ExportServiceTemplate 
{
    protected final AgentRepository agentRepository;
    protected final AgentService agentService;


    // TEMPLATE METHOD - definisce il flusso standard
    public final void exportReport(String username, HttpServletResponse response) 
    {
        try 
        {
            Agent agent = agentRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Agent not found"));
            

            // Step 1: Setup response headers
            setupResponseHeaders(username, response);
            

            // Step 2: Initialize export format
            Object writer = initializeWriter(response);
            

            // Step 3: Write sections in order
            writeAgentInfo(agent, writer);
            writeSectionSeparator(writer);
            
            writeAgentStats(agent, writer);
            writeSectionSeparator(writer);
            
            writeRealEstateStats(agent, writer);
            writeSectionSeparator(writer);
            
            writeRealEstatePerMonthStats(agent, writer);
            
            
            // Step 4: Finalize and cleanup
            finalizeWriter(writer, response);
            
            log.info("Report exported successfully for agent: {}", username);
            
        } 
        catch (Exception e) 
        {
            handleExportError(e, response);
        }
    }


    // Metodi astratti da implementare nelle sottoclassi
    protected abstract void setupResponseHeaders(String username, HttpServletResponse response);
    protected abstract Object initializeWriter(HttpServletResponse response) throws Exception;
    protected abstract void writeAgentInfo(Agent agent, Object writer) throws Exception;
    protected abstract void writeAgentStats(Agent agent, Object writer) throws Exception;
    protected abstract void writeRealEstateStats(Agent agent, Object writer) throws Exception;
    protected abstract void writeRealEstatePerMonthStats(Agent agent, Object writer) throws Exception;
    protected abstract void writeSectionSeparator(Object writer) throws Exception;
    protected abstract void finalizeWriter(Object writer, HttpServletResponse response) throws Exception;
    protected abstract void handleExportError(Exception e, HttpServletResponse response);
    
    
    // Metodi comuni (hook methods)
    protected String generateFileName(String username) 
    {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        return username + "_" + currentDateTime;
    }
    
    protected boolean hasRealEstates(Agent agent) 
    {
        return agent.getRealEstates() != null && 
               !agent.getRealEstates().isEmpty();
    }
}
