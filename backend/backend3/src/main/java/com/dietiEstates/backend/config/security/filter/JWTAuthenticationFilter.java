
package com.dietiEstates.backend.config.security.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.dietiEstates.backend.config.security.JWTUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RequiredArgsConstructor
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    private final DaoAuthenticationProvider daoAuthenticationProvider;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException
    {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        try
        {
            return daoAuthenticationProvider.authenticate(usernamePasswordAuthenticationToken);
        }
        catch (DisabledException | LockedException e)
        {
            log.error("Your account is temporarily blocked or disabled");
            response.setHeader("Error", e.getMessage());
            throw e;
        }
        catch (UsernameNotFoundException e)
        {
            log.error("You have entered a wrong username");
            response.setHeader("Error", e.getMessage());
            throw e;
        }
        catch (BadCredentialsException e)
        {
            log.error("You have entered a wrong password!");
            response.setHeader("Error", e.getMessage());
            throw e;
        }        
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
                                            FilterChain chain, Authentication authenticationResult) throws IOException, ServletException 
    {
        log.info("JWT Authentication is OK!");

        UserDetails user = (UserDetails) authenticationResult.getPrincipal();

        String accessToken = JWTUtils.generateAccessToken(user);
        String refreshToken = JWTUtils.generateRefreshToken(user);

        response.setHeader("accessToken", accessToken);
        response.setHeader("refreshToken", refreshToken); 
        
        // restituire oggetto json nel body
        Map<String,String> tokens = new HashMap<>(); 
        tokens.put("accessToken", accessToken);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);

       // new ObjectMapper().readValue(response.getContentType(), User.class); trasformare JSON in classe
    }    
}
