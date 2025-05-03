
package com.dietiEstates.backend.service;

import java.util.Optional;

import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dietiEstates.backend.dto.AdminRegistrationDTO;
import com.dietiEstates.backend.dto.OldNewPasswordDTO;
import com.dietiEstates.backend.dto.AgentCustomerRegistrationDTO;
import com.dietiEstates.backend.model.Administrator;
import com.dietiEstates.backend.model.RealEstateAgent;
import com.dietiEstates.backend.repository.AdministratorRepository;
import com.dietiEstates.backend.repository.RealEstateAgentRepository;
import com.dietiEstates.backend.utils.MockingStatsUtil;
import com.dietiEstates.backend.utils.ValidationUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class AdministratorService 
{
    private final AdministratorRepository administratorRepository;
    private final RealEstateAgentRepository realEstateAgentRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final ValidationUtil validationUtil;
    private final MockingStatsUtil mockingStatsUtil;
    


    @Transactional
    public void createCollaborator(AdminRegistrationDTO adminRegistrationDTO) throws UsernameNotFoundException, 
                                                                                    IllegalArgumentException, MappingException
    {
        Optional<Administrator> adminOptional = administratorRepository.findById(1l);
        if(adminOptional.isEmpty())
        {
            log.error("Admin not found in database");
            throw new UsernameNotFoundException("Admin not found in database");
        }
        Administrator admin = adminOptional.get();

        if(administratorRepository.findByUsername(adminRegistrationDTO.getUsername()).isPresent())
        {
            log.error("This username is already present!");
            throw new IllegalArgumentException("This username is already present!");
        }

        Administrator collaborator;
        try 
        {
            collaborator = modelMapper.map(adminRegistrationDTO, Administrator.class);
        } 
        catch (MappingException e) 
        {
            log.error("Problems while mapping! Probably the source object was different than the one expected!");
            throw e;
        }

        collaborator.setPassword(passwordEncoder.encode("default"));
        collaborator.setAgencyName(admin.getAgencyName());

        admin.addCollaborator(collaborator);
        admin = administratorRepository.save(admin);

        log.info("Collaborator was created successfully!");
    }


    @Transactional
    public void createRealEstateAgent(String username, AgentCustomerRegistrationDTO agentCustomerRegistrationDTO) throws UsernameNotFoundException, 
                                                                                          IllegalArgumentException, MappingException
    {
        Optional<Administrator> administratorOptional = administratorRepository.findByUsername(username);
        if(administratorOptional.isEmpty())
        {
            log.error("Admin not found in database");
            throw new UsernameNotFoundException("Admin not found in database");
        }
        Administrator administrator = administratorOptional.get();

        if(realEstateAgentRepository.findByUsername(agentCustomerRegistrationDTO.getUsername()).isPresent())
        {
            log.error("This username is already present!");
            throw new IllegalArgumentException("This username is already present!");
        }

        try 
        {
            validationUtil.passwordValidator(agentCustomerRegistrationDTO.getPassword());
        } 
        catch (IllegalArgumentException e) 
        {
            log.error(e.getMessage());
            throw e;
        }

        RealEstateAgent realEstateAgent;
        try 
        {
            realEstateAgent = modelMapper.map(agentCustomerRegistrationDTO, RealEstateAgent.class);
        } 
        catch (MappingException e) 
        {
            log.error("Problems while mapping! Probably the source object was different than the one expected!");
            throw e;
        }
        realEstateAgent.setPassword(passwordEncoder.encode(realEstateAgent.getPassword()));

        mockingStatsUtil.mockAgentStats(realEstateAgent);

        administrator.addRealEstateAgent(realEstateAgent);
        administrator = administratorRepository.save(administrator);

        log.info("Real Estate Agent was created successfully!");
    }


    public void updatePassword(String username, OldNewPasswordDTO oldNewPasswordDTO) throws UsernameNotFoundException, 
                                                                                            IllegalArgumentException, MappingException
    {
        Optional<Administrator> administratorOptional = administratorRepository.findByUsername(username);
        if(administratorOptional.isEmpty())
        {
            log.error("Admin not found in database");
            throw new UsernameNotFoundException("Admin not found in database");
        }

        if(!(passwordEncoder.matches(oldNewPasswordDTO.getOldPassword(), administratorOptional.get().getPassword())))
        {
            log.error("The \"old password\" you have inserted do not correspond to your current password");
            throw new IllegalArgumentException("The \"old password\" you have inserted do not correspond to your current password");
        }

        if((passwordEncoder.matches(oldNewPasswordDTO.getNewPassword(), administratorOptional.get().getPassword())))
        {
            log.error("The \"new password\" you have inserted can't be equal to your current password");
            throw new IllegalArgumentException("\"The \"new password\" you have inserted can't be equal to your current password");
        }
        
        try 
        {
            validationUtil.passwordValidator(oldNewPasswordDTO.getNewPassword());
        } 
        catch (IllegalArgumentException e) 
        {
            log.error(e.getMessage());
            throw e;
        }

        Administrator administrator = administratorOptional.get();
        administrator.setPassword(passwordEncoder.encode(oldNewPasswordDTO.getNewPassword()));
        administrator = administratorRepository.save(administrator);

        log.info("Password was updated successfully!");
    }
}
