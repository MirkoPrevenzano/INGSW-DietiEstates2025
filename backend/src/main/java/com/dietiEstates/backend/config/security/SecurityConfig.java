
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

import com.dietiEstates.backend.config.security.filter.JWTAuthenticationFilter;
import com.dietiEstates.backend.config.security.filter.JWTAuthorizationFilter;
import com.dietiEstates.backend.model.Role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig
{
    private final DaoAuthenticationProvider daoAuthenticationProvider;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter(daoAuthenticationProvider);          

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
                                                                    .requestMatchers("/admin/{username}/create-real-estate-agent")
                                                                        .hasAnyAuthority(Role.ROLE_ADMIN.name(),
                                                                                         Role.ROLE_COLLABORATOR.name()))
            .authorizeHttpRequests(authorizeHttpRequestsCustomizer-> 
                                        authorizeHttpRequestsCustomizer.anyRequest().authenticated())
            //.exceptionHandling(a -> a.accessDeniedPage("/admin/aa").accessDeniedHandler(new AccessDeniedHandlerImpl()))   
            //.authorizeHttpRequests(a -> a.requestMatchers("/auth/path/**").hasAuthority("ROLE_USER"))
			.addFilter(jwtAuthenticationFilter)
            .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
           
             

        return http.build();
    }
}