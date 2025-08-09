
package com.dietiEstates.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dietiEstates.backend.dto.request.AdminRegistrationDTO;
import com.dietiEstates.backend.model.entity.Administrator;
import com.dietiEstates.backend.model.entity.Agency;
import com.dietiEstates.backend.repository.AdministratorRepository;
import com.dietiEstates.backend.repository.AgencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AgencyService 
{
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AgencyRepository agencyRepository;
    private final AdministratorRepository administratorRepository;
    

    @Transactional
    public void createAgency(AdminRegistrationDTO adminRegistrationDTO) 
    {
        if(agencyRepository.findByBusinessNameOrVatNumber(adminRegistrationDTO.getBusinessName(), adminRegistrationDTO.getVatNumber()).isPresent())
        {
            log.error("This agency is already present!");
            throw new IllegalArgumentException("This agency is already present!");
        }

        if(administratorRepository.findByUsername(adminRegistrationDTO.getUsername()).isPresent())
        {
            log.error("This username is already present!");
            throw new IllegalArgumentException("This username is already present!");
        }

        Agency agency = modelMapper.map(adminRegistrationDTO, Agency.class);
        Administrator admin = modelMapper.map(adminRegistrationDTO, Administrator.class);
        
        String hashedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(hashedPassword);
        
        agency.addAdministrator(admin);

        log.info("Agency was created successfully!");

        agencyRepository.save(agency);
    }
}