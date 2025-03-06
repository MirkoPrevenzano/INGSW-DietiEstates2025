
package com.dietiEstates.backend.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.dietiEstates.backend.config.security.filter.JWTAuthenticationFilter;
import com.dietiEstates.backend.config.security.filter.JWTAuthorizationFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig implements WebMvcConfigurer
{
    private final DaoAuthenticationProvider daoAuthenticationProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(daoAuthenticationProvider);          

        http.csrf(csrfCustomizer -> csrfCustomizer.disable())
            .cors(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(sessionManagementCustomizer -> sessionManagementCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(authorizeHttpRequestsCustomizer-> authorizeHttpRequestsCustomizer.requestMatchers("/login/**", "/auth/**", "/CSV/**", "/PDF/**").permitAll())
            .authorizeHttpRequests(authorizeHttpRequestsCustomizer-> authorizeHttpRequestsCustomizer.anyRequest().authenticated())            
            //.authorizeHttpRequests(a -> a.requestMatchers("/auth/path/**").hasAuthority("ROLE_USER"))
			.addFilter(jwtAuthenticationFilter)
            .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    /*@Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain2(HttpSecurity http) throws Exception
    {            
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter( 
                                                               authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)));     
        jwtAuthenticationFilter.setFilterProcessesUrl("/login/google/**");

        http.csrf(a -> a.disable())
            .sessionManagement(a -> a.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .securityMatcher("/login/**","/auth/**")
			.authorizeHttpRequests(a-> a.requestMatchers("/login/google/**").permitAll())
            //.authorizeHttpRequests(a -> a.requestMatchers("/auth/path/**").hasAuthority("ROLE_AGENT"))
			.addFilter(jwtAuthenticationFilter)
            .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(a-> a.anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }*/
}

