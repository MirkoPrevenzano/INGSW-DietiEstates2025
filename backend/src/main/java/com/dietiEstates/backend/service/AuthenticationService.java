
package com.dietiEstates.backend.service;

import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dietiEstates.backend.enums.Role;
import com.dietiEstates.backend.model.entity.Administrator;
import com.dietiEstates.backend.model.entity.Agency;
import com.dietiEstates.backend.model.entity.Customer;
import com.dietiEstates.backend.dto.request.AdminRegistrationDTO;
import com.dietiEstates.backend.dto.request.CustomerRegistrationDTO;
import com.dietiEstates.backend.dto.response.AuthenticationResponseDTO;
import com.dietiEstates.backend.repository.AdministratorRepository;
import com.dietiEstates.backend.repository.CustomerRepository;
import com.dietiEstates.backend.service.CustomerService;
import com.dietiEstates.backend.service.mail.CustomerWelcomeEmailService;
import com.dietiEstates.backend.util.JwtUtil;
import com.dietiEstates.backend.validator.PasswordValidatorImpl;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import java.util.Collections;
import java.util.Optional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.UUID;
import java.util.Collection;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService 
{
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    //private final ValidationUtil validationUtil;
    private final CustomerService customerService;
    private final AdministratorRepository administratorRepository;
    private final CustomerWelcomeEmailService customerWelcomeEmailService;


    @Transactional
    public void adminRegistration(AdminRegistrationDTO adminRegistrationDTO) throws UsernameNotFoundException, 
                                                                                    IllegalArgumentException, MappingException
    {
        if(administratorRepository.findByUsername(adminRegistrationDTO.getUsername()).isPresent())
        {
            log.error("This username is already present!");
            throw new IllegalArgumentException("This username is already present!");
        }

        Administrator admin;
        Agency agency;
        try 
        {
            admin = modelMapper.map(adminRegistrationDTO, Administrator.class);
            agency = modelMapper.map(adminRegistrationDTO, Agency.class);
        } 
        catch (MappingException e) 
        {
            log.error("Problems while mapping! Probably the source object was different than the one expected!");
            throw e;
        }
        
        admin.setAgency(agency);
        administratorRepository.save(admin);
    }   


    public AuthenticationResponseDTO customerRegistration(CustomerRegistrationDTO customerRegistrationDTO) throws IllegalArgumentException, MappingException
    {
        if(customerRepository.findByUsername(customerRegistrationDTO.getUsername()).isPresent())
        {
            log.error("This e-mail is already present!");
            throw new IllegalArgumentException("This e-mail is already present!");
        }

        Customer customer;
        try 
        {
            customer = modelMapper.map(customerRegistrationDTO, Customer.class);
        } 
        catch (MappingException e) 
        {
            log.error("Problems while mapping! Probably the source object was different than the one expected!");
            throw e;
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer = customerRepository.save(customer);

        log.info("Customer was registrated successfully!");

        customer.setRole(Role.ROLE_CUSTOMER);

        customerWelcomeEmailService.sendWelcomeEmail(customer);
        return new AuthenticationResponseDTO(JwtUtil.generateAccessToken(customer));
    }


    public AuthenticationResponseDTO authenticateWithGoogle(Map <String, String> request) { 
        String googleToken = request.get("token");
        GoogleIdToken.Payload payload = verifyGoogleToken(googleToken); 
        Customer user = customerService.authenticateWithExternalAPI(payload);
        // Genera il token di autenticazione 
        Collection<SimpleGrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority(Role.ROLE_CUSTOMER.name()) // Imposta il ruolo come ROLE_CUSTOMER
        );
    
        // Genera il token di autenticazione
        String token = JwtUtil.generateAccessToken(
            new User(
                user.getUsername(),
                user.getPassword(),
                authorities // Passa le autorità richieste
            )
        );
        // Crea una risposta di autenticazione 
        return new AuthenticationResponseDTO(token); 
    } 

   
    private GoogleIdToken.Payload verifyGoogleToken(String token) {
        try {
            @SuppressWarnings("deprecation") 
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                    .setAudience(Collections.singletonList("699354462746-9ale2lg8onjqvafu9aiopmd0fo82j3b4.apps.googleusercontent.com")) // Sostituisci con il tuo Client ID
                    .build();
    
            GoogleIdToken idToken = verifier.verify(token);
            return (idToken != null) ? idToken.getPayload() : null;
        } catch (Exception e) {
            log.error("Errore durante la verifica del token Google: {}", e.getMessage());
            return null;
        }
    }

}
