
package com.dietiEstates.backend.service.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.enums.MonthLabel;
import com.dietiEstates.backend.enums.ChartType;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.service.mock.MockingStatsService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MonthlyDealsBarChartServiceJFreeChartImpl extends ChartServiceJFreeChartTemplate<Agent, DefaultCategoryDataset> implements MonthlyDealsBarChartService
{
    private final MockingStatsService mockingStatsService;

    
    private static final String CHART_TITLE = "Monthly Deals";
    private static final String X_AXIS_LABEL = "Month";
    private static final String Y_AXIS_LABEL = "Total Deals";
    private static final String DATASET_SERIES = "Sales/Rentals";


    @Override
    protected DefaultCategoryDataset buildDataset(Agent agent) 
    {
        Integer[] monthlyDeals = mockingStatsService.mockBarChartStats(agent);

        DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
        
        for (MonthLabel monthLabel : MonthLabel.getMonthLabels()) 
        {
            defaultCategoryDataset.addValue(monthlyDeals[monthLabel.getIndex()],
                                            DATASET_SERIES, 
                                            monthLabel.getMonthAbbreviation());
        }
        
        return defaultCategoryDataset;
    }

    @Override
    protected JFreeChart buildChart(DefaultCategoryDataset defaultCategoryDataset) 
    {
        return ChartFactory.createBarChart(CHART_TITLE,
                                           X_AXIS_LABEL,
                                           Y_AXIS_LABEL,
                                           defaultCategoryDataset,
                                           PlotOrientation.VERTICAL,
                                           true,
                                           false,
                                           false);
    }

    @Override
    protected ChartType getChartType() 
    {
        return ChartType.MONTHLY_DEALS_BAR_CHART;
    }

    @Override
    protected void customizeChart(JFreeChart chart) 
    {
        super.customizeChart(chart);
    }  
}