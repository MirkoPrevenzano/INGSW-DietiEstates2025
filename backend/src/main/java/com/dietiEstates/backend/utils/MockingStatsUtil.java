
package com.dietiEstates.backend.utils;

import java.util.Random;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.model.RealEstate;
import com.dietiEstates.backend.model.RealEstateAgent;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class MockingStatsUtil
{
    private static Integer[] a = new Integer[12];
    private Random rand = new Random();

    static
    {
        Random rand = new Random();

        for(int i = 0; i <= 11; i++)
            a[i] = rand.nextInt(1,100);
    }


    public void mockAgentStats(RealEstateAgent realEstateAgent)
    {        
        int randomTotalUploadedRealEstates = rand.nextInt(25,50);
        int randomTotalSoldRealEstates;
        int randomTotalRentedRealEstates;

        log.info(realEstateAgent.getName());
        if(realEstateAgent.getName().length() >= 5)
        {
            randomTotalSoldRealEstates = randomTotalUploadedRealEstates / rand.nextInt(2,6);
            randomTotalRentedRealEstates = randomTotalSoldRealEstates - rand.nextInt(1,5);
        }
        else
        {
            randomTotalRentedRealEstates = randomTotalUploadedRealEstates / rand.nextInt(2,6);
            randomTotalSoldRealEstates = randomTotalRentedRealEstates - rand.nextInt(1,5);
        }

        realEstateAgent.getRealEstateAgentStats().setTotalUploadedRealEstates(randomTotalUploadedRealEstates);
        realEstateAgent.getRealEstateAgentStats().setTotalSoldRealEstates(randomTotalSoldRealEstates);
        realEstateAgent.getRealEstateAgentStats().setTotalRentedRealEstates(randomTotalRentedRealEstates);
        realEstateAgent.getRealEstateAgentStats().setSalesIncome(randomTotalSoldRealEstates * 50000.99);
        realEstateAgent.getRealEstateAgentStats().setRentalsIncome(randomTotalRentedRealEstates * 1000.99);
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