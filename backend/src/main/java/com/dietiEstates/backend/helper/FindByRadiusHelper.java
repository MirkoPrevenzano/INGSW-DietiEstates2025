
package com.dietiEstates.backend.helper;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.dietiEstates.backend.extra.CoordinatesMinMax;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Component
@RequiredArgsConstructor
@Slf4j
public class FindByRadiusHelper 
{
    private int distancePerLatitudeGrade = 111320;
	private int distancePerLongitudeGrade = 78000;
    

    public CoordinatesMinMax calcoloLatLongMinMax(Integer radius, Double lat0, Double long0)
	{		
		System.out.println("radius: " + radius);
		System.out.println("lat0: " + lat0);
		System.out.println("long0: " + long0);
		
		double latitudeVariation = (double) radius / distancePerLatitudeGrade;
		double longitudeVariation = (double) radius / (distancePerLongitudeGrade * Math.cos(Math.toRadians(lat0)));
		longitudeVariation = Math.abs(longitudeVariation)/2;
		latitudeVariation = Math.abs(latitudeVariation)/2;
		
		
		/*double longitudeVariation = (double) radius / distancePerLongitudeGrade * Math.cos(lat0);*/

		System.out.println("");
		System.out.println("");
		System.out.println("latitudeVariation: " + latitudeVariation);
		System.out.println("longitudeVariation: " + longitudeVariation);


		double minLatitude;
		double maxLatitude;
		double minLongitude;
		double maxLongitude;

		minLatitude = lat0 - latitudeVariation;
		maxLatitude = lat0 + latitudeVariation;
		minLongitude = long0 - longitudeVariation;
		maxLongitude = long0 + longitudeVariation;

		System.out.println("");
		System.out.println("");
		System.out.println("minLatitude: " + minLatitude);
		System.out.println("maxLatitude: " + maxLatitude);
		System.out.println("minLongitude: " + minLongitude);
		System.out.println("maxLongitude: " + maxLongitude);

        return new CoordinatesMinMax(minLatitude, maxLatitude, minLongitude, maxLongitude);
	}
}

// TODO: Aggiungere Haversine

/*  */