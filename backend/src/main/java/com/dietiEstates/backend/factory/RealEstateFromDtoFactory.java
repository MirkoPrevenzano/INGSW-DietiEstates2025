
package com.dietiEstates.backend.factory;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.model.entity.RealEstate;


public interface RealEstateFromDtoFactory 
{
    RealEstate create(RealEstateCreationDTO dto);

    boolean supports(Class<? extends RealEstateCreationDTO> clazz);
}
