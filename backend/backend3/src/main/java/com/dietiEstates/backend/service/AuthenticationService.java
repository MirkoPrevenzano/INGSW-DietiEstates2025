
package com.dietiEstates.backend.service;

import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.config.security.JWTUtils;
import com.dietiEstates.backend.dto.AuthenticationResponseDTO;
import com.dietiEstates.backend.dto.UserDTO;
import com.dietiEstates.backend.model.Customer;
import com.dietiEstates.backend.model.Role;
import com.dietiEstates.backend.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService 
{
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final ValidatorService validatorService;


    public AuthenticationResponseDTO standardRegistration(UserDTO userDTO) throws IllegalArgumentException, MappingException
    {
        try 
        {
            validatorService.emailValidator(userDTO.getUsername());
            validatorService.passwordValidator(userDTO.getPassword());
        } 
        catch (IllegalArgumentException e) 
        {
            log.error(e.getMessage());
            throw e;
        }

        if(customerRepository.findByUsername(userDTO.getUsername()).isPresent())
        {
            log.error("This e-mail is already present!");
            throw new IllegalArgumentException("This e-mail is already present!");
        }

        Customer customer;
        try 
        {
            customer = modelMapper.map(userDTO, Customer.class);
        } 
        catch (MappingException e) 
        {
            log.error("Problems while mapping! Probably the source object was different than the one expected!");
            throw e;
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer = customerRepository.save(customer);

        log.info("Customer was registrated successfully!");

        customer.setRole(Role.ROLE_USER);
        return new AuthenticationResponseDTO(JWTUtils.generateAccessToken(customer));
    }



    //TODO methods:

    //googleregistration
    //googlelogin
}
