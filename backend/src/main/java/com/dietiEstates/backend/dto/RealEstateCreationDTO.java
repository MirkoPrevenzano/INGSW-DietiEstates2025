
package com.dietiEstates.backend.dto;

import com.dietiEstates.backend.model.Address;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class RealEstateCreationDTO
{
    @NonNull
    private AddressDTO address;

    @NonNull
    private EstateDescribe estateDescribe;

    @NonNull
    private EstateFeatures estateFeatures;

    @NonNull
    private EstateLocationFeatures estateLocationFeatures;
}

