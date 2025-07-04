
package com.dietiEstates.backend.dto.request.support;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateBooleanFeaturesDTO 
{
    private boolean airConditioning;

    private boolean heating;  

    private boolean elevator;

    private boolean concierge;

    private boolean terrace;

    private boolean garage;

    private boolean balcony;

    private boolean garden;

    private boolean swimmingPool;
}