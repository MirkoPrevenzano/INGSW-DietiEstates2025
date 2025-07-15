
package com.dietiEstates.backend.resolver;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.factory.RealEstateFromDtoFactory;
import com.dietiEstates.backend.factory.RealEstateRootFactory;

import lombok.RequiredArgsConstructor;

import java.util.List;


@Component
@RequiredArgsConstructor
public class RealEstateRootFactoryResolver 
{
    private final List<RealEstateRootFactory> factories;


    public RealEstateRootFactory getFactoryFromString(String realEstateType) 
    {
        return factories.stream()
            .filter(factory -> factory.supports(realEstateType))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Nessuna root factory trovata per il tipo: " + realEstateType));
    }
}