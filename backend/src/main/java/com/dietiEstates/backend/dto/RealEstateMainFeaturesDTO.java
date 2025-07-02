
package com.dietiEstates.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateMainFeaturesDTO 
{
    @NotBlank
    private String title;

    @NotBlank
    private String description;
    
    @NotNull
    @PositiveOrZero   
    private Double price;
    
    @NotNull
    @PositiveOrZero    
    private Double condoFee;
    
    @NotNull
    @PositiveOrZero    
    private Double size;

    @NotNull
    @PositiveOrZero
    private Integer roomsNumber;

    @NotNull
    @PositiveOrZero    
    private Integer floorNumber;

    @NotNull
    @PositiveOrZero    
    private Integer parkingSpacesNumber;

    @NotBlank
    private String energyClass;
    
    @NotBlank
    private String propertyCondition;

    @NotBlank
    private String furnitureCondition;
}