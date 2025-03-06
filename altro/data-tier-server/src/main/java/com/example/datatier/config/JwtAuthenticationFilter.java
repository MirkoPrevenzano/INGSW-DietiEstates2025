package com.example.datatier.config;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
/*
 * Corrisponde ad un filtro che è eseguito una volta per ogni richiesta http per gestire autentitcazione basata su JWT token
 * Intecetta le richieste HTTP e verifica presenta token nell'header. Esentendendo OncePerRequestFilter permette il controllo per ogni richiesta.
 * Nel metodo doFilterInternal si esegue la logica basata su jwt
 * 
 * Prima cosa si estrae il token dall'header http, si verifica la validità. Se valido si imposta l'autenticazione rispettando la sicurezza di spring e si prosegue.
 */
@Component
public class JwtAuthenticationFilter  extends OncePerRequestFilter{

    
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService,
                                    UserDetailsService userDetailsService)
    {
        this.jwtService=jwtService;
        this.userDetailsService=userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain
                                    )throws ServletException, IOException {
                
                                        //estraggo dall'header la sezione authorization contenente anche il token
       final String authHeader= request.getHeader("Authorization");
       final String jwt;
       final String  userEmail;
       if(authHeader==null||!authHeader.startsWith("Bearer "))
       {
        filterChain.doFilter(request, response);
        return;
       }
       //altrimenti l'header è corretto, estraiamo il token che si trova subito dopo bearer , quindi al carattere numero 7
       jwt=authHeader.substring(7);
       userEmail=jwtService.extractUsername(jwt);
       //verifico se l'email è presente è che l'utente non sia già autenticato
       if(userEmail!=null && SecurityContextHolder.getContext().getAuthentication()==null)
       {
        UserDetails userDetails= userDetailsService.loadUserByUsername(userEmail);
        if(jwtService.isValidToken(jwt, userDetails))
        {
            //creo oggetto con i dettagli dell'utente
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //imposto autenticazione nel contesto 
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
       }

       //continuo con eventuale catena di filtri
       filterChain.doFilter(request, response);


    }

    /*
     * L'oggetto UsernamePasswordAuthenticationToken è una classe di spring security. 
     * Utilizzata per rappresentare token basato su nome utente e password, oltre al ruolo
     */
   
                                
    

}
