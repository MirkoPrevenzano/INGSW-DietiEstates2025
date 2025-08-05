
package com.dietiEstates.backend.service.chart;

import java.awt.Color;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;
import com.dietiEstates.backend.service.chart.enums.ChartDimensions;
import com.dietiEstates.backend.exception.ChartServiceException;


public abstract class ChartServiceJFreeChartTemplate<T, D extends Dataset> implements ChartService<T>
{
    @Override
    public final byte[] createChart(T data) 
    {
        D dataset = buildDataset(data);
        
        JFreeChart chart = buildChart(dataset);
        
        customizeChart(chart);
        
        return convertChartToBytes(chart, getChartDimensions());
    }

    protected abstract D buildDataset(T data);
    protected abstract JFreeChart buildChart(D dataset);
    protected abstract ChartDimensions getChartDimensions();


    protected void customizeChart(JFreeChart chart) 
    {
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getPlot().setOutlinePaint(Color.BLACK);
    }

    private byte[] convertChartToBytes(JFreeChart chart, ChartDimensions dimensions) 
    {
        try 
        {
            return ChartUtils.encodeAsPNG(chart.createBufferedImage(dimensions.getWidth(), dimensions.getHeight()));
        } 
        catch (IOException e) 
        {
            throw new ChartServiceException("Failed to create chart '" + chart.getTitle().getText() + "'! " + e.getMessage(), e);
        }
    }   
}