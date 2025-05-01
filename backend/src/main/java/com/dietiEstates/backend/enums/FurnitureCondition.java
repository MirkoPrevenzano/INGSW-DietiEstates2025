
package com.dietiEstates.backend.enums;

import com.dietiEstates.backend.extra.EnumInterface;

public enum FurnitureCondition implements EnumInterface
{
    NOT_FOURNISHED("Not fournished"), 
    PARTIALLY_FOURNISHED("Partially fournished"), 
    WELL_FOURNISHED("Well fournished");

    
    private final String value;


    FurnitureCondition(String value) 
    {
        this.value = value;
    };


    public String getValue() 
    {
        return this.value;
    }
}