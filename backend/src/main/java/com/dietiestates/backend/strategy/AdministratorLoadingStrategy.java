
package com.dietiestates.backend.strategy;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.dietiestates.backend.enums.Role;
import com.dietiestates.backend.model.entity.Administrator;
import com.dietiestates.backend.repository.AdministratorRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Slf4j
public class AdministratorLoadingStrategy implements UserLoadingStrategy 
{
    private final AdministratorRepository administratorRepository;



    @Override
    public UserDetails loadUser(String username) throws UsernameNotFoundException 
    {
        Administrator administrator = administratorRepository.findByUsername(username)
                                                             .orElseThrow(() -> new UsernameNotFoundException("Amministratore non trovato con username: " + username));

        log.info("Amministratore con username '{}' trovato nel DB!", username);

        /*if (administrator.getManager() == null) 
        { 
            log.info("Amministratore (ruolo ADMIN) con username '{}' trovato nel DB!", username);
            administrator.setRole(Role.ROLE_ADMIN);
        } 
        else 
        {
            log.info("Amministratore (ruolo COLLABORATOR) con username '{}' trovato nel DB!", username);
            administrator.setRole(Role.ROLE_COLLABORATOR);
        }*/

        return administrator;
    }

    
    @Override
    public void setRole(UserDetails userDetails) 
    {
        if (userDetails instanceof Administrator administrator)
        {
            if (administrator.getManager() == null)
                administrator.setRole(Role.ROLE_ADMIN);
            else 
                administrator.setRole(Role.ROLE_COLLABORATOR);
        }
    }


    @Override
    public boolean supports(Role role) 
    {
        return Role.ROLE_ADMIN == role;
    }
}