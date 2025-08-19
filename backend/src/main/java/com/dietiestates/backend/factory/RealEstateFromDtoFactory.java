
package com.dietiestates.backend.factory;

import com.dietiestates.backend.dto.request.RealEstateCreationDto;
import com.dietiestates.backend.model.entity.RealEstate;
import com.dietiestates.backend.resolver.Supportable;


public interface RealEstateFromDtoFactory extends Supportable<RealEstateCreationDto>
{
    RealEstate create(RealEstateCreationDto dto);
}
