
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
public class RealEstateForRentCreationDTO extends RealEstateCreationDTO
{
    @NonNull
    private Double securityDeposit;

    @NonNull
    private Integer contractYears;



    public RealEstateForRentCreationDTO(AddressDTO address, EstateDescribe estateDescribe, 
                                        EstateFeatures estateFeatures, EstateLocationFeatures estateLocationFeatures, 
                                        Double securityDeposit, Integer contractYears)
    {
        super(address, estateDescribe, estateFeatures, estateLocationFeatures);
        this.securityDeposit = securityDeposit;
        this.contractYears = contractYears;
    }
}
