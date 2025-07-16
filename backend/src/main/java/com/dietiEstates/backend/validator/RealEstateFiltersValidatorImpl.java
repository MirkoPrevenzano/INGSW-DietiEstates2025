
package com.dietiEstates.backend.validator;

import java.util.Map;
import java.util.Set;
import com.dietiEstates.backend.enums.EnergyClass;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;



public class RealEstateFiltersValidatorImpl implements ConstraintValidator<RealEstateFiltersValidator, Map<String,String>> 
{
    private static final Set<String> BOOLEAN_FILTERS = Set.of(
    "hasAirConditioning", "hasHeating", "hasElevator", "hasConcierge",
    "hasTerrace", "hasGarage", "hasBalcony", "hasGarden", "hasSwimmingPool");

    private static final Set<String> NUMERICAL_FILTERS = Set.of("minPrice", "maxPrice", "rooms");



    @Override
    public boolean isValid(Map<String,String> filters, ConstraintValidatorContext context) 
    {
        context.disableDefaultConstraintViolation();

        boolean hasExceptionOccurred = false; 

        for(Map.Entry<String,String> entry : filters.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();

            if (value == null || value.trim().isEmpty()) 
            {
                addViolation(context, "value for filter '" + key + "' cannot be empty or null");
                return false;
            }

            try 
            {
                if(key.equals("lat"))
                {
                    double lat = Double.valueOf(value);
    
                    if (lat < -90.0 || lat > 90.0)
                    {
                        addViolation(context, key + " value must be between -90 and 90");
                        hasExceptionOccurred = true;
                    }
                } 

                if(key.equals("lon"))
                {
                    double lon = Double.valueOf(value);
    
                    if (lon < -180.0 || lon > 180.0)
                    {
                        addViolation(context, key + " value must be between -180 and 180");
                        hasExceptionOccurred = true;
                    }                
                } 

                if(key.equals("radius"))
                {
                    double radius = Double.valueOf(value);
    
                    if (radius < 0.0 || radius > 5000.0)
                    {
                        addViolation(context, key + " value must be between 0 and 5000");
                        hasExceptionOccurred = true;
                    }
                } 


                if(NUMERICAL_FILTERS.contains(key))
                {
                    double number = Double.valueOf(value);
    
                    if (number < 0.0)
                    {
                        addViolation(context, key + " value must be greater or equal than 0");
                        hasExceptionOccurred = true;
                    }
                } 
    
                if(BOOLEAN_FILTERS.contains(key))
                {
                    if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) 
                    {
                        addViolation(context, key + " value must be true or false");
                        hasExceptionOccurred = true;
                    }
                }  
                    
                if(key.equals("energyClass"))
                {
                    EnergyClass.of(value);
                } 
    
            } 
            catch (Exception e)
            {
                addViolation(context, "Invalid value '" + value + "' for filter: " + key);
                hasExceptionOccurred = true;
            }
        }

        
        if (!hasExceptionOccurred && filters.containsKey("minPrice") && filters.containsKey("maxPrice")) 
        {
            double minPrice = Double.valueOf(filters.get("minPrice"));
            double maxPrice = Double.valueOf(filters.get("maxPrice"));
            
            if (minPrice > maxPrice) 
            {
                addViolation(context, "minPrice cannot be greater than maxPrice");
                hasExceptionOccurred = true;
            }
        }


        return !hasExceptionOccurred;
    }



    private void addViolation(ConstraintValidatorContext context, String message) 
    {        
        context.buildConstraintViolationWithTemplate(message)
               .addConstraintViolation();
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