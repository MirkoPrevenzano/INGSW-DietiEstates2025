
package com.dietiEstates.backend.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;



@Embeddable
@Data
@NoArgsConstructor
public class RealEstateStats 
{
    @Column(name = "views_number", 
            nullable = false,
            updatable = true,
            columnDefinition = "bigint default 0")
    private long viewsNumber;

    @Column(name = "visits_number", 
            nullable = false,
            updatable = true,
            columnDefinition = "bigint default 0")
    private long visitsNumber;

    @Column(name = "offers_number", 
            nullable = false,
            updatable = true,
            columnDefinition = "bigint default 0")
    private long offersNumber;
}