
package com.dietiEstates.backend.dto;

import com.dietiEstates.backend.extra.RealEstateBooleanFeatures;
import com.dietiEstates.backend.extra.RealEstateLocationFeatures;
import com.dietiEstates.backend.extra.RealEstateMainFeatures;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;



@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@ToString(callSuper = true)
public class RealEstateForSaleCreationDTO extends RealEstateCreationDTO
{
    @NonNull
    private String notaryDeedState;   



    public RealEstateForSaleCreationDTO(AddressDTO address, RealEstateMainFeatures realEstateMainFeatures, 
                                        RealEstateBooleanFeatures realEstateBooleanFeatures, RealEstateLocationFeatures realEstateLocationFeatures, 
                                        String notaryDeedState)
    {
        super(address, realEstateMainFeatures, realEstateBooleanFeatures, realEstateLocationFeatures);
        this.notaryDeedState = notaryDeedState;
    }
}