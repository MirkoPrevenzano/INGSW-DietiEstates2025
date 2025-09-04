
package com.dietiestates.backend.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dietiestates.backend.security.interceptor.PerformanceLogInterceptor;
import com.dietiestates.backend.security.interceptor.UsernameAuthorizationInterceptor;

import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer
{
    private final UsernameAuthorizationInterceptor usernameAuthorizationInterceptor;

    private final PerformanceLogInterceptor performanceLogInterceptor;
    


    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) 
    {
        registry.addInterceptor(performanceLogInterceptor);
        registry.addInterceptor(usernameAuthorizationInterceptor);
    }

    
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) 
    {
        registry.addMapping("/**").allowedOrigins("http://localhost:4200")
                                              .allowedMethods("GET","POST","PUT", "DELETE", "OPTIONS")
                                              .allowedHeaders("*")
                                              .allowCredentials(true);                                             
    }  
}