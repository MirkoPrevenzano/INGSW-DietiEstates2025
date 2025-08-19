
package com.dietiestates.backend.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dietiestates.backend.dto.request.AgencyRegistrationDto;
import com.dietiestates.backend.model.entity.Administrator;
import com.dietiestates.backend.model.entity.Agency;
import com.dietiestates.backend.repository.AdministratorRepository;
import com.dietiestates.backend.repository.AgencyRepository;

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
    public void createAgency(AgencyRegistrationDto agencyRegistrationDto) 
    {
        if(agencyRepository.findByBusinessNameOrVatNumber(agencyRegistrationDto.getBusinessName(), agencyRegistrationDto.getVatNumber()).isPresent())
        {
            log.error("This agency is already present!");
            throw new IllegalArgumentException("This agency is already present!");
        }

        if(administratorRepository.findByUsername(agencyRegistrationDto.getUsername()).isPresent())
        {
            log.error("This username is already present!");
            throw new IllegalArgumentException("This username is already present!");
        }

        Agency agency = modelMapper.map(agencyRegistrationDto, Agency.class);
        Administrator admin = modelMapper.map(agencyRegistrationDto, Administrator.class);
        
        String hashedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(hashedPassword);
        
        agency.addAdministrator(admin);

        log.info("Agency was created successfully!");

        Agency agency2 = agencyRepository.save(agency);

        Administrator administrator = agency2.getAdministrators().get(0);

        log.info(administrator.toString());
        String agencyname = administratorRepository.findAgencyNameByUsername(administrator.getUsername());
        log.info(agencyname);
    }
}