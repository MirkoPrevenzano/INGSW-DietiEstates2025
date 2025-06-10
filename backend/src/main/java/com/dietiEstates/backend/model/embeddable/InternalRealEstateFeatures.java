
package com.dietiEstates.backend.model.embeddable;

import com.dietiEstates.backend.enums.EstateCondition;
import com.dietiEstates.backend.enums.FurnitureCondition;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;



@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class InternalRealEstateFeatures 
{
    @NonNull
    @Column(nullable = false, 
            updatable = true)
    private Double size;

    @NonNull
    @Column(name = "rooms_number", 
            nullable = false, 
            updatable = true)
    private Integer roomsNumber;

    @NonNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "estate_condition", 
            nullable = false, 
            updatable = true)
    private EstateCondition estateCondition;

    @NonNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "furniture_condition", 
            nullable = false, 
            updatable = true)
    private FurnitureCondition furnitureCondition;

    @NonNull
    @Column(name = "air_conditioning",
            nullable = true, 
            updatable = true)   
    private Boolean airConditioning;

    @NonNull
    @Column(nullable = true, 
            updatable = true)
    private Boolean heating;
}