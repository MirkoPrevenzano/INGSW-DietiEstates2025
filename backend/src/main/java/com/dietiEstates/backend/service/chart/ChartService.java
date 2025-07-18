
package com.dietiEstates.backend.service.chart;


public interface ChartService<T>
{
    public byte[] createChart(T data);
}
