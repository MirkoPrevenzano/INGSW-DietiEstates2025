
package com.dietiestates.backend.validator;

import java.util.Map;
import java.util.Set;

import com.dietiestates.backend.enums.ContractType;
import com.dietiestates.backend.enums.EnergyClass;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class RealEstateFiltersValidatorImpl implements ConstraintValidator<RealEstateFiltersValidator, Map<String,String>> 
{
    private static final String MIN_PRICE = "minPrice";

    private static final String MAX_PRICE = "maxPrice";

    private static final Set<String> BOOLEAN_FILTERS = Set.of("airConditioning", "heating", "elevator", "concierge", "terrace", "garage", 
                                                              "balcony", "garden", "swimmingPool", "isNearSchool", "isNearPark", "isNearPublicTransport");

    private static final Set<String> NUMERICAL_FILTERS = Set.of("radius", MIN_PRICE, MAX_PRICE, "rooms");



    @Override 
    public boolean isValid(Map<String,String> filters, ConstraintValidatorContext context) 
    {
        context.disableDefaultConstraintViolation();

        boolean hasExceptionOccurred = false; 

        for(Map.Entry<String,String> entry : filters.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();

            hasExceptionOccurred |= validateLatitude(key, value, context);

            hasExceptionOccurred |= validateLongitude(key, value, context);

            hasExceptionOccurred |= validateNumericalFilter(key, value, context);
    
            hasExceptionOccurred |= validateBooleanFilter(key, value, context);
                
            hasExceptionOccurred |= validateEnergyClass(key, value, context);

            hasExceptionOccurred |= validateContractType(key, value, context);
        }

        hasExceptionOccurred |= validatePriceRange(filters, context);

        return !hasExceptionOccurred;
    }



    private boolean validateLatitude(String key, String value, ConstraintValidatorContext context) 
    {
        if (key.equals("lat"))
        {
            Double lat = parseDouble(key, value, context);

            if (lat == null) return true;

            if (lat < -90.0 || lat > 90.0) 
            {
                addViolation(context, "lat value must be between -90 and 90");

                return true;
            }
        }

        return false;
    }
    

    private boolean validateLongitude(String key, String value, ConstraintValidatorContext context) 
    {
        if (key.equals("lon"))
        {
            Double lon = parseDouble(key, value, context);

            if (lon == null) return true;

            if (lon < -180.0 || lon > 180.0) 
            {
                addViolation(context, "lon value must be between -180 and 180");
                return true;
            }
        }

        return false;
    }


    private boolean validateNumericalFilter(String key, String value, ConstraintValidatorContext context) 
    {
        if (NUMERICAL_FILTERS.contains(key))
        {
            Number number;

            if (key.equals("rooms"))
                number = parseInteger(key, value, context);
            else
                number = parseDouble(key, value, context);

            if (number == null) return true;

            if (number.doubleValue() < 0.0) 
            {
                addViolation(context, key + " value must be greater or equal than 0");

                return true;
            }
        }

        return false;
    }
    

    private boolean validateBooleanFilter(String key, String value, ConstraintValidatorContext context) 
    {
        if (BOOLEAN_FILTERS.contains(key) && !value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false"))
        {
            addViolation(context, key + " value must be true or false");

            return true;
        }

        return false;
    }


    private boolean validateEnergyClass(String key, String value, ConstraintValidatorContext context) 
    {
        if (key.equals("energyClass"))
        {
            try 
            {
                EnergyClass.fromValue(value);
            } 
            catch (IllegalArgumentException e) 
            {
                addViolation(context, "Invalid value '" + value + "' for filter: " + key);
                return true;
            }
        }            

        return false;
    }


    private boolean validateContractType(String key, String value, ConstraintValidatorContext context) 
    {
        if (key.equals("type"))
        {
            try 
            {
                ContractType.fromValue(value);
            } 
            catch (IllegalArgumentException e) 
            {
                addViolation(context, "Invalid value '" + value + "' for filter: " + key);
                return true;
            }
        }            

        return false;
    }


    private boolean validatePriceRange(Map<String, String> filters, ConstraintValidatorContext context) 
    {
        if (filters.containsKey(MIN_PRICE) && filters.containsKey(MAX_PRICE)) 
        {
            double minPrice = Double.parseDouble(filters.get(MIN_PRICE));
            double maxPrice = Double.parseDouble(filters.get(MAX_PRICE));
            
            if (minPrice > maxPrice) 
            {
                addViolation(context, "minPrice cannot be greater than maxPrice");
                return true;
            }
        }

        return false;
    }


    private Double parseDouble(String key, String value, ConstraintValidatorContext context)
    {
        try 
        {
            return Double.parseDouble(value);
        } 
        catch (NumberFormatException e) 
        {
            addViolation(context, key + " must be a valid number");
            
            return null;
        }
    }


    private Integer parseInteger(String key, String value, ConstraintValidatorContext context)
    {
        try 
        {
            return Integer.parseInt(value);
        } 
        catch (NumberFormatException e) 
        {
            addViolation(context, key + " must be a valid number");
            
            return null;
        }
    }

    
    private void addViolation(ConstraintValidatorContext context, String message) 
    {        
        context.buildConstraintViolationWithTemplate(message)
               .addConstraintViolation();
    }
} 