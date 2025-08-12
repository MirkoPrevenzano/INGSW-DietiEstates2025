
package com.dietiEstates.backend.service;

import org.springframework.stereotype.Service;
import com.dietiEstates.backend.enums.Role;
import com.dietiEstates.backend.exception.EmailServiceException;
import com.dietiEstates.backend.model.entity.Customer;
import com.dietiEstates.backend.repository.CustomerRepository;
import com.dietiEstates.backend.service.mail.CustomerWelcomeEmailService;
import com.dietiEstates.backend.dto.request.CustomerRegistrationDto;
import com.dietiEstates.backend.dto.response.AuthenticationResponseDto;
import com.dietiEstates.backend.util.JwtUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import java.util.Collections;

import org.hibernate.MappingException;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Collection;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService 
{
    //private final ValidationUtil validationUtil;
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final CustomerWelcomeEmailService customerWelcomeEmailService;
    private final PasswordEncoder passwordEncoder;


/*     @Transactional
    public void adminRegistration(AgencyRegistrationDto aagencyRegistrationDto) throws UsernameNotFoundException, 
                                                                                    IllegalArgumentException, MappingException
    {
        if(administratorRepository.findByUsername(aagencyRegistrationDto.getUsername()).isPresent())
        {
            log.error("This username is already present!");
            throw new IllegalArgumentException("This username is already present!");
        }

        Administrator admin;
        Agency agency;
        try 
        {
            admin = modelMapper.map(aagencyRegistrationDto, Administrator.class);
            agency = modelMapper.map(aagencyRegistrationDto, Agency.class);
        } 
        catch (MappingException e) 
        {
            log.error("Problems while mapping! Probably the source object was different than the one expected!");
            throw e;
        }
        
        admin.setAgency(agency);
        administratorRepository.save(admin);
    }  */  


    public AuthenticationResponseDto customerRegistration(CustomerRegistrationDto customerRegistrationDto) throws IllegalArgumentException, MappingException
    {
        if(customerRepository.findByUsername(customerRegistrationDto.getUsername()).isPresent())
        {
            log.error("This e-mail is already present!");
            throw new IllegalArgumentException("This e-mail is already present!");
        }

        Customer customer;
        try 
        {
            customer = modelMapper.map(customerRegistrationDto, Customer.class);
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

        try 
        {
            customerWelcomeEmailService.sendWelcomeEmail(customer);
        } 
        catch (EmailServiceException e) 
        {
            log.warn(e.getMessage());
        }

        return new AuthenticationResponseDto(JwtUtil.generateAccessToken(customer));
    }
 

    public AuthenticationResponseDto authenticateWithGoogle(Map <String, String> request) { 
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
        return new AuthenticationResponseDto(token); 
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
