
package com.dietiEstates.backend.resolver;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.request.RealEstateCreationDto;
import com.dietiEstates.backend.factory.RealEstateFromDtoFactory;

import lombok.RequiredArgsConstructor;

// TODO: creare strategyresolverexception
@Component
@RequiredArgsConstructor
public class RealEstateFromDtoFactoryResolver 
{
    private final List<RealEstateFromDtoFactory> realEstateFromDtoFactories;


    public RealEstateFromDtoFactory getFactory(RealEstateCreationDto dto) 
    {
        return realEstateFromDtoFactories.stream()
                                         .filter(factory -> factory.supports(dto))
                                         .findFirst()
                                         .orElseThrow(() -> new IllegalArgumentException("Nessuna RealEstateFromDtoFactory supporta il tipo: " + dto.getClass().getSimpleName()));
    }
}