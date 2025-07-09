
package com.dietiEstates.backend.dto.request.support;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateLocationFeaturesDTO
{
    private Boolean nearPark = false;

    private Boolean nearSchool = false;

    private Boolean nearPublicTransport = false;   
}