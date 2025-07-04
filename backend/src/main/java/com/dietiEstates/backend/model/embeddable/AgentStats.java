
package com.dietiEstates.backend.model.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;



@Embeddable
@Data
@NoArgsConstructor
public class AgentStats 
{
    @Column(name = "total_uploaded_real_estates", 
            nullable = false,
            updatable = true,
            columnDefinition = "int default 0")
    private int totalUploadedRealEstates;

    @Column(name = "total_sold_real_estates", 
            nullable = false,
            updatable = true,
            columnDefinition = "int default 0")
    private int totalSoldRealEstates;    
    
    @Column(name = "total_rented_real_estates", 
            nullable = false,
            updatable = true,
            columnDefinition = "int default 0")
    private int totalRentedRealEstates;

    @Column(name = "sales_income", 
            nullable = false,
            updatable = true,
            columnDefinition = "double precision default 0")
    private double salesIncome;

    @Column(name = "rentals_income", 
            nullable = false,
            updatable = true,
            columnDefinition = "double precision default 0")
    private double rentalsIncome; 

    @Transient
    private double totalIncomes;  

    @Transient
    private int completedDeals;

    @Transient
    private double successRate;


    
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