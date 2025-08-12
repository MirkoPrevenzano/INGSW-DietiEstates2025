
package com.dietiEstates.backend.service.mock;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.model.embeddable.AgentStats;
import com.dietiEstates.backend.model.entity.Agent;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class MockingStatsService 
{
    private final Random RANDOM = new Random();

    
    public void mockAgentStats(Agent agent) 
    {
        int totalUploaded = RANDOM.nextInt(25, 51);
        int totalSold = RANDOM.nextInt(5, totalUploaded / 3);
        int totalRented = RANDOM.nextInt(3, totalUploaded / 4);
        
        agent.getAgentStats().setTotalUploadedRealEstates(totalUploaded);
        agent.getAgentStats().setTotalSoldRealEstates(totalSold);
        agent.getAgentStats().setTotalRentedRealEstates(totalRented);
        
        double avgSalePrice = RANDOM.nextDouble(50000, 500000);
        double avgRentPrice = RANDOM.nextDouble(500, 1500);
        
        agent.getAgentStats().setSalesIncome(totalSold * avgSalePrice);
        agent.getAgentStats().setRentalsIncome(totalRented * avgRentPrice * 12);
    }
    
    public void mockRealEstateStats(RealEstate realEstate) 
    {
        long views = RANDOM.nextLong(50, 500);
        long visits = RANDOM.nextLong(5, Math.min(views / 10, 50));
        long offers = RANDOM.nextLong(0, Math.min(visits, 10));
        
        realEstate.getRealEstateStats().setViewsNumber(views);
        realEstate.getRealEstateStats().setVisitsNumber(visits);
        realEstate.getRealEstateStats().setOffersNumber(offers);
    }
    
    public Integer[] mockBarChartStats(Agent agent) 
    {
        Integer[] monthlyData = new Integer[12];

        AgentStats stats = agent.getAgentStats();
        
        int totalSales = stats.getTotalSoldRealEstates();
        int totalRentals = stats.getTotalRentedRealEstates();
        int totalDeals = totalSales + totalRentals;
        
        int remaining = totalDeals;
        for (int i = 0; i < 11; i++) 
        {
            int maxForThisMonth = Math.min(remaining / (12 - i) + 2, remaining);
            int thisMonth = remaining > 0 ? RANDOM.nextInt(0, maxForThisMonth + 1) : 0;
            monthlyData[i] = thisMonth;
            remaining -= thisMonth;
        }

        monthlyData[11] = remaining;
        
        return monthlyData;
    }
}