
package com.dietiEstates.backend.security.filter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException
    {
        log.info("Attempting JWT Authentication...");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        username += "/" + role;

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        Authentication authentication = getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);

        return authentication;
    }
}