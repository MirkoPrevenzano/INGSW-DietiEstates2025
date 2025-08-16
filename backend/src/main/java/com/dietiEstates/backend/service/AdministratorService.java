
package com.dietiEstates.backend.service;

import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dietiEstates.backend.dto.request.CollaboratorCreationDto;
import com.dietiEstates.backend.dto.request.AgentCreationDto;
import com.dietiEstates.backend.model.entity.Administrator;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.repository.AdministratorRepository;
import com.dietiEstates.backend.repository.AgentRepository;
import com.dietiEstates.backend.service.mail.AgentWelcomeEmailService;
import com.dietiEstates.backend.service.mail.CollaboratorWelcomeEmailService;
import com.dietiEstates.backend.service.mock.MockingStatsService;
import com.dietiEstates.backend.util.PasswordGenerationUtil;

import com.dietiEstates.backend.service.mail.RememberRandomPasswordEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AdministratorService 
{
    private final AdministratorRepository administratorRepository;
    private final AgentRepository agentRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final MockingStatsService mockingStatsService;
    private final AgentWelcomeEmailService agentWelcomeEmailService;
    private final CollaboratorWelcomeEmailService collaboratorWelcomeEmailService;
    private final RememberRandomPasswordEmailService randomPasswordEmailService;



    @Transactional
    public void createCollaborator(String username, CollaboratorCreationDto collaboratorCreationDto) throws UsernameNotFoundException, 
                                                                                    IllegalArgumentException, MappingException
    {
        Administrator administrator = administratorRepository.findByUsername(username)
                                                             .orElseThrow(() -> new UsernameNotFoundException(""));

        if(administratorRepository.findByUsername(collaboratorCreationDto.getUsername()).isPresent())
        {
            log.error("This username is already present!");
            throw new IllegalArgumentException("This username is already present!");
        }

        Administrator collaborator = modelMapper.map(collaboratorCreationDto, Administrator.class);
        
        String plainTextPassword = PasswordGenerationUtil.generateRandomPassword();
        collaborator.setPassword(plainTextPassword);

        collaboratorWelcomeEmailService.sendWelcomeEmail(collaborator); 
        randomPasswordEmailService.sendRandomPasswordEmail(collaborator);

        String hashedPassword = passwordEncoder.encode(plainTextPassword);
        collaborator.setPassword(hashedPassword);
        collaborator.setAgency(administrator.getAgency());
        
        administrator.addCollaborator(collaborator);

        log.info("Collaborator was created successfully!");
    }




    // TODO: DA RIMUOVERE PER REST API, mettere in agentservice

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

        String plainTextPassword = PasswordGenerationUtil.generateRandomPassword();
        agent.setPassword(plainTextPassword);

        agentWelcomeEmailService.sendWelcomeEmail(agent);
        randomPasswordEmailService.sendRandomPasswordEmail(agent);

        String hashedPassword = passwordEncoder.encode(plainTextPassword);
        agent.setPassword(hashedPassword);

        mockingStatsService.mockAgentStats(agent);

        administrator.addAgent(agent);

        log.info("Real Estate Agent was created successfully!");        
    }
}