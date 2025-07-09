
package com.dietiEstates.backend.factory;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.request.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.dto.request.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.mapper.RealEstateCreationForRentDTOMapper;
import com.dietiEstates.backend.mapper.RealEstateCreationForSaleDTOMapper;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.model.entity.RealEstateForRent;
import com.dietiEstates.backend.model.entity.RealEstateForSale;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class RealEstateForRentFromDtoFactory implements RealEstateFromDtoFactory 
{
    private final RealEstateCreationForRentDTOMapper realEstateCreationForRentDTOMapper;



    @Override
    public RealEstate create(RealEstateCreationDTO realEstateCreationDTO) 
    {
        RealEstateForRent realEstateForRent = realEstateCreationForRentDTOMapper.toEntity((RealEstateForRentCreationDTO) realEstateCreationDTO);

        return realEstateForRent;
    }


    @Override
    public boolean supports(Class<? extends RealEstateCreationDTO> clazz) 
    {
        return RealEstateForRentCreationDTO.class.isAssignableFrom(clazz);
    }
}