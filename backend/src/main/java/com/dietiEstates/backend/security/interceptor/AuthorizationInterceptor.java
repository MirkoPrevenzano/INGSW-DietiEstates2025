
package com.dietiEstates.backend.security.interceptor;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.dietiEstates.backend.security.handler.AccessDeniedHandlerCustomImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor
{
    private final AccessDeniedHandlerCustomImpl accessDeniedHandlerCustomImpl;

    
    @SuppressWarnings("rawtypes")
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, 
                             @NonNull HttpServletResponse response, 
                             @NonNull Object handler) throws Exception 
    {
        log.info("Attempting Authorization Interceptor...");

        Map pathvariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if(pathvariables != null)
        {
            String pathUsername = (String) pathvariables.get("username");

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String authenticatedUsername = authentication.getName();

            if(pathUsername != null && authenticatedUsername != null)
            {
                if(pathUsername.equals(authenticatedUsername))
                {
                    log.info("Authorization Interceptor is OK!");
                    log.info("Request to handler \"{}\" allowed. URI: {}.", handler, request.getRequestURI());
                    return true;
                }
                
                accessDeniedHandlerCustomImpl.handle(request, response, new AccessDeniedException("User is trying to access other users'resources"));
                return false;
            }
        }

        log.info("Authorization Interceptor is OK! (No specific username path check)");
        log.info("Request to handler \"{}\" allowed. URI: {}.", handler, request.getRequestURI());
        return true;
    }
}