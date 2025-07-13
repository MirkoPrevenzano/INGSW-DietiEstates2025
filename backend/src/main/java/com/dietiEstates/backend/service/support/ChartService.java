
package com.dietiEstates.backend.service.support;

import java.awt.image.BufferedImage;

import com.dietiEstates.backend.model.entity.Agent;


public interface ChartService 
{
    public byte[] createPieChart(Agent agent);
    public byte[] createPieChart2(Agent agent);
    public void createBarChart();
}