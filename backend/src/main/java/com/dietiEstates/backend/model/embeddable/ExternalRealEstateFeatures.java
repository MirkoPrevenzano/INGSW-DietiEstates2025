
package com.dietiEstates.backend.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExternalRealEstateFeatures 
{
    @NonNull
    @Column(name = "parking_spaces_number", 
            nullable = false, 
            updatable = true)
    private Integer parkingSpacesNumber;

    @NonNull
    @Column(name = "floor_number", 
            nullable = false, 
            updatable = true)
    private Integer floorNumber;
    
    @Column(nullable = false, 
            updatable = true)    
    private boolean elevator;

    @Column(nullable = false, 
            updatable = true)     
    private boolean concierge;

    @Column(nullable = false, 
            updatable = true) 
    private boolean terrace;

    @Column(nullable = false, 
            updatable = true) 
    private boolean garage;

    @Column(nullable = false, 
            updatable = true) 
    private boolean balcony;

    @Column(nullable = false, 
            updatable = true) 
    private boolean garden;

    @Column(name = "swimming_pool",
            nullable = false, 
            updatable = true) 
    private boolean swimmingPool;

    @Column(name = "near_park",
            nullable = false, 
            updatable = true)
    private boolean nearPark;

    @Column(name = "near_school",
            nullable = false, 
            updatable = true)
    private boolean nearSchool; 

    @Column(name = "near_public_transport",
            nullable = false, 
            updatable = true)   
    private boolean nearPublicTransport;
}