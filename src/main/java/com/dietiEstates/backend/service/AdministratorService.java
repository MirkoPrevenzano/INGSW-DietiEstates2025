package com.dietiEstates.backend.service;

import java.util.Optional;

import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dietiEstates.backend.dto.AdministratorDTO;
import com.dietiEstates.backend.dto.OldNewPasswordDTO;
import com.dietiEstates.backend.dto.UserDTO;
import com.dietiEstates.backend.model.Administrator;
import com.dietiEstates.backend.model.RealEstateAgent;
import com.dietiEstates.backend.repository.AdministratorRepository;
import com.dietiEstates.backend.repository.RealEstateAgentRepository;

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
    private final ValidatorService validatorService;
    private final MockingStatsService mockingStatsService;


    public void createAdmin(AdministratorDTO administratorDTO) throws IllegalArgumentException, MappingException
    {
        if(administratorRepository.findByUsername(administratorDTO.getUsername()).isPresent())
        {
            log.error("This username is already present!");
            throw new IllegalArgumentException("This username is already present!");
        }

        try 
        {
            validatorService.passwordValidator(administratorDTO.getPassword());
        } 
        catch (IllegalArgumentException e) 
        {
            log.error(e.getMessage());
            throw e;
        }

        Administrator administrator;
        try 
        {
            administrator = modelMapper.map(administratorDTO, Administrator.class);
        } 
        catch (MappingException e) 
        {
            log.error("Problems while mapping! Probably the source object was NULL!");
            throw e;
        }
        administrator.setPassword(passwordEncoder.encode(administrator.getPassword()));
        administrator = administratorRepository.save(administrator);

        log.info("Administrator was created successfully!");
    }


    @Transactional
    public void createCollaborator(String username, UserDTO collaboratorDTO) throws UsernameNotFoundException, 
                                                                                    IllegalArgumentException, MappingException
    {
        Optional<Administrator> managerOptional = administratorRepository.findByUsername(username);
        if(managerOptional.isEmpty())
        {
            log.error("Admin not found in database");
            throw new UsernameNotFoundException("Admin not found in database");
        }
        Administrator manager = managerOptional.get();

        if(administratorRepository.findByUsername(collaboratorDTO.getUsername()).isPresent())
        {
            log.error("This username is already present!");
            throw new IllegalArgumentException("This username is already present!");
        }

        try 
        {
            validatorService.passwordValidator(collaboratorDTO.getPassword());
        } 
        catch (IllegalArgumentException e) 
        {
            log.error(e.getMessage());
            throw e;
        }

        Administrator collaborator;
        try 
        {
            collaborator = modelMapper.map(collaboratorDTO, Administrator.class);
        } 
        catch (MappingException e) 
        {
            log.error("Problems while mapping! Probably the source object was different than the one expected!");
            throw e;
        }
        collaborator.setPassword(passwordEncoder.encode(collaborator.getPassword()));
        collaborator.setAgencyName(manager.getAgencyName());

        manager.addCollaborator(collaborator);
        manager = administratorRepository.save(manager);

        log.info("Collaborator was created successfully!");
    }


    @Transactional
    public void createRealEstateAgent(String username, UserDTO realEstateAgentDTO) throws UsernameNotFoundException, 
                                                                                          IllegalArgumentException, MappingException
    {
        Optional<Administrator> administratorOptional = administratorRepository.findByUsername(username);
        if(administratorOptional.isEmpty())
        {
            log.error("Admin not found in database");
            throw new UsernameNotFoundException("Admin not found in database");
        }
        Administrator administrator = administratorOptional.get();

        if(realEstateAgentRepository.findByUsername(realEstateAgentDTO.getUsername()).isPresent())
        {
            log.error("This username is already present!");
            throw new IllegalArgumentException("This username is already present!");
        }

        try 
        {
            validatorService.passwordValidator(realEstateAgentDTO.getPassword());
        } 
        catch (IllegalArgumentException e) 
        {
            log.error(e.getMessage());
            throw e;
        }

        RealEstateAgent realEstateAgent;
        try 
        {
            realEstateAgent = modelMapper.map(realEstateAgentDTO, RealEstateAgent.class);
        } 
        catch (MappingException e) 
        {
            log.error("Problems while mapping! Probably the source object was different than the one expected!");
            throw e;
        }
        realEstateAgent.setPassword(passwordEncoder.encode(realEstateAgent.getPassword()));

        mockingStatsService.mockAgentStats(realEstateAgent);

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
            validatorService.passwordValidator(oldNewPasswordDTO.getNewPassword());
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
