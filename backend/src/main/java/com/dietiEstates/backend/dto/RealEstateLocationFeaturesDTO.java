
package com.dietiEstates.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.AllArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateLocationFeaturesDTO
{
    @NonNull
    private Boolean nearPark;

    @NonNull
    private Boolean nearSchool;

    @NonNull
    private Boolean nearPublicTransport;   
}