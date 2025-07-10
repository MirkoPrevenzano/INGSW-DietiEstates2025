
package com.dietiEstates.backend.factory;

import com.dietiEstates.backend.dto.request.RealEstateCreationDTO;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.resolver.Supportable;


public interface RealEstateFromDtoFactory extends Supportable<RealEstateCreationDTO>
{
    RealEstate create(RealEstateCreationDTO dto);

    //boolean supports(Class<? extends RealEstateCreationDTO> clazz);
}
