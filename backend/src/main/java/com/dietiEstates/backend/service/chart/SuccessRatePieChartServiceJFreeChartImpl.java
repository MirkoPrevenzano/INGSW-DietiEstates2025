
package com.dietiEstates.backend.service.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.service.chart.enums.ChartDimensions;


@Service
public class SuccessRatePieChartServiceJFreeChartImpl extends ChartServiceJFreeChartTemplate<Agent, DefaultPieDataset<String>> implements SuccessRatePieChartService
{
    private static final String CHART_TITLE = "Success Rate";
    private static final String COMPLETED_DEALS_LABEL = "Completed Deals";
    private static final String TOTAL_UPLOADED_REAL_ESTATES_LABEL = "Total Real Estates";

    
    @Override
    protected DefaultPieDataset<String> buildDataset(Agent agent) 
    {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        
        int totalUploadedRealEstates = agent.getAgentStats().getTotalUploadedRealEstates();
        int completedDeals = agent.getAgentStats().getCompletedDeals();
        
        dataset.setValue(COMPLETED_DEALS_LABEL, (double) completedDeals);
        dataset.setValue(TOTAL_UPLOADED_REAL_ESTATES_LABEL, (double) totalUploadedRealEstates);

        return dataset;
    }
    
    @Override
    protected JFreeChart buildChart(DefaultPieDataset<String> dataset) 
    {
        return ChartFactory.createPieChart(CHART_TITLE,
                                           dataset,
                                           false,
                                           false,
                                           false);
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