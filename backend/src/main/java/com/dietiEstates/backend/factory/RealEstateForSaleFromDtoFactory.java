
package com.dietiEstates.backend.factory;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.request.RealEstateCreationDto;
import com.dietiEstates.backend.dto.request.RealEstateForSaleCreationDto;
import com.dietiEstates.backend.mapper.RealEstateCreationForSaleDTOMapper;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.model.entity.RealEstateForSale;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class RealEstateForSaleFromDtoFactory implements RealEstateFromDtoFactory 
{
    private final RealEstateCreationForSaleDTOMapper realEstateCreationForSaleDTOMapper;


    @Override
    public RealEstate create(RealEstateCreationDto realEstateCreationDto) 
    {
        RealEstateForSale realEstateForSale = (RealEstateForSale) realEstateCreationForSaleDTOMapper.toEntity((RealEstateForSaleCreationDto) realEstateCreationDto);

        return realEstateForSale;
    }
    

    @Override
    public boolean supports(RealEstateCreationDto realEstateCreationDto) 
    {
        return RealEstateForSaleCreationDto.class.isAssignableFrom(realEstateCreationDto.getClass());
    }
}