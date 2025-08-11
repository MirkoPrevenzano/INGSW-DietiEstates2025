/* 
package com.dietiEstates.backend.enums;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum PropertyCondition 
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
    };


    @JsonValue
    public String getValue() 
    {
        return this.value;
    }

    
    @JsonCreator
    static public PropertyCondition of(String value) 
    {
        if(value == null)
            return null;

        return Stream.of(PropertyCondition.values())
                     .filter(propertyCondition -> propertyCondition.getValue().equals(value))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("Property Condition value not valid: '" + value + "'"));
    }
} */




package com.dietiEstates.backend.enums;

import com.dietiEstates.backend.enums.common.StringValueEnum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum PropertyCondition implements StringValueEnum
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
    };


    @Override
    @JsonValue
    public String getValue() 
    {
        return this.value;
    }

    @JsonCreator
    static public PropertyCondition fromValue(String value) 
    {
        return StringValueEnum.fromValue(PropertyCondition.class, value);
    }   
}