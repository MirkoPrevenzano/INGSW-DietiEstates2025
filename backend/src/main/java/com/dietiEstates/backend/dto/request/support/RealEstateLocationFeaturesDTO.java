
package com.dietiEstates.backend.dto.request.support;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateLocationFeaturesDTO
{
    private Boolean nearPark;

    private Boolean nearSchool;

    private Boolean nearPublicTransport;   
}