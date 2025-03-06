
package com.dietiEstates.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.dietiEstates.backend.model.Administrator;
import com.dietiEstates.backend.model.Customer;
import com.dietiEstates.backend.model.RealEstateAgent;
import com.dietiEstates.backend.model.Role;
import com.dietiEstates.backend.repository.AdministratorRepository;
import com.dietiEstates.backend.repository.CustomerRepository;
import com.dietiEstates.backend.repository.RealEstateAgentRepository;

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
                        Optional<Customer> customer = customerRepository.findByUsername(username);
                        if(customer.isEmpty())
                        {
                            log.error("Customer not found in database");
                            throw new UsernameNotFoundException("Customer not found in database");
                        }
                        else
                        {
                            log.info("Customer found in database: {}", username);
                            customer.get().setRole(Role.ROLE_USER);
                            return customer.get();
                        }
                    }

                    case "agent" :
                    {
                        Optional<RealEstateAgent> realEstateAgent = realEstateAgentRepository.findByUsername(username);
                        if(realEstateAgent.isEmpty())
                        {
                            log.error("Real Estate Agent not found in database");
                            throw new UsernameNotFoundException("Real Estate Agent not found in database");
                        }
                        else
                        {
                            log.info("Real Estate Agent found in database: {}", username);
                            realEstateAgent.get().setRole(Role.ROLE_AGENT);
                            return realEstateAgent.get();
                        }                        
                    }

                    case "admin" :
                    {
                        Optional<Administrator> administrator = administratorRepository.findByUsername(username);
                        if(administrator.isEmpty())
                        {
                            log.error("Administrator not found in database");
                            throw new UsernameNotFoundException("Administrator not found in database");
                        }
                        else
                        {
                            log.info("Administrator found in database: {}", username);
                            administrator.get().setRole(Role.ROLE_ADMIN);
                            return administrator.get();
                        }                           
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
