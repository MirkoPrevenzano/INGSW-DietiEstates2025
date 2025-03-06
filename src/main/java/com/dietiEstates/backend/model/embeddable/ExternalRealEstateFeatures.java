
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
    
    @Column(name = "has_elevator",
            nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false")    
    private boolean elevator;

    @Column(name = "has_concierge",
            nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false")     
    private boolean concierge;

    @Column(name = "has_terrace",
            nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false") 
    private boolean terrace;

    @Column(name = "has_garage",
            nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false") 
    private boolean garage;

    @Column(name = "has_balcony",
            nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false") 
    private boolean balcony;

    @Column(name = "has_garden",
            nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false") 
    private boolean garden;

    @Column(name = "has_swimming_pool",
            nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false") 
    private boolean swimmingPool;

    @Column(name = "is_near_park",
            nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false")     
    private boolean nearPark;

    @Column(name = "is_near_school",
            nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false")   
    private boolean nearSchool; 

    @Column(name = "is_near_public_transport",
            nullable = true, 
            updatable = true,
            columnDefinition = "boolean default false")   
    private boolean nearPublicTransport;
}