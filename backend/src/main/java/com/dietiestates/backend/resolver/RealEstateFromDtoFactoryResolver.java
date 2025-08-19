
package com.dietiestates.backend.resolver;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dietiestates.backend.dto.request.RealEstateCreationDto;
import com.dietiestates.backend.factory.RealEstateFromDtoFactory;

import lombok.RequiredArgsConstructor;


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