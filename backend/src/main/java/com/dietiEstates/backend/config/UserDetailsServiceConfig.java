
package com.dietiEstates.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.dietiEstates.backend.enums.Role;
import com.dietiEstates.backend.model.User;
import com.dietiEstates.backend.model.entity.Administrator;
import com.dietiEstates.backend.model.entity.Customer;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.repository.AdministratorRepository;
import com.dietiEstates.backend.repository.CustomerRepository;
import com.dietiEstates.backend.repository.UserRepository;
import com.dietiEstates.backend.repository.AgentRepository;
import com.dietiEstates.backend.util.ValidationUtil;

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
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;



    @Bean
    public UserDetailsService userDetailsService()
    {
       return new UserDetailsService() 
       {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
            {


                        Optional<User> optionalUser = userRepository.findByUsername(username);
                        User user = ValidationUtil.optionalUserValidator(optionalUser, username);   
                        
                        if(user instanceof Administrator)
                        {
                            if(passwordEncoder.matches("default", user.getPassword()))
                            {
                                log.info("{} is a NOT AUTHORIZED administrator", username);
                                user.setRole(Role.ROLE_UNAUTHORIZED);
                            }
                            else if(user.getUserId() == 1)
                            {
                                log.info("{} is an ADMIN administrator", username);
                                user.setRole(Role.ROLE_ADMIN);
                            }
                            else
                            {
                                log.info("{} is a COLLABORATOR administrator", username);
                                user.setRole(Role.ROLE_COLLABORATOR);
                            }
                        }
                        else if(user instanceof Agent)
                        {
                            user.setRole(Role.ROLE_AGENT);
                        }
                        else
                        {
                            user.setRole(Role.ROLE_CUSTOMER);
                        }      
                        
                        return user;  

            }
        };
    }
}