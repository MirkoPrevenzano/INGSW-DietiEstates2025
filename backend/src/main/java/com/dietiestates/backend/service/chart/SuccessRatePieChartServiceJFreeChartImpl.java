
package com.dietiestates.backend.service.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;

import com.dietiestates.backend.enums.ChartType;
import com.dietiestates.backend.model.entity.Agent;


@Service
public class SuccessRatePieChartServiceJFreeChartImpl extends ChartServiceJFreeChartTemplate<Agent, DefaultPieDataset<String>> implements SuccessRatePieChartService
{
    private static final String CHART_TITLE = "Success Rate";
    private static final String COMPLETED_DEALS_LABEL = "Completed Deals";
    private static final String TOTAL_UPLOADED_REAL_ESTATES_LABEL = "Total Real Estates";

    
    @Override
    protected DefaultPieDataset<String> buildDataset(Agent agent) 
    {
        DefaultPieDataset<String> defaultPieDataset = new DefaultPieDataset<>();
        
        int totalUploadedRealEstates = agent.getAgentStats().getTotalUploadedRealEstates();
        int completedDeals = agent.getAgentStats().getCompletedDeals();
        
        defaultPieDataset.setValue(COMPLETED_DEALS_LABEL, (double) completedDeals);
        defaultPieDataset.setValue(TOTAL_UPLOADED_REAL_ESTATES_LABEL, (double) totalUploadedRealEstates);

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
        return ChartType.SUCCESS_RATE_PIE_CHART;
    }
    
    @Override
    protected void customizeChart(JFreeChart chart) 
    {
        super.customizeChart(chart);
    }
}