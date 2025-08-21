
package com.dietiestates.backend.service.export;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import com.dietiestates.backend.dto.response.AgentDashboardRealEstateStatsDto;
import com.dietiestates.backend.enums.ExportingFormat;
import com.dietiestates.backend.exception.ExportServiceException;
import com.dietiestates.backend.model.entity.Agent;
import com.dietiestates.backend.repository.RealEstateRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public abstract class ExportServiceTemplate 
{
    private final RealEstateRepository realEstateRepository;


    public final ExportingResult exportReport(Agent agent) 
    {
        try 
        {
            Object writer = initializeWriter();            

            writeAgentInfo(agent, writer);
            writeSectionSeparator(writer);
            
            writeAgentStats(agent, writer);
            writeSectionSeparator(writer);
            
            writeRealEstateStats(agent, writer);
            writeSectionSeparator(writer);
            
            writeRealEstatePerMonthStats(agent, writer);
            
            byte[] data = finalizeWriter(writer);
            
            String filename = generateFileName(agent.getUsername()) + getFileExtension();
            MediaType contentType = getContentType();
            
            log.info("Report exported successfully for agent: {}", agent.getUsername());
            return new ExportingResult(data, filename, contentType);            
        } 
        catch (Exception e) 
        {
            log.error("Error exporting report for agent {}: {}", agent.getUsername(), e.getMessage());
            throw new ExportServiceException(getExportingFormat(), agent.getUserId(), e);
        }
    }
    
    
    protected abstract Object initializeWriter();
    protected abstract void writeAgentInfo(Agent agent, Object writer);
    protected abstract void writeAgentStats(Agent agent, Object writer);
    protected abstract void writeRealEstateStats(Agent agent, Object writer);
    protected abstract void writeRealEstatePerMonthStats(Agent agent, Object writer);
    protected abstract void writeSectionSeparator(Object writer);
    protected abstract byte[] finalizeWriter(Object writer);
    protected abstract ExportingFormat getExportingFormat();
    protected abstract MediaType getContentType();
    protected abstract String getFileExtension();

    
    // Metodi comuni (hook methods)
    protected String generateFileName(String username) 
    {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
        return username + "_" + currentDateTime;
    }
    
    protected List<AgentDashboardRealEstateStatsDto> getAgentDashboardRealEstateStatsByAgent(Agent agent) 
    {
        return realEstateRepository.findAgentDashboardRealEstateStatsByAgentId(agent.getUserId(), null);
    }
}