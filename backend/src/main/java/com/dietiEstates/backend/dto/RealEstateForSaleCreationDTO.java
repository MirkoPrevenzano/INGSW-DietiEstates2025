
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
public class RealEstateForSaleCreationDTO extends RealEstateCreationDTO
{
    @NonNull
    private String notaryDeedState;   



    public RealEstateForSaleCreationDTO(AddressDTO addressDTO, RealEstateMainFeaturesDTO realEstateMainFeaturesDTO, 
                                        RealEstateBooleanFeaturesDTO realEstateBooleanFeaturesDTO, RealEstateLocationFeaturesDTO realEstateLocationFeaturesDTO, 
                                        String notaryDeedState)
    {
        super(addressDTO, realEstateMainFeaturesDTO, realEstateBooleanFeaturesDTO, realEstateLocationFeaturesDTO);
        this.notaryDeedState = notaryDeedState;
    }
}