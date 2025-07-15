
package com.dietiEstates.backend.resolver;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.factory.RealEstateFromDtoFactory;

import lombok.RequiredArgsConstructor;

import java.util.List;


@Component
@RequiredArgsConstructor
public class RealEstateFactoryResolver 
{
    private final List<RealEstateFromDtoFactory> factories;


    public RealEstateFromDtoFactory getFactoryFromDto(RealEstateCreationDTO dto) 
    {
        return factories.stream()
            .filter(factory -> factory.supports(dto.getClass()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Nessuna factory trovata per il tipo: " + dto.getClass().getSimpleName()));
    }
}