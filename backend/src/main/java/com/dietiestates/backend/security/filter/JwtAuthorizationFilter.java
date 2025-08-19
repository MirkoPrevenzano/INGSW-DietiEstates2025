
package com.dietiestates.backend.security.filter;

import java.util.Arrays;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dietiestates.backend.enums.Role;
import com.dietiestates.backend.resolver.UserLoadingStrategyResolver;
import com.dietiestates.backend.security.handler.AuthenticationEntryPointCustomImpl;
import com.dietiestates.backend.strategy.UserLoadingStrategy;
import com.dietiestates.backend.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter
{
    private final AuthenticationEntryPointCustomImpl authenticationEntryPointCustomImpl;
    private final UserLoadingStrategyResolver userLoadingStrategyResolver;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain filterChain) throws ServletException, IOException 
    {
        if (request.getServletPath().equals("/login") || 
            (request.getServletPath().equals("/agency") && request.getMethod().equals("POST")) ||
            request.getServletPath().equals("/auth/customer-registration") ||
            request.getServletPath().equals("/auth/login/oauth2/code/google")) 
           
        {
            filterChain.doFilter(request, response);
            return;
        } 


        log.info("Attempting JwtAuthorizationFilter...");

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
        {
            try 
            {
                String token = authorizationHeader.substring("Bearer ".length());
                JwtUtil.VerifiedJwt verifiedJwt = JwtUtil.verifyToken(token);

                String username = verifiedJwt.getSubject();
                String[] roles = verifiedJwt.getRoles();

                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

                Arrays.stream(roles)
                      .forEach(role -> {authorities.add(new SimpleGrantedAuthority(role));});

                UserLoadingStrategy userLoadingStrategy = userLoadingStrategyResolver.getUserLoadingStrategy(Role.valueOf(roles[0]));
                UserDetails userDetails = userLoadingStrategy.loadUser(username);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, authorities);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);   
                    
                log.info("JwtAuthorizationFilter is OK!");

                filterChain.doFilter(request, response); 
            } 
            catch (UsernameNotFoundException e)
            {
                log.error("Exception occured during JwtAuthorizationFilter!");
                authenticationEntryPointCustomImpl.commence(request, response, e);
            }
            catch (JWTVerificationException e) // TODO: Sostituire con exc custom jwt
            {
                log.error("Exception occured during JwtAuthorizationFilter!");
                authenticationEntryPointCustomImpl.commence(request, response, new BadCredentialsException(e.getMessage()));
            }
        }
        else
        {
            authenticationEntryPointCustomImpl.commence(request, response, new BadCredentialsException("User is not a JWT Bearer!"));
        }
    }
}
