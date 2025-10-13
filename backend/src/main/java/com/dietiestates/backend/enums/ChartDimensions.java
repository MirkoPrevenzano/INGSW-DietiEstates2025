
package com.dietiestates.backend.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum ChartDimensions 
{
    STANDARD_PIE_CHART(450, 300),

    STANDARD_BAR_CHART(500, 450),

    LARGE_PIE_CHART(800, 550),

    LARGE_BAR_CHART(850, 750),

    SMALL_PIE_CHART(300, 200),

    SMALL_BAR_CHART(350, 250);
    

    private final int width;
    
    private final int height;
}