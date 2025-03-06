
package com.dietiEstates.backend.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Embeddable
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class InternalRealEstateFeatures 
{
    @NonNull
    @Column(name = "size", 
            nullable = false, 
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

    @Column(name = "has_air_conditioning",
            nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false")     
    private boolean airConditioning;

    @Column(name = "has_heating",
            nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false")  
    private boolean heating;
}