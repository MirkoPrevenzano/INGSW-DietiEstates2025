
package com.dietiestates.backend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentStatsDto 
{
    private int totalUploadedRealEstates;

    private int totalSoldRealEstates;    

    private int totalRentedRealEstates;

    private double salesIncome;
    
    private double rentalsIncome; 
}