
package com.dietiestates.backend.service;

import java.util.List;

import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dietiestates.backend.dto.AgentStatsDto;
import com.dietiestates.backend.dto.request.AgentCreationDto;
import com.dietiestates.backend.dto.response.AgentDashboardPersonalStatsDto;
import com.dietiestates.backend.dto.response.AgentDashboardRealEstateStatsDto;
import com.dietiestates.backend.dto.response.AgentPublicInfoDto;
import com.dietiestates.backend.dto.response.AgentRecentRealEstateDto;
import com.dietiestates.backend.model.entity.Administrator;
import com.dietiestates.backend.model.entity.Agent;
import com.dietiestates.backend.repository.AdministratorRepository;
import com.dietiestates.backend.repository.AgentRepository;
import com.dietiestates.backend.repository.RealEstateRepository;
import com.dietiestates.backend.service.export.ExportingResult;
import com.dietiestates.backend.service.export.csv.CsvExportService;
import com.dietiestates.backend.service.export.pdf.PdfExportService;
import com.dietiestates.backend.service.mail.AgentWelcomeEmailService;
import com.dietiestates.backend.service.mail.RememberRandomPasswordEmailService;
import com.dietiestates.backend.service.mock.MockingStatsService;
import com.dietiestates.backend.util.PasswordGenerationUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class AgentService 
{
    private final AdministratorRepository administratorRepository;
    private final AgentRepository agentRepository;
    private final RealEstateRepository realEstateRepository;
    private final MockingStatsService mockingStatsService;
    private final ModelMapper modelMapper;
    private final PdfExportService pdfExportService;
    private final CsvExportService csvExportService;
    private final PasswordEncoder passwordEncoder;
    private final AgentWelcomeEmailService agentWelcomeEmailService;
    private final RememberRandomPasswordEmailService rememberRandomPasswordEmailService;


    @Transactional
    public void createAgent(String username, AgentCreationDto agentCreationDto) throws UsernameNotFoundException, 
                                                                                          IllegalArgumentException, MappingException
    {
        Administrator administrator = administratorRepository.findByUsername(username)
                                                             .orElseThrow(() -> new UsernameNotFoundException(""));

        if(agentRepository.findByUsername(agentCreationDto.getUsername()).isPresent())
        {
            log.error("This username is already present!");
            throw new IllegalArgumentException("This username is already present!");
        }

        Agent agent = modelMapper.map(agentCreationDto, Agent.class);

        String randomPassword = PasswordGenerationUtil.generateRandomPassword();
        String hashedPassword = passwordEncoder.encode(randomPassword);

        agent.setPassword(hashedPassword);

        mockingStatsService.mockAgentStats(agent);

        administrator.addAgent(agent);

        administratorRepository.flush();

        log.info("Real Estate Agent was created successfully!");        

        agentWelcomeEmailService.sendWelcomeEmail(agent);
        rememberRandomPasswordEmailService.sendRandomPasswordEmail(agent, randomPassword);
    }


    @Transactional
    public ExportingResult exportPdfReport(String username) 
    {
        Agent agent = agentRepository.findByUsername(username)
                                     .orElseThrow(() -> new UsernameNotFoundException(""));
                                     
        return pdfExportService.exportPdfReport(agent);
    }

    @Transactional
    public ExportingResult exportCsvReport(String username) 
    {
        Agent agent = agentRepository.findByUsername(username)
                                     .orElseThrow(() -> new UsernameNotFoundException(""));
                                     
        return csvExportService.exportCsvReport(agent);
    }

    @Transactional(readOnly = true)
    public List<AgentRecentRealEstateDto> getAgentRecentRealEstates(String username, Integer limit) 
    {
        Agent agent = agentRepository.findByUsername(username)
                                     .orElseThrow(() -> new UsernameNotFoundException(""));
                                     
        return realEstateRepository.findAgentRecentRealEstatesByAgentId(agent.getUserId(), limit);
    }


    @Transactional(readOnly = true)
    public List<AgentDashboardRealEstateStatsDto> getAgentDashboardRealEstateStats(String username, Pageable page) 
    {
        Agent agent = agentRepository.findByUsername(username)
                                     .orElseThrow(() -> new UsernameNotFoundException(""));

        return realEstateRepository.findAgentDashboardRealEstateStatsByAgentId(agent.getUserId(), page);
    }


    @Transactional(readOnly = true)
    public AgentDashboardPersonalStatsDto getAgentDashboardPersonalStats(String username) 
    {
        Agent agent = agentRepository.findByUsername(username)
                                     .orElseThrow(() -> new UsernameNotFoundException(""));

        Integer[] estatesPerMonth = mockingStatsService.mockBarChartStats(agent);
        AgentStatsDto agentStatsDto = modelMapper.map(agent.getAgentStats(), AgentStatsDto.class);

        return new AgentDashboardPersonalStatsDto(agentStatsDto, estatesPerMonth);        
    }

    @Transactional(readOnly = true)
    public AgentPublicInfoDto getAgentPublicInfo(String username) 
    {
        agentRepository.findByUsername(username)
                       .orElseThrow(() -> new UsernameNotFoundException(""));
                                     
        return agentRepository.findAgentPublicInfoByUsername(username);
    }
}