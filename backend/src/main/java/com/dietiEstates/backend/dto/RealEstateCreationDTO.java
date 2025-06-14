
package com.dietiEstates.backend.dto;

import com.dietiEstates.backend.extra.RealEstateMainFeaturesDTO;
import com.dietiEstates.backend.extra.RealEstateBooleanFeaturesDTO;
import com.dietiEstates.backend.extra.RealEstateLocationFeaturesDTO;

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
    private AddressDTO addressDTO;

    @NonNull
    private RealEstateMainFeaturesDTO realEstateMainFeaturesDTO;

    @NonNull
    private RealEstateBooleanFeaturesDTO realEstateBooleanFeaturesDTO;

    @NonNull
    private RealEstateLocationFeaturesDTO realEstateLocationFeaturesDTO;
}