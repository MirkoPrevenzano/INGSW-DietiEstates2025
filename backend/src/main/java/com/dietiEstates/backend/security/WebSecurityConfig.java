
package com.dietiEstates.backend.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;
import com.dietiEstates.backend.enums.Role;
//import com.dietiEstates.backend.security.filter.EndpointFilter;
import com.dietiEstates.backend.security.filter.UsernamePasswordRoleAuthenticationFilter;
import com.dietiEstates.backend.security.filter.JwtAuthorizationFilter;
import com.dietiEstates.backend.security.handler.AccessDeniedHandlerCustomImpl;
import com.dietiEstates.backend.security.handler.AuthenticationEntryPointCustomImpl;
import com.dietiEstates.backend.security.handler.AuthenticationFailureHandlerCustomImpl;
import com.dietiEstates.backend.security.handler.AuthenticationSuccessHandlerJwtImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.cors.CorsConfigurationSource;
import lombok.extern.slf4j.Slf4j;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig
{
    private final CorsConfigurationSource corsConfigurationSource;
    //private final EndpointFilter endpointFilter;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UsernamePasswordRoleAuthenticationFilter usernamePasswordRoleAuthenticationFilter) throws Exception
    {
        http.csrf(csrfCustomizer -> csrfCustomizer.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource))
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(sessionManagementCustomizer -> 
                                    sessionManagementCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorizeHttpRequestsCustomizer-> 
                                        authorizeHttpRequestsCustomizer.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                        .requestMatchers("/login/**", "/auth/**", "/error/**","/admin/**").permitAll())

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
            
            .exceptionHandling(a -> a.authenticationEntryPoint(authenticationEntryPointCustomImpl).accessDeniedHandler(accessDeniedHandlerCustomImpl))   
            .addFilter(usernamePasswordRoleAuthenticationFilter)
            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
            //.addFilterBefore(endpointFilter, JwtAuthorizationFilter.class);
           
        return http.build();
    }
}