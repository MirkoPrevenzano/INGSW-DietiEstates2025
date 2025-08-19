
package com.dietiestates.backend.extra;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoordinatesBoundingBox 
{
    private double minLatitude;

    private double maxLatitude;

    private double minLongitude;
    
    private double maxLongitude;
}