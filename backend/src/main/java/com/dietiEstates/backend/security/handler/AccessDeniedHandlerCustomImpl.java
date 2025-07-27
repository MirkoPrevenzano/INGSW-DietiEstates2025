
package com.dietiEstates.backend.security.handler;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.response.ApiErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Slf4j
public class AccessDeniedHandlerCustomImpl implements AccessDeniedHandler 
{
    private final ObjectMapper objectMapper;


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException 
    {
        log.error("Denied access occurred for user: " + request.getUserPrincipal().getName());
        log.error("Attempted access to: " + request.getRequestURI());
        log.error("Exception: " + accessDeniedException.getMessage());

        int statusCode = HttpStatus.FORBIDDEN.value();
        String errorDescription = "Denied access! You don't have the permissions to access this resource.";
        String errorPath = request.getRequestURI();

        //ApiErrorResponse apiErrorResponse = new ApiErrorResponse(statusCode, errorReason, errorType, errorDescription, errorPath);
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(HttpStatus.FORBIDDEN, errorDescription, errorPath);

        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), apiErrorResponse);
    }
}