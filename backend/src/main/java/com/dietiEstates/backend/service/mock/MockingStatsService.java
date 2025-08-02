/* 
package com.dietiEstates.backend.service.mock;

import java.util.Random;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.model.entity.RealEstate;
import com.dietiEstates.backend.model.entity.Agent;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class MockingStatsService
{
    private static Integer[] a = new Integer[12];
    private Random rand = new Random();

    static
    {
        Random rand = new Random();

        for(int i = 0; i <= 11; i++)
            a[i] = rand.nextInt(1,100);
    }


    public void mockAgentStats(Agent agent)
    {        
        int randomTotalUploadedRealEstates = rand.nextInt(25,50);
        int randomTotalSoldRealEstates;
        int randomTotalRentedRealEstates;

        log.info(agent.getName());
        if(agent.getName().length() >= 5)
        {
            randomTotalSoldRealEstates = randomTotalUploadedRealEstates / rand.nextInt(2,6);
            randomTotalRentedRealEstates = randomTotalSoldRealEstates - rand.nextInt(1,5);
        }
        else
        {
            randomTotalRentedRealEstates = randomTotalUploadedRealEstates / rand.nextInt(2,6);
            randomTotalSoldRealEstates = randomTotalRentedRealEstates - rand.nextInt(1,5);
        }

        agent.getAgentStats().setTotalUploadedRealEstates(randomTotalUploadedRealEstates);
        agent.getAgentStats().setTotalSoldRealEstates(randomTotalSoldRealEstates);
        agent.getAgentStats().setTotalRentedRealEstates(randomTotalRentedRealEstates);
        agent.getAgentStats().setSalesIncome(randomTotalSoldRealEstates * 50000.99);
        agent.getAgentStats().setRentalsIncome(randomTotalRentedRealEstates * 1000.99);
    }

    public void mockEstateStats(RealEstate realEstate)
    {        
        long randomViewsNumber = rand.nextLong(100,150);
        long randomOffersNumer = randomViewsNumber-50;
        long randomVisitsNumber = randomViewsNumber-100;

        realEstate.getRealEstateStats().setViewsNumber(randomViewsNumber);
        realEstate.getRealEstateStats().setOffersNumber(randomOffersNumer);
        realEstate.getRealEstateStats().setVisitsNumber(randomVisitsNumber);
    }

    public Integer[] mockBarChartStats()
    {       
        return a;
    }
} */

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
    private final Random rand = new Random();

    
    public void mockAgentStats(Agent agent) 
    {
        int totalUploaded = rand.nextInt(25, 51);
        int totalSold = rand.nextInt(5, totalUploaded / 3);
        int totalRented = rand.nextInt(3, totalUploaded / 4);
        
        agent.getAgentStats().setTotalUploadedRealEstates(totalUploaded);
        agent.getAgentStats().setTotalSoldRealEstates(totalSold);
        agent.getAgentStats().setTotalRentedRealEstates(totalRented);
        
        double avgSalePrice = rand.nextDouble(50000, 500000);
        double avgRentPrice = rand.nextDouble(500, 1500);
        
        agent.getAgentStats().setSalesIncome(totalSold * avgSalePrice);
        agent.getAgentStats().setRentalsIncome(totalRented * avgRentPrice * 12);
    }
    
    public void mockEstateStats(RealEstate realEstate) 
    {
        long views = rand.nextLong(50, 500);
        long visits = rand.nextLong(5, Math.min(views / 10, 50));
        long offers = rand.nextLong(0, Math.min(visits, 10));
        
        realEstate.getRealEstateStats().setViewsNumber(views);
        realEstate.getRealEstateStats().setVisitsNumber(visits);
        realEstate.getRealEstateStats().setOffersNumber(offers);
    }
    
    public Integer[] mockBarChartStats() 
    {
        Integer[] monthlyData = new Integer[12];

        for (int i = 0; i < 12; i++) 
            monthlyData[i] = rand.nextInt(0, 5);
        
        return monthlyData;
    }
/*    public Integer[] mockBarChartStats(Agent agent) {
    Integer[] monthlyData = new Integer[12];
    AgentStats stats = agent.getAgentStats();
    
    int totalSold = stats.getTotalSoldRealEstates();
    int totalRented = stats.getTotalRentedRealEstates();
    int totalOperations = totalSold + totalRented;
    
    int remaining = totalOperations;
    for (int i = 0; i < 11; i++) {
        int maxForThisMonth = Math.min(remaining / (12 - i) + 2, remaining);
        int thisMonth = remaining > 0 ? rand.nextInt(0, maxForThisMonth + 1) : 0;
        monthlyData[i] = thisMonth;
        remaining -= thisMonth;
    }
    monthlyData[11] = remaining;
    
    return monthlyData; */
}