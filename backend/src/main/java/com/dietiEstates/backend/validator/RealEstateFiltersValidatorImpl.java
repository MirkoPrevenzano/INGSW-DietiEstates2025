
package com.dietiEstates.backend.validator;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.dietiEstates.backend.enums.EnergyClass;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.val;



public class RealEstateFiltersValidatorImpl implements ConstraintValidator<RealEstateFiltersValidator, Map<String,String>> 
{

    @Override
    public boolean isValid(Map<String,String> filters, ConstraintValidatorContext context) 
    {
        boolean hasExceptionOccurred = false; 

        for(Map.Entry<String,String> entry : filters.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();

            try 
            {
                if(entry.getKey().equals("minPrice"))
                {
                    double minPrice = Double.valueOf(entry.getValue());
    
                    if (minPrice < 0)
                        throw new IllegalArgumentException("Minimum price must be greater or equal than 0");
                } 
    
                if(entry.getKey().equals("maxPrice"))
                {
                    double maxPrice = Double.valueOf(entry.getValue());
    
                    if (maxPrice < 0)
                        throw new IllegalArgumentException("Maximum price must be greater or equal than 0");
                } 
    
                if(entry.getKey().equals("energyClass"))
                {
                    EnergyClass.of(entry.getValue());
                } 
    
                if(entry.getKey().equals("rooms"))
                {
                    int rooms = Integer.valueOf(entry.getValue());
    
                    if (rooms < 0)
                        throw new IllegalArgumentException("Rooms number must be greater or equal than 0");            
                }  
    
                if(entry.getKey().equals("hasAirConditioning"))
                {
                    if (!value.equalsIgnoreCase("true") || !value.equalsIgnoreCase("false"))
                        throw new IllegalArgumentException("Air Conditioning value must be true or false");
                }  
    
                if(entry.getKey().equals("hasHeating"))
                {
                    if (!value.equalsIgnoreCase("true") || !value.equalsIgnoreCase("false"))
                        throw new IllegalArgumentException("Heating value must be true or false");                
                }  
    
                if(entry.getKey().equals("hasElevator"))
                {
                    if (!value.equalsIgnoreCase("true") || !value.equalsIgnoreCase("false"))
                        throw new IllegalArgumentException("Elevator value must be true or false");                
                }
    
                if(entry.getKey().equals("hasConcierge"))
                {
                    if (!value.equalsIgnoreCase("true") || !value.equalsIgnoreCase("false"))
                        throw new IllegalArgumentException("Concierge value must be true or false");                
                }  
    
                if(entry.getKey().equals("hasTerrace"))
                {
                    if (!value.equalsIgnoreCase("true") || !value.equalsIgnoreCase("false"))
                        throw new IllegalArgumentException("Terrace value must be true or false");                
                }  
    
                if(entry.getKey().equals("hasGarage"))
                {
                    if (!value.equalsIgnoreCase("true") || !value.equalsIgnoreCase("false"))
                        throw new IllegalArgumentException("Garage value must be true or false");                
                }  
    
                if(entry.getKey().equals("hasBalcony"))
                {
                    if (!value.equalsIgnoreCase("true") || !value.equalsIgnoreCase("false"))
                        throw new IllegalArgumentException("Balcony value must be true or false");                
                }  
    
                if(entry.getKey().equals("hasGarden"))
                {
                    if (!value.equalsIgnoreCase("true") || !value.equalsIgnoreCase("false"))
                        throw new IllegalArgumentException("Garden value must be true or false");                
                }  
    
                if(entry.getKey().equals("hasSwimmingPool"))
                {
                    if (!value.equalsIgnoreCase("true") || !value.equalsIgnoreCase("false"))
                        throw new IllegalArgumentException("Swimming Pool value must be true or false");                
                }  
            } 
            catch (Exception e) 
            {
                addViolation(context, "Invalid value '" + value + "' for filter: " + key);
                hasExceptionOccurred = true;
                //return false;            
            }
        }

        return hasExceptionOccurred ? false : true;
        //return true;
    }



    private void addViolation(ConstraintValidatorContext context, String message) 
    {
        // Disabilita il messaggio di default dell'annotazione
        context.disableDefaultConstraintViolation();
        
        // Crea un nuovo messaggio personalizzato
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
















/* private static final Set<String> BOOLEAN_FILTERS = Set.of(
    "hasAirConditioning", "hasHeating", "hasElevator", "hasConcierge",
    "hasTerrace", "hasGarage", "hasBalcony", "hasGarden", "hasSwimmingPool",
    "isNearPark", "isNearSchool", "isNearPublicTransport"
);

private static final Set<String> PRICE_FILTERS = Set.of("minPrice", "maxPrice");

@Override
public boolean isValid(Map<String,String> filters, ConstraintValidatorContext context) {
    if (filters == null) return true;
    
    for (Map.Entry<String,String> entry : filters.entrySet()) {
        String key = entry.getKey();
        String value = entry.getValue()
        
        if (value == null || value.trim().isEmpty()) {
            addViolation(context, "Value cannot be empty for filter: " + key);
            return false;
        }
        
        try {
            if (PRICE_FILTERS.contains(key)) {
                // Validazione per prezzi
                Double.valueOf(value);
            } else if (BOOLEAN_FILTERS.contains(key)) {
                // Validazione per filtri booleani
                Boolean.valueOf(value);
            } else if (key.equals("energyClass")) {
                EnergyClass.of(value);
            } else if (key.equals("rooms")) {
                Integer.valueOf(value);
            } else {
                // Filtro sconosciuto
                addViolation(context, "Unknown filter: " + key);
                return false;
            }
        } catch (Exception e) {
            addViolation(context, "Invalid value '" + value + "' for filter: " + key);
            return false;
        }
    }
    return true;
}





private void addViolation(ConstraintValidatorContext context, String message) {
    // Disabilita il messaggio di default dell'annotazione
    context.disableDefaultConstraintViolation();
    
    // Crea un nuovo messaggio personalizzato
    context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
} */