
package com.dietiestates.backend.dto.request;

import com.dietiestates.backend.dto.request.support.AddressDto;
import com.dietiestates.backend.dto.request.support.RealEstateBooleanFeaturesDto;
import com.dietiestates.backend.dto.request.support.RealEstateLocationFeaturesDto;
import com.dietiestates.backend.dto.request.support.RealEstateMainFeaturesDto;
import com.dietiestates.backend.enums.ContractType;
import com.dietiestates.backend.enums.NotaryDeedState;
import com.dietiestates.backend.validator.groups.OnCreate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class RealEstateForSaleCreationDto extends RealEstateCreationDto
{
    @NotNull(groups = OnCreate.class)
    private NotaryDeedState notaryDeedState;


    public RealEstateForSaleCreationDto(ContractType contractType, AddressDto addressDto, RealEstateMainFeaturesDto realEstateMainFeaturesDto, 
                                        RealEstateBooleanFeaturesDto realEstateBooleanFeaturesDto, RealEstateLocationFeaturesDto realEstateLocationFeaturesDto, 
                                        NotaryDeedState notaryDeedState)
    {
        super(contractType, addressDto, realEstateMainFeaturesDto, realEstateBooleanFeaturesDto, realEstateLocationFeaturesDto);
        this.notaryDeedState = notaryDeedState;
    }
}