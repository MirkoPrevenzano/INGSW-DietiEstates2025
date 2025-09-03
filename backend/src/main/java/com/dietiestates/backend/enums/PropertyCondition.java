
package com.dietiestates.backend.enums;

import com.dietiestates.backend.enums.common.JsonStringValueEnum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum PropertyCondition implements JsonStringValueEnum
{
    UNDER_CONSTRUCTION("Under construction"), 

    NEW("New"), 

    TO_RENOVATE("To renovate"), 

    RENOVATED("Renovated"), 

    HABITABLE("Habitable"),

    GOOD("Good"), 

    EXCELLENT("Excellent"),

    NOT_SPECIFIED("Not specified");



    private final String value;



    private PropertyCondition(String value) 
    {
        this.value = value;
    }



    @Override
    @JsonValue
    public String getValue() 
    {
        return this.value;
    }

    
    @JsonCreator
    public static PropertyCondition fromValue(String value) 
    {
        return JsonStringValueEnum.fromValue(PropertyCondition.class, value);
    }   
}