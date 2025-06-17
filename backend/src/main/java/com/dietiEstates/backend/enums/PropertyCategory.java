
package com.dietiEstates.backend.enums;



public class PropertyCategory 
{
    UNDER_CONSTRUCTION("Under construction"), 
    NEW("New"), 
    TO_RENOVATE("To renovate"), 
    RENOVATED("Renovated"), 
    HABITABLE("Habitable"),
    GOOD("Good"), 
    EXCELLENT("Excellent");
    
    

    private final String value;


    PropertyCategory(String value) 
    {
        this.value = value;
    };


    
    public String getValue() 
    {
        return this.value;
    }
}