
package com.dietiEstates.backend.dto;

import com.dietiEstates.backend.extra.RealEstateBooleanFeatures;
import com.dietiEstates.backend.extra.RealEstateLocationFeatures;
import com.dietiEstates.backend.extra.RealEstateMainFeatures;
import com.dietiEstates.backend.model.Address;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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



    public RealEstateForRentCreationDTO(AddressDTO address, RealEstateMainFeatures realEstateMainFeatures, 
                                        RealEstateBooleanFeatures realEstateBooleanFeatures, RealEstateLocationFeatures realEstateLocationFeatures, 
                                        Double securityDeposit, Integer contractYears)
    {
        super(address, realEstateMainFeatures, realEstateBooleanFeatures, realEstateLocationFeatures);
        this.securityDeposit = securityDeposit;
        this.contractYears = contractYears;
    }
}
