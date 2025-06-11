
package com.dietiEstates.backend.factory;
 
import org.springframework.stereotype.Component;
import com.dietiEstates.backend.dto.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.dto.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.util.RealEstateMappingUtil;

import lombok.RequiredArgsConstructor;



@Component
@RequiredArgsConstructor
public class RealEstateFactory 
{
    private final RealEstateMappingUtil realEstateMappingUtil;
    
    
    public RealEstate createFromDTO(RealEstateCreationDTO realEstateCreationDTO) 
    {
        if(realEstateCreationDTO instanceof RealEstateForRentCreationDTO)
            return realEstateMappingUtil.realEstateForRentMapper((RealEstateForRentCreationDTO) realEstateCreationDTO);
        else if(realEstateCreationDTO instanceof RealEstateForSaleCreationDTO)
            return realEstateMappingUtil.realEstateForSaleMapper((RealEstateForSaleCreationDTO) realEstateCreationDTO);
        else
            return null;
    };
}