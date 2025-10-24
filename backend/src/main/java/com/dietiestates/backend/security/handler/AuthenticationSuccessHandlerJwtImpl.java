
package com.dietiestates.backend.security.handler;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.dietiestates.backend.dto.response.AuthenticationResponseDto;
import com.dietiestates.backend.security.JwtProvider;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationSuccessHandlerJwtImpl implements AuthenticationSuccessHandler 
{
    private final ObjectMapper objectMapper;

    private final JwtProvider jwtProvider;


    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException 
    {
        UserDetails user = (UserDetails) authentication.getPrincipal();

        log.info("Authentication is OK!");
        log.info("User '" + user.getUsername() + "' has been successfully authenticated.");

        String accessToken = jwtProvider.generateAccessToken(user);
        AuthenticationResponseDto authenticationResponseDto = new AuthenticationResponseDto(accessToken);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), authenticationResponseDto); 
    }
}