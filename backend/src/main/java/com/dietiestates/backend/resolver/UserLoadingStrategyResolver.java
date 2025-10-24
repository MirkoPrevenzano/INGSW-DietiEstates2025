
package com.dietiestates.backend.resolver;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dietiestates.backend.enums.Role;
import com.dietiestates.backend.strategy.UserLoadingStrategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Slf4j
public class UserLoadingStrategyResolver 
{
    private final List<UserLoadingStrategy> userLoadingStrategies;


    
    public UserLoadingStrategy getStrategy(Role role)
    {
        return userLoadingStrategies.stream()
                                    .filter(strategy -> strategy.supports(role))
                                    .findFirst()
                                    .orElseThrow(() -> new IllegalArgumentException("Nessuna UserLoadingStrategy supporta il ruolo: " + role));
    }
}