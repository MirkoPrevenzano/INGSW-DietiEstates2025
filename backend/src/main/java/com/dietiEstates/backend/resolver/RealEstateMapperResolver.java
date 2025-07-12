
package com.dietiEstates.backend.resolver;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.mapper.RealEstateCreationDTOMapper;
import com.dietiEstates.backend.model.entity.RealEstate;

import lombok.RequiredArgsConstructor;

import java.util.List;


@Component
@RequiredArgsConstructor
public class RealEstateMapperResolver 
{
    private final List<RealEstateCreationDTOMapper> realEstateCreationDTOMappers;



    public RealEstateCreationDTOMapper getMapper(RealEstate entity) 
    {
        return realEstateCreationDTOMappers.stream()
            .filter(mapper -> mapper.supports(entity.getClass()))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Nessun mapper trovato per il tipo: " + entity.getClass().getSimpleName()));
    }
}