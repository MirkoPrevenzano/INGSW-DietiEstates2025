
package com.dietiEstates.backend.factory;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.request.RealEstateCreationDto;
import com.dietiEstates.backend.dto.request.RealEstateForRentCreationDto;
import com.dietiEstates.backend.mapper.RealEstateCreationForRentDtoMapper;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.model.entity.RealEstateForRent;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class RealEstateForRentFromDtoFactory implements RealEstateFromDtoFactory 
{
    private final RealEstateCreationForRentDtoMapper realEstateCreationForRentDtoMapper;


    @Override
    public RealEstate create(RealEstateCreationDto realEstateCreationDto) 
    {
        RealEstateForRent realEstateForRent = (RealEstateForRent) realEstateCreationForRentDtoMapper.toEntity((RealEstateForRentCreationDto) realEstateCreationDto);

        return realEstateForRent;
    }


    @Override
    public boolean supports(RealEstateCreationDto realEstateCreationDto) 
    {
        return RealEstateForRentCreationDto.class.isAssignableFrom(realEstateCreationDto.getClass());
    }
}