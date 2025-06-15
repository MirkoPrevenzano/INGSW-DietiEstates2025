
package com.dietiEstates.backend.security.interceptor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

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
        log.info("Attempting Authorization Interceptor...");

        long preHandleStartTime = System.currentTimeMillis();
        request.setAttribute("preHandleStartTime", preHandleStartTime);

        Map pathvariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

        if(pathvariables != null)
        {
            String pathUsername = (String) pathvariables.get("username");
            //String tokenUsername = (String) request.getAttribute("tokenUsername");

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String authenticatedUsername = authentication.getName(); // Ottieni l'username verificato dal JWT


            if(pathUsername != null && authenticatedUsername != null)
            {
                if(pathUsername.equals(authenticatedUsername))
                {
                    log.info("Authorization Interceptor is OK!");
                    log.info("Request to handler \"{}\" allowed. URI: {}.", handler, request.getRequestURI());
                    return true;
                }

                log.info("You are trying to access other users'resources!");
                response.setHeader("Error", "You are trying to access other users'resources!");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return false;
            }
        }

        log.info("Authorization Interceptor is OK! (No specific username path check)");
        log.info("Request to handler \"{}\" allowed. URI: {}.", handler, request.getRequestURI());
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) 
    {
        long preHandleStartTime = (Long) request.getAttribute("preHandleStartTime");
        long handlerDuration = System.currentTimeMillis() - preHandleStartTime;

        log.info("Request to handler \"{}\" completed successfully. Response status: {}. Duration: {} ms", 
                handler, response.getStatus(), handlerDuration);
    }

}