
package com.dietiEstates.backend.security.filter;

import static java.util.Arrays.stream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dietiEstates.backend.repository.UserRepository;
import com.dietiEstates.backend.security.handler.AuthenticationEntryPointCustomImpl;
import com.dietiEstates.backend.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter
{
    private final AuthenticationEntryPointCustomImpl authenticationEntryPointCustomImpl;
    private final UserRepository userRepository;



    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain filterChain) throws ServletException, IOException 
    {
        if(request.getServletPath().equals("/login") || 
           request.getServletPath().equals("/auth/standard-registration") ||
           request.getServletPath().equals("/auth/admin-registration") ||
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
                JwtUtil.verifyToken(token);

                    String username = JwtUtil.extractSubject(token);
                    String[] roles = JwtUtil.extractRoles(token);
                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    stream(roles).forEach(role -> {authorities.add(new SimpleGrantedAuthority(role));});

                    UserDetails userDetails = userRepository.findByUsername(username)
                                                            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, authorities);
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);   
                    
                    log.info("JWT Authorization is OK!");
                    filterChain.doFilter(request, response); 
            } 
            catch (UsernameNotFoundException e)
            {
                log.error(e.getMessage());
                authenticationEntryPointCustomImpl.commence(request, response, e);
            }
            catch (JWTVerificationException e) 
            {
                log.error(e.getMessage());
                authenticationEntryPointCustomImpl.commence(request, response, new BadCredentialsException(e.getMessage()));
            }
        }
        else
        {
            log.error("User is not a JWT Bearer!");
            authenticationEntryPointCustomImpl.commence(request, response, new BadCredentialsException("User is not a JWT Bearer!"));
        }
    }
}
