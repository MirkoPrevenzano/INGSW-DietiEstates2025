
package com.dietiEstates.backend.dto;

import com.dietiEstates.backend.extra.RealEstateMainFeatures;
import com.dietiEstates.backend.extra.RealEstateBooleanFeatures;
import com.dietiEstates.backend.extra.RealEstateLocationFeatures;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class RealEstateCreationDTO
{
    @NonNull
    private AddressDTO address;

    @NonNull
    private RealEstateMainFeatures realEstateMainFeatures;

    @NonNull
    private RealEstateBooleanFeatures realEstateBooleanFeatures;

    @NonNull
    private RealEstateLocationFeatures realEstateLocationFeatures;

}

