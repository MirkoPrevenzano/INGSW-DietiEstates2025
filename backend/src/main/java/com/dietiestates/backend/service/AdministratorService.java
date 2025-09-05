
package com.dietiestates.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dietiestates.backend.dto.request.CollaboratorCreationDto;
import com.dietiestates.backend.model.entity.Administrator;
import com.dietiestates.backend.repository.AdministratorRepository;
import com.dietiestates.backend.service.mail.CollaboratorWelcomeEmailService;
import com.dietiestates.backend.service.mail.RememberRandomPasswordEmailService;
import com.dietiestates.backend.strategy.AdministratorLoadingStrategy;
import com.dietiestates.backend.util.PasswordGenerationUtil;

import org.modelmapper.ModelMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AdministratorService 
{
    private final AdministratorRepository administratorRepository;

    private final AdministratorLoadingStrategy administratorLoadingStrategy;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    private final CollaboratorWelcomeEmailService collaboratorWelcomeEmailService;

    private final RememberRandomPasswordEmailService randomPasswordEmailService;



    @Transactional
    public void createCollaborator(String username, CollaboratorCreationDto collaboratorCreationDto)
    {
        Administrator administrator = (Administrator) administratorLoadingStrategy.loadUser(username);

        if(administratorRepository.findByUsername(collaboratorCreationDto.getUsername()).isPresent())
        {
            log.error("This collaborator's username is already present!");
            throw new IllegalArgumentException("This collaborator's username is already present!");
        }

        Administrator collaborator = modelMapper.map(collaboratorCreationDto, Administrator.class);
        
        String randomPassword = PasswordGenerationUtil.generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(randomPassword);

        collaborator.setPassword(encodedPassword);
        collaborator.setAgency(administrator.getAgency());
        
        administrator.addCollaborator(collaborator);

        administratorRepository.flush();

        log.info("Collaborator was created successfully!");

        collaboratorWelcomeEmailService.sendWelcomeEmail(collaborator); 
        randomPasswordEmailService.sendRandomPasswordEmail(collaborator, randomPassword);
    }
}