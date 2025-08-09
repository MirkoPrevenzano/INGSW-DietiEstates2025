
package com.dietiEstates.backend.service;

import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dietiEstates.backend.dto.request.CollaboratorRegistrationDTO;
import com.dietiEstates.backend.dto.request.AgentRegistrationDTO;
import com.dietiEstates.backend.exception.EmailServiceException;
import com.dietiEstates.backend.model.entity.Administrator;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.repository.AdministratorRepository;
import com.dietiEstates.backend.repository.AgentRepository;
import com.dietiEstates.backend.service.mail.AgentWelcomeEmailService;
import com.dietiEstates.backend.service.mail.CollaboratorWelcomeEmailService;
import com.dietiEstates.backend.service.mock.MockingStatsService;
import com.dietiEstates.backend.util.PasswordGeneratorUtil;

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
    


    @Transactional
    public void createCollaborator(String username, CollaboratorRegistrationDTO collaboratorRegistrationDTO) throws UsernameNotFoundException, 
                                                                                    IllegalArgumentException, MappingException
    {
        Administrator administrator = administratorRepository.findByUsername(username)
                                                             .orElseThrow(() -> new UsernameNotFoundException(""));

        if(administratorRepository.findByUsername(collaboratorRegistrationDTO.getUsername()).isPresent())
        {
            log.error("This username is already present!");
            throw new IllegalArgumentException("This username is already present!");
        }

        Administrator collaborator = modelMapper.map(collaboratorRegistrationDTO, Administrator.class);
        
        String plainTextPassword = PasswordGeneratorUtil.generateRandomPassword();
        String hashedPassword = passwordEncoder.encode(plainTextPassword);

        collaborator.setPassword(hashedPassword);
        
        administrator.addCollaborator(collaborator);
        // admin = administratorRepository.save(admin);

        log.info("Collaborator was created successfully!");

        try 
        {
            collaboratorWelcomeEmailService.sendWelcomeEmail(collaborator);
        } 
        catch (EmailServiceException e) 
        {
            log.warn(e.getMessage());
        }
    }




    // TODO: DA RIMUOVERE PER REST API, mettere in agentservice

    @Transactional
    public void createAgent(String username, AgentRegistrationDTO agentRegistrationDTO) throws UsernameNotFoundException, 
                                                                                          IllegalArgumentException, MappingException
    {
        Administrator administrator = administratorRepository.findByUsername(username)
                                                             .orElseThrow(() -> new UsernameNotFoundException(""));

        if(agentRepository.findByUsername(agentRegistrationDTO.getUsername()).isPresent())
        {
            log.error("This username is already present!");
            throw new IllegalArgumentException("This username is already present!");
        }

        Agent agent = modelMapper.map(agentRegistrationDTO, Agent.class);

        String plainTextPassword = PasswordGeneratorUtil.generateRandomPassword();
        String hashedPassword = passwordEncoder.encode(plainTextPassword);

        agent.setPassword(hashedPassword);

        mockingStatsService.mockAgentStats(agent);

        administrator.addAgent(agent);
       // administrator = administratorRepository.save(administrator);

        log.info("Real Estate Agent was created successfully!");

        try 
        {
            agentWelcomeEmailService.sendWelcomeEmail(agent);
        } 
        catch (EmailServiceException e) 
        {
            log.warn(e.getMessage());
        }
    }
}