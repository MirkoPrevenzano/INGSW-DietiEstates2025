
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
    
    @Column(nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false")    
    private boolean elevator;

    @Column(nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false")     
    private boolean concierge;

    @Column(nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false") 
    private boolean terrace;

    @Column(nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false") 
    private boolean garage;

    @Column(nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false") 
    private boolean balcony;

    @Column(nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false") 
    private boolean garden;

    @Column(name = "swimming_pool",
            nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false") 
    private boolean swimmingPool;

    @Column(name = "near_park",
            nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false")     
    private boolean nearPark;

    @Column(name = "near_school",
            nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false")   
    private boolean nearSchool; 

    @Column(name = "near_public_transport",
            nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false")   
    private boolean nearPublicTransport;
}