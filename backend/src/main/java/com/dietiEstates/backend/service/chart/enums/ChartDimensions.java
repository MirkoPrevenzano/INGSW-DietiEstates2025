
package com.dietiEstates.backend.service.chart.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum ChartDimensions {
    STANDARD_BAR(500, 450),
    STANDARD_PIE(450, 300),
    LARGE(800, 600),
    SMALL(300, 200);
    
    private final int width;
    private final int height;
}