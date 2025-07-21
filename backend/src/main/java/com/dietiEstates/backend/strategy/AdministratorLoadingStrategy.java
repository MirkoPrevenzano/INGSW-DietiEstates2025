package com.dietiEstates.backend.strategy;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.dietiEstates.backend.enums.Role;
import com.dietiEstates.backend.model.entity.Administrator;
import com.dietiEstates.backend.repository.AdministratorRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdministratorLoadingStrategy implements UserLoadingStrategy 
{

    private final AdministratorRepository administratorRepository;
    private final PasswordEncoder passwordEncoder; // Necessario per la logica "default"


    @Override
    public boolean supports(Role role) 
    {
        return Role.ROLE_ADMIN.equals(role);
    }


    @Override
    public UserDetails loadUser(String username) throws UsernameNotFoundException 
    {
        Administrator administrator = administratorRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Amministratore non trovato con username: " + username));

        // Logica specifica dell'amministratore per assegnare il ruolo dinamico
        if (passwordEncoder.matches("default", administrator.getPassword())) {
            log.info("{} is an UNAUTHORIZED administrator (default password)", username);
            administrator.setRole(Role.ROLE_UNAUTHORIZED);
        } else if (administrator.getUserId() == 1) { // Nota: userId=1 è un hardcode, meglio un flag nel DB.
            log.info("{} is an ADMIN administrator", username);
            administrator.setRole(Role.ROLE_ADMIN);
        } else {
            log.info("{} is a COLLABORATOR administrator", username);
            administrator.setRole(Role.ROLE_COLLABORATOR);
        }

        return administrator;
    }
}