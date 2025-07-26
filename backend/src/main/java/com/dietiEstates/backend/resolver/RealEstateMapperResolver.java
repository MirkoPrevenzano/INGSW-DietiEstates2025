
package com.dietiEstates.backend.resolver;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.mapper.RealEstateCreationDTOMapper;
import com.dietiEstates.backend.model.entity.RealEstate;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class RealEstateMapperResolver 
{
    private final List<RealEstateCreationDTOMapper> mappers;


    public RealEstateCreationDTOMapper getMapper(RealEstate entity) 
    {
        return mappers.stream()
                      .filter(mapper -> mapper.supports(entity))
                      .findFirst()
                      .orElseThrow(() -> new IllegalArgumentException("Nessun RealEstateCreationDTOMapper supporta il tipo: " + entity.getClass().getSimpleName()));
    }
}