
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
import com.dietiEstates.backend.service.UserService;
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
    private final UserService userService;



    @Bean
    public UserDetailsService userDetailsService()
    {
       return userService;
    }
}