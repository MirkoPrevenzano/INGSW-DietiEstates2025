
package com.dietiEstates.backend.dto.request.support;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateBooleanFeaturesDTO 
{
    private Boolean airConditioning;

    private Boolean heating;  

    private Boolean elevator;

    private Boolean concierge;

    private Boolean terrace;

    private Boolean garage;

    private Boolean balcony;

    private Boolean garden;

    private Boolean swimmingPool;
}