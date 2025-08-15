
package com.dietiEstates.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum ChartDimensions 
{
    STANDARD_PIE_CHART(450, 300),
    STANDARD_BAR_CHART(500, 450),
    LARGE_PIE_CHART(800, 600),
    LARGE_BAR_CHART(300, 200),
    SMALL_PIE_CHART(800, 600),
    SMALL_BAR_CHART(300, 200);
    
    
    private final int width;
    private final int height;
}