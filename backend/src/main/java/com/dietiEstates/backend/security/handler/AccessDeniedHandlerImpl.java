package com.dietiEstates.backend.security.handler;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler 
{

/*     @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // TODO Auto-generated method stub
        
    }
     */
    
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

        System.out.println("AccessDeniedException occurred for user: " + request.getUserPrincipal().getName());
        System.out.println("Attempted access to: " + request.getRequestURI());

        int statusCode = HttpServletResponse.SC_FORBIDDEN; // 403 Forbidden
        String errorMessage = "Accesso negato. Non hai i permessi necessari per questa risorsa.";
        String errorDescription = "Forbidden";

        ErrorResponse errorResponse = new ErrorResponse(statusCode, errorDescription, errorMessage, request.getRequestURI());

        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ObjectMapper.writeValue(response.getWriter(), errorResponse);
    }
}
}
