
package com.dietiEstates.backend.enums;


public enum ChartType 
{
    MONTHLY_DEALS_BAR_CHART("Monthly deals bar chart", ChartDimensions.STANDARD_BAR_CHART),
    SUCCESS_RATE_PIE_CHART("Success rate pie chart", ChartDimensions.STANDARD_PIE_CHART),
    TOTAL_DEALS_PIE_CHART("Total deals pie chart", ChartDimensions.STANDARD_PIE_CHART);


    private final String value;
    private final ChartDimensions chartDimensions;


    private ChartType(String value, ChartDimensions chartDimensions)
    {
        this.value = value;
        this.chartDimensions = chartDimensions;
    }


    public String getValue()
    {
        return this.value;
    }

    public ChartDimensions getChartDimensions()
    {
        return this.chartDimensions;
    }
}
