
package com.dietiestates.backend.service.chart;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.dietiestates.backend.enums.ChartType;
import com.dietiestates.backend.exception.ChartServiceException;
import com.dietiestates.backend.model.entity.Agent;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;


public abstract class ChartServiceJFreeChartTemplate<T, D extends Dataset> implements ChartService<T>
{
    @Override
    public final byte[] createChart(T data) 
    {
        D dataset = buildDataset(data);
        
        JFreeChart chart = buildChart(dataset);
        
        customizeChart(chart);

        ChartType chartType = getChartType();
        
        return convertChartToBytes(chart, chartType, data);
    }



    protected abstract D buildDataset(T data);

    protected abstract JFreeChart buildChart(D dataset);

    protected abstract ChartType getChartType();

    
    protected void customizeChart(JFreeChart chart) 
    {
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getPlot().setOutlinePaint(Color.BLACK);
    }



    private byte[] convertChartToBytes(JFreeChart chart, ChartType chartType, T data) 
    {
        try 
        {
            BufferedImage bufferedImage = chart.createBufferedImage(chartType.getChartDimensions().getWidth(), chartType.getChartDimensions().getHeight());
            return ChartUtils.encodeAsPNG(bufferedImage);
        } 
        catch (IOException e) 
        {
            Long agentId = null; 

            if (data instanceof Agent agent)
                agentId = agent.getUserId();
            
            throw new ChartServiceException(chartType, agentId, e);
        }
    }   
}