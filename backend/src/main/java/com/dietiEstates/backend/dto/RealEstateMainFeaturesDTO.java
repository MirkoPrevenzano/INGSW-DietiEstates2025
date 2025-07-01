
package com.dietiEstates.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.AllArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateMainFeaturesDTO 
{
    @NonNull
    private String title;

    @NonNull
    private String description;
    
    @NonNull
    private Double price;
    
    @NonNull
    private Double condoFee;
    
    @NonNull
    private Double size;

    @NonNull
    private Integer roomsNumber;

    @NonNull
    private Integer floorNumber;

    @NonNull
    private Integer parkingSpacesNumber;

    @NonNull
    private String energyClass;
    
    @NonNull
    private String propertyCondition;

    @NonNull
    private String furnitureCondition;
}