
package com.dietiestates.backend.security.filter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.dietiestates.backend.exception.handler.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


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
        if (request.getServletPath().equals("/login") || 
            (request.getServletPath().equals("/agency") && request.getMethod().equals("POST")) ||
            request.getServletPath().equals("/auth/customer-registration") ||
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

                String errorDetail = "URL doesn't exist!";
                String errorPath = request.getRequestURI();

                ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.NOT_FOUND, errorDetail, errorPath);

                response.setStatus(apiErrorResponse.getStatus());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                objectMapper.writeValue(response.getWriter(), apiErrorResponse);
   
                return;
            }
        } 
        catch (Exception e) 
        {
            log.error("Exception occured during EndpointFilter: ", e.getMessage());

            String errorDetail = "Errore interno!";
            String errorPath = request.getRequestURI();

            ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, errorDetail, errorPath);

            response.setStatus(apiErrorResponse.getStatus());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            objectMapper.writeValue(response.getWriter(), apiErrorResponse);        
        } 

        log.info("EndpointFilter is OK!");
        
        filterChain.doFilter(request, response);
    }
}