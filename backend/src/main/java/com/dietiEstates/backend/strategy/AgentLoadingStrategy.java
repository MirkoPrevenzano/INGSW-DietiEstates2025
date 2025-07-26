
package com.dietiEstates.backend.strategy;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.dietiEstates.backend.enums.Role;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.repository.AgentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Slf4j
public class AgentLoadingStrategy implements UserLoadingStrategy 
{
    private final AgentRepository agentRepository;


    @Override
    public UserDetails loadUser(String username) throws UsernameNotFoundException 
    {
        Agent agent = agentRepository.findByUsername(username)
                                     .orElseThrow(() -> new UsernameNotFoundException("Agente non trovato con username: " + username));

        log.info("{} is an AGENT", username);
        agent.setRole(Role.ROLE_AGENT); 

        return agent;
    }


    @Override
    public boolean supports(Role role)
    {
        return Role.ROLE_AGENT.equals(role);
    }
}