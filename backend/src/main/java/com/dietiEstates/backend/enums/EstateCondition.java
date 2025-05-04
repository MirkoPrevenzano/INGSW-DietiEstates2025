
package com.dietiEstates.backend.enums;

import com.dietiEstates.backend.extra.ValidableEnum;



public enum EstateCondition implements ValidableEnum
{
    UNDER_CONSTRUCTION("Under construction"), 
    NEW("New"), 
    TO_RENOVATE("To renovate"), 
    RENOVATED("Renovated"), 
    HABITABLE("Habitable"),
    GOOD("Good"), 
    EXCELLENT("Excellent");
    

    private final String value;


    EstateCondition(String value) 
    {
        this.value = value;
    };


    public String getValue() 
    {
        return this.value;
    }
}