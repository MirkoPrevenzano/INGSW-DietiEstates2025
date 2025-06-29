package com.dietiEstates.backend.security.filter;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
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
import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.response.AuthenticationResponseDTO;
import com.dietiEstates.backend.model.entity.Administrator;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


//@Component //
@Slf4j
//@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    //private final DaoAuthenticationProvider daoAuthenticationProvider;
    //private final AuthenticationManager authenticationManager;



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException
    {
        log.info("Attempting JWT Authentication...");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        username += "/" + role;

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        Authentication authentication = null;
/*         try
        { */
            authentication = getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
/* 
        }
        catch (DisabledException | LockedException e)
        {
            log.error("Your account is temporarily blocked or disabled");
            response.setHeader("Error", e.getMessage());
        }
        catch (UsernameNotFoundException e)
        {
            log.error("You have entered a wrong username");
            response.setHeader("Error", e.getMessage());
        }
        catch (BadCredentialsException e)
        {
            log.error("You have entered a wrong password!");
            response.setHeader("Error", e.getMessage());
        }  
     */
        return authentication;
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, 
                                            FilterChain chain, Authentication authenticationResult) throws IOException, ServletException 
    {
        log.info("JWT Authentication is OK!");

        UserDetails user = (UserDetails) authenticationResult.getPrincipal();

        String accessToken = JwtUtil.generateAccessToken(user);
        Boolean mustChangePassword = getMustChangePassword(user);

        response.setHeader("jwtToken", accessToken);
        
        AuthenticationResponseDTO authenticationResponseDTO = new AuthenticationResponseDTO(accessToken, mustChangePassword);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), authenticationResponseDTO); 
    }    



    private boolean getMustChangePassword(UserDetails user)
    {
        if(user instanceof Administrator) 
            return ((Administrator) user).getMustChangePassword();
        else if (user instanceof Agent) 
            return ((Agent) user).getMustChangePassword();
        
        return false;
    }
}