
package com.dietiEstates.backend.dto.request.support;

import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateLocationFeaturesDto
{
    @NotNull
    private Boolean nearPark;

    @NotNull
    private Boolean nearSchool;
    
    @NotNull
    private Boolean nearPublicTransport;   
}