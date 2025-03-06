
package com.dietiEstates.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class EstateDescribe 
{
    @NonNull
    private String description;

    @NonNull
    private String title;
    
    @NonNull
    private Double price;
    
    @NonNull
    private Double condoFee;
    
    @NonNull
    private String energyClass;
    
    @NonNull
    private Double size;

    @NonNull
    private Integer roomsNumber;

    @NonNull
    private String estateCondition;

    @NonNull
    private String furnitureCondition;

    @NonNull
    private Integer parkingSpacesNumber;

    @NonNull
    private Integer floorNumber;
}
