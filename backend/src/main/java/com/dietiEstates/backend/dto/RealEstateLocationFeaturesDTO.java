
package com.dietiEstates.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.NonNull;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class RealEstateLocationFeaturesDTO
{
    @NonNull
    private Boolean nearPark;

    @NonNull
    private Boolean nearSchool;

    @NonNull
    private Boolean nearPublicTransport;   
}