
package com.dietiEstates.backend.strategy;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dietiEstates.backend.enums.Role;
import com.dietiEstates.backend.model.entity.Administrator;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.model.entity.Customer;
import com.dietiEstates.backend.repository.AdministratorRepository;
import com.dietiEstates.backend.repository.AgentRepository;
import com.dietiEstates.backend.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerLoadingStrategy implements UserLoadingStrategy {

    private final CustomerRepository customerRepository;

    @Override
    public boolean supports(Role role) {
        return Role.ROLE_CUSTOMER.equals(role);
    }


    @Override
    public UserDetails loadUser(String username) throws UsernameNotFoundException 
    {
        Customer customer = customerRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Cliente non trovato con username: " + username));

        log.info("{} is a CUSTOMER", username);
        customer.setRole(Role.ROLE_CUSTOMER);

        return customer;
    }
}
