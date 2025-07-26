
package com.dietiEstates.backend.factory;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.request.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.mapper.RealEstateCreationForRentDTOMapper;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.model.entity.RealEstateForRent;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class RealEstateForRentFromDtoFactory implements RealEstateFromDtoFactory 
{
    private final RealEstateCreationForRentDTOMapper realEstateCreationForRentDTOMapper;


    @Override
    public RealEstate create(RealEstateCreationDTO realEstateCreationDTO) 
    {
        RealEstateForRent realEstateForRent = (RealEstateForRent) realEstateCreationForRentDTOMapper.toEntity((RealEstateForRentCreationDTO) realEstateCreationDTO);

        return realEstateForRent;
    }


    @Override
    public boolean supports(RealEstateCreationDTO realEstateCreationDTO) 
    {
        return RealEstateForRentCreationDTO.class.isAssignableFrom(realEstateCreationDTO.getClass());
    }
}