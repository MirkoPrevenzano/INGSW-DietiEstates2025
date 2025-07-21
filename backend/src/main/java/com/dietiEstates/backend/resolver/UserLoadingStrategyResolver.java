
package com.dietiEstates.backend.resolver;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.enums.Role;
import com.dietiEstates.backend.factory.RealEstateFromDtoFactory;
import com.dietiEstates.backend.strategy.UserLoadingStrategy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Component
@RequiredArgsConstructor
@Slf4j
public class UserLoadingStrategyResolver 
{
    private final List<UserLoadingStrategy> userLoadingStrategies;


    public UserLoadingStrategy getUserLoadingStrategy(Role role)
    {
        return userLoadingStrategies.stream()
                             .filter(strategy -> strategy.supports(role))
                             .findFirst()
                             .orElseThrow(() -> new IllegalArgumentException("Nessuna strategy trovata per il ruolo: " + role));
    }
}