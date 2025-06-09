
package com.dietiEstates.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dietiEstates.backend.enums.Role;
import com.dietiEstates.backend.model.entity.Administrator;
import com.dietiEstates.backend.model.entity.Customer;
import com.dietiEstates.backend.model.entity.RealEstateAgent;
import com.dietiEstates.backend.repository.AdministratorRepository;
import com.dietiEstates.backend.repository.CustomerRepository;
import com.dietiEstates.backend.repository.RealEstateAgentRepository;
import com.dietiEstates.backend.utils.ValidationUtil;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.Optional;



@Configuration
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceConfig
{
    private final CustomerRepository customerRepository;
    private final RealEstateAgentRepository realEstateAgentRepository;
    private final AdministratorRepository administratorRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidationUtil validationUtil;
    
    @Autowired
    private HttpServletRequest httpServletRequest;



    @Bean
    public UserDetailsService userDetailsService()
    {
       return new UserDetailsService() 
       {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
            {
                switch(httpServletRequest.getParameter("role"))
                {
                    case "customer" :
                    {
                        Optional<Customer> optionalCustomer = customerRepository.findByUsername(username);
                        Customer customer = validationUtil.optionalUserValidator(optionalCustomer, username);
                        customer.setRole(Role.ROLE_CUSTOMER);
                        return customer;
                    }

                    case "agent" :
                    {
                        Optional<RealEstateAgent> optionalRealEstateAgent = realEstateAgentRepository.findByUsername(username);
                        RealEstateAgent realEstateAgent = validationUtil.optionalUserValidator(optionalRealEstateAgent, username);
                        realEstateAgent.setRole(Role.ROLE_AGENT);
                        return realEstateAgent;                       
                    }

                    case "admin" :
                    {
                        Optional<Administrator> optionalAdministrator = administratorRepository.findByUsername(username);
                        Administrator administrator = validationUtil.optionalUserValidator(optionalAdministrator, username);

                        if(passwordEncoder.matches("default", administrator.getPassword()))
                        {
                            log.info("{} is a NOT AUTHORIZED administrator", username);
                            administrator.setRole(Role.ROLE_UNAUTHORIZED);
                            return administrator;                           
                        }
                        else if(administrator.getUserId() == 1)
                        {
                            log.info("{} is an ADMIN administrator", username);
                            administrator.setRole(Role.ROLE_ADMIN);
                            return administrator;                           
                        }
                        
                        log.info("{} is a COLLABORATOR administrator", username);
                        administrator.setRole(Role.ROLE_COLLABORATOR);
                        return administrator;                           
                    }

                    default : 
                    {
                        log.error("Wrong role inserted!");
                        throw new IllegalArgumentException("Wrong role inserted!");
                    }
                }          
            }
        };
    }
}
