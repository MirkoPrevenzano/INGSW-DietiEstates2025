
package com.dietiestates.backend.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
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
import com.dietiestates.backend.strategy.AdministratorLoadingStrategy;
import com.dietiestates.backend.strategy.AgentLoadingStrategy;
import com.dietiestates.backend.util.PasswordGenerationUtil;

import org.modelmapper.ModelMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AgentService 
{
    private final AgentRepository agentRepository;

    private final AgentLoadingStrategy agentLoadingStrategy;

    private final AdministratorRepository administratorRepository;

    private final AdministratorLoadingStrategy administratorLoadingStrategy;

    private final RealEstateRepository realEstateRepository;

    private final MockingStatsService mockingStatsService;

    private final ModelMapper modelMapper;

    private final PdfExportService pdfExportService;

    private final CsvExportService csvExportService;

    private final PasswordEncoder passwordEncoder;

    private final AgentWelcomeEmailService agentWelcomeEmailService;

    private final RememberRandomPasswordEmailService rememberRandomPasswordEmailService;



    @Transactional
    public void createAgent(String username, AgentCreationDto agentCreationDto)
    {
        Administrator administrator = (Administrator) administratorLoadingStrategy.loadUser(username);
        
        /* Administrator administrator = administratorRepository.findByUsername(username)
                                                             .orElseThrow(() -> new UsernameNotFoundException("Amministratore '" + username + "' non trovato nel DB!")); */ 

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


    public ExportingResult exportPdfReport(String username) 
    {
        Agent agent = (Agent) agentLoadingStrategy.loadUser(username);
                                     
        return pdfExportService.exportPdfReport(agent);
    }


    public ExportingResult exportCsvReport(String username) 
    {
        Agent agent = (Agent) agentLoadingStrategy.loadUser(username);
                                     
        return csvExportService.exportCsvReport(agent);
    }


    public List<AgentRecentRealEstateDto> getAgentRecentRealEstates(String username, Integer limit) 
    {
        Agent agent = (Agent) agentLoadingStrategy.loadUser(username);
                                     
        return realEstateRepository.findAgentRecentRealEstatesByAgentId(agent.getUserId(), limit);
    }


    public List<AgentDashboardRealEstateStatsDto> getAgentDashboardRealEstateStats(String username, Pageable page) 
    {
        Agent agent = (Agent) agentLoadingStrategy.loadUser(username);

        return realEstateRepository.findAgentDashboardRealEstateStatsByAgentId(agent.getUserId(), page);
    }


    public AgentDashboardPersonalStatsDto getAgentDashboardPersonalStats(String username) 
    {
        Agent agent = (Agent) agentLoadingStrategy.loadUser(username);

        Integer[] estatesPerMonth = mockingStatsService.mockBarChartStats(agent);
        AgentStatsDto agentStatsDto = modelMapper.map(agent.getAgentStats(), AgentStatsDto.class);

        return new AgentDashboardPersonalStatsDto(agentStatsDto, estatesPerMonth);        
    }


    public AgentPublicInfoDto getAgentPublicInfo(String username) 
    {
        agentLoadingStrategy.loadUser(username);
                                     
        return agentRepository.findAgentPublicInfoByUsername(username);
    }
}