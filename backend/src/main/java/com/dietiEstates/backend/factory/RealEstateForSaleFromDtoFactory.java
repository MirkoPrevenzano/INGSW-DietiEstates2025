
package com.dietiEstates.backend.factory;

import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.request.RealEstateForSaleCreationDTO;
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
    public RealEstate create(RealEstateCreationDTO realEstateCreationDTO) 
    {
        RealEstateForSale realEstateForSale = realEstateCreationForSaleDTOMapper.toEntity((RealEstateForSaleCreationDTO) realEstateCreationDTO);

        return realEstateForSale;
    }
    

    @Override
    public boolean supports(RealEstateCreationDTO realEstateCreationDTO) 
    {
        return RealEstateForSaleCreationDTO.class.isAssignableFrom(realEstateCreationDTO.getClass());
    }
}