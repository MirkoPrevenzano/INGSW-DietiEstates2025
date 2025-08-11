
package com.dietiEstates.backend.security.handler;

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

import com.dietiEstates.backend.exception.handler.ApiErrorResponse;
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

        int statusCode = HttpStatus.UNAUTHORIZED.value();
        String errorDescription = "Authentication failed! ";
        String errorPath = request.getRequestURI();
        
        if(authException instanceof UsernameNotFoundException)
            errorDescription += "User not found in database.";
        else if(authException instanceof BadCredentialsException)
            errorDescription += "Problem with access token.";
        else 
            errorDescription += "You must be authenticated to access this resource.";

        //ApiErrorResponse apiErrorResponse = new ApiErrorResponse(statusCode, errorReason, errorType, errorDescription, errorPath);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.UNAUTHORIZED, errorDescription, errorPath);

        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), apiErrorResponse);
    }
}
