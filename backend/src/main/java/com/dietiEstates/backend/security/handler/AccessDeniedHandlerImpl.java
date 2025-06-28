
package com.dietiEstates.backend.security.handler;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class AccessDeniedHandlerImpl implements AccessDeniedHandler 
{
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException 
    {
        log.error("AccessDeniedException occurred for user: " + request.getUserPrincipal().getName());
        log.error("AccessDeniedException message: " + accessDeniedException.getMessage());
        log.error("AccessDeniedException cause: " + accessDeniedException.getCause());
        log.error("Attempted access to: " + request.getRequestURI());

        int statusCode = HttpStatus.FORBIDDEN.value(); // 403 Forbidden
        String errorReason = HttpStatus.FORBIDDEN.getReasonPhrase();
        String errorType = HttpStatus.FORBIDDEN.series().name();
        String errorDescription = "Accesso negato. Non hai i permessi necessari per questa risorsa.";
        String errorPath = request.getRequestURI();

        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(statusCode, errorReason, errorType, errorDescription, errorPath);

        response.setStatus(statusCode);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getWriter(), apiErrorResponse);
    }
}