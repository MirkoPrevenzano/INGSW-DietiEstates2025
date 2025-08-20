
package com.dietiestates.backend.security.handler;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.dietiestates.backend.exception.handler.ApiErrorResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationEntryPointCustomImpl implements AuthenticationEntryPoint
{
    private final ObjectMapper objectMapper;


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException 
    {      
        log.error("Authentication failed: " + authException.getMessage());
        log.error("Attempted access to: " + request.getRequestURI());

        String errorDetail = "Authentication failed! ";
        String errorPath = request.getRequestURI();
        
        if(authException instanceof UsernameNotFoundException)
            errorDetail += "User not found in database.";
        else if(authException instanceof BadCredentialsException)
            errorDetail += "Problem with access token.";
        else 
            errorDetail += "You must be authenticated to access this resource.";

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.UNAUTHORIZED, errorDetail, errorPath);

        response.setStatus(apiErrorResponse.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), apiErrorResponse);
    }
}
