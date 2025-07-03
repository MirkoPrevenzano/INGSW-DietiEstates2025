
package com.dietiEstates.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import com.dietiEstates.backend.enums.EnergyClass;
import com.dietiEstates.backend.validator.EnumValidator;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateMainFeaturesDTO 
{
    @NotBlank
    @Size(min = 50, max = 100)
    private String title;

    @NotBlank
    @Size(min = 200, max = 500)
    private String description;
    
    @NotNull
    @Positive  
    private Double price;
    
    @NotNull
    @PositiveOrZero    
    private Double condoFee;
    
    @NotNull
    @Positive       
    private Double size;

    @NotNull
    @Positive   
    private Integer roomsNumber;

    @NotNull
    @PositiveOrZero    
    private Integer floorNumber;

    @NotNull
    @PositiveOrZero    
    private Integer parkingSpacesNumber;

    @NotBlank // TODO: custom validator
    @EnumValidator(enumClass = EnergyClass.class)
    private String energyClass;
    
    @NotBlank // TODO: custom validator
    private String propertyCondition;

    @NotBlank // TODO: custom validator
    private String furnitureCondition;
}