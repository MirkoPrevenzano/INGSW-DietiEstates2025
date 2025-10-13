
package com.dietiestates.backend.security.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class PerformanceLogInterceptor implements HandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception 
    {
        log.info("Attempting request to handler \"{}\".\nURI: {}.", handler, request.getRequestURI());

        long preHandleStartTime = System.currentTimeMillis();
        request.setAttribute("preHandleStartTime", preHandleStartTime); 
        
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception 
    {
        long preHandleStartTime = (Long) request.getAttribute("preHandleStartTime");
        long afterCompletionEndTime = System.currentTimeMillis() - preHandleStartTime;
    
        if (response.getStatus() >= 400) 
        {
            // Richiesta completata con errore (status 4xx/5xx)
            log.error("Request to handler \"{}\" completed with error.\nURI: {}.\nResponse Status: {}.\nDuration: {} ms",
                      handler, request.getRequestURI(), response.getStatus(), afterCompletionEndTime);
        } 
        else 
        {
            // Richiesta completata con SUCCESS (status 2xx)
            log.info("Request to handler \"{}\" completed succesfully.\nURI: {}.\nResponse Status: {}.\nDuration: {} ms",
                     handler, request.getRequestURI(), response.getStatus(), afterCompletionEndTime);
        }  
    }
}