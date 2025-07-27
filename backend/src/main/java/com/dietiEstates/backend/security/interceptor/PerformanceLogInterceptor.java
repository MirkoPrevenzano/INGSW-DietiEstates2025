
package com.dietiEstates.backend.security.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class PerformanceLogInterceptor implements HandlerInterceptor
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception 
    {
        log.info("Attempting request to handler \"{}\". URI: {}.", handler, request.getRequestURI());

        long preHandleStartTime = System.currentTimeMillis();
        request.setAttribute("preHandleStartTime", preHandleStartTime); 
        
        return true;
    }


/*      @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) 
    {
        long preHandleStartTime = (Long) request.getAttribute("preHandleStartTime");
        long postHandleDuration = System.currentTimeMillis() - preHandleStartTime;

        log.info("Request to handler \"{}\" completed successfully. URI: {}. Response status: {}. Duration: {} ms", 
                handler, request.getRequestURI(), response.getStatus(), postHandleDuration);
    }  */
    

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception 
    {
        long preHandleStartTime = (Long) request.getAttribute("preHandleStartTime");
        long afterCompletionDuration = System.currentTimeMillis() - preHandleStartTime;
    
        if (ex != null) 
        {
            // Richiesta completata con eccezione NON gestita da @ControllerAdvice
            log.error("Request to handler \"{}\" failed with unhandled exception. URI: {}. Response Status: {}. Duration: {} ms. Exception: {}",
                      handler, request.getRequestURI(), response.getStatus(), afterCompletionDuration, ex.getMessage());
        } 
        else if (response.getStatus() >= 400) 
        {
            // Richiesta completata con errore (status 4xx/5xx), gestito o non lanciante eccezione
            log.error("Request to handler \"{}\" completed with error. URI: {}. Response Status: {}. Duration: {} ms",
                      handler, request.getRequestURI(), response.getStatus(), afterCompletionDuration);
        } 
        else 
        {
            // Richiesta completata con SUCCESS (status 2xx)
            log.info("Request to handler \"{}\" completed succesfully. URI: {}. Response Status: {}. Duration: {} ms",
                     handler, request.getRequestURI(), response.getStatus(), afterCompletionDuration);
        }  
    }
}