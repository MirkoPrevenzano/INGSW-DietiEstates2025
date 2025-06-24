
package com.dietiEstates.backend.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.enums.Role;
import com.dietiEstates.backend.model.entity.Administrator;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.model.entity.Customer;
import com.dietiEstates.backend.model.entity.User;
import com.dietiEstates.backend.repository.AdministratorRepository;
import com.dietiEstates.backend.repository.AgentRepository;
import com.dietiEstates.backend.repository.CustomerRepository;
import com.dietiEstates.backend.repository.UserRepository;
import com.dietiEstates.backend.util.ValidationUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService
{
    private final AdministratorRepository administratorRepository;
    private final AgentRepository agentRepository;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
    {
        log.info("\n\nSONO IN USERDETAILSSERVICEEEE\n\n");

        int index = username.indexOf("/");
        String role = username.substring(index + 1);
        username = username.substring(0, index);

        switch(role) 
        {
            case "admin":
                Optional<Administrator> optionalAdminiistrator = administratorRepository.findByUsername(username);
                Administrator administrator = ValidationUtil.optionalUserValidator(optionalAdminiistrator, username);   
                
                if(passwordEncoder.matches("default", administrator.getPassword()))
                {
                    log.info("{} is a NOT AUTHORIZED administrator", username);
                    administrator.setRole(Role.ROLE_UNAUTHORIZED);
                }
                else if(administrator.getUserId() == 1)
                {
                    log.info("{} is an ADMIN administrator", username);
                    administrator.setRole(Role.ROLE_ADMIN);
                }
                else
                {
                    log.info("{} is a COLLABORATOR administrator", username);
                    administrator.setRole(Role.ROLE_COLLABORATOR);
                }

                return administrator;


            
            case "agent":
                Optional<Agent> optionalAgent = agentRepository.findByUsername(username);
                Agent agent = ValidationUtil.optionalUserValidator(optionalAgent, username);   
                log.info("{} is an AGENT", username);
                agent.setRole(Role.ROLE_AGENT);
                return agent;


            
            case "customer":            
                Optional<Customer> optionalCustomer = customerRepository.findByUsername(username);
                Customer customer = ValidationUtil.optionalUserValidator(optionalCustomer, username);   
                log.info("{} is a CUSTOMER", username);
                customer.setRole(Role.ROLE_CUSTOMER); 
                return customer;
        

            default:
                throw new IllegalArgumentException("Wrong role inserted!");
        }    
    }

}