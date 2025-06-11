
package com.dietiEstates.backend.security.filter;

import static java.util.Arrays.stream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dietiEstates.backend.security.JWTUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;



@Slf4j
public class JWTAuthorizationFilter extends OncePerRequestFilter
{
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain filterChain) throws ServletException, IOException 
    {
        if(request.getServletPath().equals("/login") || 
           request.getServletPath().equals("/auth/standard-registration") ||
           request.getServletPath().equals("/auth/login/oauth2/code/google")) 
           
        {
            filterChain.doFilter(request, response);
            return;
        }
        
        log.info("Attempting JWT Authorization...");

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
        {
            try 
            {
                String token = authorizationHeader.substring("Bearer ".length());
                DecodedJWT decodedJWT = JWTUtils.verifyToken(token);  

                String username = decodedJWT.getSubject();
                String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                stream(roles).forEach(role -> {authorities.add(new SimpleGrantedAuthority(role));});
                    
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null, authorities);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);   
                log.info("JWT Authorization is OK!");

                request.setAttribute("tokenUsername", username);
                filterChain.doFilter(request, response);  
            } 
            catch (UsernameNotFoundException e)
            {
                log.error("{}", e.getMessage());
                response.setHeader("Error", e.getMessage());
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
            catch (JWTVerificationException e) 
            {
                log.error("{}", e.getMessage());
                response.setHeader("Error", e.getMessage());
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        }
        else
        {
            log.error("You are not a JWT Bearer!");
            response.setHeader("Error", "You are not a JWT Bearer!");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }
}
