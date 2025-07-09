
package com.dietiEstates.backend.dto.request.support;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateBooleanFeaturesDTO 
{
    private Boolean airConditioning = false;

    private Boolean heating = false;  

    private Boolean elevator = false;

    private Boolean concierge = false;

    private Boolean terrace = false;

    private Boolean garage = false;

    private Boolean balcony = false;

    private Boolean garden = false;

    private Boolean swimmingPool = false;
}