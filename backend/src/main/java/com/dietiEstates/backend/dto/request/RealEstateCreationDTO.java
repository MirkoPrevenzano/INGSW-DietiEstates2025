
package com.dietiEstates.backend.dto.request;

import com.dietiEstates.backend.dto.AddressDTO;
import com.dietiEstates.backend.dto.RealEstateBooleanFeaturesDTO;
import com.dietiEstates.backend.dto.RealEstateLocationFeaturesDTO;
import com.dietiEstates.backend.dto.RealEstateMainFeaturesDTO;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.AllArgsConstructor;




@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateCreationDTO
{
    @Valid
    private AddressDTO addressDTO;

    @Valid
    private RealEstateMainFeaturesDTO realEstateMainFeaturesDTO;

    @Valid
    private RealEstateBooleanFeaturesDTO realEstateBooleanFeaturesDTO;

    @Valid
    private RealEstateLocationFeaturesDTO realEstateLocationFeaturesDTO;
}