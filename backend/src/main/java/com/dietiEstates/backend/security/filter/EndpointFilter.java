
package com.dietiEstates.backend.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.Endpoint;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.dietiEstates.backend.dto.response.ApiErrorResponse;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;


@Component
@RequiredArgsConstructor
@Slf4j
public class EndpointFilter extends OncePerRequestFilter 
{
    private final ObjectMapper objectMapper;
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException 
    {        
        if(request.getServletPath().equals("/login") || 
           request.getServletPath().equals("/auth/standard-registration") ||
           request.getServletPath().equals("/auth/admin-registration") ||
           request.getServletPath().equals("/auth/login/oauth2/code/google")) 
           
        {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("Attempting EndpointFilter...");

        try 
        {
            if (requestMappingHandlerMapping.getHandler(request) == null) 
            {
                log.error("Endpoint not found!");
                log.error("Attempted access to: " + request.getRequestURI());

                int statusCode = HttpStatus.NOT_FOUND.value();
                String errorReason = HttpStatus.NOT_FOUND.getReasonPhrase();
                String errorType = HttpStatus.NOT_FOUND.series().name();
                String errorDescription = "URL doesn't exist!";
                String errorPath = request.getRequestURI();

                ApiErrorResponse apiErrorResponse = new ApiErrorResponse(statusCode, errorReason, errorType, errorDescription, errorPath);

                response.setStatus(statusCode);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                objectMapper.writeValue(response.getWriter(), apiErrorResponse);
   
                return;
            }
        } catch (Exception e) 
        {
            log.error("Exception occured during endpoint verification: ", e.getMessage());
        }

        log.info("EndpointFilter is OK!");
        filterChain.doFilter(request, response);
    }
}