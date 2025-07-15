
package com.dietiEstates.backend.validator;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;



public class RealEstateFiltersValidatorImpl implements ConstraintValidator<RealEstateFiltersValidator, Map<String,String>> 
{

    @Override
    public boolean isValid(Map<String,String> filters, ConstraintValidatorContext context) 
    {
        for(Map.Entry<String,String> entry : filters.entrySet())
        {
            if(entry.getKey().equals("minPrice"))
            {
                Double.valueOf(entry.getValue())
            } 

            if(entry.getKey().equals("maxPrice"))
            {
                Double.valueOf(entry.getValue())));
            } 

            if(entry.getKey().equals("energyClass"))
            {
                Ener
                entry.getValue().eq
            } 

            if(entry.getKey().equals("rooms"))
            {
                predicates.add(criteriaBuilder.gt(roomsNumber, Integer.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasAirConditioning"))
            {
                predicates.add(criteriaBuilder.equal(airConditioning, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasHeating"))
            {
                predicates.add(criteriaBuilder.equal(heating, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasElevator"))
            {
                predicates.add(criteriaBuilder.equal(elevator, Boolean.valueOf(entry.getValue())));
            }

            if(entry.getKey().equals("hasConcierge"))
            {
                predicates.add(criteriaBuilder.equal(concierge, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasTerrace"))
            {
                predicates.add(criteriaBuilder.equal(terrace, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasGarage"))
            {
                predicates.add(criteriaBuilder.equal(garage, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasBalcony"))
            {
                predicates.add(criteriaBuilder.equal(balcony, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasGarden"))
            {
                predicates.add(criteriaBuilder.equal(garden, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("hasSwimmingPool"))
            {
                predicates.add(criteriaBuilder.equal(swimmingPool, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("isNearPark"))
            {
                predicates.add(criteriaBuilder.equal(nearPark, Boolean.valueOf(entry.getValue())));
            } 

            if(entry.getKey().equals("isNearSchool"))
            {
                predicates.add(criteriaBuilder.equal(nearSchool, Boolean.valueOf(entry.getValue())));
            }  

            if(entry.getKey().equals("isNearPublicTransport"))
            {
                predicates.add(criteriaBuilder.equal(nearPublicTransport, Boolean.valueOf(entry.getValue())));
            }  
    }
}