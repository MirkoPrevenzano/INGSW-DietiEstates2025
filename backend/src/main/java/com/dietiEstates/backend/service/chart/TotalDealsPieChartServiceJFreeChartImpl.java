
package com.dietiEstates.backend.service.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.service.chart.enums.ChartDimensions;


@Service
public class TotalDealsPieChartServiceJFreeChartImpl extends ChartServiceJFreeChartTemplate<Agent, DefaultPieDataset<String>> implements TotalDealsPieChartService 
{   
    private static final String CHART_TITLE = "Sales / Rentals Ratio";
    private static final String TOTAL_SALES_LABEL = "Sales";
    private static final String TOTAL_RENTALS_LABEL = "Rentals";
    

    @Override
    protected DefaultPieDataset<String> buildDataset(Agent agent) 
    {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        
        int totalSales = agent.getAgentStats().getTotalSoldRealEstates();
        int totalRentals = agent.getAgentStats().getTotalRentedRealEstates();
        
        if (totalSales > 0 || totalRentals > 0) 
        {
            dataset.setValue(TOTAL_SALES_LABEL, (double) totalSales);
            dataset.setValue(TOTAL_RENTALS_LABEL, (double) totalRentals);
        }
        
        return dataset;
    }
    
    @Override
    protected JFreeChart buildChart(DefaultPieDataset<String> dataset) 
    {
        return ChartFactory.createPieChart(CHART_TITLE,
                                           dataset,
                                           false,
                                           false,
                                           false
        );
    }
    
    @Override
    protected ChartDimensions getChartDimensions() 
    {
        return ChartDimensions.STANDARD_PIE_CHART;
    }

    @Override
    protected void customizeChart(JFreeChart chart) 
    {
        super.customizeChart(chart);
    }
}