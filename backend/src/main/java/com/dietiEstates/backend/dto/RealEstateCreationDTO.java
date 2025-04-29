
package com.dietiEstates.backend.dto;

import com.dietiEstates.backend.extra.EstateDescribe;
import com.dietiEstates.backend.extra.EstateFeatures;
import com.dietiEstates.backend.extra.EstateLocationFeatures;

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
    private EstateDescribe estateDescribe;

    @NonNull
    private EstateFeatures estateFeatures;

    @NonNull
    private EstateLocationFeatures estateLocationFeatures;

}

