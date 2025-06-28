
package com.dietiEstates.backend.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.response.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationEntryPointCustomImpl implements AuthenticationEntryPoint
{
    private final ObjectMapper objectMapper;


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException 
    {      
        System.out.println("Authentication failed (unauthenticated): " + authException.getMessage());
        System.out.println("Attempted access to: " + request.getRequestURI());

        int statusCode = HttpStatus.UNAUTHORIZED.value();
        String errorReason = HttpStatus.UNAUTHORIZED.getReasonPhrase();
        String errorType = HttpStatus.UNAUTHORIZED.series().name();
        String errorPath = request.getRequestURI();
        
        String errorDescription = "Autenticazione richiesta. Devi effettuare il login per accedere a questa risorsa.";

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(statusCode, errorReason, errorType, errorDescription, errorPath);

        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), apiErrorResponse);
    }
}
