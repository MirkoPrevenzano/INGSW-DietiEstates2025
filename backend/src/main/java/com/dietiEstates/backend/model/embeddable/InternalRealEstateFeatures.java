
package com.dietiEstates.backend.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

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
    @Column(name = "estate_condition", 
            nullable = false, 
            updatable = true)
    private String estateCondition;

    @NonNull
    @Column(name = "furniture_condition", 
            nullable = false, 
            updatable = true)
    private String furnitureCondition;

    @Column(name = "air_conditioning",
            nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false")     
    private boolean airConditioning;

    @Column(nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false")  
    private boolean heating;
}