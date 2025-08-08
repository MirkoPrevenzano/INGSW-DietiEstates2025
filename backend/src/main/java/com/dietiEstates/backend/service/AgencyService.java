
package com.dietiEstates.backend.service;

import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dietiEstates.backend.dto.request.CollaboratorRegistrationDTO;
import com.dietiEstates.backend.dto.request.AdminRegistrationDTO;
import com.dietiEstates.backend.dto.request.AgentRegistrationDTO;
import com.dietiEstates.backend.exception.EmailServiceException;
import com.dietiEstates.backend.model.entity.Administrator;
import com.dietiEstates.backend.model.entity.Agency;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.repository.AdministratorRepository;
import com.dietiEstates.backend.repository.AgencyRepository;
import com.dietiEstates.backend.repository.AgentRepository;
import com.dietiEstates.backend.service.mail.AgentWelcomeEmailService;
import com.dietiEstates.backend.service.mail.CollaboratorWelcomeEmailService;
import com.dietiEstates.backend.service.mock.MockingStatsService;
import com.dietiEstates.backend.util.PasswordGeneratorUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class AgencyService 
{
    private final AdministratorRepository administratorRepository;
    private final AgentRepository agentRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final MockingStatsService mockingStatsService;
    private final AgentWelcomeEmailService agentWelcomeEmailService;
    private final CollaboratorWelcomeEmailService collaboratorWelcomeEmailService;
    private final AgencyRepository agencyRepository;
    

    @Transactional
    public void createAgency(String username, AdminRegistrationDTO adminRegistrationDTO) 
    {
        if(agencyRepository.findByBusinessNameOrVatNumber(adminRegistrationDTO.getBusinessName(), adminRegistrationDTO.getVatNumber()).isPresent())
        {
            log.error("This agency is already present!");
            throw new IllegalArgumentException("This agency is already present!");
        }

        Agency agency = modelMapper.map(adminRegistrationDTO, Agency.class);
        Administrator admin = modelMapper.map(adminRegistrationDTO, Administrator.class);
        
        String hashedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(hashedPassword);
        
        agency.addAdministrator(admin);

        log.info("Agency was created successfully!");
    }
}