
package com.dietiEstates.backend.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentStats 
{
    @Column(name = "total_uploaded_real_estates", 
            nullable = false,
            updatable = true)
    private int totalUploadedRealEstates;

    @Column(name = "total_sold_real_estates", 
            nullable = false,
            updatable = true)
    private int totalSoldRealEstates;    
    
    @Column(name = "total_rented_real_estates", 
            nullable = false,
            updatable = true)
    private int totalRentedRealEstates;

    @Column(name = "sales_income", 
            nullable = false,
            updatable = true)
    private double salesIncome;

    @Column(name = "rentals_income", 
            nullable = false,
            updatable = true)
    private double rentalsIncome; 


    public void incrementTotalUploadedRealEstates()
    {
        this.totalUploadedRealEstates += 1;
    }
    
    public double getTotalIncomes()
    {
        return rentalsIncome + salesIncome;
    }


    public int getCompletedDeals()
    {
        return totalSoldRealEstates + totalRentedRealEstates;
    }


    public double getSuccessRate()
    {
        if(totalUploadedRealEstates != 0)
            return ((double) (getCompletedDeals()) / totalUploadedRealEstates) * 100;
        else
            return 0;
    }
}