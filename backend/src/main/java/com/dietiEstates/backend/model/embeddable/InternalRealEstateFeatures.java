
package com.dietiEstates.backend.model.embeddable;

import com.dietiEstates.backend.enums.PropertyCondition;
import com.dietiEstates.backend.enums.FurnitureCondition;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternalRealEstateFeatures 
{
    @Column(nullable = false, 
            updatable = true)
    private Double size;

    @Column(name = "rooms_number", 
            nullable = false, 
            updatable = true)
    private Integer roomsNumber;

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
    private Boolean airConditioning;

    @Column(nullable = false, 
            updatable = true)
    private Boolean heating;
}