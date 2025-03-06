
package com.dietiEstates.backend.config.security.interceptor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class AuthorizationInterceptor implements HandlerInterceptor
{
    @SuppressWarnings("rawtypes")
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, 
                             @NonNull HttpServletResponse response, 
                             @NonNull Object handler) throws Exception 
    {
        Map pathvariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if(pathvariables != null)
        {
            String pathUsername = (String) pathvariables.get("username");
            String tokenUsername = (String) request.getAttribute("com.dietiEstates.backend.model.User.username");

            if(pathUsername != null && tokenUsername != null)
            {
                if(pathUsername.equals(tokenUsername))
                {
                    log.info("Authorization Interceptor is OK!");
                    return true;
                }

                log.info("You are trying to access other users'resources!");
                response.setHeader("Error", "You are trying to access other users'resources!");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }
        }

        return true;
    }
}