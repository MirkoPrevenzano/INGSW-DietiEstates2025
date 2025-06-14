
package com.dietiEstates.backend.dto;

import com.dietiEstates.backend.extra.RealEstateBooleanFeaturesDTO;
import com.dietiEstates.backend.extra.RealEstateLocationFeaturesDTO;
import com.dietiEstates.backend.extra.RealEstateMainFeaturesDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;



@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ToString(callSuper = true)
public class RealEstateForRentCreationDTO extends RealEstateCreationDTO
{
    @NonNull
    private Double securityDeposit;

    @NonNull
    private Integer contractYears;



    public RealEstateForRentCreationDTO(AddressDTO addressDTO, RealEstateMainFeaturesDTO realEstateMainFeaturesDTO, 
                                        RealEstateBooleanFeaturesDTO realEstateBooleanFeaturesDTO, RealEstateLocationFeaturesDTO realEstateLocationFeaturesDTO, 
                                        Double securityDeposit, Integer contractYears)
    {
        super(addressDTO, realEstateMainFeaturesDTO, realEstateBooleanFeaturesDTO, realEstateLocationFeaturesDTO);
        this.securityDeposit = securityDeposit;
        this.contractYears = contractYears;
    }
}