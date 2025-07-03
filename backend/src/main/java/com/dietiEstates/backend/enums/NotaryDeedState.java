
package com.dietiEstates.backend.enums;

import com.dietiEstates.backend.extra.ValidatableEnum;



public enum NotaryDeedState implements ValidatableEnum
{
    FREE("Free"),
    OCCUPIED("Occupied"),
    BARE_PROPERTY("Bare property");



    private final String value;



    NotaryDeedState(String value) 
    {
        this.value = value;
    };

    

    public String getValue() 
    {
        return this.value;
    }
}