
package com.dietiestates.backend.factory;

import org.springframework.stereotype.Component;

import com.dietiestates.backend.dto.request.RealEstateCreationDto;
import com.dietiestates.backend.dto.request.RealEstateForSaleCreationDto;
import com.dietiestates.backend.mapper.RealEstateCreationForSaleDtoMapper;
import com.dietiestates.backend.model.entity.RealEstate;
import com.dietiestates.backend.model.entity.RealEstateForSale;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class RealEstateForSaleFromDtoFactory implements RealEstateFromDtoFactory 
{
    private final RealEstateCreationForSaleDtoMapper realEstateCreationForSaleDtoMapper;


    @Override
    public RealEstate create(RealEstateCreationDto realEstateCreationDto) 
    {
        RealEstateForSale realEstateForSale = (RealEstateForSale) realEstateCreationForSaleDtoMapper.toEntity((RealEstateForSaleCreationDto) realEstateCreationDto);

        return realEstateForSale;
    }
    

    @Override
    public boolean supports(RealEstateCreationDto realEstateCreationDto) 
    {
        return RealEstateForSaleCreationDto.class.isAssignableFrom(realEstateCreationDto.getClass());
    }
}