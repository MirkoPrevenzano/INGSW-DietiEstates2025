
package com.dietiEstates.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import com.dietiEstates.backend.dto.request.support.AddressDto;
import com.dietiEstates.backend.dto.request.support.RealEstateBooleanFeaturesDto;
import com.dietiEstates.backend.dto.request.support.RealEstateLocationFeaturesDto;
import com.dietiEstates.backend.dto.request.support.RealEstateMainFeaturesDto;
import com.dietiEstates.backend.enums.ContractType;
import com.dietiEstates.backend.validator.groups.OnCreate;

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
    private Integer contractYears; // TODO : cambiare in Double


    public RealEstateForRentCreationDto(ContractType contractType, AddressDto addressDto, RealEstateMainFeaturesDto realEstateMainFeaturesDto, 
                                        RealEstateBooleanFeaturesDto realEstateBooleanFeaturesDto, RealEstateLocationFeaturesDto realEstateLocationFeaturesDto, 
                                        Double securityDeposit, Integer contractYears)
    {
        super(contractType, addressDto, realEstateMainFeaturesDto, realEstateBooleanFeaturesDto, realEstateLocationFeaturesDto);
        this.securityDeposit = securityDeposit;
        this.contractYears = contractYears;
    }
}