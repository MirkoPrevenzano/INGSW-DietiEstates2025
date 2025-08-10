
package com.dietiEstates.backend.dto.request;

import jakarta.validation.constraints.NotNull;

import com.dietiEstates.backend.dto.request.support.AddressDto;
import com.dietiEstates.backend.dto.request.support.RealEstateBooleanFeaturesDto;
import com.dietiEstates.backend.dto.request.support.RealEstateLocationFeaturesDto;
import com.dietiEstates.backend.dto.request.support.RealEstateMainFeaturesDto;
import com.dietiEstates.backend.enums.ContractType;
import com.dietiEstates.backend.enums.NotaryDeedState;
import com.dietiEstates.backend.validator.groups.OnCreate;

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