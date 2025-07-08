
package com.dietiEstates.backend.factory;
 
import org.springframework.stereotype.Component;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.dto.request.RealEstateForRentCreationDTO;
import com.dietiEstates.backend.dto.request.RealEstateForSaleCreationDTO;
import com.dietiEstates.backend.model.entity.RealEstate;

import lombok.RequiredArgsConstructor;



@Component
public final class RealEstateFactory 
{    
    public RealEstate createFromDTO(RealEstateCreationDTO realEstateCreationDTO) 
    {
        if(realEstateCreationDTO instanceof RealEstateForRentCreationDTO)
            return null;
        else if(realEstateCreationDTO instanceof RealEstateForSaleCreationDTO)
            return null;
        else
            return null; // TODO: lanciare eccezione.
    };
}