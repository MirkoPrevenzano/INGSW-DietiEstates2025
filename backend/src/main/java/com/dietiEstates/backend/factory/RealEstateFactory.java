
package com.dietiEstates.backend.factory;
 
import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.request.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.dto.request.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.mapper.RealEstateMappingUtil;
import com.dietiEstates.backend.model.entity.RealEstate;

import lombok.RequiredArgsConstructor;



@Component
@RequiredArgsConstructor
public final class RealEstateFactory 
{
    private final RealEstateMappingUtil realEstateMappingUtil;
    
    
    public RealEstate createFromDTO(RealEstateCreationDTO realEstateCreationDTO) 
    {
        if(realEstateCreationDTO instanceof RealEstateForRentCreationDTO)
            return realEstateMappingUtil.fromDto((RealEstateForRentCreationDTO) realEstateCreationDTO);
        else if(realEstateCreationDTO instanceof RealEstateForSaleCreationDTO)
            return realEstateMappingUtil.fromDto((RealEstateForSaleCreationDTO) realEstateCreationDTO);
        else
            return null; // TODO: lanciare eccezione.
    };
}