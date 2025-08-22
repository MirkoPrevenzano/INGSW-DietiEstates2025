
package com.dietiestates.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dietiestates.backend.dto.request.CustomerRegistrationDto;
import com.dietiestates.backend.dto.response.AuthenticationResponseDto;
import com.dietiestates.backend.enums.Role;
import com.dietiestates.backend.model.entity.Customer;
import com.dietiestates.backend.repository.CustomerRepository;
import com.dietiestates.backend.service.mail.CustomerWelcomeEmailService;
import com.dietiestates.backend.security.JwtProvider;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import java.util.Collections;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService 
{
    private final CustomerService customerService;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final CustomerWelcomeEmailService customerWelcomeEmailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;



    @Transactional
    public AuthenticationResponseDto customerRegistration(CustomerRegistrationDto customerRegistrationDto) throws IllegalArgumentException
    {
        if(customerRepository.findByUsername(customerRegistrationDto.getUsername()).isPresent())
        {
            log.error("This e-mail is already present!");
            throw new IllegalArgumentException("This e-mail is already present!");
        }

        Customer customer = modelMapper.map(customerRegistrationDto, Customer.class);

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer = customerRepository.save(customer);

        log.info("Customer was registrated successfully!");

        customerWelcomeEmailService.sendWelcomeEmail(customer);

        return new AuthenticationResponseDto(jwtProvider.generateAccessToken(customer));
    }
 

    public AuthenticationResponseDto authenticateWithGoogle(Map <String, String> request) { 
        String googleToken = request.get("token");
        GoogleIdToken.Payload payload = verifyGoogleToken(googleToken)
                                        .orElseThrow(() -> new IllegalArgumentException("Token Google non valido"));
         
        Customer user = customerService.authenticateWithExternalAPI(payload);
        // Genera il token di autenticazione 
        Collection<SimpleGrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority(Role.ROLE_CUSTOMER.name()) // Imposta il ruolo come ROLE_CUSTOMER
        );
    
        // Genera il token di autenticazione
        String token = jwtProvider.generateAccessToken(
            new User(
                user.getUsername(),
                user.getPassword(),
                authorities // Passa le autorità richieste
            )
        );
        // Crea una risposta di autenticazione 
        return new AuthenticationResponseDto(token); 
    } 

   
    private Optional<GoogleIdToken.Payload> verifyGoogleToken(String token) {
        try {
            @SuppressWarnings("deprecation") 
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), new JacksonFactory())
                    .setAudience(Collections.singletonList("699354462746-9ale2lg8onjqvafu9aiopmd0fo82j3b4.apps.googleusercontent.com")) // Sostituisci con il tuo Client ID
                    .build();
    
            GoogleIdToken idToken = verifier.verify(token);
            return Optional.ofNullable(idToken).map(GoogleIdToken::getPayload);
        } catch (Exception e) {
            log.error("Errore durante la verifica del token Google: {}", e.getMessage());
            return Optional.empty();
        }
    }

}
