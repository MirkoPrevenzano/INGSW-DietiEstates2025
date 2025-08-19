
package com.dietiestates.backend.model.embeddable;

import com.dietiestates.backend.enums.FurnitureCondition;
import com.dietiestates.backend.enums.PropertyCondition;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternalRealEstateFeatures 
{
    @Column(nullable = false, 
            updatable = true)
    private double size;

    @Column(name = "rooms_number", 
            nullable = false, 
            updatable = true)
    private int roomsNumber;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "estate_condition", 
            nullable = false, 
            updatable = true)
    private PropertyCondition propertyCondition;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "furniture_condition", 
            nullable = false, 
            updatable = true)
    private FurnitureCondition furnitureCondition;

    @Column(name = "air_conditioning",
            nullable = false, 
            updatable = true)
    private boolean airConditioning;

    @Column(nullable = false, 
            updatable = true)
    private boolean heating;
}