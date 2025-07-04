
package com.dietiEstates.backend.enums;

import com.dietiEstates.backend.extra.ValidatableEnum;



public enum FurnitureCondition implements ValidatableEnum
{
    NOT_FOURNISHED("Not fournished"), 
    PARTIALLY_FOURNISHED("Partially fournished"), 
    WELL_FOURNISHED("Well fournished");

    

    private final String value;



    private FurnitureCondition(String value) 
    {
        this.value = value;
    };


    @Override
    public String getValue() 
    {
        return this.value;
    }
}