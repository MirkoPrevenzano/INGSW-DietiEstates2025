
package com.dietiestates.backend.service.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;

import com.dietiestates.backend.enums.ChartType;
import com.dietiestates.backend.model.entity.Agent;


@Service
public class TotalDealsPieChartServiceJFreeChartImpl extends ChartServiceJFreeChartTemplate<Agent, DefaultPieDataset<String>> implements TotalDealsPieChartService 
{   
    private static final String CHART_TITLE = "Sales / Rentals Ratio";
    private static final String TOTAL_SALES_LABEL = "Sales";
    private static final String TOTAL_RENTALS_LABEL = "Rentals";
    

    @Override
    protected DefaultPieDataset<String> buildDataset(Agent agent) 
    {
        DefaultPieDataset<String> defaultPieDataset = new DefaultPieDataset<>();
        
        int totalSales = agent.getAgentStats().getTotalSoldRealEstates();
        int totalRentals = agent.getAgentStats().getTotalRentedRealEstates();

        defaultPieDataset.setValue(TOTAL_SALES_LABEL, totalSales);
        defaultPieDataset.setValue(TOTAL_RENTALS_LABEL, totalRentals);
        
        return defaultPieDataset;
    }
    
    @Override
    protected JFreeChart buildChart(DefaultPieDataset<String> defaultPieDataset) 
    {
        return ChartFactory.createPieChart(CHART_TITLE,
                                           defaultPieDataset,
                                           false,
                                           false,
                                           false);
    }
    
    @Override
    protected ChartType getChartType() 
    {
        return ChartType.TOTAL_DEALS_PIE_CHART;
    }
}