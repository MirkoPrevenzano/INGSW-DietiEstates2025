
package com.dietiestates.backend.strategy;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.dietiestates.backend.enums.Role;
import com.dietiestates.backend.model.entity.Customer;
import com.dietiestates.backend.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Slf4j
public class CustomerLoadingStrategy implements UserLoadingStrategy 
{
    private final CustomerRepository customerRepository;

    

    @Override
    public UserDetails loadUser(String username) throws UsernameNotFoundException 
    {
        Customer customer = customerRepository.findByUsername(username)
                                              .orElseThrow(() -> new UsernameNotFoundException("Customer non trovato con username: " + username));

        log.info("Customer con username '{}' trovato nel DB!", username);
        
        //customer.setRole(Role.ROLE_CUSTOMER);

        return customer;
    }
    
    
    @Override
    public void setRole(UserDetails userDetails) 
    {
        if (userDetails instanceof Customer customer)
            customer.setRole(Role.ROLE_CUSTOMER);
    }


    @Override
    public boolean supports(Role role) 
    {
        return Role.ROLE_CUSTOMER == role;
    }
}