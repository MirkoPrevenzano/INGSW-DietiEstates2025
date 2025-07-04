
package com.dietiEstates.backend.enums;

import com.dietiEstates.backend.extra.ValidatableEnum;



public enum PropertyCondition implements ValidatableEnum
{
    UNDER_CONSTRUCTION("Under construction"), 
    NEW("New"), 
    TO_RENOVATE("To renovate"), 
    RENOVATED("Renovated"), 
    HABITABLE("Habitable"),
    GOOD("Good"), 
    EXCELLENT("Excellent");
    
    

    private final String value;


    private PropertyCondition(String value) 
    {
        this.value = value;
    };


    @Override
    public String getValue() 
    {
        return this.value;
    }
}