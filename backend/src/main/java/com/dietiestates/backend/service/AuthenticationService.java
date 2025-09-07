
package com.dietiestates.backend.service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Collections;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.dietiestates.backend.dto.response.AuthenticationResponseDto;
import com.dietiestates.backend.enums.Role;
import com.dietiestates.backend.model.entity.Customer;
import com.dietiestates.backend.security.JwtProvider;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthenticationService 
{
    private final CustomerService customerService;

    private final JwtProvider jwtProvider;



    @Transactional
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
