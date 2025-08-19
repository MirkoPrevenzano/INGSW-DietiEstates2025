
package com.dietiestates.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import com.dietiestates.backend.dto.AddressDto;
import com.dietiestates.backend.dto.request.support.RealEstateBooleanFeaturesDto;
import com.dietiestates.backend.dto.request.support.RealEstateLocationFeaturesDto;
import com.dietiestates.backend.dto.request.support.RealEstateMainFeaturesDto;
import com.dietiestates.backend.enums.ContractType;
import com.dietiestates.backend.validator.groups.OnCreate;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
public class RealEstateForRentCreationDto extends RealEstateCreationDto
{
    @NotNull(groups = OnCreate.class)
    @PositiveOrZero
    private Double securityDeposit;

    @NotNull(groups = OnCreate.class)
    @PositiveOrZero
    private Integer contractYears;


    public RealEstateForRentCreationDto(ContractType contractType, AddressDto addressDto, RealEstateMainFeaturesDto realEstateMainFeaturesDto, 
                                        RealEstateBooleanFeaturesDto realEstateBooleanFeaturesDto, RealEstateLocationFeaturesDto realEstateLocationFeaturesDto, 
                                        Double securityDeposit, Integer contractYears)
    {
        super(contractType, addressDto, realEstateMainFeaturesDto, realEstateBooleanFeaturesDto, realEstateLocationFeaturesDto);
        this.securityDeposit = securityDeposit;
        this.contractYears = contractYears;
    }
}