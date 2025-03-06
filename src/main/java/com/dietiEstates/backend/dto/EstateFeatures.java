
package com.dietiEstates.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class EstateFeatures 
{
    private boolean hasAirConditioning;
    private boolean hasHeating;  
    private boolean hasElevator;
    private boolean hasConcierge;
    private boolean hasTerrace;
    private boolean hasGarage;
    private boolean hasBalcony;
    private boolean hasGarden;
    private boolean hasSwimmingPool;
}
