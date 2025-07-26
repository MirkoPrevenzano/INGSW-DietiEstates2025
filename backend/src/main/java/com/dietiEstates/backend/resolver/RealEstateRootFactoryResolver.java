
package com.dietiEstates.backend.resolver;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.factory.RealEstateRootFactory;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class RealEstateRootFactoryResolver 
{
    private final List<RealEstateRootFactory> factories;


    public RealEstateRootFactory getFactory(String realEstateType) 
    {
        return factories.stream()
                        .filter(factory -> factory.supports(realEstateType))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Nessuna RealEstateRootFactory supporta il tipo: " + realEstateType));
    }
}