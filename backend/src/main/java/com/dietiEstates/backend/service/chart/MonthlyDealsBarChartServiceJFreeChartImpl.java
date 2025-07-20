
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

import com.dietiEstates.backend.service.mock.MockingStatsService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class MonthlyDealsBarChartServiceJFreeChartImpl implements MonthlyDealsBarChartService
{
    private final MockingStatsService mockingStatsService;



    @Override
    public byte[] createChart(Void data) 
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

        Integer[] valuePerMonth = mockingStatsService.mockBarChartStats();

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
}