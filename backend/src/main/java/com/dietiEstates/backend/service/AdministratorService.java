
package com.dietiEstates.backend.service;

import java.util.Optional;

import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dietiEstates.backend.dto.request.CollaboratorRegistrationDTO;
import com.dietiEstates.backend.dto.request.AdminRegistrationDTO;
import com.dietiEstates.backend.dto.request.AgentRegistrationDTO;
import com.dietiEstates.backend.dto.request.UpdatePasswordDTO;
import com.dietiEstates.backend.model.entity.Administrator;
import com.dietiEstates.backend.model.entity.Agency;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.repository.AdministratorRepository;
import com.dietiEstates.backend.repository.AgentRepository;
import com.dietiEstates.backend.service.mail.UserWelcomeEmailService;
import com.dietiEstates.backend.service.mock.MockingStatsService;
import com.dietiEstates.backend.util.PasswordGeneratorUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class AdministratorService 
{
    private final AdministratorRepository administratorRepository;
    private final AgentRepository agentRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    //private final ValidationUtil validationUtil;
    private final MockingStatsService mockingStatsService;

    @Autowired
    @Qualifier("AgentWelcomeEmailService")
    private UserWelcomeEmailService userWelcomeEmailService;
    


    @Transactional
    public void createCollaborator(String username, CollaboratorRegistrationDTO collaboratorRegistrationDTO) throws UsernameNotFoundException, 
                                                                                    IllegalArgumentException, MappingException
    {
        Optional<Administrator> adminOptional = administratorRepository.findByUsername(username);
        if(adminOptional.isEmpty())
        {
            log.error("Admin not found in database");
            throw new UsernameNotFoundException("Admin not found in database");
        }
        Administrator admin = adminOptional.get();

        if(administratorRepository.findByUsername(collaboratorRegistrationDTO.getUsername()).isPresent())
        {
            log.error("This username is already present!");
            throw new IllegalArgumentException("This username is already present!");
        }

        Administrator collaborator;
        try 
        {
            collaborator = modelMapper.map(collaboratorRegistrationDTO, Administrator.class);
        } 
        catch (MappingException e) 
        {
            log.error("Problems while mapping! Probably the source object was different than the one expected!");
            throw e;
        }

        String plainTextPassword = PasswordGeneratorUtil.generateRandomPassword();
        String hashedPassword = passwordEncoder.encode(plainTextPassword);

        collaborator.setPassword(hashedPassword);
        
        admin.addCollaborator(collaborator);
        admin = administratorRepository.save(admin);

        log.info("Collaborator was created successfully!");
    }


    @Transactional
    public void createAgent(String username, AgentRegistrationDTO agentRegistrationDTO) throws UsernameNotFoundException, 
                                                                                          IllegalArgumentException, MappingException
    {
        Optional<Administrator> administratorOptional = administratorRepository.findByUsername(username);
        if(administratorOptional.isEmpty())
        {
            log.error("Admin not found in database");
            throw new UsernameNotFoundException("Admin not found in database");
        }
        Administrator administrator = administratorOptional.get();

        if(agentRepository.findByUsername(agentRegistrationDTO.getUsername()).isPresent())
        {
            log.error("This username is already present!");
            throw new IllegalArgumentException("This username is already present!");
        }

        Agent agent;
        try 
        {
            agent = modelMapper.map(agentRegistrationDTO, Agent.class);
        } 
        catch (MappingException e) 
        {
            log.error("Problems while mapping! Probably the source object was different than the one expected!");
            throw e;
        }

        String plainTextPassword = PasswordGeneratorUtil.generateRandomPassword();
        String hashedPassword = passwordEncoder.encode(plainTextPassword);

        agent.setPassword(hashedPassword);

        mockingStatsService.mockAgentStats(agent);

        administrator.addAgent(agent);
        administrator = administratorRepository.save(administrator);

        userWelcomeEmailService.sendWelcomeEmail(agent);

        log.info("Real Estate Agent was created successfully!");
    }


    public void updatePassword(String username, UpdatePasswordDTO updatePasswordDTO) throws UsernameNotFoundException, 
                                                                                            IllegalArgumentException, MappingException
    {
        Optional<Administrator> administratorOptional = administratorRepository.findByUsername(username);
        if(administratorOptional.isEmpty())
        {
            log.error("Admin not found in database");
            throw new UsernameNotFoundException("Admin not found in database");
        }

        if(!(passwordEncoder.matches(updatePasswordDTO.getOldPassword(), administratorOptional.get().getPassword())))
        {
            log.error("The \"old password\" you have inserted do not correspond to your current password");
            throw new IllegalArgumentException("The \"old password\" you have inserted do not correspond to your current password");
        }

        if((passwordEncoder.matches(updatePasswordDTO.getNewPassword(), administratorOptional.get().getPassword())))
        {
            log.error("The \"new password\" you have inserted can't be equal to your current password");
            throw new IllegalArgumentException("\"The \"new password\" you have inserted can't be equal to your current password");
        }
        

        Administrator administrator = administratorOptional.get();
        administrator.setPassword(passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
        administrator = administratorRepository.save(administrator);

        log.info("Password was updated successfully!");
    }
}
