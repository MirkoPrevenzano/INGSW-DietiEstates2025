
package com.dietiEstates.backend.service.support;

import com.dietiEstates.backend.model.entity.Agent;


public interface ChartService 
{
    public void createPieChart(Agent agent);
    public void createPieChart2(Agent agent);
    public void createBarChart();
}