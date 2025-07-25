
package com.dietiEstates.backend.dto.request;

import jakarta.validation.Valid;

import com.dietiEstates.backend.dto.request.support.AddressDTO;
import com.dietiEstates.backend.dto.request.support.RealEstateBooleanFeaturesDTO;
import com.dietiEstates.backend.dto.request.support.RealEstateLocationFeaturesDTO;
import com.dietiEstates.backend.dto.request.support.RealEstateMainFeaturesDTO;

import lombok.Data;
import lombok.NoArgsConstructor;
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