
package com.dietiestates.backend.service;

import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dietiestates.backend.dto.request.CollaboratorCreationDto;
import com.dietiestates.backend.model.entity.Administrator;
import com.dietiestates.backend.repository.AdministratorRepository;
import com.dietiestates.backend.service.mail.CollaboratorWelcomeEmailService;
import com.dietiestates.backend.service.mail.RememberRandomPasswordEmailService;
import com.dietiestates.backend.util.PasswordGenerationUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class AdministratorService 
{
    private final AdministratorRepository administratorRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final CollaboratorWelcomeEmailService collaboratorWelcomeEmailService;
    private final RememberRandomPasswordEmailService randomPasswordEmailService;


    @Transactional
    public void createCollaborator(String username, CollaboratorCreationDto collaboratorCreationDto) throws UsernameNotFoundException, IllegalArgumentException, MappingException
    {
        Administrator administrator = administratorRepository.findByUsername(username)
                                                             .orElseThrow(() -> new UsernameNotFoundException(""));

        if(administratorRepository.findByUsername(collaboratorCreationDto.getUsername()).isPresent())
        {
            log.error("This username is already present!");
            throw new IllegalArgumentException("This username is already present!");
        }

        Administrator collaborator = modelMapper.map(collaboratorCreationDto, Administrator.class);
        
        String randomPassword = PasswordGenerationUtil.generateRandomPassword();
        String hashedPassword = passwordEncoder.encode(randomPassword);

        collaborator.setPassword(hashedPassword);
        collaborator.setAgency(administrator.getAgency());
        
        administrator.addCollaborator(collaborator);

        administratorRepository.flush();

        log.info("Collaborator was created successfully!");

        collaboratorWelcomeEmailService.sendWelcomeEmail(collaborator); 
        randomPasswordEmailService.sendRandomPasswordEmail(collaborator, randomPassword);
    }
}