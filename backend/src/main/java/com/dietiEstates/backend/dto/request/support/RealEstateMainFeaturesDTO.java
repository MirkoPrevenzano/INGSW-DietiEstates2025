
package com.dietiEstates.backend.dto.request.support;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import com.dietiEstates.backend.enums.EnergyClass;
import com.dietiEstates.backend.enums.FurnitureCondition;
import com.dietiEstates.backend.enums.PropertyCondition;
import com.dietiEstates.backend.validator.groups.OnCreate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateMainFeaturesDTO 
{
    @NotBlank(groups = OnCreate.class)
    @Size(max = 35)
    private String title;

    @NotBlank(groups = OnCreate.class)
    @Size(min = 250)
    private String description;
    
    @NotNull(groups = OnCreate.class)
    @Positive  
    private Double price;
    
    @NotNull(groups = OnCreate.class)
    @PositiveOrZero    
    private Double condoFee;
    
    @NotNull(groups = OnCreate.class)
    @Positive       
    private Double size;

    @NotNull(groups = OnCreate.class)
    @Positive   
    private Integer roomsNumber;

    @NotNull(groups = OnCreate.class)
    @PositiveOrZero    
    private Integer floorNumber;

    @NotNull(groups = OnCreate.class)
    @PositiveOrZero    
    private Integer parkingSpacesNumber;

    @NotNull(groups = OnCreate.class)
    private EnergyClass energyClass;
    
    @NotNull(groups = OnCreate.class)
    private PropertyCondition propertyCondition;

    @NotNull(groups = OnCreate.class)
    private FurnitureCondition furnitureCondition;
}