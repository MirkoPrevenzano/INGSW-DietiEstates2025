
package com.dietiEstates.backend.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dietiEstates.backend.security.interceptor.AuthorizationInterceptor;

import lombok.RequiredArgsConstructor;



@Configuration
@EnableWebMvc
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer
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
                                              .allowCredentials(true)
                                              .exposedHeaders("error")
                                              .maxAge(900);
    }    
}