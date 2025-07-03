
package com.dietiEstates.backend.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Min;
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
    
    @NonNull
    @Column(nullable = false, 
            updatable = true)    
    private Boolean elevator;

    @NonNull
    @Column(nullable = false, 
            updatable = true)     
    private Boolean concierge;

    @NonNull
    @Column(nullable = false, 
            updatable = true) 
    private Boolean terrace;

    @NonNull
    @Column(nullable = false, 
            updatable = true) 
    private Boolean garage;

    @NonNull
    @Column(nullable = false, 
            updatable = true) 
    private Boolean balcony;

    @NonNull
    @Column(nullable = false, 
            updatable = true) 
    private Boolean garden;

    @NonNull
    @Column(name = "swimming_pool",
            nullable = false, 
            updatable = true) 
    private Boolean swimmingPool;

    @NonNull
    @Column(name = "near_park",
            nullable = false, 
            updatable = true)     
    private Boolean nearPark;

    @NonNull
    @Column(name = "near_school",
            nullable = false, 
            updatable = true)   
    private Boolean nearSchool; 

    @NonNull
    @Column(name = "near_public_transport",
            nullable = false, 
            updatable = true)   
    private Boolean nearPublicTransport;
}