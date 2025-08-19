
package com.dietiestates.backend.security.handler;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.dietiestates.backend.exception.handler.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFailureHandlerCustomImpl implements AuthenticationFailureHandler 
{
    private final ObjectMapper objectMapper;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException 
    {
        log.error("Authentication failed: " + authException.getMessage());
        log.error("Attempted access to: " + request.getRequestURI());

        String errorDetail = "Authentication failed! ";
        String errorPath = request.getRequestURI();

        if(authException instanceof DisabledException || authException instanceof LockedException)
            errorDetail += "Your account is temporarily blocked or disabled.";
        else if(authException instanceof UsernameNotFoundException)
        {
            if (authException.getCause() instanceof IllegalArgumentException)
                errorDetail += "You have entered a wrong role.";
            else
                errorDetail += "You have entered a wrong username.";
        }
        else if(authException instanceof BadCredentialsException)
        {
            errorDetail += "You have entered a wrong password.";
        }
        else
            errorDetail += "An error occurred during authentication. Try later.";


        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.UNAUTHORIZED, errorDetail, errorPath);

        response.setStatus(apiErrorResponse.getStatus());
        response.setContentType("application/json");
        objectMapper.writeValue(response.getWriter(), apiErrorResponse); 
    }
}