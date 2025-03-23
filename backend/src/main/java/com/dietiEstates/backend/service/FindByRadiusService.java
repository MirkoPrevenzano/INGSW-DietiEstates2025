
package com.dietiEstates.backend.service;

import org.springframework.stereotype.Service;

import com.dietiEstates.backend.dto.LatLongMinMax;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j
public class FindByRadiusService 
{
    private int distancePerLatitudeGrade = 111320;
	private int distancePerLongitudeGrade = 78000;
    

    public LatLongMinMax calcoloLatLongMinMax(Integer radius, Double lat0, Double long0)
	{		
		System.out.println(radius);
		System.out.println(lat0);
		System.out.println(long0);
		
		double latitudeVariation = (double) radius / distancePerLatitudeGrade;
		double longitudeVariation = (double) radius / distancePerLongitudeGrade * Math.cos(lat0);

		System.out.println("");
		System.out.println("");
		System.out.println(latitudeVariation);
		System.out.println(longitudeVariation);

		double latMin;
		double latMax;
		double longMin;
		double longMax;

		latMin = lat0 - latitudeVariation;
		latMax = lat0 + latitudeVariation;
		longMin = long0 - longitudeVariation;
		longMax = long0 + longitudeVariation;

		System.out.println("");
		System.out.println("");
		System.out.println(latMin);
		System.out.println(latMax);
		System.out.println(longMin);
		System.out.println(longMax);

        return new LatLongMinMax(latMin, latMax, longMin, longMax);
	}
}