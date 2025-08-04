
package com.dietiEstates.backend.service.chart;

import java.awt.Color;

import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.model.entity.Agent;


@Service
public class SuccessRatePieChartServiceJFreeChartImpl implements SuccessRatePieChartService
{

    @Override
    public byte[] createChart(Agent agent) 
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

        int width = 450; /* Width of the image */
        int height = 300;  /* Height of the image */ 
        File pieChart = new File( "backend/src/main/resources/PieChart2.jpeg" ); 

        byte[] i = null;
         try 
        { 
            ChartUtils.saveChartAsJPEG( pieChart , chart , width , height );
            i = ChartUtils.encodeAsPNG(chart.createBufferedImage(width, height));
         } 
        catch (IOException e) {
            e.printStackTrace();
        } 

        return i;    
    }
    
}
