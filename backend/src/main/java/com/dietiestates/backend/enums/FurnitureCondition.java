
package com.dietiestates.backend.enums;

import com.dietiestates.backend.enums.common.JsonStringValueEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;


public enum FurnitureCondition implements JsonStringValueEnum
{
    NOT_FOURNISHED("Not fournished"), 
    PARTIALLY_FOURNISHED("Partially fournished"), 
    WELL_FOURNISHED("Well fournished"),
    NOT_SPECIFIED("Not specified");


    private final String value;


    private FurnitureCondition(String value) 
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
    public static FurnitureCondition fromValue(String value) 
    {
        return JsonStringValueEnum.fromValue(FurnitureCondition.class, value);
    }   
}