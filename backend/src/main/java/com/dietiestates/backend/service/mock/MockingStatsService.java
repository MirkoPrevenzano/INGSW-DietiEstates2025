
package com.dietiestates.backend.service.mock;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

import com.dietiestates.backend.model.embeddable.AgentStats;
import com.dietiestates.backend.model.entity.Agent;
import com.dietiestates.backend.model.entity.RealEstate;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class MockingStatsService 
{
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();


    
    public void mockAgentStats(Agent agent) 
    {
        int totalUploaded = SECURE_RANDOM.nextInt(25, 51);
        int totalSold = SECURE_RANDOM.nextInt(5, totalUploaded / 3);
        int totalRented = SECURE_RANDOM.nextInt(3, totalUploaded / 4);
        
        agent.getAgentStats().setTotalUploadedRealEstates(totalUploaded);
        agent.getAgentStats().setTotalSoldRealEstates(totalSold);
        agent.getAgentStats().setTotalRentedRealEstates(totalRented);
        
        double avgSalePrice = SECURE_RANDOM.nextDouble(50000, 500000);
        double avgRentPrice = SECURE_RANDOM.nextDouble(500, 1500);
        
        agent.getAgentStats().setSalesIncome(totalSold * avgSalePrice);
        agent.getAgentStats().setRentalsIncome(totalRented * avgRentPrice * 12);
    }
    

    public void mockRealEstateStats(RealEstate realEstate) 
    {
        long views = SECURE_RANDOM.nextLong(50, 500);
        long visits = SECURE_RANDOM.nextLong(5, Math.min(views / 10, 50));
        long offers = SECURE_RANDOM.nextLong(0, Math.min(visits, 10));
        
        realEstate.getRealEstateStats().setViewsNumber(views);
        realEstate.getRealEstateStats().setVisitsNumber(visits);
        realEstate.getRealEstateStats().setOffersNumber(offers);
    }
    

    public Integer[] mockBarChartStats(Agent agent) 
    {
        Integer[] monthlyData = new Integer[12];

        AgentStats agentStats = agent.getAgentStats();
        
        int totalDeals = agentStats.getCompletedDeals();
        
        int remaining = totalDeals;
        for (int i = 0; i < 11; i++) 
        {
            int maxForThisMonth = Math.min(remaining / (12 - i) + 2, remaining);

            int thisMonth = remaining > 0 ? SECURE_RANDOM.nextInt(0, maxForThisMonth + 1) : 0;
            monthlyData[i] = thisMonth;
            
            remaining -= thisMonth;
        }

        monthlyData[11] = remaining;
        
        return monthlyData;
    }
}