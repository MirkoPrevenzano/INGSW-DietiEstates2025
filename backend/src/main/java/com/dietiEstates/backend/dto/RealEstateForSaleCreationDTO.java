
package com.dietiEstates.backend.dto;

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
public class RealEstateForSaleCreationDTO extends RealEstateCreationDTO
{
    @NonNull
    private String notaryDeedState;   



    public RealEstateForSaleCreationDTO(AddressDTO address, EstateDescribe estateDescribe, 
                                        EstateFeatures estateFeatures, EstateLocationFeatures estateLocationFeatures, String notaryDeedState)
    {
        super(address, estateDescribe, estateFeatures, estateLocationFeatures);
        this.notaryDeedState = notaryDeedState;
    }
}
