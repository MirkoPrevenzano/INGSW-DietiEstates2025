
package com.dietiEstates.backend.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dietiEstates.backend.config.security.interceptor.AuthorizationInterceptor;

import lombok.RequiredArgsConstructor;


@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer
{
    private final AuthorizationInterceptor authorizationInterceptor;

    

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) 
    {
        registry.addInterceptor(authorizationInterceptor);
    }


    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) 
    {
        registry.addMapping("/**").allowedOrigins("http://localhost:4200")
                                              .allowedMethods("GET","POST","PUT","OPTIONS")
                                              .allowedHeaders("*")
                                              .allowCredentials(true);
    }    
}
