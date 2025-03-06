
package com.dietiEstates.backend.dto;

import com.dietiEstates.backend.model.Address;

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
    private Address address;

    @NonNull
    private EstateDescribe estateDescribe;

    @NonNull
    private EstateFeatures estateFeatures;

    @NonNull
    private EstateLocationFeatures estateLocationFeatures;
}
