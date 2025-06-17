
package com.dietiEstates.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dietiEstates.backend.enums.Role;
import com.dietiEstates.backend.security.filter.JwtAuthenticationFilter;
import com.dietiEstates.backend.security.filter.JwtAuthorizationFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig
{
    private final DaoAuthenticationProvider daoAuthenticationProvider;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        JwtAuthorizationFilter jwtAuthorizationFilter = new JwtAuthorizationFilter();
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(daoAuthenticationProvider);


        http.csrf(csrfCustomizer -> csrfCustomizer.disable())
            .cors(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(sessionManagementCustomizer -> 
                                    sessionManagementCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(authorizeHttpRequestsCustomizer-> 
                                        authorizeHttpRequestsCustomizer.requestMatchers("/login/**", "/auth/**").permitAll())
			.authorizeHttpRequests(adminHttpRequestsCustomizer-> 
                                        adminHttpRequestsCustomizer.requestMatchers("/admin/create-collaborator")
                                                                        .hasAuthority(Role.ROLE_ADMIN.name())
                                                                    .requestMatchers("/admin/{username}/update-password")
                                                                        .hasAnyAuthority(Role.ROLE_ADMIN.name(),
                                                                                         Role.ROLE_COLLABORATOR.name(),
                                                                                         Role.ROLE_UNAUTHORIZED.name())
                                                                    .requestMatchers("/admin/{username}/create-agent")
                                                                        .hasAnyAuthority(Role.ROLE_ADMIN.name(),
                                                                                         Role.ROLE_COLLABORATOR.name()))
            .authorizeHttpRequests(authorizeHttpRequestsCustomizer-> 
                authorizeHttpRequestsCustomizer.anyRequest().authenticated())

            //.exceptionHandling(a -> a.accessDeniedPage("/admin/aa").accessDeniedHandler(new AccessDeniedHandlerImpl()))   

            .addFilter(jwtAuthenticationFilter)
            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
           

        return http.build();
    }
}