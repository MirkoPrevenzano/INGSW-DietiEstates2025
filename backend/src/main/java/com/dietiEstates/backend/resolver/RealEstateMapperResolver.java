
package com.dietiEstates.backend.resolver;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.mapper.RealEstateCreationDtoMapper;
import com.dietiEstates.backend.model.entity.RealEstate;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class RealEstateMapperResolver 
{
    private final List<RealEstateCreationDtoMapper> realEstateCreationDtoMappers;


    public RealEstateCreationDtoMapper getMapper(RealEstate entity) 
    {
        return realEstateCreationDtoMappers.stream()
                                           .filter(mapper -> mapper.supports(entity))
                                           .findFirst()
                                           .orElseThrow(() -> new IllegalArgumentException("Nessun RealEstateCreationDtoMapper supporta il tipo: " + entity.getClass().getSimpleName()));
    }
}