
package com.dietiestates.backend.service.chart;

import com.dietiestates.backend.model.entity.Agent;


public interface MonthlyDealsBarChartService extends ChartService<Agent>
{
    @Override
    byte[] createChart(Agent data);
}