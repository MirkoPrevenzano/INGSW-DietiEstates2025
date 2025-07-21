
package com.dietiEstates.backend.dto.request.support;

import lombok.Data;
import lombok.NoArgsConstructor;

import com.dietiEstates.backend.validator.groups.OnCreate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateLocationFeaturesDTO
{
    @NotNull
    private Boolean nearPark;

    @NotNull
    private Boolean nearSchool;
    
    @NotNull
    private Boolean nearPublicTransport;   
}