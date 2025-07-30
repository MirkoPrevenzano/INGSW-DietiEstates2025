
package com.dietiEstates.backend.security.handler;

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

import com.dietiEstates.backend.exception.ApiErrorResponse;
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

        int statusCode = HttpStatus.UNAUTHORIZED.value();
        String errorDescription = "Authentication failed! ";
        String errorPath = request.getRequestURI();

        if(authException instanceof DisabledException || authException instanceof LockedException)
            errorDescription += "Your account is temporarily blocked or disabled.";
        else if(authException instanceof UsernameNotFoundException)
        {
            if (authException.getCause() instanceof IllegalArgumentException)
                errorDescription += "You have entered a wrong role.";
            else
                errorDescription += "You have entered a wrong username.";
        }
        else if(authException instanceof BadCredentialsException)
            errorDescription += "You have entered a wrong password.";
        else
            errorDescription += "An error occurred during authentication. Try later.";


        //ApiErrorResponse apiErrorResponse = new ApiErrorResponse(statusCode, errorReason, errorType, errorDescription, errorPath);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.UNAUTHORIZED, errorDescription, errorPath);

        response.setStatus(statusCode);
        response.setContentType("application/json");
        objectMapper.writeValue(response.getWriter(), apiErrorResponse); 
    }
}