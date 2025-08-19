
package com.dietiestates.backend.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RealEstateStats 
{
    @Column(name = "views_number", 
            nullable = false,
            updatable = true)
    private long viewsNumber;

    @Column(name = "visits_number", 
            nullable = false,
            updatable = true)
    private long visitsNumber;

    @Column(name = "offers_number", 
            nullable = false,
            updatable = true)
    private long offersNumber;


    public void incrementViewsNumber()
    {
        this.viewsNumber += 1;
    }

    public void incrementVisitsNumber()
    {
        this.visitsNumber += 1;
    }

    public void incrementOffersNumber()
    {
        this.offersNumber += 1;
    }
}