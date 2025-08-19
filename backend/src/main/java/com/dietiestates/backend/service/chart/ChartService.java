
package com.dietiestates.backend.service.chart;


public interface ChartService<T>
{
    public byte[] createChart(T data);
}