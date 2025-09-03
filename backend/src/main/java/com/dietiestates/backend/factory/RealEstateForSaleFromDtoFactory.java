
package com.dietiestates.backend.factory;

import org.springframework.stereotype.Component;

import com.dietiestates.backend.dto.request.RealEstateCreationDto;
import com.dietiestates.backend.dto.request.RealEstateForSaleCreationDto;
import com.dietiestates.backend.mapper.RealEstateCreationForSaleDtoMapper;
import com.dietiestates.backend.model.entity.RealEstate;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class RealEstateForSaleFromDtoFactory implements RealEstateFromDtoFactory 
{
    private final RealEstateCreationForSaleDtoMapper realEstateCreationForSaleDtoMapper;



    @Override
    public RealEstate create(RealEstateCreationDto realEstateCreationDto) 
    {
        return realEstateCreationForSaleDtoMapper.toEntity(realEstateCreationDto);
    }


    @Override
    public boolean supports(RealEstateCreationDto realEstateCreationDto) 
    {
        return RealEstateForSaleCreationDto.class.isAssignableFrom(realEstateCreationDto.getClass());
    }
}