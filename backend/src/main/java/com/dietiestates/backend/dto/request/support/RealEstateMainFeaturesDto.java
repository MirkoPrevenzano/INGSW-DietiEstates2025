
package com.dietiestates.backend.dto.request.support;

import com.dietiestates.backend.enums.EnergyClass;
import com.dietiestates.backend.enums.FurnitureCondition;
import com.dietiestates.backend.enums.PropertyCondition;
import com.dietiestates.backend.validator.groups.OnCreate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateMainFeaturesDto 
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