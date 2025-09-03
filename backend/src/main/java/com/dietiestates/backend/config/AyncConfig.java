
package com.dietiestates.backend.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import com.dietiestates.backend.exception.EmailServiceException;

import lombok.extern.slf4j.Slf4j;


@Configuration
@EnableAsync
@Slf4j
public class AyncConfig implements AsyncConfigurer
{
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() 
    {
        return (throwable, method, params) -> {
                                                if (throwable instanceof EmailServiceException) 
                                                    log.warn(throwable.getMessage());
                                                else
                                                    log.error("Async exception occurred in the method '{}'': {}", method, throwable.getMessage());
                                              };       
    }   
}