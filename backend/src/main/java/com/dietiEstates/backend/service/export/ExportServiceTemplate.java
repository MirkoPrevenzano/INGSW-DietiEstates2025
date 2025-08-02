/* 
package com.dietiEstates.backend.service.export;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.dietiEstates.backend.dto.response.AgentDashboardRealEstateStatsDTO;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.repository.AgentRepository;
import com.dietiEstates.backend.repository.RealEstateRepository;
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
    private final RealEstateRepository realEstateRepository;


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
    
    protected List<AgentDashboardRealEstateStatsDTO> getAgentDashboardRealEstateStatsByAgent(Agent agent) 
    {
        return realEstateRepository.findAgentDashboardRealEstateStatsByAgent(agent.getUserId(), null);
    }
}
 */









package com.dietiEstates.backend.service.export;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dietiEstates.backend.dto.response.AgentDashboardRealEstateStatsDTO;
import com.dietiEstates.backend.exception.ExportServiceException;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.repository.AgentRepository;
import com.dietiEstates.backend.repository.RealEstateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public abstract class ExportServiceTemplate 
{
    private final AgentRepository agentRepository;
    private final RealEstateRepository realEstateRepository;


    public final ExportingResult exportReport(String username) 
    {
        try 
        {
            Agent agent = agentRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Agent not found"));
            
            

            Object writer = initializeWriter();            

            writeAgentInfo(agent, writer);
            writeSectionSeparator(writer);
            
            writeAgentStats(agent, writer);
            writeSectionSeparator(writer);
            
            writeRealEstateStats(agent, writer);
            writeSectionSeparator(writer);
            
            writeRealEstatePerMonthStats(agent, writer);
            
            byte[] data = finalizeWriter(writer);
            
            String filename = generateFileName(username) + getFileExtension();
            String contentType = getContentType();
            
            log.info("Report exported successfully for agent: {}", username);
            return new ExportingResult(data, filename, contentType);            
        } 
        catch (Exception e) 
        {
            log.error("Error exporting report for agent {}: {}", username, e.getMessage());
            throw new ExportServiceException("Failed to export report", e);
        }
    }
    
    
    protected abstract Object initializeWriter() throws Exception;
    protected abstract void writeAgentInfo(Agent agent, Object writer) throws Exception;
    protected abstract void writeAgentStats(Agent agent, Object writer) throws Exception;
    protected abstract void writeRealEstateStats(Agent agent, Object writer) throws Exception;
    protected abstract void writeRealEstatePerMonthStats(Agent agent, Object writer) throws Exception;
    protected abstract void writeSectionSeparator(Object writer) throws Exception;
    protected abstract byte[] finalizeWriter(Object writer) throws Exception;
    protected abstract String getContentType();
    protected abstract String getFileExtension();

    
    // Metodi comuni (hook methods)
    protected String generateFileName(String username) 
    {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        return username + "_" + currentDateTime;
    }
    
    protected List<AgentDashboardRealEstateStatsDTO> getAgentDashboardRealEstateStatsByAgent(Agent agent) 
    {
        return realEstateRepository.findAgentDashboardRealEstateStatsByAgent(agent.getUserId(), null);
    }
}