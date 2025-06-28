
package com.dietiEstates.backend.security.handler;

import java.io.IOException;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFailureHandlerCustomImpl implements AuthenticationFailureHandler 
{

/*     @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        // TODO Auto-generated method stub
        
    } */
    
 
        @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        System.out.println("Authentication failed: " + exception.getMessage());
        System.out.println("Request URI: " + request.getRequestURI());

        int statusCode = HttpServletResponse.SC_UNAUTHORIZED; // Default: 401 Unauthorized
        String errorMessage;
        String errorType = "Authentication Failed";

        if (exception instanceof BadCredentialsException) {
            errorMessage = "Credenziali non valide. Verifica username e password.";
            errorType = "Bad Credentials";
        } else if (exception instanceof DisabledException) {
            errorMessage = "Il tuo account è stato disabilitato.";
            errorType = "Account Disabled";
            statusCode = HttpServletResponse.SC_FORBIDDEN; // 403, perché l'identità è nota ma non abilitata
        } else if (exception instanceof LockedException) {
            errorMessage = "Il tuo account è bloccato. Contatta l'amministratore.";
            errorType = "Account Locked";
            statusCode = HttpServletResponse.SC_FORBIDDEN; // 403
        } else {
            errorMessage = "Si è verificato un errore durante l'autenticazione. Riprova più tardi.";
            errorType = "Unknown Authentication Error";
        }

/*         ErrorResponse errorResponse = new ErrorResponse(statusCode, errorType, errorMessage, request.getRequestURI());

        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), errorResponse); */
    }
}
