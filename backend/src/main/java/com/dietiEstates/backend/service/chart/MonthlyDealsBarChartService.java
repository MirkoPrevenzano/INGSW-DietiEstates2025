
package com.dietiEstates.backend.service.chart;

import com.dietiEstates.backend.model.entity.Agent;


public interface MonthlyDealsBarChartService extends ChartService<Agent>
{
    @Override
    byte[] createChart(Agent data);
}