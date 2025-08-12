
package com.dietiEstates.backend.util;

import com.dietiEstates.backend.extra.CoordinatesBoundingBox;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;


@UtilityClass
@Slf4j
public class FindByRadiusUtil 
{											
    private final double METERS_PER_DEGREE_OF_LATITUDE = 111320.0;
	private final double METERS_PER_DEGREE_OF_LONGITUDE = 78000.0; 
	    

    public CoordinatesBoundingBox getBoundingBox(Integer radiusMeters, Double centerLatitude, Double centerLongitude)
	{		
		double latitudeVariation = (double) radiusMeters / METERS_PER_DEGREE_OF_LATITUDE;
		double longitudeVariation = (double) radiusMeters / (METERS_PER_DEGREE_OF_LONGITUDE * Math.cos(Math.toRadians(centerLatitude)));

/* 		longitudeVariation = Math.abs(longitudeVariation)/2;
		latitudeVariation = Math.abs(latitudeVariation)/2; */

		double minLatitude = centerLatitude - latitudeVariation;
		double maxLatitude = centerLatitude + latitudeVariation;
		double minLongitude = centerLongitude - longitudeVariation;
		double maxLongitude = centerLongitude + longitudeVariation;

        return new CoordinatesBoundingBox(minLatitude, maxLatitude, minLongitude, maxLongitude);
	}
}