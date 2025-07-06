
package com.dietiEstates.backend.enums;

import java.util.stream.Stream;

import com.dietiEstates.backend.extra.ValidatableEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;



public enum FurnitureCondition
{
    NOT_FOURNISHED("Not fournished"), 
    PARTIALLY_FOURNISHED("Partially fournished"), 
    WELL_FOURNISHED("Well fournished");

    

    private final String value;



    private FurnitureCondition(String value) 
    {
        this.value = value;
    };


    
    @JsonValue
    public String getValue() 
    {
        return this.value;
    }


    @JsonCreator
    static public FurnitureCondition of(String value) 
    {
        if(value == null)
            return null;

        return Stream.of(FurnitureCondition.values())
                     .filter(furnitureCondition -> furnitureCondition.getValue().equals(value))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("Furniture Condition value not valid: '" + value + "'"));
    }
}