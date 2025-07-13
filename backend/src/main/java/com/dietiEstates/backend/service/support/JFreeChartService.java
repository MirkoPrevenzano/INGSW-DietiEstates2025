
package com.dietiEstates.backend.service.support;

import java.awt.Color;
import java.io.*;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.helper.MockingStatsHelper;
import com.dietiEstates.backend.model.entity.Agent;
import com.dietiEstates.backend.service.support.ChartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Slf4j
public class JFreeChartService implements ChartService
{
    private final MockingStatsHelper mockingStatsHelper;

    public void createPieChart(Agent agent)
    {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        if(agent.getAgentStats().getTotalUploadedRealEstates() != 0)
        {
            dataset.setValue("SALES", Double.valueOf(agent.getAgentStats().getTotalSoldRealEstates()));
            dataset.setValue("RENTALS", Double.valueOf(agent.getAgentStats().getTotalRentedRealEstates()));
        }
        else
        {
            dataset.setValue("SALES", Double.valueOf(0));
            dataset.setValue("RENTALS", Double.valueOf(0));
        }

        JFreeChart chart = ChartFactory.createPieChart(
           "Sales/Rentals Ratio",   // chart title
           dataset,          // data
           false,             // include legend
           false,
           false);

        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getPlot().setOutlinePaint(Color.BLACK);

        int width = 450;   /* Width of the image */
        int height = 300;  /* Height of the image */ 
        File pieChart = new File( "PieChart.jpeg" ); 

        try 
        {
            ChartUtils.saveChartAsJPEG( pieChart , chart , width , height );
            // ChartUtils.writeChartAsJPEG(System.out, chart, width, height);
        } 
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void createPieChart2(Agent agent)
    {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        if(agent.getAgentStats().getTotalUploadedRealEstates() != 0)
        {
            dataset.setValue("COMPLETED DEALS", Double.valueOf(agent.getAgentStats().getCompletedDeals()));
            dataset.setValue("TOTAL REAL ESTATES", Double.valueOf(agent.getAgentStats().getTotalUploadedRealEstates()));
        }
        else
        {
            dataset.setValue("COMPLETED DEALS", Double.valueOf(0));
            dataset.setValue("TOTAL REAL ESTATES", Double.valueOf(0));   
        }

        JFreeChart chart = ChartFactory.createPieChart(
           "Success Rate",   // chart title
           dataset,          // data
           false,             // include legend
           false,
           false);

        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getPlot().setOutlinePaint(Color.BLACK);

        int width = 450;   /* Width of the image */
        int height = 300;  /* Height of the image */ 
        File pieChart = new File( "PieChart2.jpeg" ); 

        try 
        {
            ChartUtils.saveChartAsJPEG( pieChart , chart , width , height );
            // ChartUtils.writeChartAsJPEG(System.out, chart, width, height);
        } 
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void createBarChart()
    {
        final String soldRentedEstates = "Sold/Rented Real Estates";

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

        Integer[] valuePerMonth = mockingStatsHelper.mockBarChartStats();

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        dataset.addValue( valuePerMonth[0], soldRentedEstates , january );
        dataset.addValue( valuePerMonth[1], soldRentedEstates , february );
        dataset.addValue( valuePerMonth[2], soldRentedEstates , march );
        dataset.addValue( valuePerMonth[3] , soldRentedEstates , april );
        dataset.addValue( valuePerMonth[4] , soldRentedEstates , may );
        dataset.addValue( valuePerMonth[5] , soldRentedEstates , june );
        dataset.addValue( valuePerMonth[6] , soldRentedEstates , july );
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
        barChart.getPlot().setOutlinePaint(Color.BLACK);

        int width = 500;    /* Width of the image */
        int height = 450;   /* Height of the image */ 
        File barChartFile = new File( "BarChart.jpeg" ); 
        try 
        {
            ChartUtils.saveChartAsJPEG( barChartFile , barChart , width , height );
        } 
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
   }
}
