
package com.dietiestates.backend.security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.dietiestates.backend.enums.Role;
import com.dietiestates.backend.security.filter.EndpointFilter;
import com.dietiestates.backend.security.filter.JwtAuthorizationFilter;
import com.dietiestates.backend.security.filter.UsernamePasswordRoleAuthenticationFilter;
import com.dietiestates.backend.security.handler.AccessDeniedHandlerCustomImpl;
import com.dietiestates.backend.security.handler.AuthenticationEntryPointCustomImpl;
import com.dietiestates.backend.security.handler.AuthenticationFailureHandlerCustomImpl;
import com.dietiestates.backend.security.handler.AuthenticationSuccessHandlerJwtImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig
{
    private final EndpointFilter endpointFilter;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final AuthenticationEntryPointCustomImpl authenticationEntryPointCustomImpl;
    private final AccessDeniedHandlerCustomImpl accessDeniedHandlerCustomImpl;
    private final AuthenticationFailureHandlerCustomImpl authenticationFailureHandlerCustomImpl;
    private final AuthenticationSuccessHandlerJwtImpl authenticationSuccessHandlerJwtImpl;


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception 
    {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UsernamePasswordRoleAuthenticationFilter usernamePasswordRoleAuthenticationFilter(AuthenticationManager authenticationManager)
    {
        UsernamePasswordRoleAuthenticationFilter usernamePasswordRoleAuthenticationFilter = new UsernamePasswordRoleAuthenticationFilter();
        
        usernamePasswordRoleAuthenticationFilter.setAuthenticationManager(authenticationManager);
        usernamePasswordRoleAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandlerCustomImpl);
        usernamePasswordRoleAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandlerJwtImpl);

        return usernamePasswordRoleAuthenticationFilter;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() 
    {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowedOrigins(List.of("http://localhost:4200"));
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return urlBasedCorsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UsernamePasswordRoleAuthenticationFilter usernamePasswordRoleAuthenticationFilter, CorsConfigurationSource corsConfigurationSource) throws Exception
    {
        http.csrf(CsrfConfigurer::disable)
            .cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource))
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(sessionManagementCustomizer -> sessionManagementCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorizeHttpRequestsCustomizer-> authorizeHttpRequestsCustomizer.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                                                                                    .requestMatchers("/login/**", "/auth/**", "/error/**").permitAll()
                                                                                                    .requestMatchers(HttpMethod.POST, "/agencies").permitAll()
                                                                                                    .requestMatchers(HttpMethod.POST, "/customer").permitAll())

/* 			.authorizeHttpRequests(adminHttpRequestsCustomizer-> 
                                        adminHttpRequestsCustomizer.requestMatchers("/admin/create-collaborator")
                                                                        .hasAuthority(Role.ROLE_ADMIN.name())
                                                                    .requestMatchers("/admin/{username}/update-password")
                                                                        .hasAnyAuthority(Role.ROLE_ADMIN.name(),
                                                                                         Role.ROLE_COLLABORATOR.name(),
                                                                                         Role.ROLE_UNAUTHORIZED.name())
                                                                    .requestMatchers("/admin/{username}/create-agent")
                                                                        .hasAnyAuthority(Role.ROLE_ADMIN.name(),
                                                                                         Role.ROLE_COLLABORATOR.name())) */
            .authorizeHttpRequests(authorizeHttpRequestsCustomizer-> authorizeHttpRequestsCustomizer.anyRequest().authenticated())
            .exceptionHandling(exceptionHandlingCustomizer -> exceptionHandlingCustomizer.authenticationEntryPoint(authenticationEntryPointCustomImpl)
                                                                                         .accessDeniedHandler(accessDeniedHandlerCustomImpl))   
            .addFilter(usernamePasswordRoleAuthenticationFilter)
            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(endpointFilter, JwtAuthorizationFilter.class);
           
        return http.build();
    }
}