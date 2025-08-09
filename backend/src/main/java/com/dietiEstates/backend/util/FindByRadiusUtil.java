
package com.dietiEstates.backend.util;

import com.dietiEstates.backend.extra.CoordinatesBoundingBox;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;


@UtilityClass
@Slf4j
public class FindByRadiusUtil 
{												
    private int distancePerLatitudeGrade = 111320;
	private int distancePerLongitudeGrade = 78000;
    

    public CoordinatesBoundingBox getBoundingBox(Integer radius, Double lat0, Double long0)
	{		
		double latitudeVariation = (double) radius / distancePerLatitudeGrade;
		double longitudeVariation = (double) radius / (distancePerLongitudeGrade * Math.cos(Math.toRadians(lat0)));

		longitudeVariation = Math.abs(longitudeVariation)/2;
		latitudeVariation = Math.abs(latitudeVariation)/2;
	

		double minLatitude = lat0 - latitudeVariation;
		double maxLatitude = lat0 + latitudeVariation;
		double minLongitude = long0 - longitudeVariation;
		double maxLongitude = long0 + longitudeVariation;

        return new CoordinatesBoundingBox(minLatitude, maxLatitude, minLongitude, maxLongitude);
	}
}