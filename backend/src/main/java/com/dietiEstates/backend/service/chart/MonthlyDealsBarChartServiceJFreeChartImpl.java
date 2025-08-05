

package com.dietiEstates.backend.service.chart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.service.chart.enums.BarChartMonthLabel;
import com.dietiEstates.backend.service.chart.enums.ChartDimensions;
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

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (BarChartMonthLabel month : BarChartMonthLabel.getAllMonths()) 
        {
            dataset.addValue(monthlyDeals[month.getIndex()],
                             DATASET_SERIES, 
                             month.getLabel());
        }
        
        return dataset;
    }

    @Override
    protected JFreeChart buildChart(DefaultCategoryDataset dataset) 
    {
        return ChartFactory.createBarChart(CHART_TITLE,
                                           X_AXIS_LABEL,
                                           Y_AXIS_LABEL,
                                           dataset,
                                           PlotOrientation.VERTICAL,
                                           true,
                                           false,
                                           false);
    }

    @Override
    protected ChartDimensions getChartDimensions() 
    {
        return ChartDimensions.STANDARD_BAR_CHART;
    }

    @Override
    protected void customizeChart(JFreeChart chart) 
    {
        super.customizeChart(chart);
    }  
}




/* 
package com.dietiEstates.backend.service.chart;

import java.awt.Color;

import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.service.mock.MockingStatsService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MonthlyDealsBarChartServiceJFreeChartImpl implements MonthlyDealsBarChartService
{
    private final MockingStatsService mockingStatsService;



    @Override
    public byte[] createChart(Agent agent) 
    {
        final String soldRentedEstates = "Sold/Rented Real Estates";
        final String boh = "boh Real Estates";
        final String boh2 = "boh2 Real Estates";
        final String boh3 = "boh3 Real Estates";

        final String january = "JAN";
        final String february = "FEB";
        final String march = "MAR";
        final String april = "APR";
        final String may = "MAY";        
        final String june = "JUN";
        final String july = "JUL";
        final String august = "AUG";
        final String september = "SEP";
        final String october = "OCT";
        final String november = "NOV";
        final String december = "DEC";

        Integer[] valuePerMonth = mockingStatsService.mockBarChartStats(agent);

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        dataset.addValue( valuePerMonth[0], soldRentedEstates , january );
        dataset.addValue( valuePerMonth[0], boh , january );
        dataset.addValue( valuePerMonth[0], boh2 , january );
        dataset.addValue( valuePerMonth[0], boh3 , january );
        dataset.addValue( valuePerMonth[1], soldRentedEstates , january );
        dataset.addValue( valuePerMonth[1], boh , february );
        dataset.addValue( valuePerMonth[1], boh2 , february );
        dataset.addValue( valuePerMonth[1], boh3 , february );
        dataset.addValue( valuePerMonth[2], soldRentedEstates , march );
        dataset.addValue( valuePerMonth[3] , soldRentedEstates , april );
        dataset.addValue( valuePerMonth[4] , soldRentedEstates , may );
        dataset.addValue( valuePerMonth[5] , soldRentedEstates , june );
        dataset.addValue( valuePerMonth[6] , boh , july );
        dataset.addValue( valuePerMonth[7] , soldRentedEstates , august );
        dataset.addValue( valuePerMonth[8] , soldRentedEstates , september );
        dataset.addValue( valuePerMonth[9] , soldRentedEstates , october );
        dataset.addValue( valuePerMonth[10] , soldRentedEstates , november );
        dataset.addValue( valuePerMonth[11] , soldRentedEstates , december );


        JFreeChart barChart = ChartFactory.createBarChart(
            "MIRKOS", 
            "MONTH", "TOTAL", 
            dataset,PlotOrientation.VERTICAL, 
            true, true, false);
            
        barChart.getPlot().setBackgroundPaint(Color.WHITE);
       // barChart.getPlot().setOutlinePaint(Color.BLACK);

        int width = 500;    
        int height = 450;   
        File barChartFile = new File( "backend/src/main/resources/BarChart.jpeg" ); 
        byte[] b = null;
        try 
        {
            //ChartUtils.saveChartAsJPEG( barChartFile , barChart , width , height );
           b =  ChartUtils.encodeAsPNG(barChart.createBufferedImage(width, height));
        } 
        catch (IOException e) {
            e.printStackTrace();
        }

        return b;
    }   
} */