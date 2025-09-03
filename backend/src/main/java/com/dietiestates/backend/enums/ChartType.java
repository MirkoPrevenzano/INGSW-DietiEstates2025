
package com.dietiestates.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum ChartType 
{
    MONTHLY_DEALS_BAR_CHART("Monthly deals bar chart", ChartDimensions.STANDARD_BAR_CHART),

    SUCCESS_RATE_PIE_CHART("Success rate pie chart", ChartDimensions.STANDARD_PIE_CHART),

    TOTAL_DEALS_PIE_CHART("Total deals pie chart", ChartDimensions.STANDARD_PIE_CHART);



    private final String value;

    private final ChartDimensions chartDimensions;
}
