
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
}