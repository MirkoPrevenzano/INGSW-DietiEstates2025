
package com.dietiestates.backend.service.chart;

import com.dietiestates.backend.model.entity.Agent;


public interface SuccessRatePieChartService extends ChartService<Agent>
{
    @Override
    byte[] createChart(Agent data);
}