
package com.dietiEstates.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dietiEstates.backend.security.interceptor.UsernameAuthorizationInterceptor;
import com.dietiEstates.backend.security.interceptor.PerformanceLogInterceptor;

import lombok.RequiredArgsConstructor;

import java.util.List;


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
                                              .allowedMethods("GET","POST","PUT","OPTIONS")
                                              .allowedHeaders("*")
                                              .allowCredentials(true);                                             
    }  
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
