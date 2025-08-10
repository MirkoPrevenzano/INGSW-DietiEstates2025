
package com.dietiEstates.backend.factory;

import com.dietiEstates.backend.dto.request.RealEstateCreationDto;
import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.resolver.Supportable;


public interface RealEstateFromDtoFactory extends Supportable<RealEstateCreationDto>
{
    RealEstate create(RealEstateCreationDto dto);
}
