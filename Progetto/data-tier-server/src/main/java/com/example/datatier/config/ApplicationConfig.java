package com.example.datatier.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.datatier.model.repository.CustomerRepository;

@Configuration
public class ApplicationConfig {
    private final CustomerRepository customerRepository; 

    public ApplicationConfig(CustomerRepository customerRepository)
    {
        this.customerRepository=customerRepository;
    }

    /*
     * Interfaccia che si occupare di caricare i dettagli dell'utente durante l'autenticazione. Permette di definire
     * il comportamento del metodo loadUserByUsername
     */
    @Bean
    public UserDetailsService  userDetailsService()
    {
        return username-> customerRepository.findByEmail(username)
                    .orElseThrow(()->new UsernameNotFoundException("User not found"));
    }

    @Bean 
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider= new DaoAuthenticationProvider();
        //specifichaimo proprietà
        authenticationProvider.setUserDetailsService(userDetailsService());
        //abbiamo bisogna che la password sia codificata
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    //indico tipo di encoder per la password
    @Bean
    public PasswordEncoder passwordEncoder() {
       return new BCryptPasswordEncoder();
    }
}
