
package com.dietiEstates.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import com.dietiEstates.backend.dto.request.support.AddressDTO;
import com.dietiEstates.backend.dto.request.support.RealEstateBooleanFeaturesDTO;
import com.dietiEstates.backend.dto.request.support.RealEstateLocationFeaturesDTO;
import com.dietiEstates.backend.dto.request.support.RealEstateMainFeaturesDTO;
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
public class RealEstateForRentCreationDTO extends RealEstateCreationDTO
{
    @NotNull(groups = OnCreate.class)
    @PositiveOrZero
    private Double securityDeposit;

    @NotNull(groups = OnCreate.class)
    @PositiveOrZero
    private Integer contractYears; // TODO : cambiare in Double


    public RealEstateForRentCreationDTO(ContractType contractType, AddressDTO addressDTO, RealEstateMainFeaturesDTO realEstateMainFeaturesDTO, 
                                        RealEstateBooleanFeaturesDTO realEstateBooleanFeaturesDTO, RealEstateLocationFeaturesDTO realEstateLocationFeaturesDTO, 
                                        Double securityDeposit, Integer contractYears)
    {
        super(contractType, addressDTO, realEstateMainFeaturesDTO, realEstateBooleanFeaturesDTO, realEstateLocationFeaturesDTO);
        this.securityDeposit = securityDeposit;
        this.contractYears = contractYears;
    }
}