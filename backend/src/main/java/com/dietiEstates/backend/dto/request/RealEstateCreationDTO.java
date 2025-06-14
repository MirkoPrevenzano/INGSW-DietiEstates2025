
package com.dietiEstates.backend.dto.request;

import com.dietiEstates.backend.dto.AddressDTO;
import com.dietiEstates.backend.dto.RealEstateBooleanFeaturesDTO;
import com.dietiEstates.backend.dto.RealEstateLocationFeaturesDTO;
import com.dietiEstates.backend.dto.RealEstateMainFeaturesDTO;

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