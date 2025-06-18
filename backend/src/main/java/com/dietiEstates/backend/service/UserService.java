
package com.dietiEstates.backend.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.enums.Role;
import com.dietiEstates.backend.model.User;
import com.dietiEstates.backend.model.entity.Administrator;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.repository.UserRepository;
import com.dietiEstates.backend.util.ValidationUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
    {
        log.info("\n\nSONO IN USERDETAILSSERVICEEEE\n\n");

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

}