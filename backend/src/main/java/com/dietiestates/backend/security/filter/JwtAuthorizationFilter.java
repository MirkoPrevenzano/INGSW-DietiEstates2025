
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

import com.dietiestates.backend.enums.Role;
import com.dietiestates.backend.resolver.UserLoadingStrategyResolver;
import com.dietiestates.backend.security.JwtProvider;
import com.dietiestates.backend.security.handler.AuthenticationEntryPointCustomImpl;
import com.dietiestates.backend.strategy.UserLoadingStrategy;

import com.auth0.jwt.exceptions.JWTVerificationException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter
{
    private final AuthenticationEntryPointCustomImpl authenticationEntryPointCustomImpl;

    private final UserLoadingStrategyResolver userLoadingStrategyResolver;

    private final JwtProvider jwtProvider;



    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, 
                                    @NonNull HttpServletResponse response, 
                                    @NonNull FilterChain filterChain) throws ServletException, IOException 
    {
        if (request.getServletPath().equals("/") ||
            request.getServletPath().equals("/login") || 
            request.getServletPath().startsWith("/swagger-ui") ||
            request.getServletPath().startsWith("/v3/api-docs") ||
            (request.getServletPath().equals("/agencies") && request.getMethod().equals("POST")) ||
            (request.getServletPath().equals("/customers") && request.getMethod().equals("POST")) ||
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
                JwtProvider.VerifiedJwt verifiedJwt = jwtProvider.verifyToken(token);

                String username = verifiedJwt.getSubject();
                String[] roles = verifiedJwt.getRoles();

                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

                Arrays.stream(roles)
                      .forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

                UserLoadingStrategy userLoadingStrategy = userLoadingStrategyResolver.getStrategy(Role.valueOf(roles[0]));
                UserDetails userDetails = userLoadingStrategy.loadUser(username);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null, authorities);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);                       
            } 
            catch (UsernameNotFoundException e)
            {
                log.error("Exception occured during JwtAuthorizationFilter!");

                authenticationEntryPointCustomImpl.commence(request, response, e);

                return;
            }
            catch (JWTVerificationException e) 
            {
                log.error("Exception occured during JwtAuthorizationFilter!");
                
                authenticationEntryPointCustomImpl.commence(request, response, new BadCredentialsException(e.getMessage()));

                return;
            }
        }

        log.info("JwtAuthorizationFilter is OK!");

        filterChain.doFilter(request, response);
    }
}
